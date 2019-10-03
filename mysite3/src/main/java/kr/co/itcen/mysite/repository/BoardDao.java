package kr.co.itcen.mysite.repository;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardCountVo;
import kr.co.itcen.mysite.vo.BoardSerchVo;
import kr.co.itcen.mysite.vo.BoardUserListVo;
import kr.co.itcen.mysite.vo.BoardViewVo;
import kr.co.itcen.mysite.vo.BoardVo;

@Repository
public class BoardDao {
	
	@Autowired
	private SqlSession sqlSession;
	
	@Autowired
	private	DataSource dataSource;
	
	// 전체 게시글의 수를 가져오는 쿼리
	public BoardCountVo countList() {
		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		BoardCountVo vo = new BoardCountVo();
		
		try {
			connection = dataSource.getConnection();
		
			String sql = "select count(*) as num from board where status = 1";
			pstmt = connection.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while (rs.next()) {
			int num = rs.getInt(1);
			
			vo.setCountRow(num);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return vo;
	}
	

	// title과 contents로 검색가능하게 하는 Dao
	public List<BoardSerchVo> serchList(String kwd) {
		
		List<BoardSerchVo> result = new ArrayList<BoardSerchVo>();

		Connection connection = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			connection = dataSource.getConnection();
			
			//아래 쿼리를 통해서 리스트에 답글과 댓글을 표시
			String sql = "select b.no, b.title, u.name, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.g_no, b.o_no, b.depth from user as u, board as b where u.no = b.user_no and b.status = 1  and ( b.title like ? or b.contents like ? ) order by b.g_no desc, b.o_no asc";
			pstmt = connection.prepareStatement(sql);
			
			//Like 검색어 핵심요소
			pstmt.setString(1, "%" + kwd + "%");
			pstmt.setString(2, "%" + kwd + "%");

			rs = pstmt.executeQuery();

			while (rs.next()) {

				Long no = rs.getLong(1);
				String title = rs.getString(2);
				String name = rs.getString(3);
				Long hit = rs.getLong(4);
				String date_format = rs.getString(5);
				Long g_no = rs.getLong(6);
				Long o_no = rs.getLong(7);
				Long depth = rs.getLong(8);

				BoardSerchVo vo = new BoardSerchVo();

				vo.setNo(no);
				vo.setTitle(title);
				vo.setName(name);
				vo.setHit(hit);
				vo.setReg_date(date_format);
				vo.setG_no(g_no);
				vo.setO_no(o_no);
				vo.setDepth(depth);

				result.add(vo);
			}
		} catch (SQLException e) {
			System.out.println("error:" + e);
		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
				if (pstmt != null) {
					pstmt.close();
				}
				if (connection != null) {
					connection.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return result;
		
	}



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
	public List<BoardUserListVo> getList(int start, int end) {
		Map<String, Integer> serch = new HashMap<String, Integer>();
		
		serch.put("startNum", start);
		serch.put("endNum", end);
		
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
