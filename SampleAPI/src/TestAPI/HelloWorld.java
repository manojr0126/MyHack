package TestAPI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import TestAPI.Model.TestModel;

@Path("/helloworld")
public class HelloWorld {

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public TestModel getHello()
	{
		TestModel tstModel = new TestModel();
		
		tstModel.setMessageText("Hello World!! - Tested");
		
		return tstModel;
	}
	
	@POST
	@Path("/post")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public TestModel postHello(TestModel tstModel)
	{		
		TestModel tstResult = new TestModel();
		
		tstResult.setMessageText(tstModel.getMessageText());
		
		return tstResult;
	}
}
