package com.kseb.materialdelivery;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.kseb.DatabaseConnection;

@WebServlet("/persistmaterialdeliverystatus")
public class PersistUpdateDeliveryStatus extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	PreparedStatement pstmt = null;
	String query = "";
	PrintWriter out = null;
	HttpSession session = null;
	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();

			int materialDeliveryId = Integer.parseInt(request.getParameter("materialdeliveryid"));
			String materialDeliveryStatus = request.getParameter("materialdeliverystatus");
			String materialDeliveryStatusDescription = request.getParameter("materialdeliverystatusdescription");

			connection = new DatabaseConnection().getConnection();
			query = "insert into material_delivery_status(fk_material_delivery_status_material_delivery_id,material_delivery_status,material_delivery_status_updated_date,material_delivery_status_description,material_delivery_status_updated_by) values(?,?,?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialDeliveryId);
			pstmt.setString(2, materialDeliveryStatus);
			pstmt.setDate(3, new Date(System.currentTimeMillis()));
			pstmt.setString(4, materialDeliveryStatusDescription);
			pstmt.setString(5, "MaterialManager");
			pstmt.executeUpdate();

			out.print("<script>alert('Material Delivery Status Updated Successfully')</script>");
			dis = request.getRequestDispatcher("materialmanagerhome.html");
			dis.include(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
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
