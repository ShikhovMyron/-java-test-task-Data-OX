package com.lift.shykhov;

import com.lift.shykhov.entity.Building;
import com.lift.shykhov.entity.Lift;
import com.lift.shykhov.entity.Passenger;
import com.lift.shykhov.exception.InvalidParameterException;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.lift.shykhov.DefaultUtils.createBuilding;
import static com.lift.shykhov.DefaultUtils.getPassengers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentCaptor.forClass;
import static org.mockito.Mockito.*;

public class LiftTest {

    @Test
    public void workBaseTest() throws InvalidParameterException {
        // Arrange
        List<Passenger> expected = getPassengers(new int[]{5, 5, 7, 3});
        ArgumentCaptor<Passenger> captor = forClass(Passenger.class);
        Building buildingSpy = spy(createBuilding(10, new int[]{1, 3, 4, 4}, expected));
        Lift lift = Lift.buildLift(6, buildingSpy);
        // Action
        doNothing().when(buildingSpy).putPassenger(anyInt(), any(Passenger.class));
        for (int i = 0; i < 15; i++) {
            lift.work();
        }
        // Assert
        //погрузились 4 пассажира
        verify(buildingSpy, times(4)).removePassenger(anyInt(), captor.capture());
        //в лифте 4 заданных пассажира
        assertEquals(expected, captor.getAllValues());
    }

    @Test // лифт должен проехать вверх-вниз поссле чего отвезди пассажира на нужный этаж
    public void workLongDistanceTest() throws InvalidParameterException {
        // Arrange
        int floorSpawn = 0;
        int neededFloor = 1;
        Passenger expected = new Passenger(neededFloor);
        ArgumentCaptor<Passenger> captorPassenger = forClass(Passenger.class);
        Building buildingSpy = spy(createBuilding(4, new int[]{}, new ArrayList<>()));
        Lift lift = Lift.buildLift(2, buildingSpy);
        // Action
        doReturn(true).when(buildingSpy).movePassengers(anyList());
        lift.work();
        buildingSpy.putPassenger(floorSpawn, expected);
        for (int i = 0; i < 7; i++) {
            lift.work();
        }
        // Assert
        //на 0м этаже погрузился 1 пассажир
        verify(buildingSpy, times(1)).removePassenger(eq(floorSpawn), captorPassenger.capture());
        assertEquals(expected, captorPassenger.getValue());
        //на 1м этаже нет пассажиров в очереди
        assertEquals(0, buildingSpy.getFloor(neededFloor).getPassengers().size());
    }

    @Test
    public void workMaxPassengersCountTest() throws InvalidParameterException {
        // Arrange
        int maxPassengersCount = 3;
        ArgumentCaptor<Passenger> captorPassenger = forClass(Passenger.class);
        List<Passenger> passengers = getPassengers(new int[]{3, 2, 1, 1});
        Building buildingSpy = spy(createBuilding(4, new int[]{0, 0, 0, 0}, passengers));
        Lift lift = Lift.buildLift(maxPassengersCount, buildingSpy);
        // Action
        doNothing().when(buildingSpy).putPassenger(anyInt(), any(Passenger.class));
        lift.work();
        // Assert
        //на 0м этаже погрузились 3 пассажира
        verify(buildingSpy, times(3)).removePassenger(eq(0), captorPassenger.capture());
        assertEquals(maxPassengersCount, captorPassenger.getAllValues().size());
        //на 0м этаже есть 1 пассажир в очереди
        assertEquals(1, buildingSpy.getFloor(0).getPassengers().size());
    }

    @Test
    public void workMaxFloorNumberTest() throws InvalidParameterException {
        // Arrange
        ArgumentCaptor<Passenger> captorPassenger = forClass(Passenger.class);
        List<Passenger> expected = getPassengers(new int[]{1, 2, 6});
        Building buildingSpy = spy(createBuilding(7, new int[]{0, 1, 3}, expected));
        Lift lift = Lift.buildLift(3, buildingSpy);
        // Action
        doNothing().when(buildingSpy).putPassenger(anyInt(), any(Passenger.class));
        for (int i = 0; i < 5; i++) {
            lift.work();
        }
        // Assert
        //на 0м этаже погрузился 1 пассажир
        verify(buildingSpy, times(1)).removePassenger(eq(0), captorPassenger.capture());
        //на 1м этаже погрузился 1 пассажир
        verify(buildingSpy, times(1)).removePassenger(eq(1), captorPassenger.capture());
        //на 3м этаже не погрузились пассажиры
        verify(buildingSpy, times(0)).removePassenger(eq(3), eq(expected.remove(2)));
        //в лифте 3 заданных пассажира
        assertEquals(expected, captorPassenger.getAllValues());
        //на 0м этаже нет пассажиров в очереди
        assertEquals(0, buildingSpy.getFloor(0).getPassengers().size());
        //на 1м этаже есть 1 пассажир в очереди
        assertEquals(0, buildingSpy.getFloor(1).getPassengers().size());
    }

    @Test
    public void workNotFullLiftTest() throws InvalidParameterException {
        // Arrange
        ArgumentCaptor<Passenger> captorPassenger = forClass(Passenger.class);
        List<Passenger> expected = getPassengers(new int[]{2, 2, 4, 5});
        Building buildingSpy = spy(createBuilding(7, new int[]{0, 0, 1, 3}, expected));
        Lift lift = Lift.buildLift(3, buildingSpy);
        // Action
        lift.work();
        doNothing().when(buildingSpy).putPassenger(anyInt(), any(Passenger.class));
        for (int i = 0; i < 8; i++) {
            lift.work();
        }
        // Assert
        //на 0м этаже погрузилось 2 пассажира
        verify(buildingSpy, times(2)).removePassenger(eq(0), captorPassenger.capture());
        //в лифте 2 заданных пассажира
        assertThat(
                expected.stream().limit(2).collect(Collectors.toList())).hasSameElementsAs(
                captorPassenger.getAllValues()
        );
        //на 1м этаже погрузился 1 пассажира
        verify(buildingSpy, times(1)).removePassenger(eq(1), captorPassenger.capture());
        assertEquals(expected.get(2), captorPassenger.getValue());
        //на 3м этаже погрузился 1 пассажира
        verify(buildingSpy, times(1)).removePassenger(eq(3), captorPassenger.capture());
        assertEquals(expected.get(3), captorPassenger.getValue());
    }

    @Test
    public void workFullLiftTest() throws InvalidParameterException {
        // Arrange
        ArgumentCaptor<Passenger> captorPassenger = forClass(Passenger.class);
        List<Passenger> expected = getPassengers(new int[]{2, 2, 4, 5});
        Building buildingSpy = spy(createBuilding(7, new int[]{0, 0, 0, 1}, expected));
        Lift lift = Lift.buildLift(3, buildingSpy);
        // Action
        lift.work();
        doNothing().when(buildingSpy).putPassenger(anyInt(), any(Passenger.class));
        for (int i = 0; i < 4; i++) {
            lift.work();
        }
        // Assert
        //на 0м этаже погрузилось 3 пассажира
        verify(buildingSpy, times(3)).removePassenger(eq(0), captorPassenger.capture());
        assertThat(
                expected.stream().limit(3).collect(Collectors.toList())).hasSameElementsAs(
                captorPassenger.getAllValues()
        );
        //на 1м этаже не погрузились пассажиры
        verify(buildingSpy, times(0)).removePassenger(eq(1), eq(expected.get(3)));
        assertEquals(1, buildingSpy.getFloor(1).getPassengers().size());
    }
}