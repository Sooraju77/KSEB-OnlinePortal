package com.kseb.inventory;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.kseb.DatabaseConnection;

@WebServlet("/persiststock")
public class PersistStock extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String query = "";
	int stockItemId;
	int quantity;
	int flag = 0;
	RequestDispatcher dis = null;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			int materialItemId = Integer.parseInt(request.getParameter("materialitemid"));
			int quant = Integer.parseInt(request.getParameter("quantity"));

			connection = new DatabaseConnection().getConnection();
			query = "select stock_item_id,stock_item_quantity from stock_item where fk_stock_item_material_item_id=?";
			pstmt = connection.prepareStatement(query);
			pstmt.setInt(1, materialItemId);
			rs = pstmt.executeQuery();
			while (rs.next()) {
				stockItemId = rs.getInt(1);
				quantity = rs.getInt(2);
				quantity += quant;
				query = "update stock_item set stock_update_date=? , stock_item_quantity=? where stock_item_id=?";
				pstmt = connection.prepareStatement(query);
				pstmt.setDate(1, new Date(System.currentTimeMillis()));
				pstmt.setInt(2, quantity);
				pstmt.setInt(3, stockItemId);
				pstmt.executeUpdate();
				flag = 1;
			}
			if (flag == 0) {
				query = "insert into stock_item(stock_update_date,fk_stock_item_material_item_id,stock_item_quantity) values(?,?,?)";
				pstmt = connection.prepareStatement(query);
				pstmt.setDate(1, new Date(System.currentTimeMillis()));
				pstmt.setInt(2, materialItemId);
				pstmt.setInt(3, quant);
				pstmt.executeUpdate();
			}

			out.print("<script>alert('Stock Updated Successfully')</script>");
			dis = request.getRequestDispatcher("materialmanagerhome.html");
			dis.include(request, response);

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
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
