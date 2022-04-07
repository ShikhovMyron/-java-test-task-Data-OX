import java.util.LinkedList;

public class Floor {
    private final LinkedList<Passenger> passengers = new LinkedList<>();

    public void addPassenger(Passenger passenger) {
        passengers.addFirst(passenger);
    }

    public boolean removePassenger(Passenger passenger) {
        return passengers.remove(passenger);
    }

    public boolean isEmpty(){
        return passengers.isEmpty();
    }
}
