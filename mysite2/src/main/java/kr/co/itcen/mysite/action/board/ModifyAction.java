package kr.co.itcen.mysite.action.board;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardVo;
import kr.co.itcen.mysite.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ModifyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String no = request.getParameter("no");
		String title = request.getParameter("title");
		String content = request.getParameter("content");

		BoardVo bo = new BoardVo();

		bo.setNo(Long.parseLong(no));
		bo.setTitle(title);
		bo.setContents(content);

		new BoardDao().updateModify(bo);

		WebUtils.redirect(request, response, request.getContextPath() + "/board?a=view&no=" + Long.parseLong(no));
		
	}

}
