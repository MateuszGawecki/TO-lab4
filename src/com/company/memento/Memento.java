package com.company.memento;

import com.company.population.Population;

public class Memento {
    private Population population;

    public Memento(Population newPopulation){ population=newPopulation;}

    public Population getSavedPopulation(){return population;}
}
