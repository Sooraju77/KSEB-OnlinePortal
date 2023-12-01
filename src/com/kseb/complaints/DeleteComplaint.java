package com.kseb.complaints;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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

@WebServlet("/deletecomplaintdetails")
public class DeleteComplaint extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	RequestDispatcher dis = null;
	String query = "";
	int workAllocationId;
	int materialRequestId;
	int materialDeliveryId;
	int payment_bill_id;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			int complaintId = Integer.parseInt(request.getParameter("complaintid"));

			connection = new DatabaseConnection().getConnection();
			query = "select work_allocation_id from work_allocation where fk_work_allocation_complaint_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, complaintId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				workAllocationId = rs.getInt(1);
			}

			query = "select material_request_id from material_request where fk_material_request_work_allocation_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, workAllocationId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				materialRequestId = rs.getInt(1);
			}

			query = "select material_delivery_id from material_delivery where fk_material_delivery_material_request_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialRequestId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				materialDeliveryId = rs.getInt(1);
			}
			
			query="select payment_bill_id from payment_bill where fk_payment_bill_work_allocation_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, workAllocationId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				payment_bill_id = rs.getInt(1);
			}
			
			query="delete from payment_bill_paid where fk_payment_bill_paid_payment_bill_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, payment_bill_id);
			pstmt.executeUpdate();
			
			query="delete from payment_bill where payment_bill_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, payment_bill_id);
			pstmt.executeUpdate();

			query = "delete from material_delivery_status where fk_material_delivery_status_material_delivery_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialDeliveryId);
			pstmt.executeUpdate();

			query = "delete from material_delivery where fk_material_delivery_material_request_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialRequestId);
			pstmt.executeUpdate();

			query = "delete from material_request_status where fk_material_request_status_material_request_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialRequestId);
			pstmt.executeUpdate();

			query = "delete from material_request where fk_material_request_work_allocation_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, workAllocationId);
			pstmt.executeUpdate();

			query = "delete from work_status where fk_work_status_work_allocation_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, workAllocationId);
			pstmt.executeUpdate();

			query = "delete from work_allocation where fk_work_allocation_complaint_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, complaintId);
			pstmt.executeUpdate();

			query = "delete from complaint_status where fk_complaint_status_complaint_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, complaintId);
			pstmt.executeUpdate();

			query = "delete from complaint_details where complaint_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, complaintId);
			pstmt.executeUpdate();

			out.print("<script>alert('Complaint has been deleted')</script>");
			dis = request.getRequestDispatcher("adminhome.html");
			dis.include(request, response);

		} catch (IOException | SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		}
	}

}
