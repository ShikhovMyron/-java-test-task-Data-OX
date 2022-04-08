import java.util.Objects;
import java.util.Random;

public class Passenger {
    private final long randomLong;

    private int neededFloor;

    public Passenger(int neededFloor) {
        this.neededFloor = neededFloor;
        this.randomLong = new Random().nextLong();
    }

    public void setNeededFloor(int neededFloor) {
        this.neededFloor = neededFloor;
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
        return randomLong == passenger.randomLong;
    }

    @Override
    public int hashCode() {
        return Objects.hash(randomLong);
    }

    @Override
    public String toString() {
        return "Passenger{" +
                "randomLong=" + randomLong +
                ", neededFloor=" + neededFloor +
                '}';
    }
}
