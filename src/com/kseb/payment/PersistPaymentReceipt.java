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

@WebServlet("/persistpaymentreceipt")
public class PersistPaymentReceipt extends HttpServlet{

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
			int billId = Integer.parseInt(request.getParameter("billid"));
			Date paymentReceiptDate = Date.valueOf(request.getParameter("paymentreceiptdate"));
			int amount = Integer.parseInt(request.getParameter("paidamount"));

			connection = new DatabaseConnection().getConnection();
			query = "insert into payment_bill_paid(fk_payment_bill_paid_payment_bill_id,payment_bill_paid_date,payment_bill_paid_amount) values(?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, billId);
			pstmt.setDate(2, paymentReceiptDate);
			pstmt.setInt(3, amount);
			pstmt.executeUpdate();

			out.print("<script>alert('Payment Receipt has been generated')</script>");
			dis = request.getRequestDispatcher("linemanhome.html");
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
