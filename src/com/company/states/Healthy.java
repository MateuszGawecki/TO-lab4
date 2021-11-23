package com.company.states;

import com.company.human.Human;

import java.util.Objects;
import java.util.Random;

import static com.company.usefulConstants.Constants.PROBABILITY_OF_SYMPTOMS;

public class Healthy implements IState {

    private final String name = "healthy";

    public Healthy getCopy(){
        return  new Healthy();
    }

    @Override
    public void handle(final Human individual) {
        if (!individual.getState().equals(new HaveNoSymptoms()) || !individual.getState().equals(new HaveNoSymptoms())) {
            if (new Random().nextInt(PROBABILITY_OF_SYMPTOMS) == 0) {
                individual.setState(new HaveSymptoms());
            } else {
                individual.setState(new HaveNoSymptoms());
            }
        }
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IState that = (IState) o;
        return Objects.equals(this.getName(), that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName());
    }
}
