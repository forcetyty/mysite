package kr.co.itcen.web.mvc;

public abstract class ActionFactory {
	
	/*
	 * - Factory 패턴을 이용해서
	 * - Servlet을 구조화 시킬수 있다.
	 */
	public abstract Action getAction(String actionName);

}
