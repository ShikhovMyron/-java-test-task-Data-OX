package com.lift.shykhov.entity;

import java.util.LinkedList;
import java.util.Objects;

public class Floor {
    private final LinkedList<Passenger> passengers = new LinkedList<>();

    public void addPassenger(Passenger passenger) {
        passengers.addFirst(passenger);
    }

    public void removePassenger(Passenger passenger) {
        passengers.remove(passenger);
    }

    public LinkedList<Passenger> getPassengers() {
        return passengers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Floor floor = (Floor) o;
        return Objects.equals(passengers, floor.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(passengers);
    }
}
