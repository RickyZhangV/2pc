package web.dao;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import web.form.UserAccount;

public interface TransferDao {
	public int transferAccount(int accountId ,int targetAccountId,BigDecimal amount);
	
	
	public  List<Map> queryBalance(int accountId);
}
