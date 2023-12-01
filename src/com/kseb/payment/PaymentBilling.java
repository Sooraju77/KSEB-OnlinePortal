package com.kseb.payment;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kseb.DatabaseConnection;

@WebServlet("/generatebill")
public class PaymentBilling extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String query = "";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			connection = new DatabaseConnection().getConnection();
			query = "select mr.fk_material_request_work_allocation_id from material_delivery md join material_request mr on md.fk_material_delivery_material_request_id=material_request_id";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>Payment Billing</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/paymentbilling.css'></head>");
			out.print("<body>");
			out.print("<div id='heading'><b>Payment Billing</b></div>");
			out.print("<div id='formdiv'>");
			out.print("<form name='paymentbilling' id='paymentbilling' method='post' action='persistpayment'>");
			out.print("<label>Allocation Id:</label>");
			out.print("<select name='workallocationid' id='workallocationid' required>");
			out.print("<option value=''>--Select--</option>");
			while (rs.next()) {
				out.print("<option value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "</option>");
			}
			out.print("</select></br></br>");
			out.print("<label>Date:</label>");
			out.print("<input type='date' name='paymentbillingdate' id='paymentbillingdate' required/><br><br>");
			out.print("<label>Amount: </label>");
			out.print("<input type='number' name='paymentamount' id='paymentamount' required/><br><br>");
			out.print("<label>Bill Details:</label>");
			out.print(
					"<textarea name='billdetails' id='billdetails' rows='4' cols='40' required></textarea></br></br>");
			out.print("<input type='submit' value='Generate Bill' id='savebtn'/></form></div>");
			out.print("<div id='home'>Go to HomePage:<a href='materialmanagerhome.html'>Home</a></div></body></html>");

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
