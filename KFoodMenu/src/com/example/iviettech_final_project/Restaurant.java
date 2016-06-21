package com.example.iviettech_final_project;

import java.io.Serializable;

public class Restaurant implements Serializable{
	private String restaurantName;
	private String timeOpen;
	private String timeClose;
	private String latX;
	private String latY;
	private String id;
	private String city;
	private String rank;
	private String phone;
	private String address;
	private String logo;
	private String images;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	public String getRestaurantName() {
		return restaurantName;
	}
	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}
	public String getTimeOpen() {
		return timeOpen;
	}
	public void setTimeOpen(String timeOpen) {
		this.timeOpen = timeOpen;
	}
	public String getTimeClose() {
		return timeClose;
	}
	public void setTimeClose(String timeClose) {
		this.timeClose = timeClose;
	}
	public String getLatX() {
		return latX;
	}
	public void setLatX(String latX) {
		this.latX = latX;
	}
	public String getLatY() {
		return latY;
	}
	public void setLatY(String latY) {
		this.latY = latY;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getRank() {
		return rank;
	}
	public void setRank(String rank) {
		this.rank = rank;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getLogo() {
		return logo;
	}
	public void setLogo(String logo) {
		this.logo = logo;
	}
	@Override
	public String toString() {
		return "Restaurant [restaurantName=" + restaurantName + ", timeOpen="
				+ timeOpen + ", timeClose=" + timeClose + ", latX=" + latX
				+ ", latY=" + latY + ", city=" + city + ", rank=" + rank
				+ ", phone=" + phone + ", address=" + address + ", logo="
				+ logo + "]";
	}
	
	
}
