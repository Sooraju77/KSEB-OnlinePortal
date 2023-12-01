package com.kseb.consumers;

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

@WebServlet("/ViewConsumers")
public class ViewConsumers extends HttpServlet {

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
			query = "select cp.consumer_id,cp.consumer_firstname,cp.consumer_lastname,cp.consumer_phone_no,cp.consumer_email_id,cp.consumer_city,cp.consumer_pincode,ck.consumer_kseb_meter_no,ck.consumer_kseb_post_no,ck.consumer_kseb_connection_type from consumer_personal_details cp join consumer_kseb ck on cp.consumer_id=ck.fk_consumer_kseb_consumer_id ";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head><title>View Consumers</title><style>");
			out.print(
					"body{background-image: url('images/addconsumer.jpg');background-repeat: no-repeat;background-size: cover;}");
			out.print("#consumertable{color:white;border: 1px solid #cd5c5c;background-color: black}");
			out.print("td,th{;text-align:center;font-size:18px;padding:8px;}");
			out.print("#tablediv{margin-top:120px;margin-left:200px;}");
			out.print("#heading{color:white;text-align:center;font-family: garamond;font-size: 40px;}");
			out.print("tr{padding:10px}");
			out.print(
					"#homediv{color: white;text-align: center;margin-top: 90px;font-family: garamond;font-size: 18px;}");
			out.print("#home{color:white}");
			out.print("</style></head>");
			out.print("<body><div id='heading'>Consumer List</div>");
			out.print("<div id='tablediv'><table id='consumertable'>");
			out.print("<tr><th>Consumer Id</th>");
			out.print("<th>First Name</th>");
			out.print("<th>Last Name</th>");
			out.print("<th>Phone No</th>");
			out.print("<th>Email Id</th>");
			out.print("<th>City</th>");
			out.print("<th>Pincode</th>");
			out.print("<th>Meter No</th>");
			out.print("<th>Post No</th>");
			out.print("<th>Connection Type</th></tr>");

			while (rs.next()) {
				out.print("<tr><td>" + rs.getInt(1) + "</td>");
				out.print("<td>" + rs.getString(2) + "</td>");
				out.print("<td>" + rs.getString(3) + "</td>");
				out.print("<td>" + rs.getString(4) + "</td>");
				out.print("<td>" + rs.getString(5) + "</td>");
				out.print("<td>" + rs.getString(6) + "</td>");
				out.print("<td>" + rs.getInt(7) + "</td>");
				out.print("<td>" + rs.getInt(8) + "</td>");
				out.print("<td>" + rs.getInt(9) + "</td>");
				out.print("<td>" + rs.getString(10) + "</td></tr>");

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
