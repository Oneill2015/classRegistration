package edu.cmich.itc630;

import java.io.IOException;
import java.util.Map;
import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegisterServlet extends HttpServlet {
	public static Cache user;
	public static Cache courses;
	public static int userInt;
	public static int proof = 0;
	public static String schedule = "";

	// Passes Users cache
	public static void passUser(Cache cache, int i) {
		user = cache;
		userInt = i;
		proof = 1;

	}

	public void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws IOException, ServletException, NullPointerException {
		// makes sure user logged in
		if (proof == 0) {
			resp.getWriter().println("Login First!");
			return;
		}
		// grab info on which class to take
		String course = req.getParameter("course");
		String time = req.getParameter("time");
		String days = req.getParameter("days");
		// grab specific student who logged in
		String[] info = (String[]) user.get(userInt);

		if (schedule.equals("")) {
			schedule = course;
			info[2] = schedule;
			resp.getWriter().println(info[2]);
			UserServlet.passCache(user);
		} else {
			schedule = course + " " + schedule;
			info[2] = schedule;
			resp.getWriter().println(info[2]);
			UserServlet.passCache(user);

		}

	}

	// passes courses from AutoPopCourse
	public static void passCourses(Cache cache2) {
		courses = cache2;

	}

}
