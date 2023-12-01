package com.kseb.workallocations;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kseb.DatabaseConnection;

@WebServlet("/persistwork")
public class PersistWork extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	String query = "";
	RequestDispatcher dis=null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();

			Date workAllocatedDate = Date.valueOf(request.getParameter("workallocateddate"));
			int complaintId = Integer.parseInt(request.getParameter("complaintno"));
			int employeeId = Integer.parseInt(request.getParameter("linemanno"));

			connection = new DatabaseConnection().getConnection();
			query = "insert into work_allocation(fk_work_allocation_complaint_id,fk_work_allocation_employee_id,work_allocated_date) values(?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, complaintId);
			pstmt.setInt(2, employeeId);
			pstmt.setDate(3, workAllocatedDate);
			pstmt.executeUpdate();

			out.print("<script>alert('Work has been allocated successfully')</script>");
			dis=request.getRequestDispatcher("adminhome.html");
			dis.include(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
