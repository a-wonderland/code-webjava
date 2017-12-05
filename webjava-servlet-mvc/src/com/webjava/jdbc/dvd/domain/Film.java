/**
 * 
 */
package com.webjava.jdbc.dvd.domain;

/**
 * @author sumyathtarwai
 *
 */
public class Film {

    private int filmId;
    private String title;
    private String categoryName;
    private String description;
    private String specialFeatures;
    private String tsQuery;
    private String rating;
    private float rentalRate;
    private int languageId;
    private String releaseYear;
    private float price;

    /**
     * 
     */
    public Film() {
	// TODO Auto-generated constructor stub
    }

    
    
    /**
     * @return the price
     */
    public float getPrice() {
        return price;
    }



    /**
     * @param price the price to set
     */
    public void setPrice(float price) {
        this.price = price;
    }



    /**
     * @return the filmId
     */
    public int getFilmId() {
	return filmId;
    }

    /**
     * @param filmId
     *            the filmId to set
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
     * @param title
     *            the title to set
     */
    public void setTitle(String title) {
	this.title = title;
    }

    /**
     * @return the categoryName
     */
    public String getCategoryName() {
	return categoryName;
    }

    /**
     * @param categoryName
     *            the categoryName to set
     */
    public void setCategoryName(String categoryName) {
	this.categoryName = categoryName;
    }

    /**
     * @return the description
     */
    public String getDescription() {
	return description;
    }

    /**
     * @param description
     *            the description to set
     */
    public void setDescription(String description) {
	this.description = description;
    }

    /**
     * @return the specialFeatures
     */
    public String getSpecialFeatures() {
	return specialFeatures;
    }

    /**
     * @param specialFeatures
     *            the specialFeatures to set
     */
    public void setSpecialFeatures(String specialFeatures) {
	this.specialFeatures = specialFeatures;
    }

    /**
     * @return the tsQuery
     */
    public String getTsQuery() {
	return tsQuery;
    }

    /**
     * @param tsQuery
     *            the tsQuery to set
     */
    public void setTsQuery(String tsQuery) {
	this.tsQuery = tsQuery;
    }

    /**
     * @return the rating
     */
    public String getRating() {
	return rating;
    }

    /**
     * @param rating
     *            the rating to set
     */
    public void setRating(String rating) {
	this.rating = rating;
    }

    /**
     * @return the rentalRate
     */
    public float getRentalRate() {
	return rentalRate;
    }

    /**
     * @param rentalRate
     *            the rentalRate to set
     */
    public void setRentalRate(float rentalRate) {
	this.rentalRate = rentalRate;
    }

    /**
     * @return the releaseYear
     */
    public String getReleaseYear() {
        return releaseYear;
    }



    /**
     * @param releaseYear the releaseYear to set
     */
    public void setReleaseYear(String releaseYear) {
        this.releaseYear = releaseYear;
    }



    /**
     * @return the languageId
     */
    public int getLanguageId() {
	return languageId;
    }

    /**
     * @param languageId
     *            the languageId to set
     */
    public void setLanguageId(int languageId) {
	this.languageId = languageId;
    }


    public Film(int filmId, String title, String categoryName, String description, float price, String specialFeatures,
	    String rating, float rentalRate) {
	super();
	this.filmId = filmId;
	this.title = title;
	this.categoryName = categoryName;
	this.description = description;
	this.price = price;
	this.specialFeatures = specialFeatures;
	this.rating = rating;
	this.rentalRate = rentalRate;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result + filmId;
	result = prime * result + ((title == null) ? 0 : title.hashCode());
	return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	Film other = (Film) obj;
	if (filmId != other.filmId)
	    return false;
	if (title == null) {
	    if (other.title != null)
		return false;
	} else if (!title.equals(other.title))
	    return false;
	return true;
    }

}
