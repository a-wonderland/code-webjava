package com.webjava.jdbc.dvd.dao;

import java.util.List;

import com.webjava.jdbc.dvd.domain.Actor;
import com.webjava.jdbc.dvd.domain.dto.ActorDetailDTO;

public interface IActorDao {
    public int getActorCount() throws DaoException;

    public List<Actor> getActors() throws DaoException;

    public List<Actor> getActors(int offset) throws DaoException;
    
    public Actor findActorById(int actorID) throws DaoException;
    
    public List<Actor> findActorByName(String fName, String lName, boolean isFullName) throws DaoException;
    
    public List<ActorDetailDTO> findActorDetailbyId(int actorId) throws DaoException;

    public long insertActor(Actor actor) throws DaoException;

    public void insertActors(List<Actor> list) throws DaoException;

    public int updateLastName(int id, String lastName) throws DaoException;

    public int deleteActor(int id) throws DaoException;

    public String properCase(String s) throws DaoException;

    public long addActorAndAssignFilm(Actor actor, int filmId) throws DaoException;
}
