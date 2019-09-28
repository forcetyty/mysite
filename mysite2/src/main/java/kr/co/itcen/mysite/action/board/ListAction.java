package kr.co.itcen.mysite.action.board;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import kr.co.itcen.mysite.dao.BoardDao;
import kr.co.itcen.mysite.vo.BoardUserListVo;

import kr.co.itcen.mysite.web.WebUtils;
import kr.co.itcen.web.mvc.Action;

public class ListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		int maxBoardlist = 5;	//최종 게시판 리스트
		int firstBoardlist = 1;	//시작 게시판!!!
		int count = new BoardDao().countList();
		System.out.println(count);
		

		
		if(request.getParameter("page")!=null) {
			firstBoardlist = Integer.parseInt(request.getParameter("page"));
		}
		
		int pageNum = ((firstBoardlist-1)/5)*5;
		
		List<BoardUserListVo> list = new BoardDao().getList((firstBoardlist-1)*maxBoardlist, maxBoardlist);
		System.out.println(firstBoardlist);
		
		
		System.out.println("pageNum :" + pageNum);
		//int index= count - ((firstBoardlist-1)*5);
		
		request.setAttribute("firstBoardlist", firstBoardlist);
		request.setAttribute("pageNum", pageNum);
		request.setAttribute("list", list);
		request.setAttribute("count", count);
		
		//request.setAttribute("index", index);
		
		//System.out.println(blockStartNum + blockLastNum + lastPageNum);

		WebUtils.forward(request, response, "/WEB-INF/views/board/list.jsp");
	}

}
