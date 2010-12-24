package com.modiopera.aventura.model;

import java.util.HashMap;
import java.util.Map;

import com.modiopera.aventura.model.enums.StatEnum;

public class Critter extends GameObject {

    private Map<StatEnum, Integer> baseStats = new HashMap<StatEnum, Integer>();
    private String imgPath;

    public void setStat(StatEnum stat, Integer amt) {
        baseStats.put(stat, amt);
    }
    
    public void setBaseStats(Map<StatEnum, Integer> baseStats) {
        this.baseStats = baseStats;
    }

    public Map<StatEnum, Integer> getBaseStats() {
        return baseStats;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public String getImgPath() {
        return imgPath;
    }
}
