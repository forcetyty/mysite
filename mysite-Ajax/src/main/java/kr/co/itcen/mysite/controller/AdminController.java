package kr.co.itcen.mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import kr.co.itcen.mysite.security.Auth;

@Auth("ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	
	// main화면과 연결되는 Controller
	@RequestMapping("")
	public String main() {
		return "admin/main";
	}
	
	// GuestBook과 연결되는 Controller
	@RequestMapping("/guestbook")
	public String guesbook() {
		return "admin/guestbook";
	}

	// Board와 연결되는 Controller
	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	// User와 연결되는 Controller
	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
	
}
