package com.kseb.inventory;

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

@WebServlet("/viewmaterials")
public class ViewMaterials extends HttpServlet {

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
			query = "select mi.material_item_id,mb.material_brand_name,mi.material_item_name,mip.material_price,si.stock_item_quantity,si.stock_update_date from material_brand mb join material_item mi on mb.material_brand_id=mi.fk_material_item_material_brand_id join material_item_pricing mip on mi.material_item_id=mip.fk_material_item_pricing_material_item_id left join stock_item si on mi.material_item_id=si.fk_stock_item_material_item_id";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>::View Materials::</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/viewmaterials.css'></head>");
			out.print("</head>");
			out.print("<body><div id='heading'>Materials List</div>");
			out.print("<div id='tablediv'><table id='materialstable'>");
			out.print("<tr><th>Material Item Id</th>");
			out.print("<th>Brand Name</th>");
			out.print("<th>Material Item Name</th>");
			out.print("<th>Material Price</th>");
			out.print("<th>Item Quantity</th>");
			out.print("<th>Stock Updated Date</th></tr>");
			while (rs.next()) {
				out.print("<tr><td>" + rs.getInt(1) + "</td>");
				out.print("<td>" + rs.getString(2) + "</td>");
				out.print("<td>" + rs.getString(3) + "</td>");
				out.print("<td>" + rs.getInt(4) + "</td>");
				out.print("<td>" + rs.getInt(5) + "</td>");
				out.print("<td>" + rs.getDate(6) + "</td></tr>");
			}
			out.print("</table></div>");
			out.print("<div id='hdiv'>Go to HomePage:<a href='materialmanagerhome.html' id='home'>Home</a></div>");
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
