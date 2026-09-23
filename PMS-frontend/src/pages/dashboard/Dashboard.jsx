import { useEffect, useState } from "react";
import "../users/Users.css";
import {
  FaUsers,
  FaUserFriends,
  FaVoteYea,
  FaBullhorn,
  FaCalendarAlt,
  FaExclamationCircle,
} from "react-icons/fa";

import dashboardService from "../../services/dashboardService";
import memberService from "../../services/memberService";
import campaignService from "../../services/campaignService";
import complaintService from "../../services/complaintService";

function Dashboard() {
  const [summary, setSummary] = useState(null);
  const [recentMembers, setRecentMembers] = useState([]);
  const [campaigns, setCampaigns] = useState([]);
  const [complaints, setComplaints] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    const loadDashboard = async () => {
      try {
        setLoading(true);

        const [summaryRes, membersRes, campaignsRes, complaintsRes] =
          await Promise.all([
            dashboardService.getSummary(),
            memberService.getAll(),
            campaignService.getAll(),
            complaintService.getAll(),
          ]);

        setSummary(summaryRes.data);
        setRecentMembers((membersRes.data || []).slice(-5).reverse());
        setCampaigns((campaignsRes.data || []).slice(-5).reverse());
        setComplaints((complaintsRes.data || []).slice(-5).reverse());
        setError("");
      } catch (err) {
        console.error("Dashboard load error:", err);
        setError("Dashboard data could not be loaded. Check if the backend is running.");
      } finally {
        setLoading(false);
      }
    };

    loadDashboard();
  }, []);

  const stats = summary
    ? [
        {
          title: "Total Users",
          value: summary.totalUsers,
          icon: <FaUsers />,
          color: "#2563eb",
        },
        {
          title: "Members",
          value: summary.totalMembers,
          icon: <FaUserFriends />,
          color: "#16a34a",
        },
        {
          title: "Voters",
          value: summary.totalVoters,
          icon: <FaVoteYea />,
          color: "#7c3aed",
        },
        {
          title: "Campaigns",
          value: summary.totalCampaigns,
          icon: <FaBullhorn />,
          color: "#ea580c",
        },
        {
          title: "Events",
          value: summary.totalEvents,
          icon: <FaCalendarAlt />,
          color: "#0891b2",
        },
        {
          title: "Complaints",
          value: summary.totalComplaints,
          icon: <FaExclamationCircle />,
          color: "#dc2626",
        },
      ]
    : [];

  if (loading) {
    return (
      <div className="users-page">
        <div className="users-header">
          <h2>Dashboard</h2>
        </div>
        <p>Loading dashboard...</p>
      </div>
    );
  }

  return (
    <div className="users-page">

      <div className="users-header">
        <h2>Dashboard</h2>
      </div>

      {error && <p style={{ color: "#dc2626" }}>{error}</p>}

      <div className="dashboard-grid">

        {stats.map((item, index) => (
          <div className="dashboard-card" key={index}>
            <div
              className="dashboard-icon"
              style={{ background: item.color }}
            >
              {item.icon}
            </div>

            <div>
              <h2>{item.value}</h2>
              <p>{item.title}</p>
            </div>
          </div>
        ))}

      </div>

      <div className="table-container">

        <h3>Recent Members</h3>

        <table className="users-table">

          <thead>
            <tr>
              <th>Name</th>
              <th>Designation</th>
              <th>Booth</th>
              <th>Mobile</th>
            </tr>
          </thead>

          <tbody>

            {recentMembers.length === 0 ? (
              <tr>
                <td colSpan={4}>No members found</td>
              </tr>
            ) : (
              recentMembers.map((member) => (
                <tr key={member.id}>
                  <td>{member.name}</td>
                  <td>{member.designation || "-"}</td>
                  <td>{member.boothName || "-"}</td>
                  <td>{member.mobile}</td>
                </tr>
              ))
            )}

          </tbody>

        </table>

      </div>

      <div className="table-container">

        <h3>Recent Campaigns</h3>

        <table className="users-table">

          <thead>
            <tr>
              <th>Name</th>
              <th>Party</th>
              <th>Start Date</th>
              <th>Status</th>
            </tr>
          </thead>

          <tbody>

            {campaigns.length === 0 ? (
              <tr>
                <td colSpan={4}>No campaigns found</td>
              </tr>
            ) : (
              campaigns.map((campaign) => (
                <tr key={campaign.id}>
                  <td>{campaign.campaignName}</td>
                  <td>{campaign.partyName || "-"}</td>
                  <td>{campaign.startDate || "-"}</td>
                  <td>
                    <span
                      className={
                        campaign.status ? "status active" : "status inactive"
                      }
                    >
                      {campaign.status ? "Active" : "Inactive"}
                    </span>
                  </td>
                </tr>
              ))
            )}

          </tbody>

        </table>

      </div>

      <div className="table-container">

        <h3>Recent Complaints</h3>

        <table className="users-table">

          <thead>
            <tr>
              <th>Complaint</th>
              <th>Member</th>
              <th>Status</th>
            </tr>
          </thead>

          <tbody>

            {complaints.length === 0 ? (
              <tr>
                <td colSpan={3}>No complaints found</td>
              </tr>
            ) : (
              complaints.map((complaint) => (
                <tr key={complaint.id}>
                  <td>{complaint.complaintTitle}</td>
                  <td>{complaint.memberName || "-"}</td>
                  <td>
                    <span
                      className={
                        complaint.complaintStatus === "Resolved"
                          ? "status active"
                          : "status pending"
                      }
                    >
                      {complaint.complaintStatus}
                    </span>
                  </td>
                </tr>
              ))
            )}

          </tbody>

        </table>

      </div>

    </div>
  );
}

export default Dashboard;
