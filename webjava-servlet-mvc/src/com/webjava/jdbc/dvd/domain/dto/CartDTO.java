package com.webjava.jdbc.dvd.domain.dto;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {

    private List<FilmDTO> cartItems = new ArrayList<>(); // List of CartItems

    public List<FilmDTO> getCartItems() {
	return cartItems;
    }

    public void setCartItems(List<FilmDTO> cartItems) {
	this.cartItems = cartItems;
    }

    public synchronized void addCart(int filmId, String title, String categoryName, String description, float price, String rating,
	    float rentalRate) {
	if (null != cartItems) {
	    FilmDTO filmDto = new FilmDTO(filmId, title, categoryName, description, price, rating, rentalRate);
	    cartItems.add(filmDto);
	}else{
	    return;
	}
    }

    public synchronized void updateCart(int filmId, String title, String categoryName, String description, float price,
	    String rating, float rentalRate) {
	// TODO do something
    }

    public synchronized void removeCart(int index) {
	if (null != cartItems && index < cartItems.size()) {
	   cartItems.remove(index);
	}else {
	    return;
	}
    }

}
