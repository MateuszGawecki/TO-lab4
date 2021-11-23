package com.company.population;

import com.company.human.Human;
import com.company.states.*;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Getter
@AllArgsConstructor(staticName = "of")
public class Population {
    private List<Human> population;

    public Population getCopy(){
        List<Human> newList = new ArrayList<>();

        for(Human individual: population){
            newList.add(individual.getCopy());
        }

        return new Population(newList);
    }

    public void addIndividual(final Human individual) {
        this.population.add(individual);
    }

    public void setPopulation(List<Human> individuals) {
        this.population = individuals;
    }

    public Human getIndividual(final String id) {
        for (Human individual : population) {
            if (individual.getId().equals(id)) {
                return individual;
            }
        }
        return null;
    }

    public void deleteIfExited() {
        population.removeIf(individual -> !individual.getIsInRoom());
    }

    public List<Human> getInfected() {
        List<Human> individuals = new LinkedList<>();
        individuals.addAll(getHaveSymptoms());
        individuals.addAll(getHaveNotSymptoms());
        return individuals;
    }

    private List<Human> getHaveSymptoms() {
        return getIndividuals(new HaveSymptoms());
    }

    private List<Human> getHaveNotSymptoms() {
        return getIndividuals(new HaveNoSymptoms());
    }

    public List<Human> getNotInfected() {
        List<Human> individuals = new LinkedList<>();
        individuals.addAll(getResist());
        individuals.addAll(getHealthy());
        return individuals;
    }

    private List<Human> getResist() {
        return getIndividuals(new Resistant());
    }

    private List<Human> getHealthy() {
        return getIndividuals(new Healthy());
    }

    public List<Human> getPossibleInfected() {
        return getHealthy();
    }

    private List<Human> getIndividuals(IState state) {
        List<Human> individuals = new LinkedList<>();
        for (Human individual : population) {
            if (individual.getState().equals(state)) {
                individuals.add(individual);
            }
        }
        return individuals;
    }
}
