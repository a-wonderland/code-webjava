/**
 *
 */
package com.webjava.jdbc.dvd.service;

import java.util.List;

import com.webjava.jdbc.dvd.dao.DaoException;
import com.webjava.jdbc.dvd.dao.FilmDao;
import com.webjava.jdbc.dvd.dao.IFilmDao;
import com.webjava.jdbc.dvd.domain.Film;

/**
 * @author sumyathtarwai
 *
 */
public class FilmService {
    IFilmDao dao = new FilmDao();

    /**
     *
     */
    public FilmService() {
	// TODO Auto-generated constructor stub
    }

    public List<Film> getPopularFilmByStoreId(int storeId, int rentalRate, boolean isRentRating) throws ServiceException {
	try {
	    return dao.getPopularFilmByStoreId(storeId, rentalRate, isRentRating);
	} catch (DaoException e) {
	   throw new ServiceException(e.getMessage());
	}
    }
    public List<Film> findFilmByStoreIdKeyword(int storeId, String keyword, boolean isTsquery) throws ServiceException {
		try {
			return dao.findFilmByStoreIdKeyword(storeId, keyword, isTsquery);
		} catch (DaoException e) {
			throw new ServiceException(e.getMessage());
		}

    }
}
