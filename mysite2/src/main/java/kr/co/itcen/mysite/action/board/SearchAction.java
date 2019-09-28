package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardSerchVo;
import kr.co.itcen.mysite.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class SearchAction implements Action{

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		String kwd = request.getParameter("kwd");
		
		System.out.println("검색어 :" + kwd);
		
		// 검색창에 아래와 같은 문장을 입력하면 검색이 안되도록 처리
		if(kwd.contains("select")) {
			response.sendRedirect(request.getContextPath() + "/board");
		}else if(kwd.contains("update")) {
			response.sendRedirect(request.getContextPath() + "/board");
		}else if(kwd.contains("delete")) {
			response.sendRedirect(request.getContextPath() + "/board");
		}else if(kwd.contains("insert")) {
			response.sendRedirect(request.getContextPath() + "/board");
		}
		
		List<BoardSerchVo> list =  new BoardDao().serchList(kwd);
		request.setAttribute("list", list);
		
		System.out.println("Serach 실행");
		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
		
		
	}
	
	

}
