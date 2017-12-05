/**
 * 
 */
package com.webjava.jdbc.dvd.domain.dto;

/**
 * @author sumyathtarwai
 *
 */
public class ActorDetailDTO {
    private int actorId;
    private int filmId;
    private String title;
    private String categoryName;
    
    
    
    public ActorDetailDTO(int actorId, int filmId, String title, String categorName) {
	super();
	this.actorId = actorId;
	this.filmId = filmId;
	this.title = title;
	this.categoryName = categorName;
    }
    /**
     * @return the actorId
     */
    public int getActorId() {
        return actorId;
    }
    /**
     * @param actorId the actorId to set
     */
    public void setActorId(int actorId) {
        this.actorId = actorId;
    }
    /**
     * @return the filmId
     */
    public int getFilmId() {
        return filmId;
    }
    /**
     * @param filmId the filmId to set
     */
    public void setFilmId(int filmId) {
        this.filmId = filmId;
    }
    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }
    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }
    /**
     * @return the categorName
     */
    public String getCategoryName() {
        return categoryName;
    }
    /**
     * @param categorName the categorName to set
     */
    public void setCategoryName(String categorName) {
        this.categoryName = categorName;
    }
    
    
}
