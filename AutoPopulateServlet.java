package edu.cmich.itc630;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.QueryResultList;

@SuppressWarnings("serial")

public class AutoPopulateServlet extends HttpServlet {
	public int ONCE = 0;

	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException, NullPointerException {

		// Check password for auto-populate
		if(! req.getParameter("password").equals("student")) {
			resp.getWriter().println("Password is incorrect.  Try again!");
			return;
		}
		if (ONCE == 1) {
			resp.getWriter().println("Already Populated!");
			return;
			
		}
		Cache studentCache = null;
        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            studentCache = cacheFactory.createCache(Collections.emptyMap());
        } catch (CacheException e) {
            // ...
        }
		
		
		// Add user 'dre' and 'student' to the datastore as 'User' kind
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		
		
		// TODO: Should check if'dre' and 'student' are in ds and only add if needed
		int key = 1;
		String username = "Sean";
		String password = "foo";
		String classes = "";
		String[] facts = {username, password, classes};
		
		// Put the value into the cache.
		
		Map<Integer, String[]> st = new HashMap<Integer, String[]>();
		st.put(key, facts);
        studentCache.putAll(st);
        
        //repeat for all students
        key = 2;
		username = "Sara";
		password = "boo";
		classes = "";
		String[] facts2 = {username, password, classes};
		
		st.put(key, facts2);
		studentCache.putAll(st);
		
		key = 3;
		username = "Joseph";
		password = "zoo";
		classes = "";
		String[] facts3 = {username, password, classes};
		
		st.put(key, facts3);
		studentCache.putAll(st);
		
		
		
		LoginServlet.passCache2(studentCache);
		UserServlet.passCache(studentCache);
		
		
		resp.setContentType("text/html");
		resp.getWriter().println("<meta http-equiv=\"refresh\" content=\"3; URL='/autopopulate'\" />");
		ONCE = 1;
		
		/*req.setAttribute("student1", user);
		RequestDispatcher jsp = req.getRequestDispatcher("classregistration.jsp");
	    jsp.forward(req, resp);
		req.setAttribute("Student2", user2);*/
		
		
	}
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, NullPointerException, ServletException {
		resp.setContentType("text/html");

		// Get existing values from the datastore
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		Query q = new Query("User");
		q.addSort("password", Query.SortDirection.DESCENDING);
		PreparedQuery pq = ds.prepare(q);
		
		
		QueryResultList<Entity> results = pq.asQueryResultList(FetchOptions.Builder.withLimit(100));
		//resp.getWriter().println(results.size());
		
		req.setAttribute("passwords", results);
		RequestDispatcher jsp = req.getRequestDispatcher("/WEB-INF/autopopulate.jsp");
		jsp.forward(req,  resp);
	
	
		
	}
}
/*Entity user = new Entity("User");
		user.setProperty("username", "Sean");
		user.setProperty("password", "boo"); // Should save a one-way hash of pw here
		user.setProperty("key", 1);
		ds.put(user);
		
		user = new Entity("User");
		user.setProperty("username", "Sara");
		user.setProperty("password", "foo");
		user.setProperty("key", 2);
		ds.put(user);
		
		
		
		resp.getWriter().println("Done!");
		resp.getWriter().println(user);*/
