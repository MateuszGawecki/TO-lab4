package com.company.states;

import com.company.human.Human;

import java.util.Objects;

public class Resistant implements IState {

    private final String name = "resist";

    public Resistant getCopy(){
        return  new Resistant();
    }

    @Override
    public void handle(final Human individual) {
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
