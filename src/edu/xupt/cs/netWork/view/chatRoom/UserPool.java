package edu.xupt.cs.netWork.view.chatRoom;

import edu.xupt.cs.netWork.userModel.UserInfo;

import java.util.HashMap;
import java.util.Map;


public class UserPool {
	private static Map<String, UserInfo> userPool = new HashMap<>();
	
	public UserPool() {
	}
	
	public void addUser(UserInfo user) {
		if (!userPool.containsKey(user.getNetId())) {
			userPool.put(user.getNetId(), user);
		}
	}
	
	public void removeUser(UserInfo user) {
		if (user != null) {
			removeUser(user.getNetId());
		}
	}
	
	public void removeUser(String netId) {
		if (userPool.containsKey(netId)) {
			userPool.remove(netId);
		}
	}
	
	public UserInfo getUser(String netId) {
		return userPool.get(netId);
	}
	
}
