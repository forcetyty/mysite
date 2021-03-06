package kr.co.itcen.mysite.controller;


import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.security.Auth;
import kr.co.itcen.mysite.security.AuthUser;
import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.UserVo;


@Controller
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/joinsuccess", method = RequestMethod.GET)
	public String joinsuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value = "/join", method = RequestMethod.GET)
	public String join(@ModelAttribute UserVo vo) {
		return "user/join";
	}

	// 오버로드
	// vailding 적용
	@RequestMapping(value = "/join", method = RequestMethod.POST)
	public String join(@ModelAttribute @Valid UserVo vo,
			BindingResult result, Model model) {
		
		
		
		if(result.hasErrors()) {
//			List<ObjectError> list = result.getAllErrors();
//			for(ObjectError error : list) {
//				System.out.println(error);
//			}
			//getModel에서 map 반환!!!!
			//getModel을 사용하지 않고, Map으로 받을수도 있다.
			model.addAllAttributes(result.getModel());
			return "user/join";
		}
		
		userService.join(vo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	//interceptor 적용했을시 미사용하게 되는 코드
//	@RequestMapping(value = "/login", method = RequestMethod.POST)
//	public String login(UserVo vo, HttpSession session, Model model) {
//		UserVo userVo = userService.getUser(vo);
//
//		if (userVo == null) {
//			model.addAttribute("result", "fail");
//			return "user/loing";
//		}
//
//		// 로그인 처리
//		session.setAttribute("authUser", userVo);
//		return "redirect:/";
//	}

//	@RequestMapping(value = "/logout", method = RequestMethod.GET)
//	public String logout(HttpSession session) {
//		// 접근 제어
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		if (authUser != null) {
//			session.removeAttribute("authUser");
//			session.invalidate();
//		}
//		return "redirect:/";
//	}
	
	//@Auth(role = Role.ADMIN)
	//접근제어 - intercepter
	//@Auth("USER")
	@Auth("USER")
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update( @AuthUser UserVo authUser, Model model) {
		
		//ACL //횡단관심!!!
		//UserVo authUser = (UserVo)session.getAttribute("authUser");
		//Long no = authUser.getNo();
		//UserVo userVo = userService.getUser(no);
	
		authUser = userService.getUser(authUser.getNo());
		model.addAttribute("authUser", authUser);
		
		System.out.println("before:" + authUser);
		
		return "user/update";
	}
	
	//@AuthUser UserVo authUser,
	@Auth("USER")
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public String update(
			@ModelAttribute @Valid UserVo vo, 
			BindingResult result,
			@AuthUser UserVo authUser,
			Model model) {
		
		System.out.println("update Process Before:" + vo);
		vo.setNo(authUser.getNo());
		
		userService.update(vo);
		
		authUser = userService.getUser(authUser.getNo());
		model.addAttribute("authUser", authUser);
		
		if(result.hasErrors()) {
			model.addAllAttributes(result.getModel());
			return "user/update";
		}
	
		System.out.println("update Process After:" + vo);
		
		return "redirect:/user/update";
	}
	
	@RequestMapping(value = "/auth", method = RequestMethod.POST)
	public void auth() {
		
	}
	
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public void logout() {
		
	}
	
	
	
//	@RequestMapping(value = "/update", method = RequestMethod.GET)
//	public String updateForm(UserVo vo, HttpSession session) {
//		System.out.println("회원정보 수정");
//		
//		// 접근 제어
//		UserVo authUser = (UserVo) session.getAttribute("authUser");
//		userService.selectList(authUser.getNo());
//				
//		return "user/update";
//	}
	
//	@RequestMapping(value = "/updateProcess")
//	public String update(@ModelAttribute UserVo vo) {
//		userService.updateList(vo);
//		
//		return "user/update" + vo.getNo();
//	}
	
//	@ExceptionHandler(UserDaoException.class)
//	public String handlerException() {
//		
//		return "error/exception";
//	}

}
