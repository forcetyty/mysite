package kr.co.itcen.mysite.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.service.GuestbookService;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Controller
@RequestMapping( "/guestbook" )
public class GuestbookController {
	// Guestbook 화면에서 동작하는 Controller
	
	// GuestBook Service와 객체와 연결되는 Service
	// DI를 위해 Autowired라는 기능을 사용
	@Autowired
	private GuestbookService guestbookService;
	
	@RequestMapping( "" )
	public String index( Model model ){
		List<GuestbookVo> list = guestbookService.getContentList();
		model.addAttribute( "list", list );
		return "guestbook/list";
	}
	
	// 게스트북 글 삭제 
	@RequestMapping( value="/delete/{no}", method=RequestMethod.GET )
	public String deleteform( @PathVariable( "no" ) Long no, Model model ){
		model.addAttribute( "no", no );
		return "guestbook/delete";
	}
	
	@RequestMapping( value="/delete", method=RequestMethod.POST )
	public String delete( @ModelAttribute GuestbookVo vo ){
		System.out.println( vo );
		guestbookService.deleteContent( vo );
		return "redirect:/guestbook";
	}
	
	@RequestMapping( value="/add", method=RequestMethod.POST )
	public String add( @ModelAttribute GuestbookVo vo ) {
		guestbookService.writeContent( vo );
		return "redirect:/guestbook";
	}

	@RequestMapping( value="/spa", method=RequestMethod.GET )
	public String spa() {
		return "guestbook/index-spa";
	}
}
