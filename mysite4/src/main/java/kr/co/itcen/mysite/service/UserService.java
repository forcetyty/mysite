package kr.co.itcen.mysite.service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co.itcen.mysite.repository.UserDao;
import kr.co.itcen.mysite.vo.UserVo;

@Service
public class UserService {

	@Autowired
	private UserDao userDao;
	
	//@Transactional
	public void join(UserVo vo) {
//		tm.begin();
//		try {
//			userDao.insert(vo);
//			emailDao.insert();
//		}catch() {
//			tm.rollback();
//		}
		
		userDao.insert(vo);
		
	}

	public UserVo getUser(UserVo vo) {
		return userDao.get(vo);
	}

	public Boolean existUser(String email) {
		return userDao.get(email) != null;
	}

	public UserVo getUser(Long no) {
		return userDao.get(no);
	}
	
	// 회원정보를 가져오는 기능
	public UserVo selectList(Long memberNo) {
		return userDao.get(memberNo);
	}
	
	public void update(UserVo vo) {
		userDao.update(vo);
	}

	// 회원정보수정
//	public void updateList(UserVo vo) {
//		userDao.update(vo);
//	}

//	public void join(UserVo vo) {
//		// TODO Auto-generated method stub
//		userDao.insert(vo);
//	}
//
//	public UserVo getUser(UserVo vo) {
//		// TODO Auto-generated method stub
//		return userDao.get(vo);
//
//	}
//
//	// 회원정보를 가져오는 기능
//	public UserVo selectList(Long memberNo) {
//		return userDao.update(memberNo);
//	}
//
//	// 회원정보수정
//	public void updateList(UserVo vo) {
//		userDao.update(vo);
//	}
//	
//	public Boolean existUser(String email) {
//		return userDao.get(email) != null;
//	}
//
//	public UserVo getUser(Long no) {
//		// TODO Auto-generated method stub
//		return null;
//	}

}
