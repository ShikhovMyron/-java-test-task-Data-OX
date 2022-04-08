public class Application {
    public static void main(String[] args) throws InterruptedException {
        Building building = new Building(Utils.getRandomNumber(5, 20));
        Utils.fillBuildingByPassengers(building, 0, 10);
        Lift lift = new Lift(5, building);
        while (true) {
            lift.work();
//            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }

}
