package com.modiopera.aventura.controller.actions;

import com.modiopera.aventura.model.Quest;

public class CompleteQuestAction extends Action {

    private Quest quest;
    
    @Override
    public boolean act() {
        if (this.quest.isCompleted()) {
            this.quest.completeQuest();
            return true;
        }
        return false;
    }

    public void setQuest(Quest quest) {
        this.quest = quest;
    }
    
    @Override
    public String toString() {
        return "Quest completed: " + quest.getName();
    }
}
