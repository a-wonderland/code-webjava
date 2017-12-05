package com.webjava.servlet.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.webjava.jdbc.connection.exception.ConnectionException;
import com.webjava.jdbc.connection.pooling.ConnectionHolder;

/**
 * Application Lifecycle Listener implementation class ContextListener
 *
 */
@WebListener
public class ContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public ContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         System.out.println("DB Close");
         try {
	  if(null != ConnectionHolder.getInstance()){
	      ConnectionHolder.getInstance().dispose();
	  }
	} catch (ConnectionException e) {
	   System.out.println(e.getMessage());
	}
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }
	
}
