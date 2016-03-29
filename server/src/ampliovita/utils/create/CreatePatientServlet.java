package ampliovita.utils.create;

//servlet & core
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.servlet.http.*;

import java.util.Date;

//appengine
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;


@SuppressWarnings("serial")
public class CreatePatientServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String namespace = "ampliovita";
		
		Key patientKey = KeyFactory.createKey("Patient", namespace);
		Entity patient = new Entity("Patient", patientKey);
		
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");
		Date dateOfBirth = new Date();
		try {
			dateOfBirth = new SimpleDateFormat("MM/dd/yyyy").parse(req.getParameter("dob"));
		} catch (ParseException e) {
			e.printStackTrace();
		} 
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		
		patient.setProperty("firstName", firstName);
		patient.setProperty("lastName", lastName);
		patient.setProperty("dateOfBirth", dateOfBirth);
		patient.setProperty("username", username);
		patient.setProperty("password",password);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(patient);
		
		resp.sendRedirect("/createPatient.html");
	}
}
