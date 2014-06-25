package web.controller;



import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ( "user" )
public class UserBalance {
	
	@RequestMapping("balance.do")
	public String balance(){
		System.out.println(123);
		return "user/balance";
	}
}
