package pl.TransportCompanySystem.Tables;

import java.io.Serializable;

public class Car implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Integer carID;
	private String carType;
	private Boolean isAvailable;

	public Integer getCarID() {
		return carID;
	}

	public void setCarID(Integer carID) {
		this.carID = carID;
	}

	public String getCarType() {
		return carType;
	}

	public void setCarType(String carType) {
		this.carType = carType;
	}

	public Boolean getAvailable() {
		return isAvailable;
	}

	public void setAvailable(Boolean isAvailable) {
		this.isAvailable = isAvailable;
	}

}
