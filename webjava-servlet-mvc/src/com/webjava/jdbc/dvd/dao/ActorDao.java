package com.webjava.jdbc.dvd.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.webjava.jdbc.connection.exception.ConnectionException;
import com.webjava.jdbc.connection.pooling.ConnectionHolder;
import com.webjava.jdbc.dvd.domain.Actor;
import com.webjava.jdbc.dvd.domain.dto.ActorDetailDTO;

public class ActorDao implements IActorDao {

    /*
     * (non-Javadoc)
     * 
     * @see com.webjava.jdbc.dao.IActorDao#getActorCount()
     */
    @Override
    public int getActorCount() throws DaoException {
	String SQL = "SELECT count(*) FROM actor";
	int count = 0;
	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQL)) {
	    rs.next();
	    count = rs.getInt(1);
	} catch (SQLException | ConnectionException ex) {
	    throw new DaoException(ex.getMessage());
	}
	return count;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.webjava.jdbc.dao.IActorDao#getActors()
     */
    @Override
    public List<Actor> getActors() throws DaoException {
	List<Actor> actors = new ArrayList<>();

	String SQL = "SELECT actor_id,first_name, last_name FROM actor";
	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQL)) {
	    while (rs.next()) {
		Actor actor = new Actor(rs.getInt("actor_id"), rs.getString("first_name"), rs.getString("last_name"));
		actors.add(actor);
	    }
	} catch (SQLException | ConnectionException ex) {
	    throw new DaoException(ex.getMessage());
	}
	return actors;

    }

    @Override
    public List<Actor> getActors(int offset) throws DaoException {
	List<Actor> actors = new ArrayList<>();

	String SQL = "SELECT actor_id,first_name, last_name FROM actor ORDER BY actor_id LIMIT 20 OFFSET " + offset
		+ " ";
	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		Statement stmt = connection.createStatement();
		ResultSet rs = stmt.executeQuery(SQL)) {
	    while (rs.next()) {
		Actor actor = new Actor(rs.getInt("actor_id"), rs.getString("first_name"), rs.getString("last_name"));
		actors.add(actor);
	    }
	} catch (SQLException | ConnectionException ex) {
	    throw new DaoException(ex.getMessage());
	}
	return actors;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.webjava.jdbc.dao.IActorDao#findActorByID(int)
     */
    @Override
    public Actor findActorById(int actorID) throws DaoException {
	String SQL = "SELECT * FROM actor WHERE actor_id=?";
	Actor actor = null;
	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		PreparedStatement pt = connection.prepareStatement(SQL);) {
	    pt.setInt(1, actorID);
	    // execute
	    ResultSet rs = pt.executeQuery();
	    // move pointer
	    if (rs.next()) {
		// create actor
		actor = new Actor(rs.getInt("actor_id"), rs.getString("first_name"), rs.getString("last_name"));
	    }

	} catch (SQLException | ConnectionException ex) {
	    System.out.println("System Error has occured!");
	    throw new DaoException(ex.getMessage());
	}
	return actor;
    }

    /*
     * Add new record to Actor table. If insert success, return generated new
     * ID.
     * 
     * @see
     * com.webjava.jdbc.dao.IActorDao#insertActor(com.webjava.jdbc.domain.Actor)
     */
    @Override
    public long insertActor(Actor actor) throws DaoException {
	String SQL = "INSERT INTO actor (first_name, last_name) VALUES (?,?)";
	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		PreparedStatement pt = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS)) {
	    pt.setString(1, actor.getFirstName());
	    pt.setString(2, actor.getLastName());
	    // execute
	    int record = pt.executeUpdate();
	    // 0 for SQL statements that return nothing
	    if (record > 0) {
		// when record insert success, get gen key
		ResultSet rs = pt.getGeneratedKeys();
		if (rs.next())
		    return rs.getLong("actor_id");
	    }
	} catch (SQLException | ConnectionException ex) {
	    throw new DaoException(ex.getMessage());
	}
	return 0;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.webjava.jdbc.dao.IActorDao#insertActors(java.util.List)
     */
    @Override
    public void insertActors(List<Actor> actors) throws DaoException {
	String SQL = "INSERT INTO actor (first_name, last_name) VALUES (?,?)";
	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		PreparedStatement pt = connection.prepareStatement(SQL);) {
	    int count = 0;
	    for (Actor actor : actors) {
		pt.setString(1, actor.getFirstName());
		pt.setString(2, actor.getLastName());
		pt.addBatch();
		count++;
		// execute every 100 rows or less
		if (count % 100 == 0 || count == actors.size()) {
		    pt.executeBatch();
		}

	    }
	} catch (SQLException | ConnectionException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.webjava.jdbc.dao.IActorDao#updateLastName(int, java.lang.String)
     */
    @Override
    public int updateLastName(int id, String lastName) throws DaoException {
	String SQL = "UPDATE actor " + "SET last_name = ? " + "WHERE actor_id = ?";

	int affectedrows = 0;

	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		PreparedStatement pstmt = connection.prepareStatement(SQL)) {

	    pstmt.setString(1, lastName);
	    pstmt.setInt(2, id);

	    affectedrows = pstmt.executeUpdate();

	} catch (SQLException | ConnectionException ex) {
	    System.out.println(ex.getMessage());
	}
	return affectedrows;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.webjava.jdbc.dao.IActorDao#deleteActor(int)
     */
    @Override
    public int deleteActor(int id) throws DaoException {
	String SQL = "DELETE FROM actor WHERE actor_id = ?";

	int affectedrows = 0;

	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		PreparedStatement pstmt = connection.prepareStatement(SQL)) {

	    pstmt.setInt(1, id);

	    affectedrows = pstmt.executeUpdate();

	} catch (SQLException | ConnectionException ex) {
	    System.out.println(ex.getMessage());
	}
	return affectedrows;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.webjava.jdbc.dao.IActorDao#properCase(java.lang.String)
     */
    @Override
    public String properCase(String s) throws DaoException {
	// TODO Auto-generated method stub
	return null;
    }

    /*
     * Handling RDBMS Transactions If insertion is fail, rollback insert
     * 
     * @see
     * com.webjava.jdbc.dao.IActorDao#addActorAndAssignFilm(com.webjava.jdbc.
     * domain.Actor, int)
     */
    @Override
    public long addActorAndAssignFilm(Actor actor, int filmId) throws DaoException {
	long actorId = -1;
	/// insert an actor into the actor table
	String SQLInsertActor = "INSERT INTO actor(first_name,last_name) " + "VALUES(?,?)";

	// assign actor to a film
	String SQLAssignActor = "INSERT INTO film_actor(actor_id,film_id) " + "VALUES(?,?)";
	// off auto commit
	Connection connection = null;
	try {
	    try {
		connection = ConnectionHolder.getInstance().getConnection();
	    } catch (ConnectionException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	    }
	    connection.setAutoCommit(false);
	    PreparedStatement pstmt = connection.prepareStatement(SQLInsertActor, Statement.RETURN_GENERATED_KEYS);
	    // add actor
	    pstmt.setString(1, actor.getFirstName());
	    pstmt.setString(2, actor.getLastName());
	    // execute
	    int record = pstmt.executeUpdate();
	    if (record > 0) {
		// when record insert success, get gen key
		ResultSet rs = pstmt.getGeneratedKeys();
		if (rs.next()) {
		    actorId = rs.getLong("actor_id");

		    if (actorId < 0) {
			// when actor insertion fail, rollback
			connection.rollback();
		    } else {
			PreparedStatement pstmt2 = connection.prepareStatement(SQLAssignActor);
			// assign to film
			pstmt2.setLong(1, actorId);
			pstmt2.setInt(2, filmId);
			pstmt2.executeUpdate();
			// finally commit it
			connection.commit();
		    }
		}
	    }

	} catch (SQLException e) {
	    // reset id
	    actorId = -1;
	    // when exception occurred, rollback
	    try {
		if (connection != null) {
		    connection.rollback();
		}
	    } catch (SQLException e1) {
		System.out.println(e.getMessage());
	    }

	} finally {
	    try {
		connection.close();
	    } catch (SQLException e) {
		System.out.println(e.getMessage());
	    }
	}

	return actorId;

    }

    /*
     * (non-Javadoc)
     * 
     * @see com.webjava.jdbc.dvd.dao.IActorDao#findActorByName(java.lang.String)
     */
    @Override
    public List<Actor> findActorByName(String fName, String lName, boolean isFullName) throws DaoException {

	/*
	 * String SQL =
	 * "Select * from actor where first_name LIKE ? ORDER BY actor_id " +
	 * (limit == true ? "LIMIT ? OFFSET ? " : "");
	 */
	String SQL = "";
	// support case incentive
	if (isFullName) {
	    SQL = "SELECT * FROM (SELECT *, (first_name ||' '|| last_name) as full_name FROM actor) AS temp WHERE lower(full_name) LIKE ?";
	} else {
	    SQL = "Select * from actor where (lower(first_name) LIKE ? AND lower(last_name) LIKE ?)";
	}

	List<Actor> actors = new ArrayList<>();
	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		PreparedStatement pt = connection.prepareStatement(SQL);) {
	    if (isFullName) {
		// fName is fullName
		pt.setString(1, "%" + fName + "%");
	    }else {
		pt.setString(1, "%" + fName + "%");
		pt.setString(2, "%" + lName + "%");
	    }
	    // execute
	    ResultSet rs = pt.executeQuery();
	    // move pointer
	    while (rs.next()) {
		// create actor
		Actor actor = new Actor(rs.getInt("actor_id"), rs.getString("first_name"), rs.getString("last_name"));
		actors.add(actor);
	    }

	} catch (SQLException | ConnectionException ex) {
	    System.out.println("System Error has occured!");
	    throw new DaoException(ex.getMessage());
	}
	return actors;
    }

    /*
     * (non-Javadoc)
     * 
     * @see com.webjava.jdbc.dvd.dao.IActorDao#findActorDetailbyId(int)
     */
    @Override
    public List<ActorDetailDTO> findActorDetailbyId(int actorId) throws DaoException {
	String SQL = "SELECT film_id, title, category_name from actor_film_info_category where actor_id =?";
	ActorDetailDTO actor = null;
	List<ActorDetailDTO> actorList = new ArrayList<>();

	try (Connection connection = ConnectionHolder.getInstance().getConnection();
		PreparedStatement pt = connection.prepareStatement(SQL);) {
	    pt.setInt(1, actorId);
	    // execute
	    ResultSet rs = pt.executeQuery();
	    // move pointer
	    while (rs.next()) {
		// create actor
		actor = new ActorDetailDTO(actorId, rs.getInt("film_id"), rs.getString("title"),
			rs.getString("category_name"));
		actorList.add(actor);
	    }

	} catch (SQLException | ConnectionException ex) {
	    System.out.println("System Error has occured!");
	    throw new DaoException(ex.getMessage());
	}
	return actorList;
    }
}
