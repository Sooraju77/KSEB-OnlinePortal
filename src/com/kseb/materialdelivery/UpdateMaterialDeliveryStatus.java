package com.kseb.materialdelivery;

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
import javax.servlet.http.HttpSession;

import com.kseb.DatabaseConnection;

@WebServlet("/updatedeliverystatus")
public class UpdateMaterialDeliveryStatus extends HttpServlet {

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
			query = "select material_delivery_id from material_delivery";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>Update Material Delivery Status</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/updatematerialdeliverystatus.css'></head>");
			out.print("<body>");
			out.print("<div id='heading'><b>Update Material Delivery Status</b></div>");
			out.print("<div id='formdiv'>");
			out.print(
					"<form name='materialdeliverystatusform' id='materialdeliverystatusform' method='post' action='persistmaterialdeliverystatus'>");
			out.print("<label>Material Delivery Id:</label>");
			out.print("<select name='materialdeliveryid' id='materialdeliveryid' required>");
			out.print("<option value=''>--Select--</option>");
			while (rs.next()) {
				out.print("<option value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "</option>");
			}
			out.print("</select></br></br>");
			out.print("<label>Material Delivery Status:</label>");
			out.print(
					"<input type='text' name='materialdeliverystatus' id='materialdeliverystatus' required/></br></br>");
			out.print("<label>Material Delivery Status Description:</label>");
			out.print(
					"<textarea name='materialdeliverystatusdescription' id='materialdeliverystatusdescription' rows='4' cols='50' required></textarea></br></br>");
			out.print("<input type='submit' value='Submit' id='submitbtn'/>");
			out.print("</form>");
			out.print("</div>");

			out.print("<div id='homediv'>Go to HomePage:<a href='materialmanagerhome.html' id='home'>Home</a></div>");

			out.print("</body>");
			out.print("</html>");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
