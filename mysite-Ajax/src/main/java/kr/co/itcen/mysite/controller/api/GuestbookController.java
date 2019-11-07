package kr.co.itcen.mysite.controller.api;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kr.co.itcen.mysite.dto.JSONResult;
import kr.co.itcen.mysite.service.GuestbookService;
import kr.co.itcen.mysite.vo.GuestbookVo;

@RestController("geustbookApiController")
@RequestMapping("/api/guestbook")
public class GuestbookController {
	
	@Autowired
	private GuestbookService guestbookService;
	
	/*
	 * @ResponseBody
	 * 
	 * @RequestMapping(value = "/add", method = RequestMethod.POST) public
	 * JSONResult add(@RequestBody GuestbookVo vo) {
	 * guestbookService.writeContent(vo); vo.setPassword(""); return
	 * JSONResult.success(vo); }
	 * 
	 * @ResponseBody
	 * 
	 * @RequestMapping(value="/list/{no}", method=RequestMethod.GET) public
	 * JSONResult list(@PathVariable("no") Long startNo) { List<GuestbookVo> list=
	 * guestbookService.getContentList(startNo); return JSONResult.success(list); }
	 */
	
	
	@PostMapping(value = "/add")
	public JSONResult add(@RequestBody GuestbookVo vo) {
		guestbookService.writeContent(vo);
		vo.setPassword("");	
		return JSONResult.success(vo);
	}
	
	@GetMapping(value="/list/{no}")
	public JSONResult list(@PathVariable("no") Long startNo) {
		List<GuestbookVo> list= guestbookService.getContentList(startNo);
		return JSONResult.success(list);
	}
	
	@DeleteMapping(value="/{no}")
	public JSONResult delete(@PathVariable("no") Long no, @RequestParam("password") String password) {
		boolean result = guestbookService.deleteContent(no, password);
		
		return JSONResult.success(result ? no : -1);
	}
}
