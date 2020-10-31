package edu.xupt.cs.netWork.userAction;

import edu.xupt.cs.factory.anno.AActionClass;
import edu.xupt.cs.factory.anno.AActionMethod;
import edu.xupt.cs.factory.anno.AActionType;
import edu.xupt.cs.netWork.userModel.UserInfo;
import edu.xupt.cs.netWork.userServer.UserServer;

@AActionClass()
public class UserAction {

	public UserAction() {
	}

	@AActionMethod(action = "userLogin")
	public UserInfo userLogin(@AActionType(parameterName="id") String id,
							  @AActionType(parameterName="password") String password) {
		//数据库连接 信息匹配
		UserInfo user = new UserServer().getUserServerById(id, password);

		if(user == null) {
			user = new UserInfo();
			user.setId("ERROR");
		}

		return user;
	}

	@AActionMethod(action = "userRegister")
	public boolean userRegister(@AActionType(parameterName="user") UserInfo user) {
		return new UserServer().setUser(user);
	}

}