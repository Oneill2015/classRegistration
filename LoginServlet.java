package edu.cmich.itc630;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.Collections;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

public class LoginServlet extends HttpServlet {

	public static Cache students;

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("text/html");

		String username = request.getParameter("username");
		String password = request.getParameter("password");

		for (int i = 1; i<4; i++) {
			String[] info = (String[]) students.get(i);
	   
			if (password.equals(info[1]) && username.equals(info[0])) {
				response.getWriter().println("Welcome " + username);
				RegisterServlet.passUser(students, i);
				request.getRequestDispatcher("login.html").include(request, response);

				return;
				
			} else if(i == 10){
				response.getWriter().println("Sorry, username or password error!");
				request.getRequestDispatcher("login.html").include(request, response);
			}
			
		}

	}

	public static void passCache2(Cache st) throws IOException {
		students = st;
	}

}
