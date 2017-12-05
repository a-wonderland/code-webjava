/**
 * 
 */
package com.webjava.jdbc.dvd.dao;

import java.util.List;

import com.webjava.jdbc.dvd.domain.Film;

/**
 * @author sumyathtarwai
 *
 */
public interface IFilmDao {

    public List<Film> getPopularFilmByStoreId(int storeId, int rentalRate, boolean isRentRating) throws DaoException;
    public List<Film> findFilmByStoreIdKeyword(int storeId, String keyword, boolean isTsquery) throws DaoException;
}
