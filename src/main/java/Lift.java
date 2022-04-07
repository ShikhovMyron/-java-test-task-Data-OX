import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lift {
    private final int maxPassengersCount;
    private final Building building;
    private final List<Passenger> passengers;
    private int currentFloor = 0;
    private int minFloor = currentFloor;
    private int maxFloor;
    private boolean isUp = true;

    public Lift(int maxPassengersCount, Building building) {
        this.maxPassengersCount = maxPassengersCount;
        this.passengers = new ArrayList<>();
        this.building = building;
        maxFloor = building.getFloorsCount();
    }

    public void work() {
        if (isUp && currentFloor <= maxFloor) {
            if (currentFloor == maxFloor) {
                isUp = false;
                maxFloor = building.getFloorsCount() - 1;
            }
            move(isUp);
        } else if (!isUp && currentFloor >= minFloor) {
            if (currentFloor == minFloor) {
                isUp = true;
                minFloor = 0;
            }
            move(isUp);
        }
        if (isUp) {
            currentFloor++;
        } else {
            currentFloor--;
        }
        ConsoleWriter.writeCurrentInfo(building, this.passengers, currentFloor);
    }

    private void move(boolean isUp) {
        List<Passenger> passengers = new ArrayList<>();
        if (isNeededFloor()) {
            passengers.addAll(passengerQuit(currentFloor));
        }
        while (!isLiftFull() && isCurrentFloorHasCorrectPassengers(isUp)) {
            Passenger passenger = building.getFirstCorrectPassenger(currentFloor, isUp);
            if (passenger != null) {
                passengerEntered(passenger);
                building.removePassenger(currentFloor, passenger);
                passengers.add(passenger);
            }
        }
        if (!passengers.isEmpty()) {
            building.movePassengers(passengers);
        }
        if (isUp) {
            recalculateMaxFloor();
        } else {
            recalculateMinFloor();
        }
    }

    public void passengerEntered(Passenger passenger) {
        if (passengers.size() < maxPassengersCount && passenger != null) {
            passengers.add(passenger);
        }
    }

    public List<Passenger> passengerQuit(int currentFloor) {
        List<Passenger> passengersToQuit = passengers.stream()
                .filter(passenger -> passenger.getNeededFloor() == currentFloor)
                .collect(Collectors.toList());
        passengers.removeAll(passengersToQuit);
        return passengersToQuit;
    }

    private boolean isLiftFull() {
        return passengers.size() == maxPassengersCount;
    }

    private boolean isCurrentFloorHasCorrectPassengers(boolean isUp) {
        return building.getFirstCorrectPassenger(currentFloor, isUp) != null;
    }

    private boolean isNeededFloor() {
        return passengers.stream().anyMatch(s -> s.getNeededFloor() == currentFloor);
    }

    private void recalculateMinFloor() {
        if (!passengers.isEmpty()) {
            minFloor = passengers.stream()
                    .map(Passenger::getNeededFloor)
                    .min(Integer::compareTo).orElse(0);
        }
    }

    private void recalculateMaxFloor() {
        if (!passengers.isEmpty()) {
            maxFloor = passengers.stream()
                    .map(Passenger::getNeededFloor)
                    .max(Integer::compareTo).orElse(0);
        }
    }
}
