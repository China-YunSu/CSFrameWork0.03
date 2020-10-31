package edu.xupt.cs.netWork.dao;

import com.mec.dataBase.core.MECDataBase;
import edu.xupt.cs.netWork.userModel.UserInfo;

public class UserDao {
	MECDataBase dataBase;

	public UserDao() {
		dataBase = new MECDataBase();
	}
	
	public UserInfo getUser(String id) {
		return dataBase.getData(UserInfo.class, id);
	}
	
	public boolean setUser(UserInfo user) {
		return dataBase.setData(user);
	}
}
