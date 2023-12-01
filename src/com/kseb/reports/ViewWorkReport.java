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

@WebServlet("/displayworkreport")
public class ViewWorkReport extends HttpServlet {

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
			usertype = (String) session.getAttribute("usertype");
			startDate = Date.valueOf(request.getParameter("startdate"));
			endDate = Date.valueOf(request.getParameter("enddate"));

			connection = new DatabaseConnection().getConnection();
			if (usertype.equalsIgnoreCase("Admin") || usertype.equalsIgnoreCase("ElectricalEngineer")) {
				query = "select wa.work_allocation_id,wa.fk_work_allocation_complaint_id,cd.complaint_description,wa.fk_work_allocation_employee_id,wa.work_allocated_date,ws.work_status,ws.work_status_updated_date,ws.work_status_description,ws.work_status_updated_by from work_allocation wa left join work_status ws on wa.work_allocation_id=ws.fk_work_status_work_allocation_id join complaint_details cd on wa.fk_work_allocation_complaint_id=cd.complaint_id where wa.work_allocated_date between ? and ?";
				pstmt = connection.prepareStatement(query);
				pstmt.setDate(1, startDate);
				pstmt.setDate(2, endDate);
				rs = pstmt.executeQuery();
			} else if (usertype.equalsIgnoreCase("Lineman")) {
				query = "select wa.work_allocation_id,wa.fk_work_allocation_complaint_id,cd.complaint_description,wa.fk_work_allocation_employee_id,wa.work_allocated_date,ws.work_status,ws.work_status_updated_date,ws.work_status_description,ws.work_status_updated_by from work_allocation wa left join work_status ws on wa.work_allocation_id=ws.fk_work_status_work_allocation_id join complaint_details cd on wa.fk_work_allocation_complaint_id=cd.complaint_id join employee_login_details eld on wa.fk_work_allocation_employee_id=eld.fk_employee_login_details_employee_id where eld.username=? and wa.work_allocated_date between ? and ?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, (String) session.getAttribute("username"));
				pstmt.setDate(2, startDate);
				pstmt.setDate(3, endDate);
				rs = pstmt.executeQuery();
			}

			out.print("<html><head><title>::Work Report::</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/viewworkreport.css'></head>");
			out.print("</head>");
			out.print("<body><div id='heading'>WORK REPORT</div>");
			out.print("<div id='tablediv'><table id='worktable'>");
			out.print("<tr><th>Allocation Id</th>");
			out.print("<th>Complaint Id</th>");
			out.print("<th>Complaint Description</th>");
			out.print("<th>Employee Id</th>");
			out.print("<th>Work Allocated Date</th>");
			out.print("<th>Work Status</th>");
			out.print("<th>Status Updated Date</th>");
			out.print("<th>Status Updated By</th>");
			out.print("<th>Status Description</th>");

			while (rs.next()) {
				out.print("<tr><td>" + rs.getInt(1) + "</td>");
				out.print("<td>" + rs.getInt(2) + "</td>");
				out.print("<td>" + rs.getString(3) + "</td>");
				out.print("<td>" + rs.getInt(4) + "</td>");
				out.print("<td>" + rs.getDate(5) + "</td>");
				out.print("<td>" + rs.getString(6) + "</td>");
				out.print("<td>" + rs.getDate(7) + "</td>");
				out.print("<td>" + rs.getString(9) + "</td>");
				out.print("<td>" + rs.getString(8) + "</td>");
				out.print("</tr>");

			}
			out.print("</table></div>");
			if (usertype.equalsIgnoreCase("Admin")) {
				out.print("<div id='homediv'>Go to HomePage:<a href='adminhome.html' id='home'>Home</a></div>");
			} else if (usertype.equalsIgnoreCase("ElectricalEngineer")) {
				out.print(
						"<div id='homediv'>Go to HomePage:<a href='electricalengineerhome.html' id='home'>Home</a></div>");
			} else if (usertype.equalsIgnoreCase("Lineman")) {
				out.print("<div id='homediv'>Go to HomePage:<a href='linemanhome.html' id='home'>Home</a></div>");
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
