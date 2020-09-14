package com.transactiontrade.trading.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.springframework.stereotype.Service;

import com.transactiontrade.trading.domain.Account;
import com.transactiontrade.trading.domain.Transaction;
import com.transactiontrade.trading.dto.TransactionBody;
import com.transactiontrade.trading.exceptions.CreditException;

@Service
public class TradingService {
	
	private static int txId = 0;
	private ReentrantReadWriteLock txLock = new ReentrantReadWriteLock();
	private Lock readLock = txLock.readLock();
	private Lock writeLock = txLock.writeLock();
	
	private static Map<String, Transaction> transactionsHistory = new HashMap<String,Transaction>();
	
	public Account createTansaction(Account account, TransactionBody txDTO) throws CreditException {
		try {
			writeLock.lock();
			int newCredit = account.getCredit();
			Transaction tx = new Transaction();
			tx.setType(txDTO.getType());
			newCredit = account.getCredit() + txDTO.getAmount();
			if (account.getCredit() > 0 && (newCredit < 0)) { 
    			throw new CreditException();
			}
	    	account.setCredit(newCredit);
			tx.setAmount(txDTO.getAmount());
	    	tx.setId(String.valueOf(++txId));
	    	tx.setEfectiveDate(LocalDateTime.now());
	    	
	    	transactionsHistory.put(tx.getId(), tx);
	    	return account;
		}finally {
			writeLock.unlock();
		}
	}
	
	public Collection<Transaction> getTransactionHistory(){
		
		Collection<Transaction> transactionsCopy = new ArrayList<Transaction>();
		try {
			readLock.lock();
			transactionsCopy.addAll(transactionsHistory.values());
		}
		finally {
			readLock.unlock();
		}
		return transactionsCopy ;
	}
	
	public Transaction getTransaction(String id) {
		return transactionsHistory.get(id);
	}

}
