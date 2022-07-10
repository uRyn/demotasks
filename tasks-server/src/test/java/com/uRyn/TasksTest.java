package com.uRyn;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

import org.glassfish.grizzly.http.server.HttpServer;

import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TasksTest {

    private HttpServer server;
    private WebTarget target;
    private DataAccess dataAccess;
    
//    private void prepareTestData() {
//    	dataAccess.addTask("task1", "01/02/2010");
//    	dataAccess.addTask("task2", "02/03/2011");
//    	dataAccess.addTask("task3", "03/04/2012");
//    	dataAccess.addTask("task4", "04/05/2013");
//       	dataAccess.addTask("task5", Utilities.formatedDate(Utilities.dateOfToday()));
//    }

    @Before
    public void setUp() throws Exception {
        // start the server
        server = Main.startServer();
        // create the client
        Client c = ClientBuilder.newClient();

        // uncomment the following line if you want to enable
        // support for JSON in the client (you also have to uncomment
        // dependency on jersey-media-json module in pom.xml and Main.startServer())
        // --
        // c.configuration().enable(new org.glassfish.jersey.media.json.JsonJaxbFeature());

        target = c.target(Main.BASE_URI);     
        dataAccess = DataAccess.getInstance();
    }

    @After
    public void tearDown() throws Exception {
        server.stop();
    }
  
    @Test
    public void _1_testGetEmptyTask() {
        String responseMsg = target.path("tasks").request().get(String.class);
        assertEquals("no tasks", responseMsg);
    }
    
    @Test
    public void _2_testAddTask() {
    	// set up
    	Map<String, String> map = new HashMap<>();
        map.put("title", "task_2_testAddTask");
        map.put("date", "9/7/2022");
        String res = Utilities.sendPost(Main.BASE_URI + "tasks/", Utilities.toUrlParams(map));
        long id = Long.parseLong(res);
        
        // execute test
        target = target.queryParam("id", id);
    	String responseMsg = target.path("tasks").request().get(String.class);
    	String expected = "Title: task_2_testAddTask, date: 09/07/2022, Status: not done, id: " + id;
        assertEquals(expected, responseMsg);
        
        // tear down
        // TODO
    }
    
    @Test
    public void _3_testUpdateTask() {
    	// set up
    	Map<String, String> map = new HashMap<>();
        map.put("title", "task_3_testUpdateTask");
        map.put("date", "9/7/2023");
        String res = Utilities.sendPost(Main.BASE_URI + "tasks/", Utilities.toUrlParams(map));
        long id = Long.parseLong(res);
        map.clear();
        map.put("status", "done");
        map.put("id", res);
        Utilities.sendPut(Main.BASE_URI + "tasks/", Utilities.toUrlParams(map));
            	
        // execute test
        target = target.queryParam("id", id);
    	String responseMsg = target.path("tasks").request().get(String.class);
    	String expected = "Title: task_3_testUpdateTask, date: 09/07/2023, Status: done, id: " + id;
        assertEquals(expected, responseMsg);
        
        // tear down
        // TODO
    }
    
    @Test
    public void _4_testDeleteTask() {
    	// set up
    	Map<String, String> map = new HashMap<>();
        map.put("title", "task_4_testDeleteTask");
        map.put("date", "9/7/2024");
        String res = Utilities.sendPost(Main.BASE_URI + "tasks/", Utilities.toUrlParams(map));
        long id = Long.parseLong(res);      
        Utilities.sendDelete(Main.BASE_URI + "tasks/id/" + res, null);

        // execute test
    	target = target.queryParam("id", id);
    	String responseMsg = target.path("tasks").request().get(String.class);
    	assertTrue(responseMsg.isEmpty() || responseMsg.equalsIgnoreCase("no tasks"));

        // tear down
        // TODO
    }
    
  @Test
  public void _5_testGetAllTask() {
	  String responseMsg = target.path("tasks").request().get(String.class);        
      assertEquals(dataAccess.getFormatedTasks(), responseMsg);
  }
}
