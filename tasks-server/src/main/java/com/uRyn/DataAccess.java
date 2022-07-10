package com.uRyn;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.logging.FileHandler;
import java.util.logging.Level;

public class DataAccess {
	private List<TaskInfo> listTasks = new ArrayList<>();
	private static long TASK_ID = 0;
	private static DataAccess instance;
	private static Logger logger;
	
	public static DataAccess getInstance() {
		// not thread-safe
		// TODO
		if(instance == null) {
			instance = new DataAccess();
			logger = Logger.getLogger(DataAccess.class.getName());
			String logPath = "./tasks.log";
			try {
				FileHandler fileHandler = new FileHandler(logPath);
				logger.setUseParentHandlers(false);
				logger.addHandler(fileHandler);
			} catch (SecurityException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}	
		
		return instance;
	}
	
	private DataAccess() {
	}
	
	
	public List<TaskInfo> getTasks() {
		logger.log(Level.INFO, "listing all tasks");
		return listTasks;
	}
	
	public String getFormatedTasks() {
		String str = new String();
		for(long i = 0; i < listTasks.size(); ++i) {
			if(i != 0)
				str += "\n";
			str += listTasks.get((int)i).toString();
		}
		
		logger.log(Level.INFO, "Formated tasks: " + str);
		return str;
	}
	
	public String getFormatedTasks(long id) {
		String str = new String();
		for(TaskInfo task : listTasks) {
			if(task.getId() == id) {
				str = task.toString();
				break;
			}
		}
		
		logger.log(Level.INFO, "Get formated task by id: " + str);
		return str;
	}
	
	public String getFormatedTasks(Date date) {
		String str = new String();
		boolean firstPos = true;
		for(TaskInfo task : listTasks) {
			if(!task.getDate().equals(date))
				continue;
		
			if(firstPos)
				firstPos = false;
			else
				str += "\n";
			
			str += task.toString();
		}
		
		logger.log(Level.INFO, "Get formated task by date: " + str);
		return str;
	}
	
	public String getFormatedTasks(Date date, long id) {
		String str = new String();
		boolean firstPos = true;
		for(TaskInfo task : listTasks) {
			if(!task.getDate().equals(date))
				continue;
		
			if(task.getId()!= id)
				continue;
			
			if(firstPos)
				firstPos = false;
			else
				str += "\n";
			
			str += task.toString();
		}
		
		logger.log(Level.INFO, "Get formated task by id and date: " + str);
		
		return str;
	}
	
	public long addTask(String title, String date) {
		long id = -1;
		Date d = Utilities.stringToDate(date);
		if(d != null) {
			id = TASK_ID++;
			listTasks.add(new TaskInfo(title, d, id));
			logger.log(Level.INFO, "add task succeed: " + title + ", " + date);
		} else {
			logger.log(Level.WARNING, "add task faile: " + title + ", " + date);
		}

		return id;
	}
	
	public boolean deleteTask(long id) {
		for(TaskInfo task : listTasks) {
			if(task.getId() == id) {
				listTasks.remove(task);
				logger.log(Level.INFO, "delete task succeeded by id: " + id);
				return true;
			}
		}
		
		logger.log(Level.WARNING, "delete task failed by id: " + id);
		return false;
	}
	
	public boolean updateTask(boolean status, long id) {
		for(TaskInfo task : listTasks) {
			if(task.getId() == id) {
				task.setStatus(status);
				logger.log(Level.INFO, "update task succeeded by id: " + id);
				return true;
			}
		}
		
		logger.log(Level.WARNING, "update task failed by id: " + id);
		return false;
	}
}
