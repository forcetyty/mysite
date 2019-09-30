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
import org.springframework.web.bind.annotation.ResponseBody;

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

	// 게시판에 목록을 뿌려주는 기능
	@RequestMapping(value = { "", "list" }, method = RequestMethod.GET)
	public String getBoardlist(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			Model model) {
		System.out.println("Board List 출력");

		List<BoardUserListVo> list = boardService.getList((page - 1) * 5, 5);

		int pageNum = ((page - 1) / 5) * 5;

		model.addAttribute("list", list);
		model.addAttribute("pageNum", pageNum);
		model.addAttribute("page", page);

		System.out.println("선택한 Page :" + page);
		System.out.println("선택한 페이지 목록 :" + pageNum);

		return "board/list";
	}

	// 로직!!!
	// - 1. 로그인 되지 않으면 게시판 목록으로 이동
	// - 2. 로그인 되어 있으면 게시판 글쓰기로 이동
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write(UserVo vo, HttpSession session) {
		UserVo authUser = (UserVo) session.getAttribute("authUser");

		// session을 통해서 객체의 정보를 가져옴.
		if (authUser == null) {
			// return "board/list";
			System.out.println("로그인 안됨 / 리스트 호출 /");
			return "redirect:/board/list";
		}

		session.setAttribute("authUser", authUser);
		return "board/write";
	}

	// 인증된 회원이 글쓰기 처리를 하는 로직!!!
	// 이 글쓰기는 원글에 대한 글쓰기임.
	// write Overroding
	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(@ModelAttribute BoardVo vo, HttpSession session) {
		// 객체는 BoardVo
		// boardService
		// dao!!!

		// Session에 있는 정보를 가져와서 사용자에 따른 글쓰기가 가능하게 구현
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		session.setAttribute("authUser", authUser);

		vo.setUser_no(authUser.getNo());

		System.out.println("write Action 발생!!!");
		System.out.println("userNo : " + vo.getUser_no());
		System.out.println("Contents :" + vo.getContents());

		boardService.boardInsert(vo);

		// view - no!!!
		return "redirect:/board";
	}

	// 작성된 글을 보여주는 로직
	@RequestMapping(value = "/view/{no}", method = RequestMethod.GET)
	public String View(@PathVariable("no") Long no, Model model) {

		System.out.println("View Page");

		BoardViewVo vo = boardService.viewSelect(no);

		model.addAttribute("vo", vo);
		return "board/view";
	}

	// 글 삭제 기능
	// 인증된 회원, 본인이 아니면 삭제가 안되게 처리
	@RequestMapping(value = "/delete/{no}", method = RequestMethod.GET)
	public String delete(@PathVariable("no") Long no, HttpSession session) {

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		session.setAttribute("authUser", authUser);

		// session을 통해서 객체의 정보를 가져옴.
		if (authUser == null) {
			// return "board/list";
			System.out.println("로그인 안됨 / 리스트 호출 /");
			return "/user/login";
		}

		BoardViewVo vo = boardService.viewSelect(no);

		if (vo.getUser_no() == authUser.getNo()) {
			System.out.println("delete Action");
			boardService.deleteUpdate(no);
			return "redirect:/board";
		} else {
			return "/user/login";
		}
	}

	// 글 수정기능
	@RequestMapping(value = "/modify/{no}", method = RequestMethod.GET)
	public String modify(@PathVariable("no") Long no, HttpSession session, Model model) {
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		session.setAttribute("authUser", authUser);
		BoardViewVo vo = boardService.viewSelect(no);
		
		// session을 통해서 객체의 정보를 가져옴.
		if (authUser == null) {
			// return "board/list";
			System.out.println("로그인 안됨 / 리스트 호출 /");
			return "/user/login";
		}

		model.addAttribute("vo", vo);		
		return "board/modify";
	}
	
	//
	@RequestMapping(value = "/modifyAction/{no}", method = RequestMethod.POST)
	public String modifyAction(@PathVariable("no") Long no, @ModelAttribute BoardVo vo) {
		System.out.println("modify 실행!!!");
		
		System.out.println(vo.getTitle());
		System.out.println(vo.getNo());
		System.out.println(vo.getContents());

		boardService.updateModify(vo);
		
		System.out.println("no: " + no);
		
		//return "redirect:/board/view/"+no;
		return "redirect:/board/view/"+vo.getNo();
	}

}
