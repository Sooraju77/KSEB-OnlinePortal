package com.kseb.workallocations;

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

@WebServlet("/persistworkstatus")
public class PersistWorkStatus extends HttpServlet {

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
			int workAllocationId = Integer.parseInt(request.getParameter("allocationid"));
			String workStatus = request.getParameter("workstatus");
			String workStatusDescription = request.getParameter("workstatusdescription");
			session = request.getSession(false);
			String usertype = (String) session.getAttribute("usertype");

			connection = new DatabaseConnection().getConnection();
			query = "insert into work_status(fk_work_status_work_allocation_id,work_status_updated_date,work_status,work_status_description,work_status_updated_by) values(?,?,?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, workAllocationId);
			pstmt.setDate(2, new Date(System.currentTimeMillis()));
			pstmt.setString(3, workStatus);
			pstmt.setString(4, workStatusDescription);
			pstmt.setString(5, usertype);

			pstmt.executeUpdate();

			if (usertype.equalsIgnoreCase("Admin")) {
				out.print("<script>alert('Status Updated Successfully');</script>");
				dis = request.getRequestDispatcher("adminhome.html");
				dis.include(request, response);

			} else if (usertype.equalsIgnoreCase("Lineman")) {
				out.print("<script>alert('Status Updated Successfully');</script>");
				dis = request.getRequestDispatcher("linemanhome.html");
				dis.include(request, response);
			}

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
