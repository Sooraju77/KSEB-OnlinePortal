package com.kseb.complaints;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updatecomplaintdetails")
public class EditComplaintDetails extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	PrintWriter out = null;

	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			response.setContentType("text/html");
			out = response.getWriter();
			int complaintId = Integer.parseInt(request.getParameter("complaintid"));
			int consumerKsebId = Integer.parseInt(request.getParameter("consumerksebid"));

			out.print("<html><head>");
			out.print("<title>Update Complaint Details</title>");
			out.print("<link rel='stylesheet' type='text/css' href='css/updatecomplaintdetails.css'></head>");
			out.print("<body>");
			out.print("<div id='compheading'><b>Update Complaint Details</b></div>");
			out.print("<div id='formdiv'>");
			out.print("<form name='complaint' id='complaint' method='post' action='persistupdatecomplaint'>");
			out.print("<label>Complaint Id </label>");
			out.print(
					"<input type='number' name='complaintid' value='" + complaintId + "' readonly='readonly'><br><br>");
			out.print("<label>Consumer Kseb Id </label>");
			out.print("<input type='number' name='consumerksebid' value='" + consumerKsebId
					+ "' readonly='readonly'><br><br>");
			out.print("<label>Complaint Description</label><br>");
			out.print(
					"<textarea name='complaintdetails' id='complaintdetails' rows='4' cols='50' placeholder='Please enter the complaint details...' required></textarea><br><br>");
			out.print("<label>Date</label>");
			out.print("<input type='date' name='complaintregdate' id='complaintregdate' required/><br><br><br>");
			out.print("<input type='submit' value='Submit' id='submitbutton'/></form></div>");
			out.print("<div id='home'>Go to HomePage:<a href='adminhome.html'>Home</a></div></body></html>");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
