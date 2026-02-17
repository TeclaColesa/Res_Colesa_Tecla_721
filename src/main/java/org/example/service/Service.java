package org.example.service;

import org.example.model.Astronaut;
import org.example.model.AstronautStatus;
import org.example.repository.AstronautRepository;
import org.example.repository.MissionEventRepository;
import org.example.repository.SupplyRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Comparator;
import java.util.List;

import static java.util.stream.Collectors.toList;

public class Service {
    private AstronautRepository astronautRepository;
    private MissionEventRepository missionEventRepository;
    private SupplyRepository supplyRepository;

    public Service(AstronautRepository astronautRepository, MissionEventRepository missionEventRepository, SupplyRepository supplyRepository) {
        this.astronautRepository = astronautRepository;
        this.missionEventRepository = missionEventRepository;
        this.supplyRepository = supplyRepository;
    }
    private final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));



    //2.Filtern nach spacecraft und Status
    //Lesen Sie von der Tastatur einen String spacecraft. Geben
    //Sie anschließend nur die Astronauten aus, welche folgende
    //Bedingungen erfüllen:
    //● spacecraft == input
    //● status == ACTIVE

    public void filterAstronautsBySpacecraftAndActive(){
        try {
            System.out.println("spacecraft (to sort by): ");
            String spacecraft = reader.readLine();


            List<Astronaut> filteredAstronauts = astronautRepository.findAll().stream()
                    .filter(a -> a.getSpacecraft().equals(spacecraft) && a.getStatus().equals(AstronautStatus.ACTIVE))
                    .collect(toList());


            System.out.println("Filtered astronauts (by spacecraft and status active):");
            filteredAstronauts.forEach(System.out::println);

        } catch (Exception e) {
            System.out.println("Eroare la citirea inputului");
        }
    }


}
