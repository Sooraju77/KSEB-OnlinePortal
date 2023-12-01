package com.kseb.reports;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/viewmaterialreport")
public class MaterialReportHome extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;
	HttpSession session = null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			session = request.getSession();
			String usertype = (String) session.getAttribute("usertype");

			out.print("<html><head>");
			out.print("<title>Material Report</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/materialreport.css'></head>");
			out.print("<body>");
			out.print("<div id='heading'><b>MATERIAL REPORT</b></div>");
			out.print("<div id='formdiv'>");
			out.print("<form name='materialreportform' id='materialreportform' action='displaymaterialreport'>");
			out.print("<label>Start date:</label>");
			out.print("<label>End Date:</label><br><br>");
			out.print("<span><input type='date' name='startdate' id='startdate'></span>");
			out.print("<span><input type='date' name='enddate' id='enddate' required></span><br><br>");
			out.print("<input type='submit' value='Submit' id='submitbtn' required>");
			out.print("</form></div>");

			if (usertype.equalsIgnoreCase("MaterialManager")) {
				out.print("<div id='homediv'>Go to HomePage:<a href='materialmanagerhome.html' id='home'>Home</a></div>");
			} else if (usertype.equalsIgnoreCase("ElectricalEngineer")) {
				out.print(
						"<div id='homediv'>Go to HomePage:<a href='electricalengineerhome.html' id='home'>Home</a></div>");
			} else if (usertype.equalsIgnoreCase("Lineman")) {
				out.print(
						"<div id='homediv'>Go to HomePage:<a href='linemanhome.html' id='home'>Home</a></div>");
			}
			out.print("</body></html>");

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			out.close();
		}
	}


}
