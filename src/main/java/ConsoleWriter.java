import java.util.List;

public class ConsoleWriter {

    public static void writeCurrentInfo(Building building, List<Passenger> passengers, int currentFloor) {
        System.out.println("***************************************************************************************");
        for (int i = building.getFloorsCount() - 1; i >= 0; i--) {
            System.out.printf(
                    "%1$2s(f)  |%2$2s \uD83D\uDD74️\u200D️  "
                    , i
                    , building.getFloor(i).getPassengers().size());
            if (i == currentFloor) {
//                if (isUp) {
//                    System.out.print("^|");
//                } else {
//                    System.out.print("v|");
//                }
                System.out.printf("%1$2s \uD83D\uDD74️\u200D|️", passengers.size());
            }
            System.out.println();
        }
    }
}
