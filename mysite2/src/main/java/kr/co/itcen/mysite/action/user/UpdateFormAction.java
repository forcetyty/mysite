package kr.co.itcen.mysite.action.user;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.mysite.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class UpdateFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// Long no = authUser.getNo();

		// 접근제어(ACL)
		HttpSession session = request.getSession();

		if (session == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}

		UserVo authUser = (UserVo)session.getAttribute("authUser");
		
		
		if (authUser == null) {
			WebUtils.redirect(request, response, request.getContextPath());
			return;
		}
		/////////////////////////////////
		
		UserVo vo = new UserDao().get(authUser.getNo());
		
		System.out.println(authUser.getNo()); // 데이터 확인 완료!!!
		
		request.setAttribute("auth", vo);
		System.out.println(vo.getEmail()); // 데이터 확인 완료!!!
		
		WebUtils.forward(request, response, "/WEB-INF/views/user/updateform.jsp");

	}

}
