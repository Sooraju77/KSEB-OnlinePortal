package com.kseb.materialrequest;

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

@WebServlet("/sendforapproval")
public class SendForApproval extends HttpServlet {

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
			query = "select material_request_id from material_request";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>Send For Approval</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/sendforapproval.css'></head>");
			out.print("</head>");
			out.print("<body>");
			out.print("<div id='heading'>SEND FOR APPROVAL</div>");
			out.print("<div id='formdiv'>");
			out.print(
					"<form name='sendforapproval' id='sendforapproval' method='post' action='persistsendforapproval'>");
			out.print("<label>Material Request Id:</label>");
			out.print("<select name='materialrequestid' id='materialrequestid' required>");
			out.print("<option value=''>--Select--</option>");
			while (rs.next()) {
				out.print("<option value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "</option>");
			}
			out.print("</select></br></br>");
			out.print("<label>Add Remarks:</label><br>");
			out.print(
					"<textarea name='statusdescription' id='statusdescription' rows='4' cols='50' required></textarea></br></br>");
			out.print("<input type='submit' value='Send For Approval' id='submitbtn'/>");
			out.print("</form>");
			out.print("</div>");
			out.print("<div id='homediv'>Go to HomePage:<a href='materialmanagerhome.html' id='home'>Home</a></div>");
			out.print("</body>");
			out.print("</html>");

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
