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

    public Passenger getFirstCorrectPassenger(int floorNumber, boolean isUp) {
        return floors[floorNumber].getPassengers().stream()
                .filter(s -> s.getNeededFloor() > floorNumber == isUp)
                .findFirst().orElse(null);
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
            int randomFloorNumber = Utils.getRandomNumber(0, floorsCount - 1);
            putPassenger(randomFloorNumber, passenger);
        }
    }
}


