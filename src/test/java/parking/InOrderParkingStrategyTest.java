package parking;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;

import static org.mockito.Mockito.*;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class InOrderParkingStrategyTest {

	@Test
    public void testCreateReceipt_givenACarAndAParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 1, Write a test case on InOrderParkingStrategy.createReceipt()
	    * With using Mockito to mock the input parameter */

        Receipt receipt = new Receipt();
        receipt.setParkingLotName("parking");
        receipt.setCarName("car");
        InOrderParkingStrategy inOrderParkingStrategy =  new InOrderParkingStrategy();
        ParkingLot parkingLot = mock(ParkingLot.class);
        Car car = mock(Car.class);

        Receipt park = inOrderParkingStrategy.park(Arrays.asList(parkingLot), car);

        when(parkingLot.getName()).thenReturn("parking");
        when(car.getName()).thenReturn("car");

        Assert.assertEquals(receipt.getCarName(),inOrderParkingStrategy.createReceipt(parkingLot,car));
        Assert.assertEquals(receipt.getParkingLotName(),inOrderParkingStrategy.createReceipt(parkingLot,car));

    }

    @Test
    public void testCreateNoSpaceReceipt_givenACar_thenGiveANoSpaceReceipt() {

        /* Exercise 1, Write a test case on InOrderParkingStrategy.createNoSpaceReceipt()
         * With using Mockito to mock the input parameter */
        Receipt receipt = new Receipt();
        receipt.setParkingLotName("parking");
        receipt.setCarName("car");


//        InOrderParkingStrategy inOrderParkingStrategy =  new InOrderParkingStrategy();
        ParkingLot parkingLot = new ParkingLot("parkinglot1",0);
        InOrderParkingStrategy inOrderParkingStrategy = spy(InOrderParkingStrategy.class);
        Car car = mock(Car.class);
        Receipt park = inOrderParkingStrategy.park(Arrays.asList(parkingLot), car);


//        inOrderParkingStrategy.park(Arrays.asList(parkingLot), car);
//
//        Mockito.verify(inOrderParkingStrategy, times(1)).createNoSpaceReceipt(car);


        when(park.getParkingLotName()).thenReturn("No Parking Lot");

        Assert.assertEquals(park.getParkingLotName(),"No Parking Lot");

    }

    @Test
    public void testPark_givenNoAvailableParkingLot_thenCreateNoSpaceReceipt(){

	    /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for no available parking lot */

    }

    @Test
    public void testPark_givenThereIsOneParkingLotWithSpace_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot */

    }

    @Test
    public void testPark_givenThereIsOneFullParkingLot_thenCreateReceipt(){

        /* Exercise 2: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for one available parking lot but it is full */

    }

    @Test
    public void testPark_givenThereIsMultipleParkingLotAndFirstOneIsFull_thenCreateReceiptWithUnfullParkingLot(){

        /* Exercise 3: Test park() method. Use Mockito.spy and Mockito.verify to test the situation for multiple parking lot situation */

        Receipt receipt = new Receipt();
        receipt.setParkingLotName("parking");
        receipt.setCarName("car");

        InOrderParkingStrategy inOrderParkingStrategy = spy(new InOrderParkingStrategy());
        ParkingLot parkingLot = new ParkingLot("parking",1);
        parkingLot.getParkedCars().add(mock(Car.class));


        inOrderParkingStrategy.park(Arrays.asList(parkingLot), mock(Car.class));
        Mockito.verify(inOrderParkingStrategy, times(1)).createNoSpaceReceipt(mock(Car.class));

    }


}
