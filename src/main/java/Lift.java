import java.util.List;
import java.util.stream.Collectors;

public class Lift {
    private final int maxPassengersCount;
    private final Building building;
    private List<Passenger> passengers;
    private int currentFloor = 0;

    public Lift(int maxPassengersCount, Building building) {
        this.maxPassengersCount = maxPassengersCount;
        this.passengers = List.of();
        this.building = building;
    }

    public void move(){
        while (true){

//            if(!isLiftFull() && isCurrentFloorHasPassengers()){

        }
    }


    private boolean isLiftFull(){
        return passengers.size() == maxPassengersCount;
    }

    private boolean isCurrentFloorHasPassengers(){
       return  !building.getFloor(currentFloor).isEmpty();
    }

    public boolean passengerEntered(Passenger passenger) {
        if (passengers.size() < maxPassengersCount) {
            return passengers.add(passenger);
        } else return false;
    }

    public List<Passenger> passengerQuit(int currentFloor) {
        List<Passenger> passengersToQuit = passengers.stream()
                .filter(passenger -> passenger.getNeededFloor() == currentFloor)
                .collect(Collectors.toList());
        passengers.removeAll(passengersToQuit);
        return passengersToQuit;
    }
}
