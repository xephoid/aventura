package com.modiopera.aventura.controller.actions;

import com.modiopera.aventura.model.Topic;

public class ActivateTopicAction extends Action {

	private Topic topic;
	
	@Override
	public void act() {
		topic.activate();
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	public Topic getTopic() {
		return this.topic;
	}
}
