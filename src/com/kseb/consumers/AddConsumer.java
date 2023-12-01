package com.kseb.consumers;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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

@WebServlet("/addConsumer")
public class AddConsumer extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	RequestDispatcher dis = null;
	int consumerId;
	String query = "";

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();

			String firstName = request.getParameter("firstname");
			String lastName = request.getParameter("lastname");
			String phoneNo = request.getParameter("phoneno");
			String emailId = request.getParameter("emailid");
			String city = request.getParameter("city");
			int pincode = Integer.parseInt(request.getParameter("pincode"));
			int meterNo = Integer.parseInt(request.getParameter("meterno"));
			int postNo = Integer.parseInt(request.getParameter("postno"));
			String connectionType = request.getParameter("connectiontype");

			connection = new DatabaseConnection().getConnection();
			query = "insert into consumer_personal_details(consumer_firstname,consumer_lastname,consumer_phone_no,consumer_email_id,consumer_city,consumer_pincode) values(?,?,?,?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			pstmt.setString(3, phoneNo);
			pstmt.setString(4, emailId);
			pstmt.setString(5, city);
			pstmt.setInt(6, pincode);
			pstmt.executeUpdate();

			query = "select consumer_id from consumer_personal_details where consumer_firstname=? and consumer_lastname=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, firstName);
			pstmt.setString(2, lastName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				consumerId = rs.getInt(1);
			}

			query = "insert into consumer_kseb(fk_consumer_kseb_consumer_id,consumer_kseb_meter_no,consumer_kseb_post_no,consumer_kseb_connection_type) values(?,?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, consumerId);
			pstmt.setInt(2, meterNo);
			pstmt.setInt(3, postNo);
			pstmt.setString(4, connectionType);
			pstmt.executeUpdate();

			out.print("<div style='color:white'>Customer added successfully</div>");
			dis = request.getRequestDispatcher("addconsumer.html");
			dis.include(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
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
