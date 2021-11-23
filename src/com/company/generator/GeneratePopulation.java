package com.company.generator;

import com.company.human.Human;
import com.company.human.HumanData;
import com.company.human.Ids;
import com.company.population.Population;
import com.company.room.Room;
import com.company.states.*;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Random;

import static com.company.usefulConstants.Constants.*;

public class GeneratePopulation {

    private static final Random random = new Random();

    public static Population generateNotResistPopulation(final int numerous, final Room room) {
        List<Human> individuals = new ArrayList<>();
        for (int i = 0; i < numerous; i++) {
            individuals.add(getNotResistIndividual(room));
        }
        return Population.of(individuals);
    }

    public static Human getNotResistIndividual(final Room room) {
        IState iState;
        if (random.nextInt(PROBABILITY_OF_ILL) != 0) {
            iState = new Healthy();
        } else if (random.nextInt(PROBABILITY_OF_SYMPTOMS) == 0) {
            iState = new HaveSymptoms();
        } else {
            iState = new HaveNoSymptoms();
        }

        return getIndividual(room, iState);
    }

    public static Population generateResistPopulation(final int numerous, final Room room) {
        List<Human> individuals = new ArrayList<>();
        for (int i = 0; i < numerous; i++) {
            individuals.add(generateResistIndividual(room));
        }
        return Population.of(individuals);
    }

    public static Human generateResistIndividual(final Room room) {
        IState iState;
        if (random.nextInt(PROBABILITY_OF_RESIST) == 0) {
            iState = new Resistant();
        } else if (random.nextInt(PROBABILITY_OF_ILL) == 0) {
            iState = new Healthy();
        } else if (random.nextInt(PROBABILITY_OF_SYMPTOMS) == 0) {
            iState = new HaveSymptoms();
        } else {
            iState = new HaveNoSymptoms();
        }
        return getIndividual(room, iState);
    }

    private static Human getIndividual(final Room room, final IState iState) {
        double x = 0;
        double y = 0;
        if (random.nextInt(PROBABILITY_OF_ENTRY) == 0) {
            x = random.nextDouble() * room.getWidth();

//            if (random.nextInt(PROBABILITY_OF_ENTRY) == 0) {
//                y = room.getHeight() -1;
//            }

        } else {
            y = random.nextDouble() * room.getHeight();

//            if (random.nextInt(PROBABILITY_OF_ENTRY) == 0) {
//                x = room.getWidth() -1;
//            }
        }
        LinkedHashMap<String, Double> distances = new LinkedHashMap<>();
        LinkedHashMap<String, Integer> times = new LinkedHashMap<>();
        HumanData individualParams = HumanData.of(distances, times);
        return Human.of(Ids.createID(), iState, individualParams, true, x, y);
    }
}
