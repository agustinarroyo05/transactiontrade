package com.transactontrade.tests;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.transactiontrade.trading.domain.Account;
import com.transactiontrade.trading.domain.Transaction;
import com.transactiontrade.trading.dto.TransactionBody;
import com.transactiontrade.trading.exceptions.CreditException;
import com.transactiontrade.trading.service.TradingService;

public class TradingServiceTest {
	
	private int TX_AMMOUNT = 100;
	
	TradingService service = new TradingService();
	private Account account;
	private TransactionBody txDto;
	
	@Before
	public void setUp() {
		account = new Account();
		account.setCredit(100000);
		account.setAccoudnId(1);
		account.setAccountName("DebitTest");
		txDto = new TransactionBody();
		txDto.setAmount(TX_AMMOUNT);
		txDto.setType("DEBIT");
		
	
	}
	
	@Test
	public void createAndGetTansactionTest(){
		try {
			service.createTansaction(account, txDto);
			assertTrue(service.getTransaction("1").getAmount() == -1 * TX_AMMOUNT);
			List<Transaction> txs = (List<Transaction>)service.getTransactionHistory(); 
			assertTrue(txs.get(0).getAmount() == -1 * TX_AMMOUNT);
			assertTrue(txs.get(0).getId().equals("1"));
			
		} catch (CreditException e) {
			fail();
		}
	}
	@Test
	public void createTansactionAndFailTest(){
		try {
			txDto.setAmount(10000000);
			service.createTansaction(account, txDto);
			fail();
		} catch (CreditException e) {
			assertTrue(true);
		}
	}

}
