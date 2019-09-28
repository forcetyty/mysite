package kr.co.itcen.mysite.action.board;

import kr.co.itcen.web.mvc.Action;
import kr.co.itcen.web.mvc.ActionFactory;

public class BoardActionFactory extends ActionFactory {

	@Override
	public Action getAction(String actionName) {
		Action action = null;
		
		//g_no  - 그룹
		//o_no  - 그룹에 종속되는 댓글
		//depth - 글에 깊이
		
		if("writeform".equals(actionName)) {
			System.out.println("writeForm 호출");
			action = new WriteFormAction();
		} else if("write".equals(actionName)) {
			System.out.println("write 호출");
			action = new WriteAction();
		} else if("view".equals(actionName)){
			action = new ViewFormAction();
		} else if("modifyform".equals(actionName)){
			action = new ModifyFormAction();
		} else if("modify".equals(actionName)) {
			action = new ModifyAction();
		} else if("delete".equals(actionName)) {
			action = new DeleteAction();
		} else if("replyform".equals(actionName)) {
			action = new ReplyFormAction();
		} else if("reply".equals(actionName)){
			action = new ReplyAction();
		} else if("serach".equals(actionName)) {
			action = new SearchAction();
		} else {
			/* deafult(list) */
			action = new ListAction();
			//System.out.println(action);
			//System.out.println(actionName);
		}
		
		return action;
	}
}