package com.kseb.payment;

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

import com.kseb.DatabaseConnection;

@WebServlet("/persistpayment")
public class PersistPaymentBilling extends HttpServlet {

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
			int allocationId = Integer.parseInt(request.getParameter("workallocationid"));
			Date billDate = Date.valueOf(request.getParameter("paymentbillingdate"));
			int amount = Integer.parseInt(request.getParameter("paymentamount"));
			String paymentDetails = request.getParameter("billdetails");

			connection = new DatabaseConnection().getConnection();
			query = "insert into payment_bill(fk_payment_bill_work_allocation_id,payment_bill_date,payment_bill_amount,payment_bill_details) values(?,?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, allocationId);
			pstmt.setDate(2, billDate);
			pstmt.setInt(3, amount);
			pstmt.setString(4, paymentDetails);
			pstmt.executeUpdate();

			out.print("<script>alert('Payment Bill has been generated')</script>");
			dis = request.getRequestDispatcher("materialmanagerhome.html");
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
