package kr.co.itcen.mysite.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.action.guestbook.GuestBookActionFactory;
import kr.co.itcen.mysite.dao.GuestbookDao;
import kr.co.itcen.mysite.vo.GuestbookVo;
import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

//Servlet을 생성할때
//Url을 지정해주는 영역에서 
//매핑시켜주기 때문에
//특정 부분을 클릭했을시 자동으로 연결되는 것!!!
//jsp 파일에서 경로를 지정해주었기 때문에 가능하다!!!
public class GuestBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		
		//factory 패턴 적용을 위한 코드
		//request.setCharacterEncoding("utf-8");
		String actionName = request.getParameter("a");
		
		ActionFactory actionFactory = new GuestBookActionFactory();
		Action action = actionFactory.getAction(actionName);
		action.execute(request, response);
		

		
		 //factory 패턴 적용을 적용하지 않고, Servlet으로만 처리하기 위한 코드
		 /* String action = request.getParameter("a");
		if ("add".equals(action)) {
			
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String contents = request.getParameter("contents");

			GuestbookVo vo = new GuestbookVo();
			vo.setName(name);
			vo.setPassword(password);
			vo.setContents(contents);

			new GuestbookDao().insert(vo);
			response.sendRedirect(request.getContextPath() + "/guestbook");
			
		} else if ("deleteform".equals(action)) {
			//forward
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/deleteform.jsp");
			rd.forward(request, response);

		} else if ("delete".equals(action)) {
			String no = request.getParameter("no");
			String password = request.getParameter("password");
			
			GuestbookVo vo = new GuestbookVo();
			
			vo.setNo(Long.parseLong(no));
			vo.setPassword(password);
			
			new GuestbookDao().delete(vo);
			
			response.sendRedirect(request.getContextPath() + "/guestbook");
			
		}else{
			// Dao에서 가져오는 기능
			List<GuestbookVo> list = new GuestbookDao().getList();
			request.setAttribute("list", list);

			// forwarding
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/guestbook/list.jsp");
			rd.forward(request, response);
		}
		*/

	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
