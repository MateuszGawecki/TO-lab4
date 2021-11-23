package com.company.states;

import com.company.human.Human;
import lombok.Setter;

import java.util.Objects;
import java.util.Random;

import static com.company.usefulConstants.Constants.PROBABILITY_OF_RECOVER;

@Setter
public class HaveNoSymptoms implements IState {
    private final String name = "haveNotSymptoms";
    private int illnessCounter = 0;

    public HaveNoSymptoms getCopy(){
        return  new HaveNoSymptoms();
    }

    @Override
    public void handle(final Human individual) {
        int mod = this.getIllnessCounter() / 25;
        if (mod >= 20 && mod < 30) {
            if (new Random().nextInt(PROBABILITY_OF_RECOVER) == 0) {
                individual.setState(new Resistant());
            }
        } else if (mod >= 30) {
            individual.setState(new Resistant());
        }
        incIllnessCounter();
    }

    public int getIllnessCounter() {
        return this.illnessCounter;
    }

    public void incIllnessCounter() {
        this.illnessCounter++;
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
