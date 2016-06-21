package com.example.iviettech_final_project;

import java.io.Serializable;


public class Food implements Serializable {
	private String id;
	private String foodName;
	private String point;
	private String cost;
	private String information;
	private String restaurantID;
	private String images;
	

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFoodName() {
		return foodName;
	}
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public String getPoint() {
		return point;
	}
	public void setPoint(String point) {
		this.point = point;
	}
	public String getCost() {
		return cost;
	}
	public void setCost(String cost) {
		this.cost = cost;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public String getRestaurantID() {
		return restaurantID;
	}
	public void setRestaurantID(String restaurantID) {
		this.restaurantID = restaurantID;
	}
	public String getImages() {
		return images;
	}
	public void setImages(String images) {
		this.images = images;
	}
	@Override
	public String toString() {
		return "Food [id=" + id + ", foodName=" + foodName + ", point=" + point
				+ ", cost=" + cost + ", information=" + information
				+ ", restaurantID=" + restaurantID + ", images=" + images + "]";
	}
	
}
