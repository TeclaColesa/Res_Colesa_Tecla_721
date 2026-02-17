package org.example.repository;

import org.example.model.MissionEvent;

public class MissionEventRepository extends JsonRepository<MissionEvent>{
    public MissionEventRepository() {super("events.json", MissionEvent.class);}
}
