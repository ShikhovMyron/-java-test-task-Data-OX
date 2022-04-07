import java.util.Objects;

public class Passenger {
    private final String name ;
    private final long creationTime;
    private final int neededFloor;

    public Passenger(String name, int neededFloor) {
        this.name = name;
        this.neededFloor = neededFloor;
        this.creationTime = System.currentTimeMillis();
    }

    public String getName() {
        return name;
    }

    public long getCreationTime() {
        return creationTime;
    }

    public int getNeededFloor() {
        return neededFloor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return creationTime == passenger.creationTime && name.equals(passenger.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, creationTime);
    }
}
