package TestAPI;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.ApplicationPath;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.ClientProperties;

import TestAPI.Model.RequestModel;
import TestAPI.Model.ResponseModel;
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
	
	@POST
	@Path("/sendtoapi")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public ResponseModel sendToAPI(RequestModel rModel)
	{
		WebTarget webTarget;
		String host = "https://api.api.ai/v1/";
		
		ClientConfig clientConfig = new ClientConfig()
                .property(ClientProperties.READ_TIMEOUT, 30000)
                .property(ClientProperties.CONNECT_TIMEOUT, 5000);
		
		Client client = ClientBuilder.newClient(clientConfig);
 
        webTarget = client.target(host).path("query");
		        
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).header("Authorization", "Bearer 16096ae63c7a426abd25f1e4c23eb44f");
		
        Response response = invocationBuilder.post(Entity.entity(rModel, MediaType.APPLICATION_JSON));
        
        if (response.getStatus() != 200) {
 		   throw new RuntimeException("Failed : HTTP error code : "
 			+ response.getStatus());
 		}
        
        ResponseModel resModel = response.readEntity(ResponseModel.class);
                
		return resModel;
	}
}
