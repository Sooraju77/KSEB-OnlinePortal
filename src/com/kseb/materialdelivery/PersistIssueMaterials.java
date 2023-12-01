package com.kseb.materialdelivery;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kseb.DatabaseConnection;

@WebServlet("/persistmaterialdelivery")
public class PersistIssueMaterials extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	String query = "";
	RequestDispatcher dis = null;
	ResultSet rs = null;
	int deliveryQuantity;
	int materialId;
	int currentQuantity;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			int materialRequestId = Integer.parseInt(request.getParameter("materialrequestid"));

			connection = new DatabaseConnection().getConnection();
			query = "select material_request_quantity,fk_material_request_material_item_id from material_request where material_request_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialRequestId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				deliveryQuantity = rs.getInt(1);
				materialId = rs.getInt(2);
			}

			query = "select stock_item_quantity from stock_item where fk_stock_item_material_item_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				currentQuantity = rs.getInt(1);
			}

			if (currentQuantity >= deliveryQuantity) {
				query = "insert into material_delivery(fk_material_delivery_material_request_id,material_delivery_quantity,material_delivery_initiated_date) values(?,?,?)";
				pstmt = connection.prepareStatement(query);
				pstmt.setInt(1, materialRequestId);
				pstmt.setInt(2, deliveryQuantity);
				pstmt.setDate(3, new Date(System.currentTimeMillis()));
				pstmt.executeUpdate();

				currentQuantity -= deliveryQuantity;

				query = "update stock_item set stock_item_quantity=? where fk_stock_item_material_item_id=?";
				pstmt = connection.prepareStatement(query);
				pstmt.setInt(1, currentQuantity);
				pstmt.setInt(2, materialId);
				pstmt.executeUpdate();

				out.print("<script>alert('Material Delivery has been initiated')</script>");
				dis = request.getRequestDispatcher("materialmanagerhome.html");
				dis.include(request, response);
			} else{
				out.print("<script>alert('Insufficient Stock..Please update stock!!')</script>");
				dis = request.getRequestDispatcher("materialmanagerhome.html");
				dis.include(request, response);
			}

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