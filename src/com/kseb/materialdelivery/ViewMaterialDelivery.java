package com.kseb.materialdelivery;

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

@WebServlet("/viewmaterialdelivery")
public class ViewMaterialDelivery extends HttpServlet {

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
			query = "select md.material_delivery_id,md.fk_material_delivery_material_request_id,mi.material_item_id,mi.material_item_name,md.material_delivery_quantity,md.material_delivery_initiated_date,ms.material_delivery_status,ms.material_delivery_status_updated_date,ms.material_delivery_status_updated_by,ms.material_delivery_status_description from material_delivery md left join material_delivery_status ms on md.material_delivery_id=ms.fk_material_delivery_status_material_delivery_id join material_request mr on mr.material_request_id=md.fk_material_delivery_material_request_id join material_item mi on mi.material_item_id=mr.fk_material_request_material_item_id";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>View Material Delivery</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/viewmaterialdelivery.css'></head>");
			out.print("<body>");
			out.print("<div id='heading'>Material Delivery Details</div>");
			out.print("<div id='tablediv'>");
			out.print("<table id='materialdeliverytable'>");
			out.print("<tr><th>Delivery Id</th>");
			out.print("<th>Request Id</th>");
			out.print("<th>Material Item Id</th>");
			out.print("<th>Material Item Name</th>");
			out.print("<th>Quantity</th>");
			out.print("<th>Delivery Initiated Date</th>");
			out.print("<th>Delivery Status</th>");
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
			out.print("<div id='homediv'>Go to HomePage:<a href='materialmanagerhome.html' id='home'>Home</a></div>");

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
