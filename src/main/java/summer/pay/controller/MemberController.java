package summer.pay.controller;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import summer.pay.controller.dto.MemberDto;
import summer.pay.service.member.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {

	private final MemberService memberService;

	@GetMapping("/add")
	public String addForm(@ModelAttribute("memberDto") MemberDto memberDto){
		return "members/addMemberForm";
	}

	@PostMapping("/add")
	public String saved(@Valid @ModelAttribute("memberDto") MemberDto memberDto,
		BindingResult result,
		RedirectAttributes redirectAttributes){
		if(result.hasErrors()){
			return "members/addMemberForm";
		}
		Long memberId = memberService.join(memberDto);
		redirectAttributes.addAttribute("memberId", memberId);
		return "redirect:/members/{memberId}";
	}
}
