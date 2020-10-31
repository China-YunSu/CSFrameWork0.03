package edu.xupt.cs.netWork.view.chatRoom;

public class AppMessage {
	private String parameter;
	private EAppCommond commond;
	
	public AppMessage() {
	}

	public String getParameter() {
		return parameter;
	}

	public AppMessage setParameter(String parameter) {
		this.parameter = parameter;
		return this;
	}

	public EAppCommond getCommond() {
		return commond;
	}

	public AppMessage setCommond(EAppCommond commond) {
		this.commond = commond;
		return this;
	}
	
}
