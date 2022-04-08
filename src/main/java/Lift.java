import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lift {
    private final int maxPassengersCount;
    private final Building building;
    private final List<Passenger> passengers;
    private int currentFloor = 0;
    private int maxFloor;
    private boolean isUp = true;

    public Lift(int maxPassengersCount, Building building) {
        this.maxPassengersCount = maxPassengersCount;
        this.passengers = new ArrayList<>();
        this.building = building;
        maxFloor = building.getFloorsCount()-1;
    }

    public void work() {
        checkNextStep();
        move();
        ConsoleWriter.writeCurrentInfo(building, this.passengers, currentFloor, isUp);

        if (isUp) {
            currentFloor++;
        } else {
            currentFloor--;
        }
    }

    private void checkNextStep() {
        if (isUp) {
            if (currentFloor == maxFloor || currentFloor == building.getFloorsCount() - 1) {
                isUp = false;
            }
        } else {
            if (currentFloor == 0) {
                isUp = true;
            }
        }
    }

    private void move() {
        List<Passenger> passengers = new ArrayList<>();
        if (isNeededFloor()) {
            passengers.addAll(passengerQuit(currentFloor));
        }
        while (!isLiftFull() && isCurrentFloorHasCorrectPassengers()) {
            Passenger passenger = building.getFirstCorrectPassenger(currentFloor, isUp);
            if (passenger != null) {
                passengerEntered(passenger);
                building.removePassenger(currentFloor, passenger);
            }
        }
        if (!passengers.isEmpty()) {
            building.movePassengers(passengers);
        }
        getMaxNeededFloor();


    }

    private void passengerEntered(Passenger passenger) {
        if (passengers.size() < maxPassengersCount && passenger != null) {
            passengers.add(passenger);
        }
    }

    private List<Passenger> passengerQuit(int currentFloor) {
        List<Passenger> passengersToQuit = passengers.stream()
                .filter(passenger -> passenger.getNeededFloor() == currentFloor)
                .collect(Collectors.toList());
        passengers.removeAll(passengersToQuit);
        return passengersToQuit;
    }

    private boolean isLiftFull() {
        return passengers.size() == maxPassengersCount;
    }

    private boolean isCurrentFloorHasCorrectPassengers() {
        return building.getFirstCorrectPassenger(currentFloor, isUp) != null;
    }

    private boolean isNeededFloor() {
        return passengers.stream().anyMatch(s -> s.getNeededFloor() == currentFloor);
    }


    private void getMaxNeededFloor() {
        maxFloor = passengers.stream()
                .map(Passenger::getNeededFloor)
                .max(Integer::compareTo).orElse(building.getFloorsCount() - 1);
    }

}
