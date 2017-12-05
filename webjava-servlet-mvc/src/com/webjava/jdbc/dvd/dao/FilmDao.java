/**
 *
 */
package com.webjava.jdbc.dvd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.webjava.jdbc.connection.exception.ConnectionException;
import com.webjava.jdbc.connection.pooling.ConnectionHolder;
import com.webjava.jdbc.dvd.domain.Film;

/**
 * @author sumyathtarwai
 *
 */
public class FilmDao implements IFilmDao {

    /**
     *
     */
    public FilmDao() {
	// TODO Auto-generated constructor stub
    }

    /*
     * (non-Javadoc)
     *
     * @see com.webjava.jdbc.dvd.dao.IFilmDao#getPopularFilmByStoreId(int, int,
     * java.lang.String, boolean)
     */
    @Override
    public List<Film> getPopularFilmByStoreId(int storeId, int rentalRate, boolean isRentRating)
	    throws DaoException {
	List<Film> films = new ArrayList<>();
	String SQL = "SELECT DISTINCT ficview.film_id, ficview.title, ficview.description, ficview.price, ficview.special_features, "
		+ "ficview.category_name, ficview.rental_rate, ficview.rating, "
		+ "(SELECT COUNT(film_id) AS quantity FROM inventory inv WHERE inv.film_id = ficview.film_id GROUP BY ficview.film_id) "
		+ "FROM film_inventory_category ficview WHERE ficview.store_id=? AND ficview.rental_rate>=? ORDER BY quantity DESC LIMIT 20";
	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		PreparedStatement pt = connection.prepareStatement(SQL)) {

	    if(isRentRating){
		pt.setInt(1, storeId);
		pt.setInt(2, rentalRate);
	    }else {
		// do something
	    }

	    ResultSet rs = pt.executeQuery();
	    while (rs.next()) {
		Film film = new Film(rs.getInt("film_id"), rs.getString("title"),  rs.getString("category_name"),
			rs.getString("description"), rs.getFloat("price"), rs.getString("special_features"), rs.getString("rating"),
			rs.getFloat("rental_rate"));
		// add to list
		films.add(film);
	    }

	} catch (SQLException | ConnectionException e) {
	    throw new DaoException(e.getMessage());
	}
	return films;
    }

    /*
     * (non-Javadoc)
     *
     * @see com.webjava.jdbc.dvd.dao.IFilmDao#findFilmByStoreIdKeyword(int,
     * java.lang.String, boolean)
     */
    @Override
    public List<Film> findFilmByStoreIdKeyword(int storeId, String keyword, boolean isTsquery) throws DaoException {
    	List<Film> films = new ArrayList<>();
    	String SQL = "SELECT DISTINCT ficview.film_id, ficview.title, ficview.description, ficview.price, ficview.special_features, "
    		+ "ficview.category_name, ficview.rental_rate, ficview.rating, "
    		+ "(SELECT COUNT(film_id) AS quantity FROM inventory inv WHERE inv.film_id = ficview.film_id GROUP BY ficview.film_id) "
    		+ "FROM film_inventory_category ficview WHERE ficview.store_id=? AND lower(ficview.title) LIKE ? ORDER BY quantity DESC";
    	try (Connection connection = ConnectionHolder.getInstance().getConnection();
    		PreparedStatement pt = connection.prepareStatement(SQL)) {

    	    if(!isTsquery){
    		pt.setInt(1, storeId);
    		pt.setString(2, "%"+keyword+"%");
    	    }else {
    		// do something
    	    }

    	    ResultSet rs = pt.executeQuery();
    	    while (rs.next()) {
    		Film film = new Film(rs.getInt("film_id"), rs.getString("title"),  rs.getString("category_name"),
    			rs.getString("description"), rs.getFloat("price"), rs.getString("special_features"), rs.getString("rating"),
    			rs.getFloat("rental_rate"));
    		// add to list
    		films.add(film);
    	    }

    	} catch (SQLException | ConnectionException e) {
    	    throw new DaoException(e.getMessage());
    	}
    	return films;
    }

}
