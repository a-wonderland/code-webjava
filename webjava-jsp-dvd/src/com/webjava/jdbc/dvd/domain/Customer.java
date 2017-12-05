/**
 *
 */
package com.webjava.jdbc.dvd.domain;

import java.time.LocalDate;

/**
 * @author sumyathtarwai
 *
 */
public class Customer {
    // auto-generated
    private long id;
    private int storeId;
    private String name;
    private String firstName;
    private String lastName;
    private String email;
    private int addressId;
    private LocalDate birthDate;
    private String ph;
    private String address;
    private String password;


    public Customer(String name, LocalDate birthDate, String ph, String address, String password) {
	super();
	this.name = name;
	this.birthDate = birthDate;
	this.ph = ph;
	this.address = address;
	this.password = password;
    }

    public Customer(int storeId, String firstName, String lastName, String email, int addressId,
			String password) {
		super();
		this.storeId = storeId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.addressId = addressId;
		this.password = password;
	}


	public Customer(int id, String password) {
	super();
	this.id = id;
	this.password = password;
    }

    public long getId() {
	return id;
    }

    public void setId(long id) {
	this.id = id;
    }

    public String getName() {
	return name;
    }

    public void setName(String name) {
	this.name = name;
    }

    public LocalDate getBirthDate() {
	return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
	this.birthDate = birthDate;
    }

    public String getPh() {
	return ph;
    }

    public void setPh(String ph) {
	this.ph = ph;
    }

    public String getAddress() {
	return address;
    }

    public void setAddress(String address) {
	this.address = address;
    }

    public String getPassword() {
	return password;
    }

    public void setPassword(String password) {
	this.password = password;
    }


    public int getStoreId() {
		return storeId;
	}

	public void setStoreId(int storeId) {
		this.storeId = storeId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAddressId() {
		return addressId;
	}

	public void setAddressId(int addressId) {
		this.addressId = addressId;
	}

	@Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + (int) (id ^ (id >>> 32));
	result = prime * result + ((password == null) ? 0 : password.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Customer other = (Customer) obj;
	if (id != other.id)
	    return false;
	if (password == null) {
	    if (other.password != null)
		return false;
	} else if (!password.equals(other.password))
	    return false;
	return true;
    }


}
