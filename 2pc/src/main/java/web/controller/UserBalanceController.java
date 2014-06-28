package web.controller;



import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import web.form.UserAccount;
import web.service.TransferService;


@Controller
@RequestMapping ( "user" )
public class UserBalanceController {
	
	@Resource
	private TransferService transferServiceImpl;
	
	
	@RequestMapping("balance.do")
	public String balance(UserAccount userAccount,Model model){
		
		model.addAttribute("user1",transferServiceImpl.queryBalance(1));
		model.addAttribute("user2",transferServiceImpl.queryBalance(2));
		
		return "user/balance";
	}
	
	
	
	@RequestMapping("transfer.do")
	public String transfer(UserAccount userAccount,Model model){
		
		int result = transferServiceImpl.transferAccount(userAccount.getAccountId(), userAccount.getTargetAccountId(), userAccount.getTransferAmount());

		
		if( result == 0 )
			model.addAttribute("info","转账成功");
		else
			model.addAttribute("info","转账失败");
		
		model.addAttribute("user1",transferServiceImpl.queryBalance(1));
		model.addAttribute("user2",transferServiceImpl.queryBalance(2));
		
		return "user/balance";
	}
}
