package edu.cmich.itc630;
//http://stackoverflow.com/questions/19405757/how-to-get-data-from-jsp-page-to-servlet

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.Collections;
import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

public class UserServlet extends HttpServlet {
	public static Cache nu;

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("username");
        String password = request.getParameter("password");
        response.getWriter().println("Name :"+ name);
        response.getWriter().println("password :"+ password);
    }
    
    public static void passCache(Cache cache2) throws IOException {
    	nu = cache2;
    }
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	if (nu == null) {
    		response.getWriter().println("AutoPopulate First!");
    		return;
    	}
    	String[] cName2 = (String[]) nu.get(1);
        response.getWriter().println(cName2[0] + "is taking " + cName2[2]);
        
        String[] cName3 = (String[]) nu.get(2);
        response.getWriter().println(cName3[0] + "is taking " + cName3[1]);
        
        String[] cName4 = (String[]) nu.get(3);
        response.getWriter().println(cName4[0] + "is taking " + cName4[2]);
        
        //String[] cName4 = (String[]) nu.get(6);
        //response.getWriter().println(cName4[3]);
        
        
    }

}