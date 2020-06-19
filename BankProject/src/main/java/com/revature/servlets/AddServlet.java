package com.revature.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AddServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void init() throws ServletException {

		System.out.println(this.getServletName() + " is instantiated!");
		super.init();
	}

	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		System.out.println(this.getServletName() + " service method called!");
		
		String number1 = req.getParameter("number1");
		String number2 = req.getParameter("number2");
		
		int sum = Integer.parseInt(number1) + Integer.parseInt(number2);
		
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();
		out.println("<h1>The result is :" + sum +"</h1>");
	}

	public void destroy() {
		System.out.println(this.getServletName() + " destroy method called!");
		super.destroy();
	}

}
