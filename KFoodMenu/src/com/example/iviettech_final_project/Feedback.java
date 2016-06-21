package com.example.iviettech_final_project;

public class Feedback {
	private String id;
	private String userName;
	private String comment;
	private String feedBackAt;
	private String foodID;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public String getFeedBackAt() {
		return feedBackAt;
	}
	public void setFeedBackAt(String feedBackAt) {
		this.feedBackAt = feedBackAt;
	}
	public String getFoodID() {
		return foodID;
	}
	public void setFoodID(String foodID) {
		this.foodID = foodID;
	}
	@Override
	public String toString() {
		return "Feedback [id=" + id + ", userName=" + userName + ", comment="
				+ comment + ", feedBackAt=" + feedBackAt + ", foodID=" + foodID
				+ "]";
	}
	
}
