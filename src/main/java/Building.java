import java.util.List;

public class Building {
    private final int floorsCount;
    private final Floor[] floors;

    public Building(int floorsCount) {
        this.floorsCount = floorsCount;
        this.floors = new Floor[floorsCount];
        for (int i = 0; i < floorsCount; i++) {
            this.floors[i] = new Floor();
        }
    }

    public void putPassenger(int floorNumber, Passenger passenger) {
        floors[floorNumber].addPassenger(passenger);
    }

    public Passenger getFirstPassenger(int floorNumber) {
        return floors[floorNumber].getFirstPassenger();
    }

    public void removePassenger(int floorNumber, Passenger passenger) {
        floors[floorNumber].removePassenger(passenger);
    }

    public Floor getFloor(int floorNumber) {
        return floors[floorNumber];
    }

    public int getFloorsCount() {
        return floorsCount;
    }

    public void movePassengers(List<Passenger> passengers) {
        for (Passenger passenger : passengers) {
            int randomFloorNumber = Utils.getRandomNumber(0, floorsCount);
            putPassenger(randomFloorNumber, passenger);
        }
    }
}


