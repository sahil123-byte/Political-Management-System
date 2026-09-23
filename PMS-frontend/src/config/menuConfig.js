import {
  FaTachometerAlt,
  FaUsers,
  FaUserShield,
  FaBuilding,
  FaFlag,
  FaMapMarkedAlt,
  FaVoteYea,
  FaBullhorn,
  FaCalendarAlt,
  FaExclamationCircle,
  FaChartBar,
  FaChartLine,
  FaBell,
  FaCommentDots,
  FaClipboardCheck,
  FaUserCircle,
  FaCog,
} from "react-icons/fa";

import Dashboard from "../pages/dashboard/Dashboard";
import Users from "../pages/users/Users";
import Roles from "../pages/roles/Roles";
import Organization from "../pages/organization/Organization";
import Party from "../pages/party/Party";
import State from "../pages/state/State";
import District from "../pages/district/District";
import Constituency from "../pages/constituency/Constituency";
import Booths from "../pages/booths/Booths";
import Members from "../pages/members/Members";
import Voters from "../pages/voters/Voters";
import Campaigns from "../pages/campaigns/Campaigns";
import Events from "../pages/events/Events";
import Complaints from "../pages/complaints/Complaints";
import Reports from "../pages/reports/Reports";
import Analytics from "../pages/analytics/Analytics";
import Notifications from "../pages/notifications/Notifications";
import Feedback from "../pages/feedback/Feedback";
import EventAttendance from "../pages/eventAttendance/EventAttendance";
import Profile from "../pages/profile/Profile";
import Settings from "../pages/settings/Settings";

export const menuItems = [
  {
    path: "/dashboard",
    name: "Dashboard",
    icon: FaTachometerAlt,
    component: Dashboard,
  },
  {
    path: "/users",
    name: "Users",
    icon: FaUsers,
    component: Users,
    roles: ["ADMIN", "MANAGER"],
  },
  {
    path: "/roles",
    name: "Roles",
    icon: FaUserShield,
    component: Roles,
    roles: ["ADMIN"],
  },
  {
    path: "/organization",
    name: "Organization",
    icon: FaBuilding,
    component: Organization,
  
    roles: ["ADMIN", "MANAGER"],
  },
  {
    path: "/party",
    name: "Political Party",
    icon: FaFlag,
    component: Party,
  
    roles: ["ADMIN", "MANAGER"],
  },
  {
    path: "/state",
    name: "State",
    icon: FaMapMarkedAlt,
    component: State,
  
    roles: ["ADMIN", "MANAGER"],
  },
  {
    path: "/district",
    name: "District",
    icon: FaMapMarkedAlt,
    component: District,
  
    roles: ["ADMIN", "MANAGER"],
  },
  {
    path: "/constituency",
    name: "Constituency",
    icon: FaMapMarkedAlt,
    component: Constituency,
  
    roles: ["ADMIN", "MANAGER"],
  },
  {
    path: "/booths",
    name: "Booths",
    icon: FaMapMarkedAlt,
    component: Booths,
  
    roles: ["ADMIN", "MANAGER"],
  },
  {
    path: "/members",
    name: "Members",
    icon: FaUsers,
    component: Members,
  },
  {
    path: "/voters",
    name: "Voters",
    icon: FaVoteYea,
    component: Voters,
  },
  {
    path: "/campaigns",
    name: "Campaigns",
    icon: FaBullhorn,
    component: Campaigns,
  },
  {
    path: "/events",
    name: "Events",
    icon: FaCalendarAlt,
    component: Events,
  },
  {
    path: "/complaints",
    name: "Complaints",
    icon: FaExclamationCircle,
    component: Complaints,
  },
  {
    path: "/reports",
    name: "Reports",
    icon: FaChartBar,
    component: Reports,
  },
  {
    path: "/analytics",
    name: "Analytics",
    icon: FaChartLine,
    component: Analytics,
  
    roles: ["ADMIN", "MANAGER"],
  },
  {
    path: "/notifications",
    name: "Notifications",
    icon: FaBell,
    component: Notifications,
  },
  {
    path: "/feedback",
    name: "Feedback",
    icon: FaCommentDots,
    component: Feedback,
  },
  {
    path: "/event-attendance",
    name: "Event Attendance",
    icon: FaClipboardCheck,
    component: EventAttendance,
  },
  {
    path: "/profile",
    name: "My Profile",
    icon: FaUserCircle,
    component: Profile,
  },
  {
    path: "/settings",
    name: "Settings",
    icon: FaCog,
    component: Settings,
  },
];