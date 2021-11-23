package com.company.controller;

import com.company.human.Human;
import com.company.human.HumanData;
import com.company.population.Population;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Setter
@Getter
public class Controller {

    public void prepareSimulation(final Population population) {
        for (int j = 0; j < population.getInfected().size(); j++) {
            Human individual = population.getInfected().get(j);
            List<Human> comparedIndividuals = new LinkedList<>(population.getPossibleInfected());
            Map<String, Double> distances = individual.getDistances(comparedIndividuals);
            Map<String, Integer> times = individual.getTimes(comparedIndividuals);

            individual.setIndividualParams(HumanData.of(distances, times));
        }
    }
}
