package com.kseb.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kseb.DatabaseConnection;

@WebServlet("/viewcompreport")
public class ViewComplaintReport extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	HttpSession session = null;
	String query = "";
	Date startDate;
	Date endDate;
	String usertype;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			session = request.getSession();
			String usertype = (String) session.getAttribute("usertype");
			startDate = Date.valueOf(request.getParameter("startdate"));
			endDate = Date.valueOf(request.getParameter("enddate"));

			connection = new DatabaseConnection().getConnection();
			query = "select d.complaint_id,d.fk_complaint_details_consumer_kseb_id,d.complaint_description,d.complaint_logged_date,c.complaint_status,c.complaint_status_updated_date,c.fk_complaint_status_updated_by,c.complaint_status_description FROM complaint_details d left join complaint_status c on d.complaint_id=c.fk_complaint_status_complaint_id where d.complaint_logged_date between ? and ?";
			pstmt = connection.prepareStatement(query);
			pstmt.setDate(1, startDate);
			pstmt.setDate(2, endDate);
			rs = pstmt.executeQuery();

			out.print("<html><head><title>::Complaint Report::</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/viewcomplaintreport.css'></head>");
			out.print("</head>");
			out.print("<body><div id='heading'><b>Complaints Report</b></div>");
			out.print("<div id='tablediv'><table id='complainttable'>");
			out.print("<tr><th>Complaint Id</th>");
			out.print("<th>Consumer Kseb Id</th>");
			out.print("<th>Complaint Description</th>");
			out.print("<th>Complaint Registered Date</th>");
			out.print("<th>Complaint Status</th>");
			out.print("<th>Status Updated Date</th>");
			out.print("<th>Status Updated By</th>");
			out.print("<th>Status Description</th></tr>");

			while (rs.next()) {
				out.print("<tr><td>" + rs.getInt(1) + "</td>");
				out.print("<td>" + rs.getInt(2) + "</td>");
				out.print("<td>" + rs.getString(3) + "</td>");
				out.print("<td>" + rs.getDate(4) + "</td>");
				out.print("<td>" + rs.getString(5) + "</td>");
				out.print("<td>" + rs.getDate(6) + "</td>");
				out.print("<td>" + rs.getString(7) + "</td>");
				out.print("<td>" + rs.getString(8) + "</td></tr>");
			}
			out.print("</table></div>");
			if (usertype.equalsIgnoreCase("Admin")) {
				out.print("<div id='homediv'>Go to HomePage:<a href='adminhome.html' id='home'>Home</a></div>");
			} else if (usertype.equalsIgnoreCase("ElectricalEngineer")) {
				out.print(
						"<div id='homediv'>Go to HomePage:<a href='electricalengineerhome.html' id='home'>Home</a></div>");
			}
			out.print("</body></html>");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			try {
				connection.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
