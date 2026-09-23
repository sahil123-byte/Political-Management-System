import "../users/Users.css";
import { useEffect, useState } from "react";
import {
  BarChart,
  Bar,
  XAxis,
  YAxis,
  CartesianGrid,
  Tooltip,
  ResponsiveContainer,
  PieChart,
  Pie,
  Cell,
  Legend,
} from "recharts";
import analyticsService from "../../services/analyticsService";

const COLORS = ["#2563eb", "#16a34a", "#f59e0b", "#dc2626", "#7c3aed", "#0891b2"];

function Analytics() {
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
      setError("Analytics data load nahi ho paya. Backend chal raha hai check karein.");
    } finally {
      setLoading(false);
    }
  };

  const barData = analytics
    ? [
        { name: "Users", value: analytics.totalUsers },
        { name: "Members", value: analytics.totalMembers },
        { name: "Voters", value: analytics.totalVoters },
        { name: "Campaigns", value: analytics.totalCampaigns },
        { name: "Events", value: analytics.totalEvents },
        { name: "Organizations", value: analytics.totalOrganizations },
      ]
    : [];

  const complaintPieData = analytics
    ? [
        { name: "Pending", value: analytics.pendingComplaints },
        { name: "Resolved", value: analytics.resolvedComplaints },
      ]
    : [];

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Analytics</h2>
      </div>

      {loading && <h3>Loading Analytics...</h3>}
      {error && <p style={{ color: "#dc2626" }}>{error}</p>}

      {!loading && analytics && (
        <>
          <div className="dashboard-grid">
            <div className="dashboard-card">
              <div className="dashboard-icon" style={{ background: "#2563eb" }}>
                <span>{analytics.totalFeedbacks}</span>
              </div>
              <div>
                <h2>{analytics.totalFeedbacks}</h2>
                <p>Total Feedbacks</p>
              </div>
            </div>

            <div className="dashboard-card">
              <div className="dashboard-icon" style={{ background: "#16a34a" }}>
                <span>★</span>
              </div>
              <div>
                <h2>{analytics.averageFeedbackRating?.toFixed(1) ?? "0.0"}</h2>
                <p>Average Rating</p>
              </div>
            </div>

            <div className="dashboard-card">
              <div className="dashboard-icon" style={{ background: "#7c3aed" }}>
                <span>{analytics.totalNotifications}</span>
              </div>
              <div>
                <h2>{analytics.totalNotifications}</h2>
                <p>Total Notifications</p>
              </div>
            </div>
          </div>

          <div className="table-container" style={{ padding: "20px" }}>
            <h3>Overview</h3>

            <ResponsiveContainer width="100%" height={320}>
              <BarChart data={barData}>
                <CartesianGrid strokeDasharray="3 3" />
                <XAxis dataKey="name" />
                <YAxis allowDecimals={false} />
                <Tooltip />
                <Bar dataKey="value" fill="#2563eb" radius={[6, 6, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>

          <div className="table-container" style={{ padding: "20px" }}>
            <h3>Complaint Status</h3>

            <ResponsiveContainer width="100%" height={300}>
              <PieChart>
                <Pie
                  data={complaintPieData}
                  dataKey="value"
                  nameKey="name"
                  cx="50%"
                  cy="50%"
                  outerRadius={100}
                  label
                >
                  {complaintPieData.map((entry, index) => (
                    <Cell key={`cell-${index}`} fill={COLORS[index % COLORS.length]} />
                  ))}
                </Pie>
                <Tooltip />
                <Legend />
              </PieChart>
            </ResponsiveContainer>
          </div>
        </>
      )}

    </div>
  );
}

export default Analytics;
