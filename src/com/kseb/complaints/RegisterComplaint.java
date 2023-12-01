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

@WebServlet("/registercomplaint")
public class RegisterComplaint extends HttpServlet {

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
			query = "select consumer_kseb_id from consumer_kseb";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>Complaint Registration</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/registercomplaint.css'></head>");
			out.print("<body>");
			out.print("<div id='compheading'><b>New Complaint Registration</b></div>");
			out.print("<div id='formdiv'>");
			out.print("<form name='complaint' id='complaint' method='post' action='persistcomplaint'>");
			out.print("<label>Consumer Id</label>");
			out.print("<select name='consumerksebid' id='consumerid' required>");
			out.print("<option value=''>--Select--</option>");
			while (rs.next()) {
				out.print("<option value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "</option>");
			}
			out.print("</select></br></br>");
			out.print("<label>Complaint Description</label><br>");
			out.print(
					"<textarea name='complaintdetails' id='complaintdetails' rows='4' cols='50' placeholder='Please enter the complaint details...' required></textarea><br><br>");
			out.print("<label>Date</label>");
			out.print("<input type='date' name='complaintregdate' id='complaintregdate' required/><br><br><br>");
			out.print("<input type='submit' value='Submit' id='submitbutton'/></form></div>");
			out.print("	<div id='home'>Go to HomePage:<a href='adminhome.html'>Home</a></div></body></html>");

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
