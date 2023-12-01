package com.kseb.reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kseb.DatabaseConnection;

@WebServlet("/displaypaymentreport")
public class ViewPaymentReport extends HttpServlet {

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
	String usertype;
	Date startDate;
	Date endDate;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			session = request.getSession();
			usertype = (String) session.getAttribute("usertype");
			startDate = Date.valueOf(request.getParameter("startdate"));
			endDate = Date.valueOf(request.getParameter("enddate"));
			connection = new DatabaseConnection().getConnection();

			if (usertype.equalsIgnoreCase("Admin") || usertype.equalsIgnoreCase("ElectricalEngineer")) {
				query = "select pb.payment_bill_id,pb.fk_payment_bill_work_allocation_id,pb.payment_bill_date,pb.payment_bill_amount,pb.payment_bill_details,pbp.payment_bill_paid_date,pbp.payment_bill_paid_amount from payment_bill pb left join payment_bill_paid pbp on pb.payment_bill_id=pbp.fk_payment_bill_paid_payment_bill_id where pb.payment_bill_date between ? and ?";
				pstmt = connection.prepareStatement(query);
				pstmt.setDate(1, startDate);
				pstmt.setDate(2, endDate);
				rs = pstmt.executeQuery();

			} else if (usertype.equalsIgnoreCase("Lineman")) {
				query = "select pb.payment_bill_id,pb.fk_payment_bill_work_allocation_id,pb.payment_bill_date,pb.payment_bill_amount,pb.payment_bill_details,pbp.payment_bill_paid_date,pbp.payment_bill_paid_amount from payment_bill pb left join payment_bill_paid pbp on pb.payment_bill_id=pbp.fk_payment_bill_paid_payment_bill_id join work_allocation wa on pb.fk_payment_bill_work_allocation_id=wa.work_allocation_id join employee_login_details eld on wa.fk_work_allocation_employee_id=eld.fk_employee_login_details_employee_id where eld.username=? and pb.payment_bill_date between ? and ?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, (String) session.getAttribute("username"));
				pstmt.setDate(2, startDate);
				pstmt.setDate(3, endDate);
				rs = pstmt.executeQuery();

			}

			out.print("<html><head>");
			out.print("<title>::Payment Report::</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/viewpaymentreport.css'></head>");
			out.print("</head>");
			out.print("<body><div id='heading'><b>PAYMENT REPORT</b></div>");
			out.print("<div id='tablediv'><table id='paymenttable'>");
			out.print("<tr><th>Bill Id</th>");
			out.print("<th>Work Allocation Id</th>");
			out.print("<th>Bill Date</th>");
			out.print("<th>Bill Amount</th>");
			out.print("<th>Bill Details</th>");
			out.print("<th>Paid Date</th>");
			out.print("<th>Paid Amount</th></tr>");
			while (rs.next()) {
				out.print("<tr><td>" + rs.getInt(1) + "</td>");
				out.print("<td>" + rs.getInt(2) + "</td>");
				out.print("<td>" + rs.getDate(3) + "</td>");
				out.print("<td>" + rs.getInt(4) + "</td>");
				out.print("<td>" + rs.getString(5) + "</td>");
				out.print("<td>" + rs.getDate(6) + "</td>");
				out.print("<td>" + rs.getInt(7) + "</td></tr>");
			}
			out.print("</table></div>");
			if (usertype.equalsIgnoreCase("Admin")) {
				out.print("<div id='homediv'>Go to HomePage:<a href='adminhome.html' id='home'>Home</a></div>");
			} else if (usertype.equalsIgnoreCase("ElectricalEngineer")) {
				out.print(
						"<div id='homediv'>Go to HomePage:<a href='electricalengineerhome.html' id='home'>Home</a></div>");
			} else if (usertype.equalsIgnoreCase("Lineman")) {
				out.print("<div id='homediv'>Go to HomePage:<a href='linemanhome.html' id='home'>Home</a></div>");
			}
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
