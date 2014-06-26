package web.controller;



import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import web.form.UserAccount;


@Controller
@RequestMapping ( "user" )
public class UserBalance {
	
	@RequestMapping("balance.do")
	public String balance(UserAccount userAccount,Model model){
		
				
		
		System.out.println(userAccount);
		
		UserAccount user1 = new UserAccount();
		UserAccount user2 = new UserAccount();
		
		user1.setAccountId(1);
		user2.setAccountId(2);
		
		user1.setUsername("Peter");
		user2.setUsername("Jack");
		
		model.addAttribute("user1",user1);
		model.addAttribute("user2",user2);
		
		return "user/balance";
	}
	
	
	
	@RequestMapping("transfer.do")
	public String transfer(UserAccount userAccount,Model model){
		
		
		UserAccount user1 = new UserAccount();
		UserAccount user2 = new UserAccount();
		
		user1.setAccountId(1);
		user2.setAccountId(2);
		
		user1.setUsername("Peter");
		user2.setUsername("Jack");
		
		model.addAttribute("user1",user1);
		model.addAttribute("user2",user2);
		
		return "user/balance";
	}
}
