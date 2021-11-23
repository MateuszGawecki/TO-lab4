package com.company.human;

import com.company.population.Population;
import com.company.room.Room;
import com.company.states.HaveNoSymptoms;
import com.company.states.IState;
import com.company.vector.IVector;
import com.company.vector.Vector2D;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import static com.company.usefulConstants.Constants.*;

@Setter
@Getter
@Builder
@AllArgsConstructor(staticName = "of")
public class Human {

    private String id;
    private IState state;
    private HumanData individualParams;

    private Boolean isInRoom;

    private double positionX;
    private double positionY;

    public Human getCopy(){
        return Human.of(id,state.getCopy(),individualParams.getCopy(),isInRoom,positionX,positionY);
    }

    @Override
    public String toString() {
        return state.getName() + "-" + positionX + ";" + positionY;
    }


    public void setPosition(final IVector iVector) {
        double[] components = iVector.getComponents();
        this.positionX += components[0];
        this.positionY += components[1];
    }

    public void generatePosition(final Room room, final Random random) {
        double x = random.nextDouble() * MAX_DISTANCE;
        double y = random.nextDouble() * (MAX_DISTANCE - x);
        if (random.nextInt(PROBABILITY_OF_NEGATIVE) < 2) {
            x *= -1;
            y *= -1;
        }

        IVector iVector = new Vector2D(x, y);
        this.setPosition(iVector);
        if (getPositionX() > room.getWidth()) {
            if (random.nextInt(PROBABILITY_OF_RETURN) == 0) {
                this.setPositionX(getPositionX() - 1);
            } else {
                this.setIsInRoom(false);
            }
        } else if (getPositionX() < 0) {
            if (random.nextInt(PROBABILITY_OF_RETURN) == 0) {
                this.setPositionX(getPositionX() + 1);
            } else {
                this.setIsInRoom(false);
            }
        } else if (getPositionY() > room.getHeight()) {
            if (random.nextInt(PROBABILITY_OF_RETURN) == 0) {
                this.setPositionY(getPositionY() - 1);
            } else {
                this.setIsInRoom(false);
            }
        } else if (getPositionY() < 0) {
            if (random.nextInt(PROBABILITY_OF_RETURN) == 0) {
                this.setPositionY(getPositionY() + 1);
            } else {
                this.setIsInRoom(false);
            }
        }
    }

    public IState getState() {
        return state;
    }

    public void setState(final IState state) {
        this.state = state;
    }

    public void handle() {
        this.state.handle(this);
    }

    public void handle(final Human individual) {
        if (individual.getState().equals(new HaveNoSymptoms())) {
            if (new Random().nextInt(PROBABILITY_OF_INFECT) == 0) {
                this.handle();
            }
        } else {
            this.handle();
        }
    }

    public double getDistance(final Human individual) {
        return Math.sqrt(Math.pow(individual.getPositionX() - this.getPositionX(), 2) + Math.pow(individual.getPositionY() - this.getPositionY(), 2));
    }

    public Map<String, Double> getDistances(final List<Human> individuals) {
        Map<String, Double> distances = individualParams.getDistances();
        for (Human individual : individuals) {
            distances.put(individual.getId(), this.getDistance(individual));
        }
        return distances;
    }

    public Map<String, Integer> getTimes(final List<Human> individuals) {
        Map<String, Integer> times = individualParams.getTimes();
        Map<String, Double> distances = individualParams.getDistances();
        for (Human individual : individuals) {
            if (times.get(individual.getId()) == null) {
                times.put(individual.getId(), 0);
            } else if (distances.get(individual.getId()) <= 2) {
                int currentTime = times.get(individual.getId());
                times.put(individual.getId(), ++currentTime);
            } else {
                times.put(individual.getId(), 0);
            }
        }
        return times;
    }

    public void clearParams(final Population population) {
        Map<String, Integer> times = getIndividualParams().getTimes();
        Map<String, Double> distances = getIndividualParams().getDistances();
        times.keySet().removeIf(s -> population.getIndividual(s) == null);
        distances.keySet().removeIf(s -> population.getIndividual(s) == null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Human that = (Human) o;
        return Objects.equals(state, that.state);
    }

    @Override
    public int hashCode() {
        return Objects.hash(state);
    }
}
