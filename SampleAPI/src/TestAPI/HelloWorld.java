package TestAPI;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

@Path("/helloworld")
public class HelloWorld {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String getHello()
	{
		return "Hello World!!";
	}
}
