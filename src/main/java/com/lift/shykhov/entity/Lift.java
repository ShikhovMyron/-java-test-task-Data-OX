package com.lift.shykhov.entity;

import com.lift.shykhov.exception.InvalidParameterException;
import com.lift.shykhov.service.ConsoleWriter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Lift {
    private final int maxPassengersCount;
    private final Building building;
    private final List<Passenger> passengers;
    private int currentFloor = 0;
    private int maxFloor;
    private boolean isUp = true;

    public static Lift buildLift(int maxPassengersCount, Building building) throws InvalidParameterException {
        if (maxPassengersCount > 0 && building != null && building.getFloorsCount() > 0) {
            return new Lift(maxPassengersCount, building);
        } else {
            throw new InvalidParameterException();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lift lift = (Lift) o;
        return maxPassengersCount == lift.maxPassengersCount && currentFloor == lift.currentFloor && maxFloor == lift.maxFloor && isUp == lift.isUp && building.equals(lift.building) && passengers.equals(lift.passengers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maxPassengersCount, building, passengers, currentFloor, maxFloor, isUp);
    }

    public boolean work() {
        boolean isMovePassengers = move();
        checkNextStep();
        ConsoleWriter.writeCurrentInfo(building, this.passengers, currentFloor, isUp);
        if (isUp) {
            currentFloor++;
        } else {
            currentFloor--;
        }

        return isMovePassengers;
    }

    private Lift(int maxPassengersCount, Building building) {
        this.maxPassengersCount = maxPassengersCount;
        this.passengers = new ArrayList<>();
        this.building = building;
        this.maxFloor = building.getFloorsCount() - 1;
    }

    private boolean move() {
        boolean isMovePassengers = true;

        List<Passenger> passengers = new ArrayList<>();
        if (isNeededFloor()) {
            passengers.addAll(passengerQuit(currentFloor));
        }
        addPassengersIfPossible();
        if (!passengers.isEmpty()) {
            isMovePassengers = building.movePassengers(passengers);
        }
        setMaxNeededFloor();

        return isMovePassengers;
    }

    private void checkNextStep() {
        if (isUp) {
            if (currentFloor == maxFloor || currentFloor == building.getFloorsCount() - 1) {
                isUp = false;
                addPassengersIfPossible();
            }
        } else {
            if (currentFloor == 0) {
                isUp = true;
                addPassengersIfPossible();
            }
        }
    }

    private List<Passenger> passengerQuit(int currentFloor) {
        List<Passenger> passengersToQuit = passengers.stream()
                .filter(passenger -> passenger.getNeededFloor() == currentFloor)
                .collect(Collectors.toList());
        passengers.removeAll(passengersToQuit);
        return passengersToQuit;
    }

    private void addPassengersIfPossible() {
        try {
            while (!isLiftFull() && isCurrentFloorHasCorrectPassengers()) {
                Passenger passenger = building.getFirstCorrectPassenger(currentFloor, isUp);
                if (isPassengerValid(passenger) && passengerEntered(passenger)) {
                    building.removePassenger(currentFloor, passenger);
                } else {
                    throw new InvalidParameterException(passenger.toString());
                }
            }
        } catch (InvalidParameterException e) {
            e.printStackTrace();
        }
    }

    public boolean passengerEntered(Passenger passenger) {
        if (passengers.size() < maxPassengersCount && passenger != null) {
            passengers.add(passenger);
            return true;
        }
        return false;
    }

    private boolean isPassengerValid(Passenger passenger) {
        return !(passenger == null ||
                (passenger.getNeededFloor() < 0 || passenger.getNeededFloor() >= building.getFloorsCount()));
    }

    private boolean isLiftFull() {
        return passengers.size() == maxPassengersCount;
    }

    private boolean isCurrentFloorHasCorrectPassengers() throws InvalidParameterException {
        return building.getFirstCorrectPassenger(currentFloor, isUp) != null;
    }

    private boolean isNeededFloor() {
        return passengers.stream().anyMatch(s -> s.getNeededFloor() == currentFloor);
    }


    private void setMaxNeededFloor() {
        maxFloor = passengers.stream()
                .map(Passenger::getNeededFloor)
                .max(Integer::compareTo).orElse(maxFloor);
    }

}
