package edu.cmich.itc630;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.PreparedQuery;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.QueryResultIterable;
import com.google.appengine.api.datastore.QueryResultIterator;
import com.google.appengine.api.datastore.QueryResultList;
import com.google.appengine.api.datastore.Text;

@SuppressWarnings("serial")
public class AutoPopulateAPIServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
		resp.setContentType("text/html");
		resp.getWriter().println("<h1>Hello API</h>");
		PrintWriter out = resp.getWriter(); 

        String URL = req.getRequestURI();
        String [] URLParts = URL.split("/");

                DatastoreService ds = DatastoreServiceFactory.getDatastoreService();
        Query q = new Query("User");

        if(URLParts[3].equals("list")) {
            q.addSort("username");
            PreparedQuery pq = ds.prepare(q);

            for(Entity result: pq.asIterable()) {
                out.println(result.getProperty("username"));
                // Display the other properties here
            }


        } else if(URLParts[3].equals("searchByName")) {
            String queryName = URLParts[4];
            q.setFilter(new Query.FilterPredicate("username", FilterOperator.EQUAL, queryName));
            q.addSort("password");
            PreparedQuery pq = ds.prepare(q);

            for(Entity result: pq.asIterable()) {
                out.println(result.getProperty("password"));
                // Display the other properties here
            }

        } else {
            out.println("Unsupported action: " + URLParts[3]);
        }
	}
}