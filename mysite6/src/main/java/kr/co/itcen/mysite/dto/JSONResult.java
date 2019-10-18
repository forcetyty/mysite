package kr.co.itcen.mysite.dto;

public class JSONResult<T> {
	private String result; // Success or fail
	private Object data; // if success, set
	private String message; // if fail, set

	public static JSONResult success(Object data) {
		return new JSONResult(data);
	}
	
	public static JSONResult fail(String data) {
		return new JSONResult(data);
	}

	private JSONResult() {

	}

	private JSONResult(Object data) {
		this.result = "success";
		this.data = data;
	}
	
	private JSONResult(String message) {
		this.result = "fail";
		this.message = message;
	}

	public String getResult() {
		return result;
	}

	public Object getData() {
		return data;
	}

	public String getMessage() {
		return message;
	}

}
