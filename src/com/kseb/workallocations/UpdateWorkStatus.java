package com.kseb.workallocations;

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

@WebServlet("/updateworkstatus")
public class UpdateWorkStatus extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	Connection connection = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String query = "";
	HttpSession session = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			session = request.getSession();
			String userType = (String) session.getAttribute("usertype");
			String username = (String) session.getAttribute("username");
			connection = new DatabaseConnection().getConnection();

			if (userType.equals("Lineman")) {
				query = "select wa.work_allocation_id from work_allocation wa join employee_login_details el on wa.fk_work_allocation_employee_id=el.fk_employee_login_details_employee_id where el.username=?";
				pstmt = connection.prepareStatement(query);
				pstmt.setString(1, username);
				rs = pstmt.executeQuery();
			} else if (userType.equals("Admin")) {
				query = "select work_allocation_id from work_allocation";
				pstmt = connection.prepareStatement(query);
				rs = pstmt.executeQuery();
			}

			out.print("<html><head>");
			out.print("<title>::Update Work Status::</title>");
			out.print("<style>");
			out.print(
					"body{background-image:url('images/allocatework.jpg');background-repeat:no-repeat;background-size:cover;}");
			out.print("#compheading{text-align:center;font-family:garamond;font-size:40px;color:white;}");
			out.print(
					"#formdiv{text-align:center;font-size:18px;font-family:Arial;color:white;width:450px;margin-top:90px;margin-left:500px;padding:30px;border-style:dashed;border-radius:5px;}");
			out.print("#submitbtn{border-radius:12px;background-color:green;color:white;padding:12px;}");
			out.print(
					"#homediv{color: white;text-align: center;margin-top: 90px;font-family: garamond;font-size: 18px;}");
			out.print("#home{color:white}");
			out.print("</style>");
			out.print("</head>");

			out.print("<body>");
			out.print("<div id='compheading'>UPDATE WORK STATUS</div>");
			out.print("<div id='formdiv'>");
			out.print("<form name='workstatusform' id='workstatusform' method='post' action='persistworkstatus'>");
			out.print("<label>Work Allocation Id:</label>");
			out.print("<select name='allocationid' id='allocationid' required>");
			out.print("<option value=''>--Select--</option>");
			while (rs.next()) {
				out.print("<option value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "</option>");
			}
			out.print("</select></br></br>");
			out.print("Work Status:");
			out.print("<input type='text' name='workstatus' id='workstatus' required/></br></br>");
			out.print("Work Status Description:");
			out.print(
					"<textarea name='workstatusdescription' id='workstatusdesc' rows='4' cols='50' required></textarea></br></br>");
			out.print("<input type='submit' value='Submit' id='submitbtn'/>");
			out.print("</form>");
			out.print("</div>");

			if (userType.equals("Admin")) {
				out.print("<div id='homediv'>Go to HomePage:<a href='adminhome.html' id='home'>Home</a></div>");
			} else if (userType.equals("Lineman")) {
				out.print("<div id='homediv'>Go to HomePage:<a href='linemanhome.html' id='home'>Home</a></div>");
			} else if (userType.equals("ElectricalEngineer")) {
				out.print(
						"<div id='homediv'>Go to HomePage:<a href='electricalengineerhome.html' id='home'>Home</a></div>");
			} else if (userType.equals("MaterialManager")) {
				out.print(
						"<div id='homediv'>Go to HomePage:<a href='materialmanagerhome.html' id='home'>Home</a></div>");
			}
			out.print("</body>");
			out.print("</html>");

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
