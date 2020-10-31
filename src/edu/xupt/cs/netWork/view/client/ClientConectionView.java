package edu.xupt.cs.netWork.view.client;

import com.mec.util.FrameIsNullException;
import com.mec.util.IMecView;
import com.mec.util.ViewTool;
import edu.xupt.cs.action.abstract_.ActionBeanFactory;
import edu.xupt.cs.core.Client;
import edu.xupt.cs.core.ClientActionNotSetException;
import edu.xupt.cs.core.ClientActionProcessAdpter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;


public class ClientConectionView implements IMecView {
	private ActionBeanFactory factory;
	private JFrame jfmClientView;
	private JLabel jlbConnetMessage;
	private Client client;
	private int count;

	public ClientConectionView(ActionBeanFactory factory) {
		this.factory = factory;
		client = new Client();
		initView();
	}

	
	@Override
	public void dealEvent() {
		jfmClientView.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					exitView();
				} catch (FrameIsNullException e1) {
					e1.printStackTrace();
				}
			}
			
		});
	}


	@Override
	public void init() {
		jfmClientView = new JFrame();
		jfmClientView.setSize(new Dimension(470,250 ));
		jfmClientView.setLocationRelativeTo(null);
		jfmClientView.setTitle("聊天室");
		jfmClientView.setFont(defaultFont);
		jfmClientView.setResizable(false);
		jfmClientView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		
		/* ����  */
		JLabel topTitle = new JLabel("服务器连接",JLabel.CENTER);
		topTitle.setFont(topticFont);
		topTitle.setForeground(topticColor);
		jfmClientView.add(topTitle,BorderLayout.NORTH);
		
		
		jlbConnetMessage = new JLabel("正在第"+ ++count + " 次连接.....", JLabel.CENTER);
		jfmClientView.add(jlbConnetMessage);
		jlbConnetMessage.setFont(defaultFont);
		
		
//		JPanel jplInputArea = new JPanel();
//		jfmClientView.add(jplInputArea,BorderLayout.CENTER);
//		
//		JPanel jplUser = new JPanel();
//		jplInputArea.add(jplUser);
//		
//		JLabel jblUser = new JLabel("�� ��:");
//		jplUser.add(jblUser);
//		jblUser.setFont(deafaultFont);
//		
//		JTextField jxtUesr = new JTextField(textFieldLength);
//		jplUser.add(jxtUesr);
//		jxtUesr.setFont(deafaultFont);
//		
//		JPanel jplPassword = new JPanel();
//		jplInputArea.add(jplPassword);
//		
//		JLabel jblPassword = new JLabel("�� ��:");
//		jplPassword.add(jblPassword);
//		jblPassword.setFont(deafaultFont);
//		
//		JTextField jxtPassword = new JTextField(textFieldLength);
//		jplPassword.add(jxtPassword);
//		jxtPassword.setFont(deafaultFont);
//		
//		JPanel jplSouth = new JPanel();
//		jfmClientView.add(jplSouth,BorderLayout.SOUTH);
//		
//		JButton jbtLogin = new JButton("����");
//		jplSouth.add(jbtLogin);
//		jbtLogin.setFont(deafaultFont);
//		
//		JButton jbtExit = new JButton("�˳�");
//		jplSouth.add(jbtExit);
//		jbtExit.setFont(deafaultFont);
//		
	}

	@Override
	public void reinit() {
		boolean successful = false;
		int choice = JOptionPane.YES_OPTION;
		
		client.setClientAction(new ClientAction());
		while (!successful && choice == JOptionPane.YES_OPTION) {
			try {	
				successful = client.connectToServer();
				if(!successful) {
					choice = ViewTool.getChoice(jfmClientView, "是否继续连接?", JOptionPane.YES_NO_OPTION);
					if (choice == JOptionPane.YES_OPTION) {
						jlbConnetMessage.setText("正在第 "+ ++count + " 次连接.....");
					}
				}
			} catch (ClientActionNotSetException e) {
				ViewTool.showErrorMessage(jfmClientView, e.toString());
				choice = JOptionPane.NO_OPTION;
			}
		}
		if (choice == JOptionPane.NO_OPTION) {
			try {
				exitView();
			} catch (FrameIsNullException e) {
				e.printStackTrace();
			}
			return;
		}
	}

	class ClientAction extends ClientActionProcessAdpter {
		public ClientAction() {
		}
		
		@Override
		public void afterConnectionToServer() {
			try {
				exitView();
				UserLogInView ulv = new UserLogInView(client,factory);
				ulv.showView();
			} catch (FrameIsNullException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void serverOutOfRoom() {
			ViewTool.showMessage(jfmClientView, "服务器已满,请稍后连接");
			try {
				exitView();
			} catch (FrameIsNullException e) {
				e.printStackTrace();
			}
		}
		
		@Override
		public void serverPeerDrop() {				
			try {
				ViewTool.showErrorMessage(jfmClientView, "服务器宕机！连接终止ֹ");
				exitView();
			} catch (FrameIsNullException e) {
				e.printStackTrace();
			}
		}
		
	}

	@Override
	public JFrame getJFrame() {
		return jfmClientView;
	}
	
}
