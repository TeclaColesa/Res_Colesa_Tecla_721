package org.example.model;

public class Astronaut implements Identifiable<Integer>{
    private Integer id;
    private String name;
    private String spacecraft;
    private AstronautStatus status;
    private int experienceLevel;


    protected Astronaut(){}

    public Astronaut(Integer id, String name, String spacecraft, AstronautStatus status, int experienceLevel) {
        this.id = id;
        this.name = name;
        this.spacecraft = spacecraft;
        this.status = status;
        this.experienceLevel = experienceLevel;
    }

    @Override
    public Integer getId() {return id;}

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpacecraft() {
        return spacecraft;
    }

    public void setSpacecraft(String spacecraft) {
        this.spacecraft = spacecraft;
    }

    public AstronautStatus getStatus() {
        return status;
    }

    public void setStatus(AstronautStatus status) {
        this.status = status;
    }

    public int getExperienceLevel() {
        return experienceLevel;
    }

    public void setExperienceLevel(int experienceLevel) {
        this.experienceLevel = experienceLevel;
    }
    @Override
    public String toString() {
        return "[#" + id + "] | " + name + " | " + spacecraft + " | " + status + " | exp=" + experienceLevel;
    }

}
