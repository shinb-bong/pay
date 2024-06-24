package summer.pay.controller;

import static summer.pay.controller.LoginController.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

import summer.pay.domain.Member;
@Controller
public class HomeController {

	@GetMapping("/")
	public String home(@SessionAttribute(name = LOGIN_MEMBER, required = false)
	Member loginMember, Model model){

		if (loginMember == null) {
			return "home";
		}
	//세션이 유지되면 로그인으로 이동
		model.addAttribute("member", loginMember);
		return "loginHome";
	}

}
