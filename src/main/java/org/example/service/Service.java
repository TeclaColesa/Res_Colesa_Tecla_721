package org.example.service;

import org.example.model.Astronaut;
import org.example.model.AstronautStatus;
import org.example.repository.AstronautRepository;
import org.example.repository.MissionEventRepository;
import org.example.repository.SupplyRepository;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
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

    //3.Sortierung der Astronauten
    //Sortieren Sie die Liste aller Astronauten nach folgenden
    //Kriterien:
    //● zuerst nach experienceLevel absteigend
    //● bei gleichem experienceLevel nach name aufsteigend
    //Geben Sie anschließend die sortierte Liste auf der
    //Konsole aus.

    public void sortAstronautsByExperienceLevelAndName() {
        List<Astronaut> sortedAstronauts = astronautRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Astronaut::getExperienceLevel).reversed().thenComparing(Astronaut::getName))
                .collect(toList());
        sortedAstronauts.forEach(System.out::println);
    }

//4.(1 Punkt) Schreiben in eine Datei
    //Schreiben Sie die in Aufgabe 3 sortierte Astronauten Liste in umgekehrter Reihenfolge
    //in die Datei astronauts_sorted.txt. Jeder Astronaut soll in einer
    //eigenen Zeile gespeichert werden, im selben Format wie bei der Konsolenausgabe.

    public void saveAstronautsToFileInReversedOrder(){
        List<Astronaut> sortedAstronauts = astronautRepository.findAll().stream()
                .sorted(Comparator.comparingInt(Astronaut::getExperienceLevel).reversed().thenComparing(Astronaut::getName))
                .collect(toList());
        List<Astronaut> reversedAstronauts = new ArrayList<>(sortedAstronauts);
        Collections.reverse(reversedAstronauts);


        try (PrintWriter writer = new PrintWriter("astronauts_sorted.txt")) {
            for (Astronaut a : reversedAstronauts) {
                writer.println(a.toString());
            }
            System.out.println("Datele au fost scrise in fisier txt");
        } catch (Exception e) {
            throw new RuntimeException("cannot write to file: " + "astronauts_sorted.txt", e);
        }


    }




}
