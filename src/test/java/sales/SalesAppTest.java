package sales;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class SalesAppTest {

	@InjectMocks
	private SalesDao salesDao = spy(new SalesDao());

	@InjectMocks
	private SalesReportDao salesReportDao = spy(new SalesReportDao());

	@InjectMocks
	private EcmService ecmService = spy(new EcmService());

	@InjectMocks
	private SalesApp salesApp = spy(new SalesApp());

	@Test
	public void test_sales_app_generate_sales_activity_report_when_is_nat_trade_equal_to_true() {

		//given
		Sales sales = spy(new Sales());
		when(sales.getEffectiveFrom()).thenReturn(new Date(new Date().getTime() - 3 * 24 * 60 * 60 * 1000));
		when(sales.getEffectiveTo()).thenReturn(new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000));
		when(sales.isSupervisor()).thenReturn(true);

		SalesReportData salesReportData = spy(new SalesReportData());
		when(salesReportData.getType()).thenReturn("SalesActivity");
		when(salesReportData.isConfidential()).thenReturn(false);


		SalesActivityReport salesActivityReport = spy(new SalesActivityReport());
		when(salesActivityReport.toXml()).thenReturn("xml content!!!");

		doReturn(sales).when(salesDao).getSalesBySalesId(anyString());
		doReturn(false).when(salesApp).isEffectiveTime(any(Sales.class));
		doReturn(salesActivityReport).when(salesApp).generateReport(anyListOf(String.class),  anyListOf(SalesReportData.class));

		// when
		salesApp.generateSalesActivityReport("DUMMY", 1000, true, false);

		//then
		verify(salesApp).generateReport(eq(Arrays.asList("Sales ID", "Sales Name", "Activity", "Time")), anyListOf(SalesReportData.class));
		verify(salesApp,times(1)).generateReport(anyListOf(String.class),  anyListOf(SalesReportData.class));
		verify(ecmService).uploadDocument("xml content!!!");
	}

	@Test
	public void test_sales_app_generate_sales_activity_report_when_is_nat_trade_equal_to_false() {

		//given
		Sales sales = spy(new Sales());
		when(sales.getEffectiveFrom()).thenReturn(new Date(new Date().getTime() - 3 * 24 * 60 * 60 * 1000));
		when(sales.getEffectiveTo()).thenReturn(new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000));
		when(sales.isSupervisor()).thenReturn(true);

		SalesReportData salesReportData = spy(new SalesReportData());
		when(salesReportData.getType()).thenReturn("SalesActivity");
		when(salesReportData.isConfidential()).thenReturn(false);


		SalesActivityReport salesActivityReport = spy(new SalesActivityReport());
		when(salesActivityReport.toXml()).thenReturn("xml content!!!");

		doReturn(sales).when(salesDao).getSalesBySalesId(anyString());
		doReturn(false).when(salesApp).isEffectiveTime(any(Sales.class));
		doReturn(salesActivityReport).when(salesApp).generateReport(anyListOf(String.class),  anyListOf(SalesReportData.class));

		// when
		salesApp.generateSalesActivityReport("DUMMY", 1000, false, false);

		//then
		verify(salesApp).generateReport(eq(Arrays.asList("Sales ID", "Sales Name", "Activity", "Local Time")), anyListOf(SalesReportData.class));
		verify(salesApp,times(1)).generateReport(anyListOf(String.class),  anyListOf(SalesReportData.class));
		verify(ecmService).uploadDocument("xml content!!!");
	}


	@Test
	public void test_sales_dao_get_sale_by_sales_id_when_give_a_sales_id() {
        //given
		Sales sales = mock(Sales.class);
		when(sales.getEffectiveFrom()).thenReturn(new Date(new Date().getTime() + 3 * 24 * 60 * 60 * 1000));
		when(sales.getEffectiveTo()).thenReturn(new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000));
		when(sales.isSupervisor()).thenReturn(true);
		doReturn(sales).when(salesDao).getSalesBySalesId(anyString());

		//when
		Sales sales1 = salesDao.getSalesBySalesId("a1111");

		//then
		assertEquals(true,salesDao.getSalesBySalesId("a1111").isSupervisor());
		assertEquals(sales.getEffectiveFrom(),sales1.getEffectiveFrom());
		assertEquals(sales.getEffectiveTo(),sales1.getEffectiveTo());
	}

	@Test
	public void test_sales_app_should_return_true_when_is_effective_time() {

		//given
		Sales sales = mock(Sales.class);
		when(sales.getEffectiveFrom()).thenReturn(new Date(new Date().getTime() + 3 * 24 * 60 * 60 * 1000));
		when(sales.getEffectiveTo()).thenReturn(new Date(new Date().getTime() - 1 * 24 * 60 * 60 * 1000));
		when(sales.isSupervisor()).thenReturn(true);

        // when
		boolean result = salesApp.isEffectiveTime(sales);

		//then
		assertTrue(salesApp.isEffectiveTime(sales));
	}


	@Test
	public void test_sales_dao_get_report_data_by_sales_should_return_sales_activity() {

		//given
		Sales sales = mock(Sales.class);
		when(sales.getEffectiveFrom()).thenReturn(new Date());
		when(sales.getEffectiveTo()).thenReturn(new Date(new Date().getTime()+100110));
		when(sales.isSupervisor()).thenReturn(true);

		SalesReportData salesReportData = mock(SalesReportData.class);
		when(salesReportData.getType()).thenReturn("SalesActivity");
		when(salesReportData.isConfidential()).thenReturn(false);

		//when
		doReturn(Arrays.asList(salesReportData)).when(salesDao).getReportData(sales);

		//then
		assertEquals("SalesActivity",salesDao.getReportData(sales).get(0).getType());
	}

	@Test
	public void test_sales_app_should_return_true_when_is_not_effective_time() {

		//given
		Sales sales = mock(Sales.class);
		when(sales.getEffectiveFrom()).thenReturn(new Date(new Date().getTime() - 3 * 24 * 60 * 60 * 1000));
		when(sales.getEffectiveTo()).thenReturn(new Date(new Date().getTime() + 1 * 24 * 60 * 60 * 1000));
		when(sales.isSupervisor()).thenReturn(true);

		// when
		boolean result = salesApp.isEffectiveTime(sales);

		//then
		assertFalse(salesApp.isEffectiveTime(sales));
	}

}
