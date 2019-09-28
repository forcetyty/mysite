package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardViewVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.mysite.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class DeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String no = request.getParameter("no");
		BoardViewVo vo = new BoardDao().selectView(Long.parseLong(no));
		System.out.println(no);
		
		// Session을 통해서 삭제할수 인원을 통제!!!
		HttpSession session = request.getSession();
		
		if(session == null) {
			WebUtils.forward(request, response, "/WEB-INF/views/user/loginform.jsp");
			return;
		}
		
		UserVo authUser = (UserVo) session.getAttribute("authUser");
		
		if(authUser == null) {
			WebUtils.forward(request, response, "/WEB-INF/views/user/loginform.jsp");
			return;
		}
		////////////
		
		if(authUser.getNo() == vo.getUser_no()) {
			System.out.println("delete Action");		
			new BoardDao().deleteUpdate(Long.parseLong(no));
			WebUtils.redirect(request, response, request.getContextPath() + "/board" );
		}else {
			WebUtils.redirect(request, response, request.getContextPath() + "/board" );
		}
		
	
		
	}

}
