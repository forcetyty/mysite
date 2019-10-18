package kr.co.itcen.mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import kr.co.itcen.mysite.vo.BoardVo;


// [고민]
// 1. Vo 객체가 중복된다.
//    객체가 중복된 원인은 객체에 따라서 각기 다른 기능을 해주기 위함이었다
// 2. 중복되는 Vo 객체를 줄이는것이 맞는것일까 아니면, 분리시키는것이 맞는것일까?
//    분리시킨 이유 중 하나는 단일 책임원칙 때문이었다.

// MyBatis로 변경하면서 BoardVo로 통일하여 사용하기로 결정

@Repository
public class ReplyDao {
	
	@Autowired
	private SqlSession sqlSession;

	@Autowired
	private DataSource dataSource;

	// 원글에 대한 답글을 달때
	// MyBatis 적용완료!!!
	public boolean replyInsert(BoardVo vo) {
		
		Boolean result = false;
		int count = 0;
		
		count = sqlSession.insert("reply.replyInsert", vo);
	
		if(count == 1) {
			result = true;
		}
		
		return result;
	}

	// 답글에 답글을 달떄
	// MyBatis 적용완료!!!
	public boolean reply2Insert(BoardVo vo) {
		
		Boolean result = false;
		int count = 0;
		
		count = sqlSession.insert("reply.reply2Insert", vo);
	
		if(count == 1) {
			result = true;
		}
		
		return result;
	}



}
