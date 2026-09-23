# Notification System - Architecture Notes

This backend implements the **event-driven pattern** from the architecture
diagram (Outbox → Kafka → multi-channel dispatch → user preferences)
inside this one Spring Boot app, rather than as separate microservices.
That keeps it something you can actually run and test locally. Where the
diagram called for infrastructure this app doesn't run (Debezium, Redis,
ELK, Vault, a separate Config/Reporting/Template service), the note below
says what was used instead and why.

## How a notification flows through the system

1. **`POST /api/notifications`** (`NotificationController` →
   `NotificationService`) still works exactly as before - it creates a row
   in the `notifications` table (what the frontend's bell icon reads).
2. In the **same database transaction**, it now also writes a row to
   **`notifications_outbox`** (`NotificationOutbox` entity). This is the
   Transactional Outbox pattern: the event can never go missing because
   it's saved atomically with the notification itself.
3. **`NotificationOutboxPublisher`** polls that table every 5 seconds
   (`@Scheduled`), and for each unpublished row, publishes one message to
   each of three Kafka topics: `notifications.email`, `notifications.sms`,
   `notifications.inapp`. Only after every send succeeds is the row marked
   `published = true`. If Kafka is unreachable, the row is simply left
   unpublished and retried on the next poll - nothing is lost, and the
   `POST` call above never fails because of this.
4. **`NotificationEventConsumer`** has one `@KafkaListener` per topic. For
   each message, it looks up whether the recipient has a `User` login
   linked to the `Member` the notification was addressed to, and if so
   checks `UserNotificationPreference` for that channel - a channel the
   user has explicitly turned off is skipped.
5. If the channel is enabled (or there's no preference row - the default
   is opted-in), the event is handed to that channel's **`ChannelProvider`**
   (`EmailChannelProvider` / `SmsChannelProvider` / `InAppChannelProvider`).

## What's real vs simulated

| Diagram piece | This app |
|---|---|
| Notification Service | `NotificationService` + `NotificationController` (existing) |
| Notifications Outbox (transactional) | `NotificationOutbox` entity/table - real, and actually transactional |
| Debezium CDC | **Simulated** by `NotificationOutboxPublisher`'s poller. Real Debezium needs a separate Kafka Connect deployment watching the DB's binlog - that's an ops/infra task, not application code, so a polling publisher (a well-known lightweight alternative to CDC) stands in for it here |
| Kafka Cluster + topics | Real - needs an actual broker reachable at `spring.kafka.bootstrap-servers` (see below). Topic set is simplified to one topic per channel + a DLQ, rather than the full priority × channel matrix in the diagram |
| Channel Providers (Twilio/SendGrid/FCM) | **Simulated** - `EmailChannelProvider`/`SmsChannelProvider`/`InAppChannelProvider` just log what they would send. Swap in a real HTTP call to Twilio/SendGrid inside those classes once you have API keys; nothing else needs to change |
| Template Service + Templates DB | `NotificationTemplate` entity + `NotificationTemplateService`/`NotificationTemplateController` (`/api/notification-templates`) - a real CRUD module in this app, not a separate service. `NotificationTemplateService.render()` fills `{{placeholder}}` tokens |
| User Preference Service/Cache | `UserNotificationPreference` entity + `UserNotificationPreferenceService`/`Controller` (`/api/notification-preferences/me`) - a real DB-backed table, read directly instead of through a Redis cache in front of it |
| Reporting Service | Not built - this app already has a separate `Reports`/`Analytics` module; notification-specific reporting wasn't added to keep scope reasonable |
| Redis Cache, Object Storage, ELK, Prometheus, Jaeger, Config/Secrets Management | **Not implemented.** These are deployment/ops infrastructure, not application code - they'd be set up alongside the app in a real environment, not written as Java classes |

## Running it locally

The REST APIs (creating notifications, managing templates/preferences)
work with **no Kafka running at all** - only the async fan-out needs it.

To see the full pipeline (including the EMAIL/SMS/INAPP log lines from the
channel providers), start a local broker:

```bash
docker compose up -d
```

(`docker-compose.yml` in this repo runs a single-node Kafka broker in
KRaft mode on `localhost:9092` - no Zookeeper needed.)

Then create a notification as usual through the app. Within ~5 seconds
you should see log lines like:

```
[EMAIL] To member 3: "Event Reminder" - ...
[SMS] To member 3: ...
[INAPP] Notification 42 ready for member 3 in the notifications table.
```

To point at a different broker (e.g. a shared dev Kafka), set
`KAFKA_BOOTSTRAP_SERVERS` as an environment variable instead of editing
`application.properties`.

## New endpoints

- `GET /api/notification-templates`, `POST`, `PUT /{id}`, `DELETE /{id}`
  (ADMIN/MANAGER; delete is ADMIN-only) - manage reusable message templates.
- `GET /api/notification-preferences/me`, `PUT /api/notification-preferences/me`
  (any logged-in user) - a user's own channel opt-in/out, body:
  `{ "channelType": "EMAIL", "isEnabled": false }`.
