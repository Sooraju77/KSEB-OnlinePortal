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
import javax.servlet.http.HttpSession;

import com.kseb.DatabaseConnection;

@WebServlet("/creatematerialrequest")
public class CreateMaterialRequest extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	String query = "";
	ResultSet rs = null;
	HttpSession session = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			connection = new DatabaseConnection().getConnection();
			session = request.getSession();
			String username = (String) session.getAttribute("username");
			query = "select w.work_allocation_id from work_allocation w join employee_login_details e on w.fk_work_allocation_employee_id=e.fk_employee_login_details_employee_id where e.username=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, username);
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>Create Material Request</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/creatematerialrequest.css'></head>");
			out.print("<body>");
			out.print("<div id='heading'><b>Create Material Request</b></div>");
			out.print("<div id='formdiv'>");
			out.print(
					"<form name='materialrequest' id='materialrequest' method='post' action='persistmaterialrequest'>");
			out.print("<label>Date:</label>");
			out.print("<input type='date' name='materialrequestdate' id='materialrequestdate' required/><br><br>");
			out.print("<label>Work Allocation Id:</label>");
			out.print("<select name='workallocationno' id='workallocationno' required>");
			out.print("<option value=''>--Select--</option>");
			while (rs.next()) {
				out.print("<option value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "</option>");
			}
			out.print("</select></br></br>");

			query = "select material_item_id from material_item";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<label>Material Id:</label>");
			out.print("<select name='materialid' id='materialid' required>");
			out.print("<option value=''>--Select--</option>");
			while (rs.next()) {
				out.print("<option value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "</option>");
			}
			out.print("</select></br></br>");
			out.print("<label>Material Quantity:</label>");
			out.print("<input type='number' name='materialquantity' id='materialquantity'/><br><br>");
			out.print("<input type='submit' value='Save' id='savebtn'/></form></div>");
			out.print("<div id='home'>Go to HomePage:<a href='linemanhome.html'>Home</a></div></body></html>");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
