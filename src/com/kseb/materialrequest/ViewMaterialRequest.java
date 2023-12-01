package com.kseb.materialrequest;

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

@WebServlet("/viewmaterialrequest")
public class ViewMaterialRequest extends HttpServlet {

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
			String username = (String) session.getAttribute("username");
			String usertype = (String) session.getAttribute("usertype");
			connection = new DatabaseConnection().getConnection();
			if (usertype.equalsIgnoreCase("Lineman")) {
				query = "select m.material_request_id,m.fk_material_request_work_allocation_id,m.fk_material_request_material_item_id,mi.material_item_name,m.material_request_quantity,m.material_request_logged_date,ms.material_request_status,ms.material_request_status_updated_date,ms.material_request_status_updated_by,ms.material_request_status_description from material_request m left join material_request_status ms on m.material_request_id=ms.fk_material_request_status_material_request_id join work_allocation w on m.fk_material_request_work_allocation_id=w.work_allocation_id join employee_login_details e on w.fk_work_allocation_employee_id=e.fk_employee_login_details_employee_id join material_item mi on mi.material_item_id=m.fk_material_request_material_item_id where e.username=?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, username);

			} else if (usertype.equalsIgnoreCase("MaterialManager")
					|| usertype.equalsIgnoreCase("ElectricalEngineer")) {
				query = "select m.material_request_id,m.fk_material_request_work_allocation_id,m.fk_material_request_material_item_id,mi.material_item_name,m.material_request_quantity, m.material_request_logged_date,ms.material_request_status,ms.material_request_status_updated_date,ms.material_request_status_updated_by, ms.material_request_status_description from material_request m left join material_request_status ms on m.material_request_id=ms.fk_material_request_status_material_request_id join material_item mi on mi.material_item_id=m.fk_material_request_material_item_id";
				pstmt = connection.prepareStatement(query);
			}
			rs = pstmt.executeQuery();
			out.print("<html><head>");
			out.print("<title>View Material Request</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/viewmaterialrequests.css'></head>");
			out.print("<body>");
			out.print("<div id='heading'>Material Requests List</div>");
			out.print("<div id='tablediv'>");
			out.print("<table id='materialrequesttable'>");
			out.print("<tr><th>Request Id</th>");
			out.print("<th>Allocation Id</th>");
			out.print("<th>Material Item Id</th>");
			out.print("<th>Material Item Name</th>");
			out.print("<th>Quantity</th>");
			out.print("<th>Request Logged Date</th>");
			out.print("<th>Request Status</th>");
			out.print("<th>Status Updated Date</th>");
			out.print("<th>Status Updated By</th>");
			out.print("<th>Status Description</th></tr>");

			while (rs.next()) {
				out.print("<tr><td>" + rs.getInt(1) + "</td>");
				out.print("<td>" + rs.getInt(2) + "</td>");
				out.print("<td>" + rs.getInt(3) + "</td>");
				out.print("<td>" + rs.getString(4) + "</td>");
				out.print("<td>" + rs.getInt(5) + "</td>");
				out.print("<td>" + rs.getDate(6) + "</td>");
				out.print("<td>" + rs.getString(7) + "</td>");
				out.print("<td>" + rs.getDate(8) + "</td>");
				out.print("<td>" + rs.getString(9) + "</td>");
				out.print("<td>" + rs.getString(10) + "</td></tr>");
			}
			out.print("</table></div>");
			if (usertype.equalsIgnoreCase("Lineman")) {
				out.print("<div id='homediv'>Go to HomePage:<a href='linemanhome.html' id='home'>Home</a></div>");
			} else if (usertype.equalsIgnoreCase("ElectricalEngineer")) {
				out.print(
						"<div id='homediv'>Go to HomePage:<a href='electricalengineerhome.html' id='home'>Home</a></div>");
			} else if (usertype.equalsIgnoreCase("MaterialManager")) {
				out.print(
						"<div id='homediv'>Go to HomePage:<a href='materialmanagerhome.html' id='home'>Home</a></div>");
			}

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
