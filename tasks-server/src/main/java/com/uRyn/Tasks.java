package com.uRyn;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

/**
 * Root resource (exposed at "tasks" path)
 */
@Path("tasks")
public class Tasks {
	private DataAccess dataAccess = DataAccess.getInstance();

    @GET
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String getTask(@DefaultValue("")@QueryParam("filter") String filter, @DefaultValue("-1")@QueryParam("id") long id) {
    	if (dataAccess.getTasks().size() <= 0)
    		return "no tasks";
    	
    	if(!filter.isEmpty() && (filter.equalsIgnoreCase("expiring-today") || filter.equalsIgnoreCase("--expiring-today"))) {
    		if(id >= 0)
    			return dataAccess.getFormatedTasks(Utilities.dateOfToday(), id);
    		else
    			return dataAccess.getFormatedTasks(Utilities.dateOfToday());
    	} else {
    		if(id >= 0)
    			return dataAccess.getFormatedTasks(id);
    		else
    			return dataAccess.getFormatedTasks();
    	}
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public long addTask(@FormParam("title") String title, @FormParam("date") String date) {
    	long id = dataAccess.addTask(title, date);
    	return id;
    }
    
    @PUT
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.TEXT_PLAIN)
    public String updateTask(@FormParam("status") String status, @FormParam("id") long id) {
    	boolean done = status.equalsIgnoreCase("done");
    	return dataAccess.updateTask(done, id) ? "succeeded" : "failed";
    }
    
    @Path("/id/{id}")
    @DELETE
    @Produces(MediaType.TEXT_PLAIN)
    public String deleteTask(@PathParam("id") long id) {
    	return dataAccess.deleteTask(id) ? "succeeded" : "failed";
    }  
}
