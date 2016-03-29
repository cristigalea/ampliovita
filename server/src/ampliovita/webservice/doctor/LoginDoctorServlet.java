package ampliovita.webservice.doctor;

//servlet & core
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//appengine
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;
import com.google.appengine.api.datastore.Query.CompositeFilterOperator;
import com.google.appengine.api.datastore.Query.Filter;
import com.google.appengine.api.datastore.Query.FilterOperator;
import com.google.appengine.api.datastore.Query.FilterPredicate;


@SuppressWarnings("serial")
public class LoginDoctorServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String usr = req.getParameter("username");
		String pass= req.getParameter("password");
		
		resp.setContentType("application/json");
		PrintWriter out = resp.getWriter();
	
		String namespace = "ampliovita";		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key doctorKey = KeyFactory.createKey("Doctor", namespace);
		Query query = new Query("Doctor",doctorKey);
		Filter validUser = new FilterPredicate("username", FilterOperator.EQUAL, usr);
		Filter validPass = new FilterPredicate("password", FilterOperator.EQUAL, pass);
		query.setFilter(CompositeFilterOperator.and(validUser,validPass));
		
		Entity doctor = datastore.prepare(query).asSingleEntity();
		if(doctor != null){
			out.println("{\"login\": \"valid\"}");
		}else{
			out.println("{\"login\": \"invalid\"}");
		}
	}
}