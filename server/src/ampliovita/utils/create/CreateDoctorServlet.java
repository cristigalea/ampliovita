package ampliovita.utils.create;

//servlet & core
import java.io.IOException;
import javax.servlet.http.*;

//appengine
import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

@SuppressWarnings("serial")
public class CreateDoctorServlet extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws IOException {
		
		String namespace = "ampliovita";
		
		Key doctorKey = KeyFactory.createKey("Doctor", namespace);
		Entity doctor = new Entity("Doctor", doctorKey);
		
		String firstName = req.getParameter("firstname");
		String lastName = req.getParameter("lastname");		
		String specialization = req.getParameter("specialization");
		String username = req.getParameter("username");
		String password = req.getParameter("password");
		
		doctor.setProperty("firstName", firstName);
		doctor.setProperty("lastName", lastName);
		doctor.setProperty("specialization", specialization);
		doctor.setProperty("username", username);
		doctor.setProperty("password", password);
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		datastore.put(doctor);
		
		resp.sendRedirect("/createDoctor.html");
		
	}

}
