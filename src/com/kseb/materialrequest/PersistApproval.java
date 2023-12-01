package com.kseb.materialrequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kseb.DatabaseConnection;

@WebServlet("/persistapproval")
public class PersistApproval extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String query = "";
	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			connection = new DatabaseConnection().getConnection();
			query = "insert into material_request_status(fk_material_request_status_material_request_id,material_request_status,material_request_status_updated_date,material_request_status_updated_by,material_request_status_description) values(?,?,?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, Integer.parseInt(request.getParameter("materialrequestid")));
			pstmt.setString(2, "Approved");
			pstmt.setDate(3, new Date(System.currentTimeMillis()));
			pstmt.setString(4, "ElectricalEngineer");
			pstmt.setString(5, request.getParameter("statusdescription"));
			pstmt.executeUpdate();

			out.print("<script>alert('Material Request Approved')</script>");
			dis = request.getRequestDispatcher("electricalengineerhome.html");
			dis.include(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

}
