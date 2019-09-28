package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.BoardRelyVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.mysite.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ReplyFormAction implements Action {

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
		
		UserVo vo = new UserDao().get(authUser.getNo());

		String g_no = request.getParameter("g_no");
		String o_no = request.getParameter("o_no");
		String depth = request.getParameter("depth");
		
		System.out.println("replyFormAction");
		System.out.println("그룹 :" + g_no + "답글순서 :" + o_no + "깊이 :" + depth);
		
		BoardRelyVo vo1 = new BoardRelyVo();
		

		vo1.setG_no(Long.parseLong(g_no));
		vo1.setO_no(Long.parseLong(o_no));
		vo1.setDepth(Long.parseLong(depth));
		
		request.setAttribute("auth", vo);
		request.setAttribute("vo1", vo1);
		
		
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/replywrite.jsp");			
		

	}

}
