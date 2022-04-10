package com.lift.shykhov;

import com.lift.shykhov.entity.Building;
import com.lift.shykhov.entity.Lift;
import com.lift.shykhov.exception.InvalidParameterException;
import com.lift.shykhov.util.Utils;

import java.util.concurrent.TimeUnit;

public class Application {
    public static void main(String[] args) throws InterruptedException, InvalidParameterException {
        Building building = new Building(Utils.getRandomNumber(5, 20));
        Utils.fillBuildingByPassengers(building, 0, 10);
        Lift lift = Lift.buildLift(5, building);

        while (lift.work()) {
            TimeUnit.MILLISECONDS.sleep(1000);
        }
    }

}
