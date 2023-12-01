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

@WebServlet("/editworkallocation")
public class EditWorkAllocation extends HttpServlet {

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
			int workAllocationId = Integer.parseInt(request.getParameter("allocationid"));
			connection = new DatabaseConnection().getConnection();
			query = "select fk_employee_usertype_employee_id from employee_usertype where fk_employee_usertype_user_type_id=2";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>Edit Allcoation</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/editworkallocation.css'></head>");
			out.print("<body>");
			out.print("<div id='heading'><b>Edit Work Allocation</b></div>");
			out.print("<div id='formdiv'>");
			out.print("<form name='editallocation' id='editallocation' method='post' action='persisteditallocation'>");
			out.print("<label>Work Allocation Id:</label>");
			out.print("<input type='number' name='allocationid' value='" + workAllocationId
					+ "' readonly='readonly'><br><br>");
			out.print("<label>Lineman Id:</label>");
			out.print("<select name='linemanno' id='linemanno' required>");
			out.print("<option value=''>--Select--</option>");
			while (rs.next()) {
				out.print("<option value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "</option>");
			}
			out.print("</select></br></br>");
			out.print("<input type='submit' value='Save' id='savebtn'/></form></div>");
			out.print("<div id='home'>Go to HomePage:<a href='adminhome.html'>Home</a></div></body></html>");

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
