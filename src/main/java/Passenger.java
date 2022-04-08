import java.util.Objects;
import java.util.Random;

public class Passenger {
    private final String name;
    private final long randomLong;

    public void setNeededFloor(int neededFloor) {
        this.neededFloor = neededFloor;
    }

    private int neededFloor;

    public Passenger(String name, int neededFloor) {
        this.name = name;
        this.neededFloor = neededFloor;
        this.randomLong = new Random().nextLong();
    }

    public String getName() {
        return name;
    }

    public long getRandomLong() {
        return randomLong;
    }

    public int getNeededFloor() {
        return neededFloor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return randomLong == passenger.randomLong && name.equals(passenger.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, randomLong);
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "name='" + name + '\'' +
                ", randomLong=" + randomLong +
                ", neededFloor=" + neededFloor +
                '}';
    }
}
