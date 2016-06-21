package com.example.iviettech_final_project;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class FeedbackArrayAdapter extends ArrayAdapter<Feedback> {
	private int resourceID;
	public FeedbackArrayAdapter(Context context, int resource,
			List<Feedback> objects) {
		super(context, resource, objects);
		this.resourceID = resource;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Feedback feedbackItem = getItem(position);
		View row = convertView;
		
		if (row == null) {
			LayoutInflater inflater;
			inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			row = inflater.inflate(resourceID, null);
		}
		else {
			row = convertView;
		}
		TextView tvUsername = (TextView) row.findViewById(R.id.tv_username);
		tvUsername.setText(feedbackItem.getUserName());
		TextView tvComment = (TextView) row.findViewById(R.id.tv_comment);
		tvComment.setText(feedbackItem.getComment());
		TextView tvFeedbackAt = (TextView) row.findViewById(R.id.tv_feedback_at);
		tvFeedbackAt.setText(feedbackItem.getFeedBackAt());
		return row;
	}
	
}
