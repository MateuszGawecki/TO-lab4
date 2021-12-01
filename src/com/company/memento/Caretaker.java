package com.company.memento;

import java.util.ArrayList;

public class Caretaker {
    ArrayList<Memento> savedPopulations = new ArrayList<Memento>();

    public void addMemento(Memento memento){savedPopulations.add(memento);}

    public Memento getMemento(int idx){
        Memento memento = savedPopulations.get(idx);
        savedPopulations.remove(idx);
        return memento;
    }
}
