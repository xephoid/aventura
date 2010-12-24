package com.modiopera.aventura.controller.actions;

import com.modiopera.aventura.model.Topic;

public class ActivateTopicAction extends Action {

	private Topic topic;
	
	@Override
	public boolean act() {
		return topic.activate();
	}

	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	
	@Override
	public String toString() {
	    return "Topic" +topic+ " activated!";
	}
}
