package kr.co.itcen.mysite.repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


import kr.co.itcen.mysite.vo.BoardUserListVo;
import kr.co.itcen.mysite.vo.BoardViewVo;
import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private	DataSource dataSource;
	
//	// 전체 게시글의 수를 가져오는 쿼리
	// 불필요한 Query라 판단 / 추후 삭제
//	public BoardCountVo countList() {
//		Connection connection = null;
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		
//		BoardCountVo vo = new BoardCountVo();
//		
//		try {
//			connection = dataSource.getConnection();
//		
//			String sql = "select count(*) as num from board where status = 1";
//			pstmt = connection.prepareStatement(sql);
//			rs = pstmt.executeQuery();
//			
//			while (rs.next()) {
//			int num = rs.getInt(1);
//			
//			vo.setCountRow(num);
//			}
//		} catch (SQLException e) {
//			System.out.println("error:" + e);
//		} finally {
//			try {
//				if (rs != null) {
//					rs.close();
//				}
//				if (pstmt != null) {
//					pstmt.close();
//				}
//				if (connection != null) {
//					connection.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//		return vo;
//	}
	

	// My Batis 적용완료
	// Hit Update 처리해주는 Dao
	public Boolean  hitUpdate(Long vo) {
		
		Boolean result = false;
		int count = 0;
		
		count = sqlSession.update("board.hitUpdate", vo);
	
		if(count == 1) {
			result = true;
		}
		
		return result;
	}

	//MyBatis
	// View에서 Modify에 들어간후 Modify에서 수정을 했을때의 동작
	// Update를 동작하게 하는 화면!!!
	public Boolean updateModify(BoardVo vo) {
		
		Boolean result = false;
		int count = 0;
		
		count = sqlSession.update("board.updateModify", vo);
	
		if(count == 1) {
			result = true;
		}
		
		return result;
	}
	
	//MyBatis 적용완료!!!
	// 게시글에 대한 삭제를 처리해주는 Dao
	public Boolean deleteUpdate(Long vo) {
	
		Boolean result = false;
		int count = 0;
		
		count = sqlSession.update("board.deleteUpdate", vo);
	
		if(count == 1) {
			result = true;
		}
		
		return result;

	}
	
	// MyBatis 적용완료!!!
	// 게시판에 글을 출력해주는 Dao
	public List<BoardUserListVo> getList(int start, int end, String kwd) {
		
		Map<String, Object> serch = new HashMap<String, Object>();
		
		serch.put("startNum", start);
		serch.put("endNum", end);
		serch.put("kwd", kwd);
		
		List<BoardUserListVo> result = sqlSession.selectList("board.getList",serch);
		return result;
	}

	// MyBatis 적용완료!!!
	// View에 선택한 게시글을 표시해주는 Dao Method
	public BoardViewVo selectView(Long no) {
		BoardViewVo result = new BoardViewVo();
		result = sqlSession.selectOne("board.viewSelectBoard", no);
		return result; 
	}


	// MyBatis 적용완료!!!
	// 원 게시글을 등록하는 Dao
	// 답글과 댓글 구현 Dao는 Reply Dao에 구현되어 있음
	public Boolean insertBoardDao(BoardVo vo) {
		Boolean result = false;
		int count = 0;
		
		count = sqlSession.insert("board.insertBoard", vo);
	
		if(count == 1) {
			result = true;
		}
		
		return result;
	}



}
