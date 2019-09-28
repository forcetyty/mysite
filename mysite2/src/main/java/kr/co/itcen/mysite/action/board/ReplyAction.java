package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.co.itcen.mysite.dao.ReplyDao;
import kr.co.itcen.mysite.dao.UserDao;
import kr.co.itcen.mysite.vo.BoardRelyVo;
import kr.co.itcen.mysite.vo.UserVo;
import kr.co.itcen.mysite.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// g_no / o_no / depth

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

		System.out.println("ReplyAction");

		
		String g_no = request.getParameter("g_no");
		String o_no = request.getParameter("o_no");
		String depth = request.getParameter("depth");
		String no = request.getParameter("no");
		
		BoardRelyVo vo1 = new BoardRelyVo();
		
		System.out.println("그룹 :" + vo1.getG_no() + "답글순서 :" + vo1.getO_no() + "깊이 :" + vo1.getDepth());
		
		System.out.println("그룹 :" + g_no + "답글순서 :" + o_no + "깊이 :" + depth);
		System.out.println("제목 :" + title + "내용 :" + content);
		System.out.println(vo.getNo());
		/*vo.setG_no(vo.getG_no());
		vo.setO_no(vo.getO_no());
		vo.setDepth(vo.getDepth());*/
		System.out.println(no);
		
		
		vo1.setUser_no(vo.getNo());
		vo1.setG_no(Long.parseLong(g_no));
		vo1.setO_no(Long.parseLong(o_no));
		vo1.setDepth(Long.parseLong(depth));
		vo1.setTitle(title);
		vo1.setContents(content);

		if (Long.parseLong(o_no) == 0) {
			System.out.println("답글실행");
			new ReplyDao().replyInsert(vo1);
		} else {
			System.out.println("댓글실행");
			new ReplyDao().reply2Insert(vo1);
		}

		response.sendRedirect(request.getContextPath() + "/board");

	}

}
