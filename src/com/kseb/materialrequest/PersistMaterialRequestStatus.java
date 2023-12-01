package com.kseb.materialrequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kseb.DatabaseConnection;

@WebServlet("/persistmaterialrequeststatus")
public class PersistMaterialRequestStatus extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	PreparedStatement pstmt = null;
	String query = "";
	PrintWriter out = null;
	HttpSession session = null;
	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			session = request.getSession();
			int materialRequestId = Integer.parseInt(request.getParameter("materialrequestid"));
			String materialRequestStatus = request.getParameter("materialrequeststatus");
			String materialRequestStatusDescription = request.getParameter("materialrequeststatusdescription");

			connection = new DatabaseConnection().getConnection();
			query = "insert into material_request_status(fk_material_request_status_material_request_id,material_request_status,material_request_status_updated_date,material_request_status_updated_by,material_request_status_description) values(?,?,?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialRequestId);
			pstmt.setString(2, materialRequestStatus);
			pstmt.setDate(3, new Date(System.currentTimeMillis()));
			pstmt.setString(4, (String) session.getAttribute("usertype"));
			pstmt.setString(5, materialRequestStatusDescription);
			pstmt.executeUpdate();

			out.print("<script>alert('Material Request Status Updated Successfully')</script>");
			dis = request.getRequestDispatcher("linemanhome.html");
			dis.include(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
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
