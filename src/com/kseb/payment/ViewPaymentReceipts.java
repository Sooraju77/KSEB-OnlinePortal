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

@WebServlet("/viewreceipts")
public class ViewPaymentReceipts extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	HttpSession session = null;
	String query = "";

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			session = request.getSession();
			connection = new DatabaseConnection().getConnection();
			query = "select pp.payment_bill_paid_id,pp.payment_bill_paid_date,pp.payment_bill_paid_amount from payment_bill_paid pp join payment_bill pb on pp.fk_payment_bill_paid_payment_bill_id=pb.payment_bill_id join work_allocation wa on pb.fk_payment_bill_work_allocation_id=wa.work_allocation_id join employee_login_details el on wa.fk_work_allocation_employee_id=el.fk_employee_login_details_employee_id where el.username=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, (String) session.getAttribute("username"));
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>::View Receipts::</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/viewreceipts.css'></head>");
			out.print("</head>");
			out.print("<body><div id='heading'>Payment Receipts</div>");
			out.print("<div id='tablediv'><table id='receiptstable'>");
			out.print("<tr><th>Receipt Id</th>");
			out.print("<th>Payment Receipt Date</th>");
			out.print("<th>Paid Amount</th></tr>");
			while (rs.next()) {
				out.print("<tr><td>" + rs.getInt(1) + "</td>");
				out.print("<td>" + rs.getDate(2) + "</td>");
				out.print("<td>" + rs.getInt(3) + "</td></tr>");
			}
			out.print("</table></div>");
			out.print("<div id='hdiv'>Go to HomePage:<a href='linemanhome.html' id='home'>Home</a></div>");
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
