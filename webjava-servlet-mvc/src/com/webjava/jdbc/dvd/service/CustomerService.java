/**
 *
 */
package com.webjava.jdbc.dvd.service;

import com.webjava.jdbc.dvd.dao.CustomerDAO;
import com.webjava.jdbc.dvd.dao.DaoException;
import com.webjava.jdbc.dvd.dao.ICustomerDAO;
import com.webjava.jdbc.dvd.domain.Customer;

/**
 * @author sumyathtarwai
 *
 */
public class CustomerService {
	private ICustomerDAO dao = new CustomerDAO();

	public Customer getUserById(int id) throws DaoException {
		return dao.getUserById(id);
	}

	public long insertCustomer(Customer customer) throws DaoException {
		return dao.insertCustomer(customer);
	}
}
