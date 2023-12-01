package com.kseb.inventory;

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

@WebServlet("/addmaterials")
public class AddMaterials extends HttpServlet {

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
	int materialBrandId;
	int materialItemId;
	int flag = 0;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			String brandName = request.getParameter("brandname").trim();
			String materialItemName = request.getParameter("materialname").trim();
			int price = Integer.parseInt(request.getParameter("materialprice"));

			connection = new DatabaseConnection().getConnection();
			query = "select material_brand_id from material_brand where material_brand_name=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setString(1, brandName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				materialBrandId = rs.getInt(1);
				flag = 1;
			}
			if (flag == 0) {
				query = "insert into material_brand(material_brand_name) values(?)";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, brandName);
				pstmt.executeUpdate();
				query = "select material_brand_id from material_brand where material_brand_name=?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, brandName);
				rs = pstmt.executeQuery();
				while (rs.next()) {
					materialBrandId = rs.getInt(1);
				}
			}
			query = "insert into material_item(fk_material_item_material_brand_id,material_item_name) values(?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialBrandId);
			pstmt.setString(2, materialItemName);
			pstmt.executeUpdate();

			query = "select material_item_id from material_item where fk_material_item_material_brand_id=? and material_item_name=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialBrandId);
			pstmt.setString(2, materialItemName);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				materialItemId = rs.getInt(1);
			}

			query = "insert into material_item_pricing(fk_material_item_pricing_material_item_id,material_price) values(?,?)";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialItemId);
			pstmt.setInt(2, price);
			pstmt.executeUpdate();

			out.print("<script>alert('material added successfully');</script>");
			dis = request.getRequestDispatcher("materialmanagerhome.html");
			dis.include(request, response);

		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			out.close();
			try {
				connection.close();
				pstmt.close();
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}

	}

}
