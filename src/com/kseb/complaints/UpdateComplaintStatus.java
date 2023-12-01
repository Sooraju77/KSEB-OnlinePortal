package com.kseb.complaints;

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

@WebServlet("/updatecomplaintstatus")
public class UpdateComplaintStatus extends HttpServlet {

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
			query = "select complaint_id from complaint_details";
			pstmt = connection.prepareStatement(query);
			rs = pstmt.executeQuery();

			out.print("<html><head>");
			out.print("<title>Update Status</title>");
			out.print("<style>");
			out.print(
					"body{background-image:url('images/complaintregbg.jpg');background-repeat:no-repeat;background-size:cover;}");
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
			out.print("<div id='compheading'>UPDATE COMPLAINT STATUS</div>");
			out.print("<div id='formdiv'>");
			out.print(
					"<form name='complaintstatusform' id='complaintstatusform' method='post' action='persistcomplaintstatus'>");
			out.print("<label>Complaint Id:</label>");
			out.print("<select name='complaintid' id='complaintid' required>");
			out.print("<option value=''>--Select--</option>");
			while (rs.next()) {
				out.print("<option value='" + rs.getInt(1) + "'>" + rs.getInt(1) + "</option>");
			}
			out.print("</select></br></br>");
			out.print("<label>Complaint Status:</label>");
			out.print("<input type='text' name='complaintstatus' id='comstatus' required/></br></br>");
			out.print("<label>Complaint Status Description:</label>");
			out.print(
					"<textarea name='complaintdescription' id='comstatusdesc' rows='4' cols='50' required></textarea></br></br>");
			out.print("<input type='submit' value='Submit' id='submitbtn'/>");

			out.print("</form>");
			out.print("</div>");
			out.print("<div id='homediv'>Go to HomePage:<a href='adminhome.html' id='home'>Home</a></div>");
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
