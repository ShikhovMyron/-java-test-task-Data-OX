import java.util.Random;

public class Utils {

    public static void fillBuildingByPassengers(Building building, int minOnFloor, int maxOnFloor){
        for (int i = 0; i < building.getFloorsCount(); i++) {
            for (int j = 0; j < getRandomNumber(minOnFloor, maxOnFloor); j++) {
                building.putPassenger(i,new Passenger("",getRandomNumber(0,building.getFloorsCount())));
            }
        }
    }

    public static int getRandomNumber(int min, int max) {
        return new Random().nextInt(max+1-min)+min;
    }


}
