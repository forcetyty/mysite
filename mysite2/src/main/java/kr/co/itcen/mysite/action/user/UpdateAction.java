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



public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String no = request.getParameter("no");
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String gender = request.getParameter("gender");

		UserVo vo = new UserVo();

		vo.setNo(Long.parseLong(no));
		vo.setName(name);
		vo.setPassword(password);
		vo.setGender(gender);

		new UserDao().updateUser(vo);

		// 인증처리(Session 처리)
		HttpSession session = request.getSession(true); // true 없으면 새로 만들어서 전달해달라!!! // false -> null!!!
		session.setAttribute("authUser", vo);

		WebUtils.redirect(request, response, request.getContextPath());

	}

}
