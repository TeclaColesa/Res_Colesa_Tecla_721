package org.example.model;

public class Supply implements Identifiable<Integer>{
    private Integer id;
    private int astronautId;
    private SupplyType type;
    private int value;

    protected Supply(){}

    public Supply(Integer id, int astronautId, SupplyType type, int value) {
        this.id = id;
        this.astronautId = astronautId;
        this.type = type;
        this.value = value;
    }

    @Override
    public Integer getId() {return id;}

    public void setId(Integer id) {
        this.id = id;
    }

    public int getAstronautId() {
        return astronautId;
    }

    public void setAstronautId(int astronautId) {
        this.astronautId = astronautId;
    }

    public SupplyType getType() {
        return type;
    }

    public void setType(SupplyType type) {
        this.type = type;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
    @Override
    public String toString() {
        return "[" + id + "] | " + astronautId + " | " + type + " | " + value;
    }
}
