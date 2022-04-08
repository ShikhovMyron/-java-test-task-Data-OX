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

    public void putPassenger(int floorNumber, Passenger passenger) throws InvalidParameterException {
        if (floorNumber >= 0 && floorNumber < floorsCount && passenger != null) {
            floors[floorNumber].addPassenger(passenger);
        }else {
            throw new InvalidParameterException();
        }
    }

    public Passenger getFirstCorrectPassenger(int floorNumber, boolean isUp) throws InvalidParameterException {
        if (floorNumber >= 0 && floorNumber < floorsCount) {
            return floors[floorNumber].getPassengers().stream()
                    .filter(s -> s.getNeededFloor() > floorNumber == isUp)
                    .findFirst().orElse(null);
        }else {
            throw new InvalidParameterException(String.valueOf(floorNumber));
        }
    }

    public void removePassenger(int floorNumber, Passenger passenger) throws InvalidParameterException {
        if (floorNumber >= 0 && floorNumber < floorsCount && passenger != null) {
            floors[floorNumber].removePassenger(passenger);
        }else {
            throw new InvalidParameterException();
        }
    }

    public Floor getFloor(int floorNumber) throws InvalidParameterException {
        if (floorNumber >= 0 && floorNumber < floorsCount) {
            return floors[floorNumber];
        }else {
            throw new InvalidParameterException(String.valueOf(floorNumber));
        }
    }

    public int getFloorsCount() {
        return floorsCount;
    }

    public void movePassengers(List<Passenger> passengers) {
        for (Passenger passenger : passengers) {
            int randomFloorNumber = Utils.getRandomNumber(0, floorsCount - 1);
            int randomFloorNumberNeeded = Utils.getRandomNumber(0, floorsCount - 1);
            while (randomFloorNumberNeeded == randomFloorNumber) {
                randomFloorNumberNeeded = Utils.getRandomNumber(0, floorsCount - 1);
            }

            passenger.setNeededFloor(randomFloorNumberNeeded);
            try {
                putPassenger(randomFloorNumber, passenger);
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }
        }
    }
}


