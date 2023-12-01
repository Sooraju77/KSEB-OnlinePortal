package com.kseb.complaints;

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

import com.kseb.DatabaseConnection;

@WebServlet("/persistupdatecomplaint")
public class PersistUpdateComplaintDetails extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	RequestDispatcher dis = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	String query = "";

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			int complaintId = Integer.parseInt(request.getParameter("complaintid"));
			connection = new DatabaseConnection().getConnection();
			query = "update complaint_details set complaint_description=?,complaint_logged_date=? where complaint_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, request.getParameter("complaintdetails"));
			pstmt.setDate(2, Date.valueOf(request.getParameter("complaintregdate")));
			pstmt.setInt(3, complaintId);
			pstmt.executeUpdate();

			out.print("<script>alert('Complaint Details Updated Successfully')</script>");
			dis = request.getRequestDispatcher("adminhome.html");
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
