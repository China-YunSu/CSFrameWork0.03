package edu.xupt.cs.netWork.view.client;

import com.mec.util.ArgumentMaker;
import com.mec.util.FrameIsNullException;
import com.mec.util.IMecView;
import com.mec.util.ViewTool;
import edu.xupt.cs.action.abstract_.ActionBeanFactory;
import edu.xupt.cs.factory.anno.AActionClass;
import edu.xupt.cs.factory.anno.AActionMethod;
import edu.xupt.cs.factory.anno.AActionType;
import edu.xupt.cs.action.processer.ActionProcess;
import edu.xupt.cs.core.Client;
import edu.xupt.cs.core.ClientActionProcessAdpter;
import edu.xupt.cs.netWork.userModel.UserInfo;
import edu.xupt.cs.netWork.view.chatRoom.ChatRoom;
import edu.xupt.cs.netWork.view.layout.GridBagConstraint;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
@AActionClass()
public class UserLogInView implements IMecView{
	private JFrame jfmloginView;
	private JTextField jtxtUser;
	private JPasswordField jpfdPassword;
	private JButton jbtLogIn;
	private JButton jbtRegister;
	private Client client;
	private CardLayout cardLayout;
	private JTextField jtxtId;
	private JTextField jtxtPassword;
	private JTextField jtxtNick;
	private JButton jbtRegisterFire;
	private JButton jbtGoBack;
	private ActionBeanFactory factory;
	public UserLogInView() {
		
	}
	
	public UserLogInView(Client client,ActionBeanFactory factory) {
		this.client = client;
		this.factory = factory;
		client.setActionProcess(new ActionProcess(factory));
		client.setClientAction(new ClientActionProcessAdpter() {

			@Override
			public void serverForceDrop() {
				try {
					ViewTool.showErrorMessage(jfmloginView, "服务器强制宕机！连接终止");
					exitView();
				} catch (FrameIsNullException e) {
					e.printStackTrace();
				}
			}

			@Override
			public boolean confirmOffline() {
				int choice = ViewTool.getChoice(jfmloginView, "您真的退出?", JOptionPane.YES_NO_OPTION);

				return choice == JOptionPane.YES_OPTION;
			}

			@Override
			public void afterOffLine() {
				try {
					exitView();
				} catch (FrameIsNullException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void serverPeerDrop() {
				try {
					ViewTool.showErrorMessage(jfmloginView, "服务器宕机！连接终止");
					exitView();
				} catch (FrameIsNullException e) {
					e.printStackTrace();
				}
			}

		});
		initView();
	}

	@Override
	public void dealEvent() {
		jfmloginView.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				client.offLine();
			}
		});
		
		jtxtUser.addFocusListener(new FocusAdapter() {

			@Override
			public void focusGained(FocusEvent e) {
				jtxtUser.selectAll();
			}
			
		});
		
		jtxtUser.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
					jpfdPassword.requestFocus();
			}
		});
		
		jpfdPassword.addFocusListener(new FocusAdapter() {
			@Override
			public void focusGained(FocusEvent e) {
				jpfdPassword.setText("");
			}
		});
		
		jpfdPassword.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				jbtLogIn.requestFocus();
			}
		});
		
		jbtLogIn.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = jtxtUser.getText();
				String password = new String(jpfdPassword.getPassword());

				factory.setObject("afterLogin", UserLogInView.this);
				client.request("userLogin", "afterLogin", new ArgumentMaker()
							.add("id", id)
							.add("password", password)
							.toString());
				
				cardLayout.show(jfmloginView.getContentPane(), "connection");
			}
		});	
		
		jbtRegister.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(jfmloginView.getContentPane(), "Register");
			}
		});
		
		jbtGoBack.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				cardLayout.show(jfmloginView.getContentPane(), "login");
			}
		});
		
		jbtRegisterFire.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				UserInfo user = new UserInfo();
				user.setId(jtxtId.getText().trim());
				user.setNick(jtxtNick.getText().trim());
				user.setPassword(jtxtPassword.getText().trim());
				factory.setObject("afterRegister", UserLogInView.this);
				client.request("userRegister", "afterRegister", new ArgumentMaker()
						.add("user", user)
						.toString());
			}
		});
	}
	
	
	@Override
	public void reinit() {
	}

	private JPanel loginView() {
		JPanel jplLogin = new JPanel(new BorderLayout());
		JLabel jlbTopic = new JLabel("登 录",JLabel.CENTER);
		jplLogin.add(jlbTopic, BorderLayout.NORTH);
		jlbTopic.setFont(topticFont);
		jlbTopic.setForeground(topticColor);

		JLabel jlblBlankWest = new JLabel(" ");
		jlblBlankWest.setFont(defaultFont);
		jplLogin.add(jlblBlankWest, BorderLayout.WEST);

		JLabel jlblBlankEast = new JLabel(" ");
		jlblBlankEast.setOpaque(true);
		jplLogin.add(jlblBlankEast, BorderLayout.EAST);

		JPanel jplButtons = new	JPanel();
		jplLogin.add(jplButtons, BorderLayout.SOUTH);

		jbtLogIn = new JButton("登录");
		jbtLogIn.setFont(defaultFont);
		jplButtons.add(jbtLogIn);

		jbtRegister = new JButton("注册");
		jbtRegister.setFont(defaultFont);
		jplButtons.add(jbtRegister);


		GridBagLayout gridBagLayout = new GridBagLayout();
		JPanel jplBody  = new JPanel(gridBagLayout);
		jplLogin.add(jplBody, BorderLayout.CENTER);

		JPanel jplUser = new JPanel();
		gridBagLayout.setConstraints(jplUser, new GridBagConstraint(0, 0)
				.setInsets(20, 0, 0, 0));
		jplBody.add(jplUser);
		JLabel jlbUserName = new JLabel("账号:");
		jplUser.add(jlbUserName);
		jlbUserName.setFont(defaultFont);
		jtxtUser = new JTextField(textFieldLength);
		jtxtUser.setFont(defaultFont);
		jplUser.add(jtxtUser);

		JPanel jplPassword = new JPanel();
		gridBagLayout.setConstraints(jplPassword, new GridBagConstraint(0, 1));
		jplBody.add(jplPassword);
		JLabel jlbPasswordName = new JLabel("密码:");
		jplPassword.add(jlbPasswordName);
		jlbPasswordName.setFont(defaultFont);
		jpfdPassword = new JPasswordField(textFieldLength);
		jpfdPassword.setFont(defaultFont);
		jplPassword.add(jpfdPassword);

		return jplLogin;
	}

	private JPanel connectionWaittingView() {
		JPanel jplConnection = new JPanel(new BorderLayout());

		JLabel jlbWaitting = new JLabel("正在登陆.......",JLabel.CENTER);
		jlbWaitting.setFont(maxFont);
		jplConnection.add(jlbWaitting,BorderLayout.CENTER);

		return jplConnection;
	}

	private JPanel register() {
		JPanel jplRegisterView = new JPanel(new BorderLayout());

		JLabel jlbRegister = new JLabel("注 册",JLabel.CENTER);
		jlbRegister.setFont(topticFont);
		jlbRegister.setForeground(topticColor);
		jplRegisterView.add(jlbRegister, BorderLayout.NORTH);

		JPanel jplBody = new JPanel(new GridLayout(3,1));
		jplRegisterView.add(jplBody,BorderLayout.CENTER);

		JPanel jplId = new JPanel();
		JLabel jlbId = new JLabel("账号:");
		jlbId.setFont(minFont);
		jplId.add(jlbId);
		jtxtId = new JTextField(textFieldLength);
		jtxtId.setFont(minFont);
		jplId.add(jtxtId);
		jplBody.add(jplId);

		JPanel jplPassword = new JPanel();
		JLabel jlbPassword  = new JLabel("密码:");
		jlbPassword.setFont(minFont);
		jplPassword.add(jlbPassword);
		jtxtPassword  = new JTextField(textFieldLength);
		jtxtPassword .setFont(minFont);
		jplPassword .add(jtxtPassword );
		jplBody.add(jplPassword);

		JPanel jplNick = new JPanel();
		JLabel jlbNick  = new JLabel("昵称:");
		jlbNick.setFont(minFont);
		jplNick.add(jlbNick);
		jtxtNick  = new JTextField(textFieldLength);
		jtxtNick.setFont(minFont);
		jplNick.add(jtxtNick );
		jplBody.add(jplNick);

		JPanel jplButtons = new JPanel();
		jbtRegisterFire = new JButton("确认");
		jbtRegisterFire.setFont(defaultFont);
		jplButtons.add(jbtRegisterFire);

		jbtGoBack = new JButton("返回");
		jbtGoBack.setFont(defaultFont);
		jplButtons.add(jbtGoBack);

		jplRegisterView.add(jplButtons,BorderLayout.SOUTH);

		return jplRegisterView;
	}
	@Override
	public void init() {
		cardLayout = new CardLayout();
//TODO
		jfmloginView = new JFrame();
		jfmloginView.setSize(new Dimension(470,250 ));
		jfmloginView.setLocationRelativeTo(null);
		jfmloginView.setTitle("聊天室");
		jfmloginView.setFont(defaultFont);
		jfmloginView.setResizable(false);
		jfmloginView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		jfmloginView.setLayout(cardLayout);

		JPanel jplLogin = loginView();
		cardLayout.addLayoutComponent(jplLogin, "login");
		jfmloginView.add(jplLogin);

		JPanel jplConnection = connectionWaittingView();
		cardLayout.addLayoutComponent(jplConnection, "connection");
		jfmloginView.add(jplConnection);

		JPanel jplRegister = register();
		cardLayout.addLayoutComponent(jplRegister, "Register");
		jfmloginView.add(jplRegister);

	}

	@AActionMethod(action = "afterLogin")
	public void afterLogin(@AActionType(parameterName = "user") UserInfo user) {
		try {
		if (user.getId().equals("ERROR")) {
			cardLayout.show(jfmloginView.getContentPane(), "login");
			ViewTool.showMessage(jfmloginView, "账号或密码错误!");
			return;
		}
			user.setNetId(client.getId());
			ChatRoom chatRoom = new ChatRoom(client,user);
			exitView();
			chatRoom.showView();
		} catch (FrameIsNullException e) {
			e.printStackTrace();
		}
	}
	@AActionMethod(action = "afterRegister")
	public void afterRegister(@AActionType(parameterName = "result") boolean result) {
		if (result == true) {
			ViewTool.showMessage(jfmloginView, "注册成功！");
			cardLayout.show(jfmloginView.getContentPane(), "login");
			return;
		}
		ViewTool.showMessage(jfmloginView, "用户已存在！");
		jtxtId.setText("");
		jtxtPassword.setText("");
		jtxtNick.setText("");
	}
	
	@Override
	public JFrame getJFrame() {
		return jfmloginView;
	}
}
