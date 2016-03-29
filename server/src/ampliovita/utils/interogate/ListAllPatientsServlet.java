package ampliovita.utils.interogate;

//servlet & core
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
public class ListAllPatientsServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		resp.setContentType("text/html");
		PrintWriter out = resp.getWriter();

		String namespace = "ampliovita";

		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key patientKey = KeyFactory.createKey("Patient", namespace);

		Query query = new Query("Patient", patientKey);

		List<Entity> patientList = datastore.prepare(query).asList(FetchOptions.Builder.withOffset(0));
		if(patientList.isEmpty() == false){
			DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
			out.println("<table>");
			out.println("<tr><th>First Name</th><th>Last Name</th><th>Date of Birth</th><th>Username</th><th>Password</th></tr>");
			for(Entity patient : patientList){
				out.println("<tr>");
				out.println("<td>" + patient.getProperty("firstName") + "</td>");
				out.println("<td>" + patient.getProperty("lastName") + "</td>");
				out.println("<td>" + df.format(patient.getProperty("dateOfBirth")) + "</td>");
				out.println("<td>" + patient.getProperty("username") + "</td>");
				out.println("<td>" + patient.getProperty("password") + "</td>");
				out.println("</tr>");
			}
			out.println("</table>");
		}else{
			out.println("<h3>Empty Datastore</h3>");
		}
		out.println("<a href='index.html'>Back to dashboard</a>");
	}}
