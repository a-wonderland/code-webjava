/**
 *
 */
package com.webjava.jdbc.dvd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.webjava.jdbc.connection.exception.ConnectionException;
import com.webjava.jdbc.connection.pooling.ConnectionHolder;
import com.webjava.jdbc.dvd.domain.Customer;

/**
 * @author sumyathtarwai
 *
 */
public class CustomerDAO implements ICustomerDAO {

    /*
     * (non-Javadoc)
     *
     * @see com.webjava.jdbc.customer.dao.ICustomerDAO#getUserById(int)
     */
    @Override
    public Customer getUserById(int id) throws DaoException {
	Customer user = null;
	String SQL = "SELECT * FROM users WHERE user_id=?";
	String SQL2 = "SELECT store_id FROM customer WHERE customer_id=?";
	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		PreparedStatement pt = connection.prepareStatement(SQL)) {

	    pt.setInt(1, id);
	    ResultSet rsUsr = pt.executeQuery();
	    if (rsUsr.next()) {
		user = new Customer(rsUsr.getInt("user_id"), rsUsr.getString("password"));
		// get store id
		PreparedStatement ptCus = connection.prepareStatement(SQL2);
		ptCus.setInt(1, id);
		ResultSet rsCus = ptCus.executeQuery();
		 if (rsCus.next()) {
			user.setStoreId(rsCus.getInt("store_id"));
		 }else {
		     user = null;
		 }
	    }

	} catch (SQLException | ConnectionException e) {
	    throw new DaoException(e.getMessage());
	}
	return user;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.webjava.jdbc.customer.dao.ICustomerDAO#InsertCustomer(com.webjava.
     * jdbc.customer.domain.Customer)
     */
    @Override
    public long insertCustomer(Customer customer) {
	String SQL_CUSTOMER = "INSERT INTO customer (store_id, first_name, last_name, email, address_id) VALUES (?, ?, ?, ?, ?)";
	//String SQL_USER = "INSERT INTO users (user_id, password, last_update) VALUES (?, ?, now())";
	String SQL_USER = "INSERT INTO users (user_id, password) VALUES (?, ?)";
	long successFlag = 0;
	int record = 0;
	Connection connection = null;
	try {
	    connection = ConnectionHolder.getInstance().getConnection();
	    connection.setAutoCommit(false);
	    // create statement which return auto-generated id
	    PreparedStatement customerPt = connection.prepareStatement(SQL_CUSTOMER, Statement.RETURN_GENERATED_KEYS);
	    // prepare customer
	    customerPt.setInt(1, customer.getStoreId());
	    customerPt.setString(2, customer.getFirstName());
	    customerPt.setString(3, customer.getLastName());
	    customerPt.setString(4, customer.getEmail());
	    customerPt.setInt(5, customer.getAddressId());
	    // execute
	    record = customerPt.executeUpdate();

	    // if customer insert success, prepare for user
	    if (record > 0) {
		// get auto-generated id
		ResultSet customerRs = customerPt.getGeneratedKeys();
		long id = 0;
		if (customerRs.next()) {
		    id = customerRs.getLong("customer_id");
		}
		PreparedStatement userPt = connection.prepareStatement(SQL_USER);
		// prepare user
		userPt.setLong(1, id);
		userPt.setString(2, customer.getPassword());
		// execute
		record = userPt.executeUpdate();
		// if user insert fail, rollback
		if (record < 0) {
		    connection.rollback();
		} else {
		    successFlag = id;
		}
	    } else {
		connection.rollback();
	    }
	    // everything ok
	    connection.commit();
	}
	// handling connection
	catch (SQLException | ConnectionException e) {
		System.out.println(e.getMessage());
	    try {
		if (connection != null) {
		    // rollback
		    connection.rollback();
		}
	    } catch (SQLException e1) {
		System.out.println(e1.getMessage());
	    }

	} finally {
	    ConnectionHolder.getInstance().close(connection);
	}

	return successFlag;
    }

	@Override
	public long selectCountry() throws DaoException {
		// TODO 自動生成されたメソッド・スタブ
		return 0;
	}

}
