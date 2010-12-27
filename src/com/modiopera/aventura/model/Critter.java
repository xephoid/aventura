package com.modiopera.aventura.model;

import java.util.HashMap;
import java.util.Map;

import com.modiopera.aventura.model.enums.StatEnum;

public class Critter extends GameObject {

    private Map<StatEnum, Integer> baseStats = new HashMap<StatEnum, Integer>();
    private String imgPath;
    private int herdSize;

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

	public void setHerdSize(int herdSize) {
		this.herdSize = herdSize;
	}

	public int getHerdSize() {
		return herdSize;
	}
	
	public void recalcStats(Integer playerLevel) {
		this.baseStats.put(StatEnum.DAMAGE, this.getStat(StatEnum.STRENGTH) * playerLevel);
		this.baseStats.put(StatEnum.DAMAGE_RESISTANCE, 0 * playerLevel);
		this.baseStats.put(StatEnum.HIT_POINTS, this.getStat(StatEnum.FORTITUDE) * playerLevel);
	}
	
	public int getStat(StatEnum stat) {
		return this.baseStats.get(stat);
	}
}
