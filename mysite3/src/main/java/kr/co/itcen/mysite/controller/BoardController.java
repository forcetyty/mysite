package kr.co.itcen.mysite.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.BoardUserListVo;
import kr.co.itcen.mysite.vo.BoardViewVo;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@Autowired
	private UserService userService;

	//게시판에 목록을 뿌려주는 기능
	@RequestMapping(value = { "", "list" }, method = RequestMethod.GET)
	public String getBoardlist(@RequestParam(value="page",required=false, defaultValue="1") int page, Model model) {
		System.out.println("Board List 출력");
		
		List<BoardUserListVo> list = boardService.getList((page - 1)*5, 5);
				
		int pageNum = ((page - 1) / 5) * 5;
	
		model.addAttribute("list", list);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("page", page);
		
		System.out.println("선택한 Page :" + page);
		System.out.println("선택한 페이지 목록 :" + pageNum);
		
	 
		return "board/list";
	}
	
	//로직!!!
	// - 1. 로그인 되지 않으면 게시판 목록으로 이동
	// - 2. 로그인 되어 있으면 게시판 글쓰기로 이동
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(UserVo vo, HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("authUser"); 
				
		//session을 통해서 객체의 정보를 가져옴.
		if(authUser == null) {
			//return "board/list";
			System.out.println("로그인 안됨 / 리스트 호출 /");
			return "redirect:/board/list";
		}
		
		session.setAttribute("authUser", authUser);
		return "board/write";
	}
	
	//인증된 회원이 글쓰기 처리를 하는 로직!!!
	//이 글쓰기는 원글에 대한 글쓰기임.
	//write Overroding
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo, HttpSession session) {
		//객체는 BoardVo
		//boardService
		//dao!!!
		
		//Session에 있는 정보를 가져와서 사용자에 따른 글쓰기가 가능하게 구현
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		session.setAttribute("authUser", authUser);
		
		vo.setUser_no(authUser.getNo());
		
		System.out.println("write Action 발생!!!");
		System.out.println("userNo : " + vo.getUser_no());
		System.out.println("Contents :" + vo.getContents());
		
		boardService.boardInsert(vo);
	
		//view - no!!!
		return "redirect:/board/view/"+vo.getNo();//
	}
	
	//
	//pathParam
	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String View(@PathVariable("no") Long no, Model model) {
		BoardViewVo vo = boardService.viewSelect(no);
		
		model.addAttribute("vo",vo);
		
		return "board/view";
	}
	
	
	

}
