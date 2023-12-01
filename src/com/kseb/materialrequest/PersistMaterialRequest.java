package com.kseb.materialrequest;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kseb.DatabaseConnection;

@WebServlet("/persistmaterialrequest")
public class PersistMaterialRequest extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Connection connection = null;
	PreparedStatement pstmt = null;
	String query = "";

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");

			Date materialRequestLoggedDate = Date.valueOf(request.getParameter("materialrequestdate"));
			int workAllocationId = Integer.parseInt(request.getParameter("workallocationno"));
			int materialItemId = Integer.parseInt(request.getParameter("materialid"));
			int materialRequestedQuantity = Integer.parseInt(request.getParameter("materialquantity"));

			connection = new DatabaseConnection().getConnection();
			query = "insert into material_request(fk_material_request_work_allocation_id,fk_material_request_material_item_id,material_request_quantity,material_request_logged_date) values (?,?,?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, workAllocationId);
			pstmt.setInt(2, materialItemId);
			pstmt.setInt(3, materialRequestedQuantity);
			pstmt.setDate(4, materialRequestLoggedDate);
			pstmt.executeUpdate();

			response.sendRedirect("linemanhome.html");

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				connection.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

}
