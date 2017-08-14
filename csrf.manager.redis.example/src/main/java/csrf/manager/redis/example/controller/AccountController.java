package csrf.manager.redis.example.controller;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/account/v1")
public class AccountController {

	@RequestMapping(value="/amount", method=RequestMethod.POST)
	public @ResponseBody String saveUser(@ModelAttribute("amountRequest") AmountRequest amountRequest) {
		
		String message = "I got " + amountRequest.getAmount() + " from you!";
		
		return message;
	}
	
}
