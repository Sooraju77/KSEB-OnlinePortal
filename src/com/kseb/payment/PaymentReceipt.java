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
import javax.servlet.http.HttpSession;

import com.kseb.DatabaseConnection;

@WebServlet("/generatereceipt")
public class PaymentReceipt extends HttpServlet{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String query = "";
	HttpSession session=null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			session=request.getSession();
			connection = new DatabaseConnection().getConnection();
			query = "select pb.payment_bill_id from payment_bill pb join work_allocation wa on pb.fk_payment_bill_work_allocation_id=wa.work_allocation_id join employee_login_details el on wa.fk_work_allocation_employee_id=el.fk_employee_login_details_employee_id where el.username=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, (String) session.getAttribute("username"));
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>Payment Receipt</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/paymentreceipt.css'></head>");
			out.print("<body>");
			out.print("<div id='heading'><b>Payment Receipt</b></div>");
			out.print("<div id='formdiv'>");
			out.print("<form name='paymentreceipt' id='paymentreceipt' method='post' action='persistpaymentreceipt'>");
			out.print("<label>Bill Id:</label>");
			out.print("<select name='billid' id='billid' required>");
			out.print("<option value=''>--Select--</option>");
			while (rs.next()) {
				out.print("<option value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "</option>");
			}
			out.print("</select></br></br>");
			out.print("<label>Date:</label>");
			out.print("<input type='date' name='paymentreceiptdate' id='paymentreceiptdate' required/><br><br>");
			out.print("<label>Amount: </label>");
			out.print("<input type='number' name='paidamount' id='paidamount' required/><br><br>");
			
			out.print("<input type='submit' value='Generate Receipt' id='savebtn'/></form></div>");
			out.print("<div id='home'>Go to HomePage:<a href='linemanhome.html'>Home</a></div></body></html>");

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
