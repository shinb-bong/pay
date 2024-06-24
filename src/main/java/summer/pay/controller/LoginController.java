package summer.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import summer.pay.controller.form.LoginForm;
import summer.pay.domain.Member;
import summer.pay.service.member.LoginService;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

	private final LoginService loginService;

	public static final String LOGIN_MEMBER = "loginMember";

	@GetMapping("/login")
	public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
		return "login/loginForm";
	}

	@PostMapping("/login")
	public String login(@Valid @ModelAttribute LoginForm form
		, BindingResult bindingResult
		, HttpServletRequest request) {
		if (bindingResult.hasErrors()) {
			return "login/loginForm";
		}
		Member loginMember = loginService.login(form.getEmail(), form.getPassword());

		log.info("login? {}", loginMember);
		if (loginMember == null) {
			bindingResult.reject("loginFail","아이디 또는 비밀번호가 맞지 않습니다.");
			return "login/loginForm";
		}
		HttpSession session = request.getSession(); //세션에 로그인 회원 정보 보관
		session.setAttribute(LOGIN_MEMBER, loginMember);

		 return "redirect:/";
	}

	@PostMapping("/logout")
	public String logoutV3(HttpServletRequest request) {
		//세션을 삭제한다.
		HttpSession session = request.getSession(false);
		if (session.getAttribute(LOGIN_MEMBER)!= null) {
			session.removeAttribute(LOGIN_MEMBER);
		}
		return "redirect:/";
	}


}
