import java.util.Arrays;

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

    public void removePassenger(int floor, Passenger passenger) {
        floors[floor].removePassenger(passenger);
    }

    public Floor getFloor(int floorNumber) {
        return floors[floorNumber];
    }

    public int getFloorsCount() {
        return floorsCount;
    }
}


