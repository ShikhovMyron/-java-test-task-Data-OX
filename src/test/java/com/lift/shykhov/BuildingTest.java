package com.lift.shykhov;

import com.lift.shykhov.entity.Building;
import com.lift.shykhov.entity.Passenger;
import com.lift.shykhov.exception.InvalidParameterException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.lift.shykhov.DefaultUtils.createBuilding;
import static com.lift.shykhov.DefaultUtils.getPassengers;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

class BuildingTest {

    @Test
    void putPassengerValidTest() throws InvalidParameterException {
        // Arrange
        Passenger expected = new Passenger(0);
        Building building = createBuilding(5, new int[]{1, 2, 2, 4}, getPassengers(new int[]{2, 3, 3, 2}));
        // Action
        building.putPassenger(4, expected);
        // Assert
        assertEquals(expected, building.getFloor(4).getPassengers().get(0));
    }

    @Test
    void putPassengerNegativeFloorNumberTest() {
        // Arrange
        Building building = createBuilding(5, new int[]{1, 2, 2, 4}, getPassengers(new int[]{2, 3, 3, 2}));
        // Action & Assert
        assertThatThrownBy(() -> building.putPassenger(-1, new Passenger(0)))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void putPassengerTooBigFloorNumberTest() {
        // Arrange
        Building building = createBuilding(5, new int[]{1, 2, 2, 4}, getPassengers(new int[]{2, 3, 3, 2}));
        // Action & Assert
        assertThatThrownBy(() -> building.putPassenger(7, new Passenger(0)))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void putPassengerNullPassengerTest() {
        // Arrange
        Building building = createBuilding(5, new int[]{1, 2, 2, 4}, getPassengers(new int[]{2, 3, 3, 2}));
        // Action & Assert
        assertThatThrownBy(() -> building.putPassenger(0, null))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void getFirstCorrectPassengerValidUpTest() throws InvalidParameterException {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{3, 0, 5});
        Building building = createBuilding(5, new int[]{2, 2, 3}, passengers);
        // Action
        Passenger actual = building.getFirstCorrectPassenger(2, true);
        // Assert
        assertEquals(passengers.get(0), actual);

    }

    @Test
    void getFirstCorrectPassengerValidDownTest() throws InvalidParameterException {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{3, 0, 5});
        Building building = createBuilding(5, new int[]{2, 2, 3}, passengers);
        // Action
        Passenger actual = building.getFirstCorrectPassenger(2, false);
        // Assert
        assertEquals(passengers.get(1), actual);

    }

    @Test
    void getFirstCorrectNegativeFloorNumberTest() {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{3, 0, 5});
        Building building = createBuilding(5, new int[]{2, 2, 3}, passengers);
        // Action & Assert
        assertThatThrownBy(() -> building.getFirstCorrectPassenger(-1, false))
                .isInstanceOf(InvalidParameterException.class);

    }

    @Test
    void getFirstCorrectTooBigFloorNumberTest() {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{3, 0, 5});
        Building building = createBuilding(5, new int[]{2, 2, 3}, passengers);
        // Action & Assert
        assertThatThrownBy(() -> building.getFirstCorrectPassenger(8, false))
                .isInstanceOf(InvalidParameterException.class);

    }

    @Test
    void getFirstCorrectEmptyFloorTest() throws InvalidParameterException {
        // Arrange
        Building building = new Building(5);
        // Action & Assert
        assertNull(building.getFirstCorrectPassenger(2, false));

    }

    @Test
    void removePassengerValidTest() throws InvalidParameterException {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{3, 0, 5});
        Building building = createBuilding(5, new int[]{2, 2, 3}, passengers);
        // Action
        building.removePassenger(2, passengers.get(0));
        // Assert
        assertEquals(List.of(passengers.get(1)), building.getFloor(2).getPassengers());
    }

    @Test
    void removePassengerNonexistentPassengerTest() throws InvalidParameterException {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{3, 0, 5});
        Building building = createBuilding(5, new int[]{2, 2, 3}, passengers);
        // Action
        building.removePassenger(2, new Passenger(3));
        // Assert
        assertEquals(3, DefaultUtils.getSummaryPassengersCount(building));
    }

    @Test
    void removePassengerNullPassengerTest() {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{3, 0, 5});
        Building building = createBuilding(5, new int[]{2, 2, 3}, passengers);
        // Action & Assert
        assertThatThrownBy(() -> building.removePassenger(2, null))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void removePassengerNegativeFloorNumberTest() {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{3, 0, 5});
        Building building = createBuilding(5, new int[]{2, 2, 3}, passengers);
        // Action & Assert
        assertThatThrownBy(() -> building.removePassenger(-1, passengers.get(0)))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void removePassengerTooBigFloorNumberTest() {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{3, 0, 5});
        Building building = createBuilding(5, new int[]{2, 2, 3}, passengers);
        // Action & Assert
        assertThatThrownBy(() -> building.removePassenger(5, passengers.get(2)))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void getFloorValidTest() throws InvalidParameterException {
        // Arrange
        List<Passenger> expected = getPassengers(new int[]{3, 0, 5, 2});
        Building building = createBuilding(7, new int[]{4, 4, 4, 3}, expected);
        expected.remove(3);
        // Action
        List<Passenger> actual = building.getFloor(4).getPassengers();
        // Assert
        assertThat(expected).hasSameElementsAs(actual);
    }

    @Test
    void getFloorNegativeFloorNumberTest() {
        // Arrange
        List<Passenger> expected = getPassengers(new int[]{3, 0, 5, 2});
        Building building = createBuilding(7, new int[]{4, 4, 4, 3}, expected);
        // Action & Assert
        assertThatThrownBy(() -> building.getFloor(-5))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void getFloorTooBigFloorNumberTest() {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{3, 0, 5});
        Building building = createBuilding(5, new int[]{2, 2, 3}, passengers);
        // Action & Assert
        assertThatThrownBy(() -> building.getFloor(10))
                .isInstanceOf(InvalidParameterException.class);
    }

    @Test
    void movePassengersValidTest() {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{5, 2, 8});
        List<Passenger> passengersToMove = getPassengers(new int[]{3, 0, 5});
        Building building = createBuilding(12, new int[]{2, 2, 3}, passengers);
        // Action
        building.movePassengers(passengersToMove);
        // Assert
        assertEquals(6, DefaultUtils.getSummaryPassengersCount(building));
    }

    @Test
    void movePassengersEmptyTest() {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{5, 2, 8, 1, 2});
        List<Passenger> passengersToMove = getPassengers(new int[]{});
        Building building = createBuilding(9, new int[]{2, 2, 3, 1, 0}, passengers);
        // Action
        building.movePassengers(passengersToMove);
        // Assert
        assertEquals(5, DefaultUtils.getSummaryPassengersCount(building));
    }

    @Test
    void movePassengersNullTest() {
        // Arrange
        List<Passenger> passengers = getPassengers(new int[]{5, 2, 8, 1, 2});
        Building building = createBuilding(9, new int[]{2, 2, 3, 1, 0}, passengers);
        // Action & Assert
        assertFalse(() -> building.movePassengers(null));
    }
}