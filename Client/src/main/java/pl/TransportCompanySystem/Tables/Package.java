package pl.TransportCompanySystem.Tables;

import java.io.Serializable;

public class Package implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer packageID;
	private Integer userID;
	private Integer courierID;
	private Integer locationID;
	private String packageSize;
	private Double packageWeight;
	private Boolean withComplaint = false;
	private Double price;
	private String status;
	private String paymentMethod;
	private String recipientName;
	private String recipientSurname;
	private String recipientAddress;

	public Integer getPackageID() {
		return packageID;
	}

	public void setPackageID(Integer packageID) {
		this.packageID = packageID;
	}

	public Integer getUserID() {
		return userID;
	}

	public void setUserID(Integer userID) {
		this.userID = userID;
	}

	public Integer getCourierID() {
		return courierID;
	}

	public void setCourierID(Integer courierID) {
		this.courierID = courierID;
	}

	public String getPackageSize() {
		return packageSize;
	}

	public void setPackageSize(String packageSize) {
		this.packageSize = packageSize;
	}

	public Boolean getWithComplaint() {
		return withComplaint;
	}

	public void setWithComplaint(Boolean withComplaint) {
		this.withComplaint = withComplaint;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getRecipientName() {
		return recipientName;
	}

	public void setRecipientName(String recipientName) {
		this.recipientName = recipientName;
	}

	public String getRecipientSurname() {
		return recipientSurname;
	}

	public void setRecipientSurname(String recipientSurname) {
		this.recipientSurname = recipientSurname;
	}

	public String getRecipientAddress() {
		return recipientAddress;
	}

	public void setRecipientAddress(String recipientAddress) {
		this.recipientAddress = recipientAddress;
	}

	public Integer getLocationID() {
		return locationID;
	}

	public void setLocationID(Integer locationID) {
		this.locationID = locationID;
	}

	public Double getPackageWeight() {
		return packageWeight;
	}

	public void setPackageWeight(Double packageWeight) {
		this.packageWeight = packageWeight;
	}

}
