package org.example.service;

import org.example.model.*;
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

import static java.util.stream.Collectors.filtering;
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


//5.Punktberechnung
//Implementieren Sie die Berechnung der computedPoints für
//jedes MissionEvent gemäß den folgenden Regeln:
//● EVA → computedPoints = basePoints + 2 * day
//● SYSTEM_FAILURE → computedPoints = basePoints - 3 - day
//● SCIENCE → computedPoints = basePoints + (day % 4)
//● MEDICAL → computedPoints = basePoints - 2 * (day % 3)
//● COMMUNICATION → computedPoints = basePoints + 5
//Geben Sie anschließend die ersten 5 Events aus
//events.json auf der Konsole aus.

    //Event <id> -> raw=<basePoints> -> computed=<computedPoints>

    public int computedPoints(MissionEvent event){
        if (event.getType() == MissionEventType.EVA) {
            return event.getBasePoints() + 2 * event.getDay();
        } else if (event.getType() == MissionEventType.SYSTEM_FAILURE) {
            return event.getBasePoints() -3 - event.getDay();
        } else if (event.getType() == MissionEventType.SCIENCE){
            return event.getBasePoints() + event.getDay() % 4;
        } else if (event.getType() == MissionEventType.MEDICAL) {
            return event.getBasePoints() -2 * (event.getDay() % 3);
        } else //COMMUNICATION
            return event.getBasePoints() + 5;
    }

    public void printFirstFiveMissionEvents(){
        List<MissionEvent> firstFiveMissions= missionEventRepository.findAll().stream()
                .limit(5)
                .toList();

        System.out.println("First 5 MissionEvents:");
        firstFiveMissions.forEach(m -> {
            int computedPoints = computedPoints(m);
            System.out.printf("Event %s -> raw=%s -> computed=%s%n",
                    m.getId(),
                    m.getBasePoints(),
                    computedPoints
            );
        });
    }

    //6.Ranking
    //Berechnen Sie für jeden Astronauten den Gesamtwert
    //totalScore nach folgender Formel:
    //
    //totalScore = Sum(computedPoints aus allen MissionEvents des
    //Astronauten) + Sum(value aus allen Supplies des Astronauten)
    //
    //Anforderungen:
    //● Berechnen Sie totalScore für alle Astronauten.
    //● Geben Sie die Top 5 auf der Konsole aus, sortiert
    //nach:
    //● totalScore absteigend
    //● bei Gleichstand name aufsteigend
    //● Bestimmen und geben Sie zusätzlich das Leading
    //spacecraft aus (= spacecraft des Astronauten auf
    //Platz 1).

    public int calculateAstronautTotalScore (Astronaut astronaut) {
        int astronautId = astronaut.getId();

        int sumComputedPointFromMissionEvents = missionEventRepository.findAll().stream()
                .filter(e -> e.getAstronautId() == astronautId)
                .mapToInt(this::computedPoints)
                .sum();

        int sumValuesFromSupplies = supplyRepository.findAll().stream()
                .filter(s -> s.getAstronautId() == astronautId)
                .mapToInt(Supply::getValue)
                .sum();
        return sumComputedPointFromMissionEvents + sumValuesFromSupplies;
    }

    public void calculateTotalScoreForAllAstronautsAndPrintTopFive(){
        List<Astronaut> sortedAstronauts = astronautRepository.findAll().stream()
                .sorted(Comparator.comparingInt(this::calculateAstronautTotalScore).reversed().thenComparing(Astronaut::getName))
                .limit(5)
                .collect(toList());
        System.out.println("Top 5 Astronauts sorted by total score:");
        sortedAstronauts.forEach(m -> {
            System.out.printf("%s. %s (%s) -> %s%n",
                    m.getId(),
                    m.getName(),
                    m.getSpacecraft(),
                    calculateAstronautTotalScore(m)
            );
        });
    }


}
