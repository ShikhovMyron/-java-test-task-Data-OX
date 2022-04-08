import java.util.ArrayList;
import java.util.List;

public class DefaultUtils {
    public static Building createBuilding(
            int floorsCount, int[] floorsNumberSpawn, List<Passenger> passengers
    ) {
        Building building = new Building(floorsCount);
        for (int i = 0; i < floorsNumberSpawn.length; i++) {
            try {
                building.putPassenger(floorsNumberSpawn[i], passengers.get(i));
            } catch (InvalidParameterException e) {
                e.printStackTrace();
            }
        }
        return building;
    }

    public static List<Passenger> getPassengers(int[] floorsNumberNeeded) {
        List<Passenger> passengers = new ArrayList<>();
        for (int i = 0; i < floorsNumberNeeded.length; i++) {
            passengers.add(new Passenger(floorsNumberNeeded[i]));
        }
        return passengers;
    }

    public static int getSummaryPassengersCount(Building building) {
        int count = 0;
        for (int i = 0; i < building.getFloorsCount(); i++) {
            try {
                count += building.getFloor(i).getPassengers().size();
            } catch (InvalidParameterException ignore) {
            }
        }
        return count;
    }
}
