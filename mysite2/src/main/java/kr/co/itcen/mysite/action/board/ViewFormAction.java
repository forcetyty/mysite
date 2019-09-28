package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardViewVo;
import kr.co.itcen.mysite.web.WebUtils;
import kr.co.itcen.web.mvc.Action;


public class ViewFormAction implements Action  {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String no = request.getParameter("no");
		
		BoardViewVo vo = new BoardDao().selectView(Long.parseLong(no));
		new BoardDao().hitUpdate(Long.parseLong(no));
		
		System.out.println(no);
		request.setAttribute("vo", vo);
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/view.jsp");
	
	}

	

}
