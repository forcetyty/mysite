package kr.co.itcen.mysite.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.itcen.mysite.repository.BoardDao;
import kr.co.itcen.mysite.repository.ReplyDao;
import kr.co.itcen.mysite.vo.BoardUserListVo;
import kr.co.itcen.mysite.vo.BoardViewVo;
import kr.co.itcen.mysite.vo.BoardVo;

@Service
public class BoardService {

	@Autowired
	private BoardDao boardDao;
	
	@Autowired
	private ReplyDao replyDao;
	
	//원글에 대한 답글
	public boolean replyInsert(BoardVo vo) {
		Boolean result = false;

		if (vo != null) {
			replyDao.replyInsert(vo);
			System.out.println("ReplyInsert");
			result = true;
		}
		return result;
	}
	
	//답글에 대한 답글
	public boolean reply2Insert(BoardVo vo) {
		Boolean result = false;

		if (vo != null) {
			replyDao.reply2Insert(vo);
			System.out.println("ReplyInsert");
			result = true;
		}
		return result;
	}
	
	
	
	
	//조회수 업데이트
	public boolean hitUpdate(Long no) {
		Boolean result = false;

		if (no != null) {
			System.out.println("Hit Update Event");
			boardDao.hitUpdate(no);
			result = true;
		}

		return result;
	}
	
	
	// 리스트
	public List<BoardUserListVo> getList(int start, int end, String kwd) {
		return boardDao.getList(start, end, kwd);
	}

	// 원글 등록
	public boolean boardInsert(BoardVo vo) {
		Boolean result = false;

		if (vo != null) {
			boardDao.insertBoardDao(vo);
			System.out.println("BoardInsert");
			result = true;
		}

		return result;
	}

	// 글 보임
	public BoardViewVo viewSelect(Long no) {
		BoardViewVo vo = boardDao.selectView(no);
		return vo;
	}

	// 글 삭제
	public Boolean deleteUpdate(Long no) {
		Boolean result = false;

		if (no != null) {
			System.out.println("DeleteAction");
			boardDao.deleteUpdate(no);
			result = true;
		}

		return result;
	}

	// 글 수정
	public Boolean updateModify(BoardVo vo) {
		Boolean result = false;

		if (vo != null) {
			System.out.println("Modify");
			boardDao.updateModify(vo);
			result = true;
		}
		return result;
	}
	
	

}
