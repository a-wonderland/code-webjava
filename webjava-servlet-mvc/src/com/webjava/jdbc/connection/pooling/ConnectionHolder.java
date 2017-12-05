package com.webjava.jdbc.connection.pooling;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;

import com.webjava.jdbc.connection.exception.ConnectionException;

//singleton class
public final class ConnectionHolder {
    private static ConnectionHolder instance = null;
    private DataSource datasource = null;

    private ConnectionHolder() {
	super();
    }

    public static ConnectionHolder getInstance() {
	synchronized (Connection.class) {
	    if (instance == null) {
		instance = new ConnectionHolder();
		try {
		    instance.initAppDatasource();
		} catch (ConnectionException e) {
		    System.out.println(e.getMessage());
		}
	    }
	}
	return instance;
    }

    private void initAppDatasource() throws ConnectionException {
	try {
	    Context initContext = new InitialContext();
	    Context envContext = (Context) initContext.lookup("java:/comp/env");
	    this.datasource = (DataSource) envContext.lookup("jdbc/postgres");

	} catch (NamingException e) {
	    throw new ConnectionException("Unable to get the connection data to the database", e);
	}
    }

    public Connection getConnection() throws ConnectionException {
	try {
	    return datasource.getConnection();
	} catch (SQLException e) {
	    throw new ConnectionException("Unable to get a connection", e);
	}
    }

    public void dispose() throws ConnectionException {
	try {
	    BasicDataSource bds = (BasicDataSource) this.datasource;
	    bds.close();
	} catch (Exception e) {
	    throw new ConnectionException("Unable to close a connection", e);
	}
    }

    public void close(AutoCloseable closeable) {
	try {
	    if (closeable != null) {
		closeable.close();
	    }
	} catch (Exception e) {
	    System.out.println(e.getMessage());
	}
    }

}