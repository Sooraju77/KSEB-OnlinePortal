package com.kseb.workallocations;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kseb.DatabaseConnection;

@WebServlet("/persisteditallocation")
public class PersistEditAllocation extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	PreparedStatement pstmt = null;
	String query = "";
	PrintWriter out = null;
	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			int workAllocationId = Integer.parseInt(request.getParameter("allocationid"));
			int employeeId = Integer.parseInt(request.getParameter("linemanno"));

			connection = new DatabaseConnection().getConnection();
			query = "update work_allocation set fk_work_allocation_employee_id=? where work_allocation_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, employeeId);
			pstmt.setInt(2, workAllocationId);
			pstmt.executeUpdate();

			out.print("<script>alert('Work Allocation has been modified')</script>");
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
