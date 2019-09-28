package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.BoardViewVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.mysite.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		// Session을 통해서 수정을 할수 있는 인원을 통제!!!
		// 접근제어(ACL)
		HttpSession session = request.getSession();

		if (session == null) {
			WebUtils.forward(request, response, "/WEB-INF/views/user/loginform.jsp");
			return;
		}

		UserVo authUser = (UserVo) session.getAttribute("authUser");

		if (authUser == null) {
			WebUtils.forward(request, response, "/WEB-INF/views/user/loginform.jsp");
			return;
		}

		//게시판의 글 번호를 가져와 Dao에 넣어주고 그걸 바탕으로 
		//세션의 no와 게시판의 User_no를 가지고 계산하는 기능!!!
		String no = request.getParameter("no");
		BoardViewVo vo = new BoardDao().selectView(Long.parseLong(no));
		
		request.setAttribute("vo", vo);

		if (authUser.getNo() == vo.getUser_no()) {
			WebUtils.forward(request, response, "/WEB-INF/views/board/modify.jsp");
		} else {
			response.sendRedirect(request.getContextPath() + "/board");
		}

	}

}
