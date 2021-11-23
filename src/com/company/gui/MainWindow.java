package com.company.gui;

import com.company.board.Board;
import com.company.controller.Controller;
import com.company.generator.GeneratePopulation;
import com.company.human.Human;
import com.company.memento.Caretaker;
import com.company.memento.Originator;
import com.company.population.Population;
import com.company.room.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Map;
import java.util.Random;

public class MainWindow extends JFrame {
    public JButton saveBut, undoBut, redoBut;
    Caretaker caretaker = new Caretaker();
    Originator originator = new Originator();
    int savedPopulations = 0, counterPopulation = 0;

    public Population getPopulation() {
        return population;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    private Population population;

    public MainWindow(){
        Random random = new Random();
        Room room = new Room(40, 30);
        setPopulation(GeneratePopulation.generateNotResistPopulation(500, room));
        Controller controller = new Controller();


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(1000, 700);
        this.setLocation(50, 50);
        this.setTitle("Symulacja choroby");
        //this.setVisible(true);

        Board chart = new Board(getPopulation().getPopulation().toString());
        chart.setBackground(Color.GRAY);

        ButtonListener saveListener = new ButtonListener();
        ButtonListener redoListener = new ButtonListener();
        ButtonListener undoListener = new ButtonListener();


        saveBut = new JButton("Save");
        undoBut = new JButton("Undo");
        redoBut = new JButton("Redo");

        saveBut.addActionListener(saveListener);
        redoBut.addActionListener(redoListener);
        undoBut.addActionListener(undoListener);

//        saveBut.addActionListener(e -> {
//            if(e.getSource() == saveBut) {
//                Population currentPopulation = getPopulation();
//
//                originator.set(currentPopulation);
//                caretaker.addMemento(originator.storeInMemento());
//
//                savedPopulations++;
//                counterPopulation++;
//
//                undoBut.setEnabled(true);
//            }
//        });
//
//        undoBut.addActionListener(e -> {
//            if(e.getSource() == undoBut) {
//                if(counterPopulation>=1) {
//                    counterPopulation--;
//                    setPopulation(originator.restoreFromMemento(caretaker.getMemento(counterPopulation)));
//                    redoBut.setEnabled(true);
//                }
//                else {
//                    undoBut.setEnabled(false);
//                }
//            }
//        });
//
//        redoBut.addActionListener(e -> {
//                if(e.getSource()==redoBut) {
//                    if((savedPopulations -1) > counterPopulation) {
//                        counterPopulation++;
//
//                        setPopulation(originator.restoreFromMemento(caretaker.getMemento(counterPopulation)));
//
//                        undoBut.setEnabled(true);
//                    }
//                    else {
//                        redoBut.setEnabled(false);
//                    }
//                }
//        });

        JPanel jp = new JPanel();
        jp.add(saveBut);
        jp.add(undoBut);
        jp.add(redoBut);

        this.add(chart , BorderLayout.WEST);
        this.add(jp, BorderLayout.EAST);
        this.setVisible(true);

        controller.prepareSimulation(population);
        int counter = 0;
        for (; ; ) {
            for (int i = 0; i < 25; i++) {

                population.getInfected().forEach(Human::handle);
                chart.updateList(population.getPopulation().toString());

                this.add(chart);
                this.revalidate();
                this.repaint();

                for (int j = 0; j < population.getInfected().size(); j++) {
                    Human individual = population.getInfected().get(j);
                    individual.clearParams(population);
                    individual.getDistances(population.getNotInfected());
                    individual.getTimes(population.getNotInfected());

                    Map<String, Integer> times = individual.getIndividualParams().getTimes();

                    for (String key : times.keySet()) {
                        if (times.get(key) >= 75) {
                            population.getIndividual(key).handle(individual);
                        }
                    }
                }
                population.getPopulation().forEach(individual1 -> individual1.generatePosition(room, random));
                population.deleteIfExited();
                if (counter % 7 == 0) {
                    population.addIndividual(GeneratePopulation.getNotResistIndividual(room));
                }
            }

            System.out.println(counter);
            System.out.println("Infected: " + population.getInfected().size() + " " + "Not infected: " + population.getNotInfected().size());
            if (counter == 10000) {
                break;
            }
            if (population.getInfected().size() == 0) {
                break;
            }
            counter++;
        }
        population.getPopulation().forEach(System.out::println);
        System.out.println("Infected: " + population.getInfected().size() + " " + "Not infected: " + population.getNotInfected().size());
    }

    class ButtonListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource() == saveBut) {
                Population currentPopulation = getPopulation().getCopy();

                originator.set(currentPopulation);
                caretaker.addMemento(originator.storeInMemento());

                savedPopulations++;
                counterPopulation++;

                undoBut.setEnabled(true);
            }
            else{
                if(e.getSource() == undoBut) {
                    if(counterPopulation>=1) {
                        counterPopulation--;
                        setPopulation(originator.restoreFromMemento(caretaker.getMemento(counterPopulation)));
                        redoBut.setEnabled(true);
                    }
                    else {
                        undoBut.setEnabled(false);
                    }
                }
                else{
                    if(e.getSource()==redoBut) {
                        if((savedPopulations -1) > counterPopulation) {
                            counterPopulation++;

                            setPopulation(originator.restoreFromMemento(caretaker.getMemento(counterPopulation)));

                            undoBut.setEnabled(true);
                        }
                        else {
                            redoBut.setEnabled(false);
                        }
                    }
                }
            }
        }
    }
}
