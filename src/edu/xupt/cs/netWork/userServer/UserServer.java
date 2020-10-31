package edu.xupt.cs.netWork.userServer;


import edu.xupt.cs.netWork.dao.UserDao;
import edu.xupt.cs.netWork.userModel.UserInfo;

public class UserServer {
	UserDao userDao;
	
	public UserServer() {
		userDao = new UserDao();
	}
	
	public UserInfo getUserServerById(String id, String passWord) {
		UserInfo user = userDao.getUser(id);
		if (user == null || !passWord.equals(user.getPassword())) {
			return null;
		}
		return user;
	}
	
	public boolean setUser(UserInfo user) {
		return userDao.setUser(user);
	}

}
