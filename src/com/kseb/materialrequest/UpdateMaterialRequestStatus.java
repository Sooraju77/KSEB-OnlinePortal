package com.kseb.materialrequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kseb.DatabaseConnection;

@WebServlet("/updatematerialrequeststatus")
public class UpdateMaterialRequestStatus extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	HttpSession session = null;
	ResultSet rs = null;
	String query = "";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			session = request.getSession();
			String usertype = (String) session.getAttribute("usertype");
			String username = (String) session.getAttribute("username");
			connection = new DatabaseConnection().getConnection();
			if (usertype.equalsIgnoreCase("Lineman")) {
				query = "select m.material_request_id from material_request m join work_allocation w on m.fk_material_request_work_allocation_id=w.work_allocation_id join employee_login_details e on w.fk_work_allocation_employee_id=e.fk_employee_login_details_employee_id where e.username=?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, username);
				rs = pstmt.executeQuery();
			} else if (usertype.equalsIgnoreCase("ElectricalEngineer")) {
				query = "select material_request_id from material_request";
				pstmt = connection.prepareStatement(query);
				rs = pstmt.executeQuery();
			}

			out.print("<html><head>");
			out.print("<title>Update Material Request Status</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/updatematerialrequeststatus.css'></head>");
			out.print("<body>");
			out.print("<div id='heading'><b>Update Material Request Status</b></div>");
			out.print("<div id='formdiv'>");
			out.print(
					"<form name='materialrequeststatusform' id='materialrequeststatusform' method='post' action='persistmaterialrequeststatus'>");
			out.print("<label>Material Request Id:</label>");
			out.print("<select name='materialrequestid' id='materialrequestid' required>");
			out.print("<option value=''>--Select--</option>");
			while (rs.next()) {
				out.print("<option value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "</option>");
			}
			out.print("</select></br></br>");
			out.print("<label>Material Request Status:</label>");
			out.print(
					"<input type='text' name='materialrequeststatus' id='materialrequeststatus' required/></br></br>");
			out.print("<label>Material Request Status Description:</label>");
			out.print(
					"<textarea name='materialrequeststatusdescription' id='materialrequeststatusdescription' rows='4' cols='50' required></textarea></br></br>");
			out.print("<input type='submit' value='Submit' id='submitbtn'/>");
			out.print("</form>");
			out.print("</div>");
			if (usertype.equalsIgnoreCase("Lineman")) {
				out.print("<div id='homediv'>Go to HomePage:<a href='linemanhome.html' id='home'>Home</a></div>");
			} else if (usertype.equalsIgnoreCase("ElectricalEngineer")) {
				out.print(
						"<div id='homediv'>Go to HomePage:<a href='electricalengineerhome.html' id='home'>Home</a></div>");
			}
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
