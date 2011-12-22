package org.got5.tapestry5.jquery.data;

import java.util.Date;



public class Celebrity {
	private long id;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private Occupation occupation;
    private String biography;
    private boolean birthDateVerified;

	public Celebrity() { 
    }
    
    public Celebrity(String firstName, String lastName, 
					Date dateOfBirth, Occupation occupation) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.occupation = occupation;
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

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    
    public String getBiography() {
		return biography;
	}

	public void setBiography(String biography) {
		this.biography = biography;
	}

	public boolean isBirthDateVerified() {
		return birthDateVerified;
	}

	public void setBirthDateVerified(boolean birthDateVerified) {
		this.birthDateVerified = birthDateVerified;
	}
}
