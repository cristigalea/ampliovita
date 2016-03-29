package ampliovita.utils.interogate;

//servlet & core
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//appengine
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.FetchOptions;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Query;

@SuppressWarnings("serial")
public class ListAllDoctorsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String namespace = "ampliovita";
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key patientKey = KeyFactory.createKey("Doctor", namespace);

		Query query = new Query("Doctor", patientKey);

		List<Entity> doctorList = datastore.prepare(query).asList(FetchOptions.Builder.withOffset(0));
		if(doctorList.isEmpty() == false){
			out.println("<table>");
			out.println("<tr><th>First Name</th><th>Last Name</th><th>Specialization</th><th>Username</th><th>Password</th></tr>");
			for(Entity doctor : doctorList){
				out.println("<tr>");
				out.println("<td>" + doctor.getProperty("firstName") + "</td>");
				out.println("<td>" + doctor.getProperty("lastName") + "</td>");
				out.println("<td>" + doctor.getProperty("specialization") + "</td>");
				out.println("<td>" + doctor.getProperty("username") + "</td>");
				out.println("<td>" + doctor.getProperty("password") + "</td>");
			}
			out.println("</table>");
		}else{
			out.println("<h3>Empty Datastore</h3>");
		}
		out.println("<a href='index.html'>Back to dashboard</a>");
	}

}
