import java.util.LinkedList;

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
}
