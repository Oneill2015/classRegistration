package edu.cmich.itc630;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.Blob;
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;

import java.util.Collections;
import javax.cache.Cache;
import javax.cache.CacheException;
import javax.cache.CacheFactory;
import javax.cache.CacheManager;

@SuppressWarnings("serial")
public class AutoPopClassServlet extends HttpServlet {
	public int ONCE = 0;
	public void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

		// Check password for auto-populate
		if(! req.getParameter("password2").equals("class")) {
			resp.getWriter().println("Password is incorrect.  Try again!");
			return;
		}
		
		if (ONCE == 1) {
			resp.getWriter().println("Already Populated!");
			return;
			
		}
		Cache cache2 = null;
        try {
            CacheFactory cacheFactory = CacheManager.getInstance().getCacheFactory();
            cache2 = cacheFactory.createCache(Collections.emptyMap());
        } catch (CacheException e) {
            // ...
        }
		
		// Add user 'dre' and 'student' to the datastore as 'User' kind
		DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
		// TODO: Should check if'dre' and 'student' are in ds and only add if needed
		
		int key = 11;
		String cName = "ITC630";
		String size = "20";
		String days = "mwf";
		String time = "9am";
		String[] facts = {cName, size, days, time};
		
		// Put the value into the cache.
		
		Map<Integer, String[]> cl = new HashMap<Integer, String[]>();
		cl.put(key, facts);
        cache2.putAll(cl);
        
        //repeat for all classes
        key = 12;
		cName = "CPS180";
		size = "35";
		days = "th";
		time = "3pm";
		String[] facts2 = {cName, size, days, time};
		
		cl.put(key, facts2);
		cache2.putAll(cl);
		
		key = 13;
		cName = "CPS680";
		size = "15";
		days = "mwf";
		time = "12pm";
		String[] facts3 = {cName, size, days, time};
		
		cl.put(key, facts3);
		cache2.putAll(cl);
		
		key = 14;
		cName = "CPS680";
		size = "15";
		days = "mwf";
		time = "11am";
		String[] facts4 = {cName, size, days, time};
		
		cl.put(key, facts4);
		cache2.putAll(cl);
		
		key = 15;
		cName = "CPS685";
		size = "35";
		days = "th";
		time = "5pm";
		String[] facts5 = {cName, size, days, time};
		
		cl.put(key, facts5);
		cache2.putAll(cl);

		
		key = 16;
		cName = "CPS686";
		size = "26";
		days = "mwf";
		time = "12pm";
		String[] facts6 = {cName, size, days, time};
		
		cl.put(key, facts2);
		cache2.putAll(cl);
		
		key = 17;
		cName = "ITC630";
		size = "25";
		days = "mwf";
		time = "11am";
		String[] facts7 = {cName, size, days, time};
		
		cl.put(key, facts2);
		cache2.putAll(cl);
		
	

        // Get the value from the cache.
        String[] cName2 = (String[]) cache2.get(11);
        resp.getWriter().println(cName2);
        
        String[] cName3 = (String[]) cache2.get(12);
        resp.getWriter().println(cName3[0]);
		
		
		resp.getWriter().println("Done!");
		
		//update once so autopopulate isnt called more than once.
		ONCE = 1;
		UserServlet.passCache(cache2);
		RegisterServlet.passCourses(cache2);
		
		
	}
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
		resp.setContentType("text/plain");
		resp.getWriter().println("Hello, world");
	
	
		
	}
}
/*http://dustin.sallings.org/java-memcached-client/apidocs/net/spy/memcached/CacheMap.html
 * https://groups.google.com/forum/#!topic/memcached/MeuXn7iaczM
 * http://stackoverflow.com/questions/15097801/hashmap-cache-in-servlet
 * https://cloud.google.com/appengine/docs/java/memcache/using#retrieving_cache_statistics
 * https://cloud.google.com/appengine/docs/java/memcache/using*/

/*Entity course = new Entity("Course");
course.setProperty("cName", "CPS180");
course.setProperty("size", "30"); // Should save a one-way hash of pw here
ds.put(course);

Entity course2 = new Entity("Course");
course2.setProperty("cName", "ITC630");
course2.setProperty("size", "20");
ds.put(course2);*/
