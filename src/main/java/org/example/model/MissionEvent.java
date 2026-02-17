package org.example.model;

public class MissionEvent implements Identifiable<Integer>{
    private Integer id;
    private int astronautId;
    private int day;
    private MissionEventType type;
    private int basePoints;


    protected MissionEvent(){}

    public MissionEvent(Integer id, int astronautId, int day, MissionEventType type, int basePoints) {
        this.id = id;
        this.astronautId = astronautId;
        this.day = day;
        this.type = type;
        this.basePoints = basePoints;
    }

    @Override
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAstronautId() {
        return astronautId;
    }

    public void setAstronautId(int astronautId) {
        this.astronautId = astronautId;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public MissionEventType getType() {
        return type;
    }

    public void setType(MissionEventType type) {
        this.type = type;
    }

    public int getBasePoints() {
        return basePoints;
    }

    public void setBasePoints(int basePoints) {
        this.basePoints = basePoints;
    }

    @Override
    public String toString() {
        return "MissionEvent{" + "id=" + id + ", astronautId=" + astronautId + ", day=" + day + ", type=" + type + ", basePoints=" + basePoints + '}';
    }
}
