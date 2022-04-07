import java.util.List;

public class ConsoleWriter {
    public static void writeCurrentInfo(List<Passenger>[] floorsQueue, Lift lift, int currentFloor, boolean isUp) {
        System.out.println("***************************************************************************************");
        for (int i = floorsQueue.length; i > 0; i--) {
            System.out.printf("%1$2s(f)  |%2$2s \uD83D\uDD74️\u200D️  ", i, floorsQueue[i - 1].size());
            if (i == currentFloor) {
                if (isUp) {
                    System.out.print("^|");
                } else {
                    System.out.print("v|");
                }
                System.out.printf("%1$2s \uD83D\uDD74️\u200D|️", lift.getPassengers().size());
            }
            System.out.println();
        }
    }
}
