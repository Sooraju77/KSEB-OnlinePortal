package com.kseb.complaints;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
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

@WebServlet("/persistcomplaintstatus")
public class PersistComplaintStatus extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String query = "";
	HttpSession session = null;
	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			int complaintId = Integer.parseInt(request.getParameter("complaintid"));
			String complaintStatus = request.getParameter("complaintstatus");
			String complaintDescription = request.getParameter("complaintdescription");
			session = request.getSession(false);

			connection = new DatabaseConnection().getConnection();
			query = "insert into complaint_status(fk_complaint_status_complaint_id,complaint_status_updated_date,complaint_status,fk_complaint_status_updated_by,complaint_status_description) values(?,?,?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, complaintId);
			pstmt.setDate(2, new Date(System.currentTimeMillis()));
			pstmt.setString(3, complaintStatus);
			pstmt.setString(4, (String) session.getAttribute("usertype"));
			pstmt.setString(5, complaintDescription);
			pstmt.executeUpdate();

			out.print("<script>alert('Complaint status updated successfully')</script>");
			dis = request.getRequestDispatcher("adminhome.html");
			dis.include(request, response);

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
