import java.util.LinkedList;

public class Floor {
    private LinkedList<Passenger> passengers = new LinkedList<>();

    public void addPassenger(Passenger passenger) {
        passengers.addFirst(passenger);
    }

    public void removePassenger(Passenger  passenger) {
        passengers.remove(passenger);
    }

    public Passenger getFirstPassenger(){
      return   passengers.getFirst();
    }

    public boolean isEmpty(){
        return passengers.isEmpty();
    }

}
