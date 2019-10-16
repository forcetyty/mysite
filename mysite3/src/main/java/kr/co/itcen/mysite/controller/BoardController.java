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

import ch.qos.logback.core.net.SyslogOutputStream;
import kr.co.itcen.mysite.service.BoardService;
import kr.co.itcen.mysite.service.UserService;
import kr.co.itcen.mysite.vo.BoardUserListVo;
import kr.co.itcen.mysite.vo.BoardViewVo;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.UserVo;

/*
 * BoardSerchVo
 * 별도로 분리되어 있던 검색기능을 list와 결합하여 
 * 불필요한 BoardSerchVo 를 걷어냄
 */

@Controller
@RequestMapping("/board")
public class BoardController {

	@Autowired
	private BoardService boardService;

	@Autowired
	private UserService userService;
	
	//답글 no={no }
	@RequestMapping(value = "/replyform/g_no={g_no}&o_no={o_no}&depth={depth}", method = RequestMethod.GET)
	public String replyForm(@PathVariable("g_no") long g_no,
			@PathVariable("o_no") long o_no,
			@PathVariable("depth") long depth,
			HttpSession session,
			Model model) {
		
		System.out.println(g_no);
		System.out.println(o_no);
		System.out.println(depth);
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		BoardVo vo = new BoardVo();
		
		vo.setG_no(g_no);
		vo.setO_no(o_no);
		vo.setDepth(depth);
		
		model.addAttribute("vo", vo);
		
		// session을 통해서 객체의 정보를 가져옴.
		if (authUser == null) {
			// return "board/list";
			System.out.println("reply - 로그인 안됨 / 리스트 호출 /");
			return "/user/login";
		}
		
		session.setAttribute("authUser", authUser);
		return "board/replywrite";
	}
	
	//답글 처리 로직
	@RequestMapping(value = "/reply", method = RequestMethod.POST)
	public String reply(@ModelAttribute BoardVo vo, HttpSession session) {
		
		// Session에 있는 정보를 가져와서 사용자에 따른 글쓰기가 가능하게 구현
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		session.setAttribute("authUser", authUser);
		
		System.out.println("reply Action");

		vo.setUser_no(authUser.getNo());
		
		System.out.println("답글 처리 로직 확인 값!!!");
		System.out.println(vo.getG_no());
		System.out.println(vo.getO_no());
		System.out.println(vo.getDepth());
		System.out.println(vo.getTitle());
		System.out.println(vo.getDepth());
		System.out.println(vo.getContents());
		System.out.println(vo.getUser_no());
		System.out.println("---------------------");
		
		if(vo.getO_no() == 0) {
			System.out.println("답글 실행");
			boardService.replyInsert(vo);
		}else {
			System.out.println("댓글 실행");
			boardService.reply2Insert(vo);
		}
		
		return "redirect:/board";
	}
	
	// 게시판에 목록을 뿌려주는 기능
	@RequestMapping(value = { "", "list", "/serach"  } , method = RequestMethod.GET)
	public String getBoardlist(@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			@RequestParam(value = "kwd", required = false, defaultValue = "") String kwd,
			Model model) {
		
		System.out.println("Board List 출력");
		System.out.println("검색어 확인 :" + kwd);
		
		List<BoardUserListVo> list = boardService.getList((page - 1) * 5, 5, kwd);

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
		boardService.hitUpdate(no);

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
	
	//수정
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
