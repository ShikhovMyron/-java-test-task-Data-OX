public class Building {
    private final int floorsCount;
    private final Floor[] floors;

    public Building(int floorsCount) {
        this.floorsCount = floorsCount;
        this.floors = new Floor[floorsCount];
    }

    public void putPassenger(int floor, Passenger passenger) {
        floors[floor].addPassenger(passenger);
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


