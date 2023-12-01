package com.kseb.workallocations;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kseb.DatabaseConnection;

@WebServlet("/viewallworkallocations")
public class ViewAllWorkAllocations extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String query = "";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			connection = new DatabaseConnection().getConnection();
			query = "select wa.work_allocation_id,wa.fk_work_allocation_complaint_id,cd.complaint_description,wa.fk_work_allocation_employee_id,wa.work_allocated_date,ws.work_status,ws.work_status_updated_date,ws.work_status_description,ws.work_status_updated_by from work_allocation wa left join work_status ws on wa.work_allocation_id=ws.fk_work_status_work_allocation_id join complaint_details cd on wa.fk_work_allocation_complaint_id=cd.complaint_id;";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head><title>::View Allocations::</title><style>");
			out.print(
					"body{background-image: url('images/allocatework.jpg');background-repeat: no-repeat;background-size: cover;}");
			out.print("#allocationtable{color:white;border: 2px solid #cd5c5c;background-color: black}");
			out.print("td,th{;text-align:center;font-size:18px;padding:8px;}");
			out.print("#tablediv{margin-top:120px;}");
			out.print("#heading{color:white;text-align:center;font-family: garamond;font-size: 40px;}");
			out.print("tr{padding:10px}");
			out.print("td a{color:white}");
			out.print(
					"#homediv{color: white;text-align: center;margin-top: 90px;font-family: garamond;font-size: 18px;}");
			out.print("#home{color:white}");
			out.print("</style></head>");
			out.print("<body><div id='heading'>Work Allocations</div>");
			out.print("<div id='tablediv'><table id='allocationtable'>");
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
				out.print("<td><a href='editworkallocation?allocationid=" + rs.getInt(1) + "'>Edit</a></td></tr>");

			}
			out.print("</table></div>");
			out.print("<div id='homediv'>Go to HomePage:<a href='adminhome.html' id='home'>Home</a></div>");
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
