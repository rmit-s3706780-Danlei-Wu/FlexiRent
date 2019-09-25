package com.Flexirent.Model;

public class RentBean {

	/**
	 * 
	 */
	private String id;
	/**
	 * 
	 */
	private String rentDate;
	/**
	 * (dd/MM/yyyy)
	 */
	private String estimatedReturnDate;
	/**
	 * (dd/MM/yyyy)
	 */
	private String actualRetuenDate = "none";
	/**
	 * 
	 */
	private String rentalFee = "none";
	/**
	 * 
	 */
	private String lateFee = "none";
	/**
	 * ID
	 */
	private String customerName;
	private String roomid;
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
	private String status ;
	
	/**
	 * The image of room
	 */
	private String image ;
	
	/**
	 * The description of room
	 */
	private String description;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRentDate() {
		return rentDate;
	}

	public void setRentDate(String rentDate) {
		this.rentDate = rentDate;
	}

	public String getEstimatedReturnDate() {
		return estimatedReturnDate;
	}

	public void setEstimatedReturnDate(String estimatedReturnDate) {
		this.estimatedReturnDate = estimatedReturnDate;
	}

	public String getActualRetuenDate() {
		return actualRetuenDate;
	}

	public void setActualRetuenDate(String actualRetuenDate) {
		this.actualRetuenDate = actualRetuenDate;
	}

	public String getRentalFee() {
		return rentalFee;
	}

	public void setRentalFee(String rentalFee) {
		this.rentalFee = rentalFee;
	}

	public String getLateFee() {
		return lateFee;
	}

	public void setLateFee(String lateFee) {
		this.lateFee = lateFee;
	}

	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getRoomid() {
		return roomid;
	}

	public void setRoomid(String roomid) {
		this.roomid = roomid;
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

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
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

	public RentBean(String id, String rentDate, String estimatedReturnDate, String actualRetuenDate, String rentalFee,
			String lateFee, String customerName, String roomid, String type, String stretNunber, String stretName,
			String roomNumber, String suburb, String status, String image, String description) {
		super();
		this.id = id;
		this.rentDate = rentDate;
		this.estimatedReturnDate = estimatedReturnDate;
		this.actualRetuenDate = actualRetuenDate;
		this.rentalFee = rentalFee;
		this.lateFee = lateFee;
		this.customerName = customerName;
		this.roomid = roomid;
		this.type = type;
		this.stretNunber = stretNunber;
		this.stretName = stretName;
		this.roomNumber = roomNumber;
		this.suburb = suburb;
		this.status = status;
		this.image = image;
		this.description = description;
	}

	@Override
	public String toString() {
		return rentDate + ":" + estimatedReturnDate
				+ ":" + actualRetuenDate + ":" + rentalFee + ":" + lateFee
				+ ":" + customerName;
	}
	
	
}
