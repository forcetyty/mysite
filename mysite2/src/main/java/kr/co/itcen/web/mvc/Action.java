package kr.co.itcen.web.mvc;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


// HttpServletRequest request, HttpServletResponse response
public interface Action {

	public void execute(HttpServletRequest request, HttpServletResponse response)  throws ServletException, IOException;
	
	

}
