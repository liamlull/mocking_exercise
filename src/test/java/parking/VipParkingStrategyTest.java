package parking;

import org.junit.Test;

import java.util.List;

import static org.mockito.Mockito.*;

public class VipParkingStrategyTest {

	@Test
    public void testPark_givenAVipCarAndAFullParkingLog_thenGiveAReceiptWithCarNameAndParkingLotName() {

	    /* Exercise 4, Write a test case on VipParkingStrategy.park()
	    * With using Mockito spy, verify and doReturn */
//        doReturn(“Hello World”).when(mockedCls).method3();
        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        Car car = new Car("Vip");
        ParkingLot parkingLot = new ParkingLot("parking Lot1",1);
        parkingLot.getParkedCars().add(car);

//        Receipt park = vipParkingStrategy.park((List<ParkingLot>) parkingLot, car);

        doReturn(true).when(vipParkingStrategy).isAllowOverPark(car);



    }

    @Test
    public void testPark_givenCarIsNotVipAndAFullParkingLog_thenGiveNoSpaceReceipt() {

        /* Exercise 4, Write a test case on VipParkingStrategy.park()
         * With using Mockito spy, verify and doReturn */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsVipCar_thenReturnTrue(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */

        VipParkingStrategy vipParkingStrategy = spy(new VipParkingStrategy());
        Car car = new Car("aaaa");
        ParkingLot parkingLot = new ParkingLot("parking Lot1",1);
        parkingLot.getParkedCars().add(car);

        CarDao carDao = vipParkingStrategy.carDao;

        carDao.isVip("b");


//        Receipt park = vipParkingStrategy.park((List<ParkingLot>) parkingLot, car);

        doReturn(true).when(vipParkingStrategy).isAllowOverPark(car);


    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsVipCar_thenReturnFalse(){

        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameContainsCharacterAAndIsNotVipCar_thenReturnFalse(){
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    @Test
    public void testIsAllowOverPark_givenCarNameDoesNotContainsCharacterAAndIsNotVipCar_thenReturnFalse() {
        /* Exercise 5, Write a test case on VipParkingStrategy.isAllowOverPark()
         * You may refactor the code, or try to use
         * use @RunWith(MockitoJUnitRunner.class), @Mock (use Mockito, not JMockit) and @InjectMocks
         */
    }

    private Car createMockCar(String carName) {
        Car car = mock(Car.class);
        when(car.getName()).thenReturn(carName);
        return car;
    }
}
