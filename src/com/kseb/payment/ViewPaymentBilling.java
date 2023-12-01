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

@WebServlet("/viewbilling")
public class ViewPaymentBilling extends HttpServlet {

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
			query = "select payment_bill_id,fk_payment_bill_work_allocation_id,payment_bill_date,payment_bill_amount,payment_bill_details from payment_bill";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>::View Billings::</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/viewbillings.css'></head>");
			out.print("</head>");
			out.print("<body><div id='heading'>Payment Billings</div>");
			out.print("<div id='tablediv'><table id='billingstable'>");
			out.print("<tr><th>Bill Id</th>");
			out.print("<th>Work Allocation Id</th>");
			out.print("<th>Bill Generated Date</th>");
			out.print("<th>Bill Amount</th>");
			out.print("<th>Bill Details</th></tr>");
			while (rs.next()) {
				out.print("<tr><td>" + rs.getInt(1) + "</td>");
				out.print("<td>" + rs.getInt(2) + "</td>");
				out.print("<td>" + rs.getDate(3) + "</td>");
				out.print("<td>" + rs.getInt(4) + "</td>");
				out.print("<td>" + rs.getString(5) + "</td></tr>");
			}
			out.print("</table></div>");
			out.print("<div id='hdiv'>Go to HomePage:<a href='materialmanagerhome.html' id='home'>Home</a></div>");
			out.print("</body></html>");

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
