import "../users/Users.css";
import {
  FaUsers,
  FaVoteYea,
  FaBuilding,
  FaBullhorn,
  FaCalendarAlt,
  FaExclamationCircle,
} from "react-icons/fa";
import { useEffect, useState } from "react";
import analyticsService from "../../services/analyticsService";

function Reports() {
  const [analytics, setAnalytics] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    loadAnalytics();
  }, []);

  const loadAnalytics = async () => {
    try {
      setLoading(true);
      const response = await analyticsService.getAnalytics();
      setAnalytics(response.data);
      setError("");
    } catch (err) {
      console.error("Error loading analytics:", err);
      setError("Reports data load nahi ho paya. Backend chal raha hai check karein.");
    } finally {
      setLoading(false);
    }
  };

  const cards = analytics
    ? [
        {
          title: "Total Members",
          value: analytics.totalMembers,
          icon: <FaUsers />,
          color: "#2563eb",
        },
        {
          title: "Total Voters",
          value: analytics.totalVoters,
          icon: <FaVoteYea />,
          color: "#16a34a",
        },
        {
          title: "Organizations",
          value: analytics.totalOrganizations,
          icon: <FaBuilding />,
          color: "#7c3aed",
        },
        {
          title: "Campaigns",
          value: analytics.totalCampaigns,
          icon: <FaBullhorn />,
          color: "#f97316",
        },
        {
          title: "Events",
          value: analytics.totalEvents,
          icon: <FaCalendarAlt />,
          color: "#0891b2",
        },
        {
          title: "Complaints",
          value: analytics.totalComplaints,
          icon: <FaExclamationCircle />,
          color: "#dc2626",
        },
      ]
    : [];

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Reports Dashboard</h2>
      </div>

      {loading && <h3>Loading Reports...</h3>}
      {error && <p style={{ color: "#dc2626" }}>{error}</p>}

      {!loading && analytics && (
        <>
          <div className="report-grid">
            {cards.map((card, index) => (
              <div className="report-card" key={index}>
                <div className="report-icon" style={{ background: card.color }}>
                  {card.icon}
                </div>

                <div>
                  <h3>{card.value}</h3>
                  <p>{card.title}</p>
                </div>
              </div>
            ))}
          </div>

          <div className="report-table">
            <h3>Complaint Breakdown</h3>

            <table className="users-table">
              <thead>
                <tr>
                  <th>Total Complaints</th>
                  <th>Pending</th>
                  <th>Resolved</th>
                </tr>
              </thead>

              <tbody>
                <tr>
                  <td>{analytics.totalComplaints}</td>
                  <td>
                    <span className="status pending">{analytics.pendingComplaints}</span>
                  </td>
                  <td>
                    <span className="status active">{analytics.resolvedComplaints}</span>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>

          <div className="report-table">
            <h3>Feedback Summary</h3>

            <table className="users-table">
              <thead>
                <tr>
                  <th>Total Feedbacks</th>
                  <th>Average Rating</th>
                  <th>Total Notifications Sent</th>
                </tr>
              </thead>

              <tbody>
                <tr>
                  <td>{analytics.totalFeedbacks}</td>
                  <td>{analytics.averageFeedbackRating?.toFixed(1) ?? "0.0"} / 5</td>
                  <td>{analytics.totalNotifications}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </>
      )}

    </div>
  );
}

export default Reports;
