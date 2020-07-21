package com.transactiontrade.trading.controller;

import java.util.Collection;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.transactiontrade.trading.domain.Account;
import com.transactiontrade.trading.domain.Transaction;
import com.transactiontrade.trading.dto.TransactionBody;
import com.transactiontrade.trading.exceptions.CreditException;
import com.transactiontrade.trading.service.TradingService;


@RestController
@RequestMapping("transactiontrade")
public class TradeManagerController {
	
	private static int INITIAL_CREDIT = 100000;
	
	private static Account account;
	
	@Autowired
	private TradingService tradingService;
	
	static{
		account = new Account();
		account.setAccoudnId(Math.abs((new Random()).nextInt()));
		account.setCredit(INITIAL_CREDIT);
		account.setAccountName("DefaultAccount");
	}
    
    @RequestMapping(value="/transactions/create", method = RequestMethod.POST)
    public Account doTransaction(@RequestBody TransactionBody tx) throws CreditException {
    	boolean validType = tx.getType().equals(Transaction.Type.CREDIT.toString()) || 
    			tx.getType().equals(Transaction.Type.DEBIT.toString()); 
    	if (tx.getAmount() == 0 || !validType)
    		throw new RuntimeException("Invalid parameter");
    	return tradingService.createTansaction(account, tx);
    }
    
    @RequestMapping(value="/transactions", method = RequestMethod.GET)
    public Collection<Transaction> getTransactionHistory(){
    	return tradingService.getTransactionHistory();
    }
    
    @RequestMapping(value="/transactions/{transactionId}", method = RequestMethod.GET)
    public Transaction getTransactionHistory(@PathVariable(value="transactionId") String id){
    	return tradingService.getTransaction(id);
    }
    
    @RequestMapping(value="/accountbalance", method = RequestMethod.GET)
    public Integer getAccountBalance(){
    	return account.getCredit();
    }

    @ExceptionHandler(CreditException.class)
    public ResponseEntity<Object> handleCreditException() {
    	return new ResponseEntity<>(HttpStatus.PAYMENT_REQUIRED);
    }
    
    @ExceptionHandler({Exception.class, RuntimeException.class})
    public ResponseEntity<Object> handleException() {
    	return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    
}
