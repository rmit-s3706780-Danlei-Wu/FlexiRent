package com.Flexirent.Model;

public class RoomBean {

	
	private String id;
	/**
	 *Type(1: Apartment/2:Premium-Suite)
	 */
	private String type;
	/**
	 * StreetNumber
	 */
	private String stretNunber;
	/**
	 * StreetName
	 */
	private String stretName;
	/**
	 * Number of rooms
	 */
	private String roomNumber;
	/**
	 * Suburb
	 */
	private String suburb;
	/**
	 * The status of room
	 */
	private String status = "available";
	
	/**
	 * The image of room
	 */
	private String image ;
	
	/**
	 * The description of room
	 */
	private String description;
	private String maintaindate;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getStretNunber() {
		return stretNunber;
	}
	public void setStretNunber(String stretNunber) {
		this.stretNunber = stretNunber;
	}
	public String getStretName() {
		return stretName;
	}
	public void setStretName(String stretName) {
		this.stretName = stretName;
	}
	public String getSuburb() {
		return suburb;
	}
	public void setSuburb(String suburb) {
		this.suburb = suburb;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRoomNumber() {
		return roomNumber;
	}
	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public String getMaintaindate() {
		return maintaindate;
	}
	public void setMaintaindate(String maintaindate) {
		this.maintaindate = maintaindate;
	}
	@Override
	public String toString() {
		return  type + ":" + stretNunber + ":" + 
	stretName+ ":" + roomNumber + ":" + suburb + ":" + status + ":" + image
				+ ":" + description;
	}
	
	
	
	
	
	
	
	
}
