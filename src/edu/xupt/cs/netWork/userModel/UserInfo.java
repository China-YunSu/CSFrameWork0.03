package edu.xupt.cs.netWork.userModel;

import com.mec.dataBase.annon.Column;
import com.mec.dataBase.annon.Table;

@Table(table = "players", primarykey = "id")
public class UserInfo {
	@Column("id")
	private String id;
	
	@Column("nick")
	private String nick;
	private String netId;
	
	@Column("password")
	private String password;
	
	public UserInfo() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNick() {
		return nick;
	}

	public void setNick(String nick) {
		this.nick = nick;
	}

	public String getNetId() {
		return netId;
	}

	public void setNetId(String netId) {
		this.netId = netId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return nick;
	}

	
	
	
}
