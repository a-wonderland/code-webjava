package com.webjava.jdbc.dvd.service;

import java.util.List;

import com.webjava.jdbc.dvd.dao.ActorDao;
import com.webjava.jdbc.dvd.dao.DaoException;
import com.webjava.jdbc.dvd.dao.IActorDao;
import com.webjava.jdbc.dvd.domain.Actor;

public class ActorService {
    IActorDao actorDao = new ActorDao();

    public int getActorCount() throws ServiceException {
	try {
	    return actorDao.getActorCount();
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage());
	}
    }

    public List<Actor> getActors() throws ServiceException {
	try {
	    return actorDao.getActors();
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage());
	}

    }

    public List<Actor> getActors(int offset) throws ServiceException {
	try {
	    return actorDao.getActors(offset);
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage());
	}

    }
    
    public Actor findActorByID(int actorID) throws ServiceException {
	try {
	    return actorDao.findActorByID(actorID);
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage());
	}
    }

    public long insertActor(Actor actor) throws ServiceException {
	try {
	    return actorDao.insertActor(actor);
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage());
	}
    }

    public void insertActors(List<Actor> list) throws ServiceException {
	try {
	    actorDao.insertActors(list);
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage());
	}
    }

    public int updateLastName(int id, String lastName) throws ServiceException {
	try {
	    return actorDao.updateLastName(id, lastName);
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage());
	}
    }

    public int deleteActor(int id) throws ServiceException {
	try {
	    return actorDao.deleteActor(id);
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage());
	}
    }

    public String properCase(String s) throws ServiceException {
	try {
	    return actorDao.properCase(s);
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage());
	}
    }

    public long addActorAndAssignFilm(Actor actor, int filmId) throws ServiceException {
	try {
	    return actorDao.addActorAndAssignFilm(actor, filmId);
	} catch (DaoException e) {
	    throw new ServiceException(e.getMessage());
	}
    }
}