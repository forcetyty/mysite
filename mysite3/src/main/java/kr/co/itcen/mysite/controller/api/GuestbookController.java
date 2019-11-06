package kr.co.itcen.mysite.controller.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import kr.co.itcen.mysite.dto.JSONResult;
import kr.co.itcen.mysite.service.GuestBookService;
import kr.co.itcen.mysite.vo.GuestbookVo;

@Controller("guestbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestBookService guestbookService;
	
	
	// @RequestParam은 form에서 오는 데이터를 처리할때 사용
	// @RequestBody는 객체에서 오는 데이터를 처리
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public JSONResult add(@RequestBody GuestbookVo vo) {
		guestbookService.insertAddList(vo);
		System.out.println(vo);
		
		return JSONResult.success(vo);
	}

}
