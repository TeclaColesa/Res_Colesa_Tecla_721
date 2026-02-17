package org.example;


import org.example.model.Astronaut;
import org.example.repository.AstronautRepository;
import org.example.repository.MissionEventRepository;
import org.example.repository.SupplyRepository;
import org.example.service.Service;

public class Main {
    static void main(String[] args) {



        System.out.println("1.---------------------------------------------------------------------");
        AstronautRepository astronautRepository = new AstronautRepository();
        SupplyRepository supplyRepository = new SupplyRepository();
        MissionEventRepository missionEventRepository = new MissionEventRepository();
        Service service = new Service(astronautRepository, missionEventRepository, supplyRepository);

        System.out.println("Astronauts loaded:" + astronautRepository.findAll().size());
        System.out.println("Mission events loaded:" + missionEventRepository.findAll().size());
        System.out.println("Supplies loaded:" + supplyRepository.findAll().size());
        for (Astronaut a: astronautRepository.findAll()) {
            System.out.println(a);
        }

        System.out.println("2.---------------------------------------------------------------------");
        service.filterAstronautsBySpacecraftAndActive();

        System.out.println("3.---------------------------------------------------------------------");
        service.sortAstronautsByExperienceLevelAndName();

        System.out.println("4.---------------------------------------------------------------------");
        service.saveAstronautsToFileInReversedOrder();

        System.out.println("5.---------------------------------------------------------------------");
        service.printFirstFiveMissionEvents();

        System.out.println("6.---------------------------------------------------------------------");

        
        System.out.println("7.---------------------------------------------------------------------");


    }
}
