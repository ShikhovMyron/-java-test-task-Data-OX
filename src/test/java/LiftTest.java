import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class LiftTest {

    @Test
    public void workBaseTest() {
        // Arrange
        List<Passenger> expected = DefaultUtils.getPassengers(new int[]{5, 5, 7, 3});
        ArgumentCaptor<Passenger> captor = ArgumentCaptor.forClass(Passenger.class);
        Building buildingSpy = spy(DefaultUtils.createBuilding(10, new int[]{1, 3, 4, 4}, expected));
        Lift lift = new Lift(6, buildingSpy);
        // Action
        doNothing().when(buildingSpy).movePassengers(any());
        for (int i = 0; i < 15; i++) {
            lift.work();
        }
        // Assert
        verify(buildingSpy, times(4)).removePassenger(anyInt(), captor.capture());
        List<Passenger> actual = captor.getAllValues();
        assertEquals(expected, actual);
    }

    @Test // лифт должен проехать вверх-вниз поссле чего отвезди пассажира на нужный этаж
    public void workLongDistanceTest() {
        // Arrange
        int floorSpawn = 0;
        int neededFloor = 1;
        Passenger expected = new Passenger(neededFloor);
        ArgumentCaptor<Passenger> captorPassenger = ArgumentCaptor.forClass(Passenger.class);
        Building buildingSpy = spy(DefaultUtils.createBuilding(4, new int[]{}, new ArrayList<>()));
        Lift lift = new Lift(2, buildingSpy);
        // Action
        doNothing().when(buildingSpy).movePassengers(any());
        lift.work();
        buildingSpy.putPassenger(floorSpawn, expected);
        for (int i = 0; i < 7; i++) {
            lift.work();
        }
        // Assert
        verify(buildingSpy, times(1)).removePassenger(eq(floorSpawn), captorPassenger.capture());
        assertEquals(0, buildingSpy.getFloor(neededFloor).getPassengers().size());
        assertEquals(expected, captorPassenger.getValue());
    }

    @Test
    public void workMaxPassengersCountTest() {
        // Arrange
        int maxPassengersCount = 3;
        ArgumentCaptor<Passenger> captorPassenger = ArgumentCaptor.forClass(Passenger.class);
        List<Passenger> passengers = DefaultUtils.getPassengers(new int[]{3, 2, 1, 1});
        Building buildingSpy = spy(DefaultUtils.createBuilding(4, new int[]{0, 0, 0, 0}, passengers));
        Lift lift = new Lift(maxPassengersCount, buildingSpy);
        // Action
        doNothing().when(buildingSpy).movePassengers(any());
        lift.work();
        // Assert
        verify(buildingSpy, times(3)).removePassenger(eq(0), captorPassenger.capture());
        assertEquals(1, buildingSpy.getFloor(0).getPassengers().size());
        assertEquals(maxPassengersCount, captorPassenger.getAllValues().size());
    }

    @Test
    public void workMaxFloorNumberTest() {
        // Arrange
        ArgumentCaptor<Passenger> captorPassenger = ArgumentCaptor.forClass(Passenger.class);
        List<Passenger> expected = DefaultUtils.getPassengers(new int[]{1, 2, 6});
        Building buildingSpy = spy(DefaultUtils.createBuilding(7, new int[]{0, 1, 3}, expected));
        Lift lift = new Lift(3, buildingSpy);
        // Action
        doNothing().when(buildingSpy).movePassengers(any());
        for (int i = 0; i < 5; i++) {
            lift.work();
        }
        // Assert
        verify(buildingSpy, times(1)).removePassenger(eq(0), captorPassenger.capture());
        verify(buildingSpy, times(1)).removePassenger(eq(1), captorPassenger.capture());
        verify(buildingSpy, times(0)).removePassenger(eq(3), eq(expected.remove(2)));
        assertEquals(0, buildingSpy.getFloor(0).getPassengers().size());
        assertEquals(0, buildingSpy.getFloor(1).getPassengers().size());
        assertEquals(expected, captorPassenger.getAllValues());
    }

    @Test
    public void workNotFullLiftTest() {
        // Arrange
        ArgumentCaptor<Passenger> captorPassenger = ArgumentCaptor.forClass(Passenger.class);
        List<Passenger> expected = DefaultUtils.getPassengers(new int[]{2, 2, 4, 5});
        Building buildingSpy = spy(DefaultUtils.createBuilding(7, new int[]{0, 0, 1, 3}, expected));
        Lift lift = new Lift(3, buildingSpy);
        // Action
        lift.work();
        doNothing().when(buildingSpy).movePassengers(any());
        for (int i = 0; i < 8; i++) {
            lift.work();
        }
        // Assert
        verify(buildingSpy, times(2)).removePassenger(eq(0), captorPassenger.capture());
        assertThat(
                expected.stream().limit(2).collect(Collectors.toList())).hasSameElementsAs(
                captorPassenger.getAllValues()
        );
        verify(buildingSpy, times(1)).removePassenger(eq(1), captorPassenger.capture());
        assertEquals(expected.get(2), captorPassenger.getValue());
        verify(buildingSpy, times(1)).removePassenger(eq(3), captorPassenger.capture());
        assertEquals(expected.get(3), captorPassenger.getValue());
    }

    @Test
    public void workFullLiftTest() {
        // Arrange
        ArgumentCaptor<Passenger> captorPassenger = ArgumentCaptor.forClass(Passenger.class);
        List<Passenger> expected = DefaultUtils.getPassengers(new int[]{2, 2, 4, 5});
        Building buildingSpy = spy(DefaultUtils.createBuilding(7, new int[]{0, 0, 0, 1}, expected));
        Lift lift = new Lift(3, buildingSpy);
        // Action
        lift.work();
        doNothing().when(buildingSpy).movePassengers(any());
        for (int i = 0; i < 8; i++) {
            lift.work();
        }
        // Assert
        verify(buildingSpy, times(3)).removePassenger(eq(0), captorPassenger.capture());
        assertThat(
                expected.stream().limit(3).collect(Collectors.toList())).hasSameElementsAs(
                captorPassenger.getAllValues()
        );
        verify(buildingSpy, times(0)).removePassenger(eq(1), eq(expected.get(3)));
        assertEquals(1, buildingSpy.getFloor(1).getPassengers().size());
    }
}