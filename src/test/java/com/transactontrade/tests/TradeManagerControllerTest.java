package com.transactontrade.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import com.transactiontrade.trading.controller.TradeManagerController;
import com.transactiontrade.trading.domain.Account;
import com.transactiontrade.trading.dto.TransactionBody;
import com.transactiontrade.trading.exceptions.CreditException;
import com.transactiontrade.trading.service.TradingService;

@RunWith(MockitoJUnitRunner.class)
public class TradeManagerControllerTest {
	
	@Mock
	private TradingService service;
	private Account account = new Account();
	private TransactionBody txDTO = new TransactionBody();
	private TradeManagerController controller;
	
	@Before
	public void setUp() {
		account.setAccoudnId(1);
		account.setCredit(10000);
		account.setAccountName("TestCredit");
		txDTO.setAmount(100);
		txDTO.setType("CREDIT");
		
		controller = new TradeManagerController();

		Field serviceField;
		try {
			serviceField = controller.getClass().getDeclaredField("tradingService");
			serviceField .setAccessible(true);
			serviceField .set(controller, service);
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | 
				IllegalAccessException e) {
			fail();
		}

	}
	
	@Test
	public void doTransactionTest() {
				
		try {
			controller.doTransaction(txDTO);
			assertTrue(true);
		} catch (CreditException | RuntimeException e ) {
			fail();
		}
		
		txDTO.setAmount(0);
		try {
			controller.doTransaction(txDTO);
			fail();
		} catch (CreditException | RuntimeException e ) {
			assertTrue(true);
		}
		
		txDTO.setAmount(100);
		txDTO.setType("CRE");
		try {
			controller.doTransaction(txDTO);
			fail();
		} catch (CreditException | RuntimeException e ) {	
			assertTrue(true);
		}
			
	} 
	
	@Test
	public void getTransactionHistoryTest(){
		
    	assertTrue(controller.getTransactionHistory().isEmpty());
    	
	}
	
	@Test
    public void getOneTransaction(){
    	assertNull(controller.getTransactionHistory("2"));
	}
	
	@Test
	public void accountBalanceTest() {
		assertEquals(controller.getAccountBalance(), new Integer(100000));
	}	
}
