package web.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import web.dao.TransferDao;
import web.form.UserAccount;
import web.service.TransferService;


@Service
public class TransferServiceImpl implements  TransferService{
	
	@Resource
	TransferDao transferDao;
	
	@Override
	public int transferAccount(int accountId, int targetAccountId,
			BigDecimal amount) {
		// TODO Auto-generated method stub
		return 	transferDao.transferAccount(accountId, targetAccountId, amount);

	}

	@Override
	public UserAccount queryBalance(int accountId) {
		// TODO Auto-generated method stub
		UserAccount userAccount = new UserAccount();
		if(accountId == 0)
			return null;
		List<Map> list = transferDao.queryBalance(accountId);

		if(list == null || list.size()==0) 
			return userAccount;
		
		Map map = (Map) list.get(0);
		
		userAccount.setAccountId(Integer.parseInt(map.get("item1").toString()));
		userAccount.setUsername((map.get("item2").toString()));
		userAccount.setBalance(new BigDecimal(map.get("item3").toString()));
		
		
		return userAccount;
	}
}
