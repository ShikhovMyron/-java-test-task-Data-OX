import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Lift {
    private final int maxPassengersCount;
    private final Building building;
    private List<Passenger> passengers;
    private int currentFloor = 0;



    public Lift(int maxPassengersCount, Building building) {
        this.maxPassengersCount = maxPassengersCount;
        this.passengers = new ArrayList<>();
        this.building = building;
    }

    public void move() {
        List<Passenger> passengers = new ArrayList<>();
        if (isNeededFloor()) {
            passengers.addAll(passengerQuit(currentFloor));
        }
        while (!isLiftFull() && isCurrentFloorHasPassengers()) {
            Passenger passenger = building.getFirstPassenger(currentFloor);
            if (passenger != null) {
                passengerEntered(passenger);
                building.removePassenger(currentFloor, passenger);
                passengers.add(passenger);
            }
        }
        if (!passengers.isEmpty()) {
            building.movePassengers(passengers);
        }

        currentFloor++;
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

    private boolean isCurrentFloorHasPassengers() {
        return !building.getFloor(currentFloor).isEmpty();
    }

    private boolean isNeededFloor() {
        return passengers.stream().anyMatch(s -> s.getNeededFloor() == currentFloor);
    }
}
