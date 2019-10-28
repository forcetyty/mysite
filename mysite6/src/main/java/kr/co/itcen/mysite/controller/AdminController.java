package kr.co.itcen.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.itcen.mysite.security.Auth;


@Auth(value = "ADMIN")		// 인증
@Controller					// Controller 
@RequestMapping("/admin")	// URL Mapping 작업
public class AdminController {
	
	// Mapping 경로가 매우 짧아짐
	@RequestMapping("")
	public String main() {
		return "admin/main";
	}
	
	@RequestMapping("/guetsbook")
	public String guestbook() {
		return "admin/guestbook";
	}
	
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}
	
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}

}
