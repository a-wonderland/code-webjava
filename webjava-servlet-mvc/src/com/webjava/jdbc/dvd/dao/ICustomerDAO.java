/**
 *
 */
package com.webjava.jdbc.dvd.dao;

import com.webjava.jdbc.dvd.domain.Customer;


/**
 * @author sumyathtarwai
 *
 */
public interface ICustomerDAO {

    public Customer getUserById(int id) throws DaoException;
    public long selectCountry() throws DaoException;
    public long insertCustomer(Customer customer) throws DaoException;
}
