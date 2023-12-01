package com.kseb.complaints;

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

@WebServlet("/viewcomplaints")
public class ViewComplaints extends HttpServlet {

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
			query = "select d.complaint_id,d.fk_complaint_details_consumer_kseb_id,d.complaint_description,d.complaint_logged_date,c.complaint_status,c.complaint_status_updated_date,c.fk_complaint_status_updated_by,c.complaint_status_description FROM complaint_details d left join complaint_status c on d.complaint_id=c.fk_complaint_status_complaint_id ";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head><title>::View Complaints::</title><style>");
			out.print(
					"body{background-image: url('images/complaintregbg.jpg');background-repeat: no-repeat;background-size: cover;}");
			out.print("#complainttable{color:white;border: 1px solid #cd5c5c;background-color: black}");
			out.print("td,th{;text-align:center;font-size:18px;padding:8px;}");
			out.print("td a{color:white}");
			out.print("#tablediv{margin-top:120px;}");
			out.print("#heading{color:white;text-align:center;font-family: garamond;font-size: 40px;}");
			out.print("tr{padding:10px}");
			out.print(
					"#homediv{color: white;text-align: center;margin-top: 90px;font-family: garamond;font-size: 18px;}");
			out.print("#home{color:white}");
			out.print("</style></head>");
			out.print("<body><div id='heading'>Complaints List</div>");
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
				out.print("<td>" + rs.getString(8) + "</td>");
				out.print("<td><a href='updatecomplaintdetails?complaintid=" + rs.getInt(1) + "&consumerksebid="
						+ rs.getInt(2) + "'>Update</a></td>");
				out.print("<td><a href='deletecomplaintdetails?complaintid=" + rs.getInt(1) + "'>Delete</a></td></tr>");
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
