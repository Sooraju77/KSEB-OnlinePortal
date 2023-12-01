package com.kseb.login;

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
import javax.servlet.http.HttpSession;

import com.kseb.DatabaseConnection;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	RequestDispatcher dis = null;
	int flag = 0;

	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			String username = request.getParameter("username");
			String password = request.getParameter("password");
			String usertype = request.getParameter("usertype");

			connection = new DatabaseConnection().getConnection();
			pstmt = connection.prepareStatement(
					"SELECT e.username,e.password,ur.user_type  FROM employee_login_details e join employee_usertype u on e.fk_employee_login_details_employee_id=u.fk_employee_usertype_employee_id join user_types ur on u.fk_employee_usertype_user_type_id=ur.user_type_id where e.username='"
							+ username + "'");
			rs = pstmt.executeQuery();
			while (rs.next()) {
				if (username.equals(rs.getString(1)) && password.equals(rs.getString(2))
						&& usertype.equalsIgnoreCase(rs.getString(3))) {
					flag = 1;
					HttpSession session = request.getSession();
					session.setAttribute("username", username);
					session.setAttribute("usertype", usertype);
				}
			}
			if (flag == 1 && usertype.equalsIgnoreCase("Admin")) {
				dis = request.getRequestDispatcher("adminhome.html");
				dis.forward(request, response);

			} else if (flag == 1 && usertype.equalsIgnoreCase("Lineman")) {
				dis = request.getRequestDispatcher("linemanhome.html");
				dis.forward(request, response);
			} else if (flag == 1 && usertype.equalsIgnoreCase("MaterialManager")) {
				dis = request.getRequestDispatcher("materialmanagerhome.html");
				dis.forward(request, response);
			} else if (flag == 1 && usertype.equalsIgnoreCase("ElectricalEngineer")) {
				dis = request.getRequestDispatcher("electricalengineerhome.html");
				dis.forward(request, response);
			} else {
				out.print("<script>alert('Invalid username/password/usertype')</script>");
				dis = request.getRequestDispatcher("index.html");
				dis.include(request, response);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		} catch (ServletException e) {
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
