public class LiftFactory {
    public Lift buildLift(int maxPassengersCount, Building building){
        if (maxPassengersCount>0&&building!=null&&building.getFloorsCount()>0){
            return new Lift(maxPassengersCount,building);
        }
        return null;
    }
}
