package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.dao.GuestbookDao;
import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.vo.GuestbookVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.mysite.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class WriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// 접근제어(ACL)
		HttpSession session = request.getSession();

		if (session == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}

		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		
		UserVo vo = new UserDao().get(authUser.getNo());
				
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo bvo = new BoardVo();
				
		bvo.setNo(vo.getNo());
		bvo.setTitle(title);
		bvo.setContents(content);
		bvo.setUser_no(vo.getNo());
		
		
		
		new BoardDao().insertBoardDao(bvo);
		
		response.sendRedirect(request.getContextPath() + "/board");

	}

}
