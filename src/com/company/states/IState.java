package com.company.states;

import com.company.human.Human;

public interface IState {
    void handle(final Human individual);

    String getName();

    IState getCopy();
}
