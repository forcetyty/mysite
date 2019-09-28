package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.mysite.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

/*
 * 기능설명
 * - 로그인시에는 글쓰기 기능이 활성화
 * - 로그인이 안되어 있을시에는 로그인 화면으로 전환
 */
public class WriteFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		// 접근제어(ACL)
		HttpSession session = request.getSession();
		
		///////// session
		if(session == null) {
			WebUtils.forward(request, response, "/WEB-INF/views/user/loginform.jsp");
			return;
		}
		/////////
		
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		/////////
		if(authUser == null) {
			WebUtils.forward(request, response, "/WEB-INF/views/user/loginform.jsp");
			return;
		}
		/////////
		UserVo vo = new UserDao().get(authUser.getNo());
		
		request.setAttribute("auth", vo);
		
		
		WebUtils.forward(request, response, "/WEB-INF/views/board/write.jsp");

	}

}
