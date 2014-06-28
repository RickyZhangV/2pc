package web.service;

import java.math.BigDecimal;

import web.form.UserAccount;

public interface TransferService {
	public int transferAccount(int accountId ,int targetAccountId,BigDecimal amount);
	
	
	public UserAccount queryBalance(int accountId);
}
