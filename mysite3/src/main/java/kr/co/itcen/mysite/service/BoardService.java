package kr.co.itcen.mysite.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.vo.BoardUserListVo;
import kr.co.itcen.mysite.vo.BoardViewVo;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {
	
	@Autowired
	private BoardDao boardDao;
	
	public List<BoardUserListVo> getList(int start, int end){
//		List<BoardUserListVo> result = ArrayList<BoardUserListVo>();
		
//		int maxBoardlist = 5;	//최종 게시판 리스트
//		int firstBoardlist = 1;	//시작 게시판!!!
//		int pageNum = ((firstBoardlist-1)/5)*5;
		
		return boardDao.getList(start, end);
		//return result;
	}
	
	public boolean boardInsert(BoardVo vo) {
		Boolean result = false;
		
		if(vo != null) {
			boardDao.insertBoardDao(vo);
			System.out.println("BoardInsert");
			result = true;
		}
		
		return result;
	}
	
	public BoardViewVo viewSelect(Long no) {
		BoardViewVo vo = boardDao.selectView(no);
		
		return vo;
	}

}
