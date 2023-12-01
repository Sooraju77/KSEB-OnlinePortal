package com.kseb.complaints;

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

@WebServlet("/persistcomplaint")
public class PersistComplaint extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	RequestDispatcher dis=null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();

			int consumerKsebId = Integer.parseInt(request.getParameter("consumerksebid"));
			String complaintDescription = request.getParameter("complaintdetails");
			Date complaintLoggedDate = Date.valueOf(request.getParameter("complaintregdate"));

			connection = new DatabaseConnection().getConnection();
			pstmt = connection.prepareStatement(
					"insert into complaint_details(fk_complaint_details_consumer_kseb_id,complaint_description,complaint_logged_date) values(?,?,?)");
			pstmt.setInt(1, consumerKsebId);
			pstmt.setString(2, complaintDescription);
			pstmt.setDate(3, complaintLoggedDate);
			pstmt.executeUpdate();
			
			out.print("<script>alert('Complaint has been registered successfully')</script>");
			dis=request.getRequestDispatcher("adminhome.html");
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
