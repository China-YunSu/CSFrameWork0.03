package edu.xupt.cs.netWork.view.chatRoom;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;


import com.mec.util.FrameIsNullException;
import com.mec.util.IMecView;
import com.mec.util.ViewTool;
import edu.xupt.cs.factory.anno.ArgumentMaker;
import edu.xupt.cs.core.Client;
import edu.xupt.cs.core.ClientActionProcessAdpter;
import edu.xupt.cs.netWork.userModel.UserInfo;

public class ChatRoom implements IMecView {
    JFrame jfmChatRoom;
    DefaultListModel<UserInfo> dlmFriendList;
    JList<UserInfo> jlstFriendList;
    JButton jbtSend;
    JButton jbtExit;
    JTextArea jtxtChatArea;
    JTextArea jtxtYouSay;
    Client client;
    JLabel jlbCurNick;
    JLabel jlbNick;
    JTextArea jtxtSystemInfo;
    UserInfo userInfo;
    UserInfo curUserInfo;
    private static String ALL = "000000";
    private static UserPool userPool;

    static {
        userPool = new UserPool();

        UserInfo allFriend = new UserInfo();
        allFriend.setNick("所有人");
        allFriend.setNetId(ALL);
        userPool.addUser(allFriend);
    }

    public ChatRoom(Client client, UserInfo userInfo) {
        this.userInfo = userInfo;
        this.client = client;
        client.setClientAction(new ClientAction());
        initView();
    }



    @Override
    public void dealEvent() {

        jbtSend.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String targetId = curUserInfo.getNetId();
                String message = jtxtYouSay.getText();
                if (!targetId.equals(ALL)) {
                    AppMessage appMessage = new AppMessage()
                            .setCommond(EAppCommond.TO_ONE)
                            .setParameter(message);
                    client.talkToOne(targetId, ArgumentMaker.toJson(appMessage));
                    jtxtChatArea.append("你对[" + curUserInfo.getNick() +"]说:" + message + "\n");
                } else {
                    AppMessage appMessage = new AppMessage()
                            .setCommond(EAppCommond.TO_OTHERS)
                            .setParameter(message);
                    client.toOthers(ArgumentMaker.toJson(appMessage));
                    jtxtChatArea.append("你对[" + "所有人" +"]说:" + message + "\n");
                    jtxtChatArea.setCaretPosition(jtxtChatArea.getText().length());

                }
                jtxtYouSay.setText("");
            }
        });

        jfmChatRoom.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                client.offLine();
            }
        });

        jbtExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.offLine();
            }
        });

        jlstFriendList.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting() == false) {
                    ChatRoom.this.curUserInfo = jlstFriendList.getSelectedValue();
                    ChatRoom.this.jlbCurNick.setText(curUserInfo.getNick());
                }
            }
        });
    }

    @Override
    public void reinit() {
        jtxtChatArea.setEditable(false);
        jtxtChatArea.setFocusable(false);

        AppMessage appMessage = new AppMessage()
                .setCommond(EAppCommond.I_AM_COMMING)
                .setParameter(ArgumentMaker.toJson(userInfo));
        client.toOthers(ArgumentMaker.toJson(appMessage));
        dlmFriendList.addElement(userPool.getUser(ALL));
        jlstFriendList.setSelectedIndex(0);
        jlbNick.setText(userInfo.getNick());
        jlbCurNick.setText(curUserInfo.getNick());
    }

    @Override
    public void init() {
        jfmChatRoom = new JFrame();
        jfmChatRoom.setBounds(100, 100, 930, 768);
        jfmChatRoom.setMinimumSize(new Dimension(600,300));
        jfmChatRoom.setLocationRelativeTo(null);
        jfmChatRoom.setTitle("聊天室");
        jfmChatRoom.setFont(defaultFont);
        jfmChatRoom.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JLabel topTitle = new JLabel("聊天室",JLabel.CENTER);
        topTitle.setFont(topticFont);
        topTitle.setForeground(topticColor);
        jfmChatRoom.add(topTitle,BorderLayout.NORTH);

        JLabel jlblBlankWest = new JLabel(" ");
        jlblBlankWest.setFont(defaultFont);
        jfmChatRoom.add(jlblBlankWest, BorderLayout.WEST);

        JLabel jlblBlankEast = new JLabel(" ");
        jfmChatRoom.add(jlblBlankEast, BorderLayout.EAST);


        JPanel jplBody  = new JPanel();
        GridBagLayout gbl_jplBody = new GridBagLayout();
        gbl_jplBody.columnWidths = new int[] {628, 247};
        gbl_jplBody.rowHeights = new int[] {29, 270, 191, 39};
        gbl_jplBody.columnWeights = new double[]{1.0, 1.0};
        gbl_jplBody.rowWeights = new double[]{1.0, 1.0, 1.0, 1.0};
        jplBody.setLayout(gbl_jplBody);
        jfmChatRoom.add(jplBody, BorderLayout.CENTER);

        JPanel jplUserInfor = new JPanel();
        GridBagConstraints gbc_jplUserInfor = new GridBagConstraints();
        gbc_jplUserInfor.weighty = 1.0;
        gbc_jplUserInfor.weightx = 100.0;
        gbc_jplUserInfor.anchor = GridBagConstraints.WEST;
        gbc_jplUserInfor.insets = new Insets(0, 0, 5, 5);
        gbc_jplUserInfor.fill = GridBagConstraints.VERTICAL;
        gbc_jplUserInfor.gridx = 0;
        gbc_jplUserInfor.gridy = 0;
        jplBody.add(jplUserInfor,gbc_jplUserInfor);

        JLabel jlbUserName = new JLabel("欢迎: ");
        jlbUserName.setFont(boldFont);
        jlbUserName.setForeground(Color.RED);
        jplUserInfor.add(jlbUserName);

        jlbNick = new JLabel();
        jlbNick.setFont(boldFont);
        jplUserInfor.add(jlbNick);


        JPanel jplCurFriend = new JPanel();
        GridBagConstraints gbc_jplCurFriend = new GridBagConstraints();
        gbc_jplCurFriend.weightx = 1.0;
        gbc_jplCurFriend.insets = new Insets(0, 0, 5, 0);
        gbc_jplCurFriend.fill = GridBagConstraints.BOTH;
        gbc_jplCurFriend.gridx = 1;
        gbc_jplCurFriend.gridy = 0;
        jplBody.add(jplCurFriend,gbc_jplCurFriend);

        JLabel jlbCurName = new JLabel("当前好友：");
        jlbCurName.setFont(boldFont);
        jlbCurName.setForeground(Color.BLUE);
        jplCurFriend.add(jlbCurName);

        jlbCurNick = new JLabel();
        jlbCurNick.setFont(boldFont);
        jplCurFriend.add(jlbCurNick);


        TitledBorder ttdbChatArea = new TitledBorder("消息区");
        ttdbChatArea.setTitleFont(defaultFont);
        ttdbChatArea.setTitlePosition(TitledBorder.ABOVE_TOP);

        jtxtChatArea = new JTextArea();
        jtxtChatArea.setFont(defaultFont);

        JScrollPane jspChatArea = new JScrollPane(jtxtChatArea);
        GridBagConstraints gbc_jspChatArea = new GridBagConstraints();
        gbc_jspChatArea.weighty = 100.0;
        gbc_jspChatArea.weightx = 100.0;
        gbc_jspChatArea.insets = new Insets(0, 0, 5, 5);
        gbc_jspChatArea.fill = GridBagConstraints.BOTH;
        gbc_jspChatArea.gridx = 0;
        gbc_jspChatArea.gridy = 1;
        jspChatArea.setBorder(ttdbChatArea);
        jplBody.add(jspChatArea, gbc_jspChatArea);

        TitledBorder ttdbYouSay = new TitledBorder("编辑区");
        ttdbYouSay.setTitleFont(defaultFont);
        ttdbYouSay.setTitlePosition(TitledBorder.ABOVE_TOP);

        jtxtYouSay = new JTextArea();
        jtxtYouSay.setFont(defaultFont);

        JScrollPane jspYouSay = new JScrollPane();
        jspYouSay.setViewportView(jtxtYouSay);
        GridBagConstraints gbc_jspYouSay = new GridBagConstraints();
        gbc_jspYouSay.weighty = 1.0;
        gbc_jspYouSay.weightx = 100.0;
        gbc_jspYouSay.insets = new Insets(0, 0, 5, 5);
        gbc_jspYouSay.fill = GridBagConstraints.BOTH;
        gbc_jspYouSay.gridx = 0;
        gbc_jspYouSay.gridy = 2;
        jspYouSay.setBorder(ttdbYouSay);
        jplBody.add(jspYouSay,gbc_jspYouSay);

        JPanel jplButtons = new JPanel();
        GridBagConstraints gbc_jplButtons = new GridBagConstraints();
        gbc_jplButtons.weighty = 1.0;
        gbc_jplButtons.weightx = 100.0;
        gbc_jplButtons.anchor = GridBagConstraints.SOUTHEAST;
        gbc_jplButtons.insets = new Insets(0, 0, 0, 5);
        gbc_jplButtons.gridx = 0;
        gbc_jplButtons.gridy = 3;
        jplBody.add(jplButtons,gbc_jplButtons);

        jbtExit = new JButton("关闭");
        jbtExit.setFont(defaultFont);
        jplButtons.add(jbtExit);

        jbtSend = new JButton("发送");
        jbtSend.setFont(defaultFont);
        jplButtons.add(jbtSend);

        dlmFriendList = new DefaultListModel<UserInfo>();
        jlstFriendList = new JList<UserInfo>(dlmFriendList);
        jlstFriendList.setFont(minFont);

        TitledBorder ttbdFriendInfo = new TitledBorder("好友列表");
        ttbdFriendInfo.setTitleFont(defaultFont);
        ttbdFriendInfo.setTitlePosition(TitledBorder.ABOVE_TOP);
        ttbdFriendInfo.setTitleJustification(TitledBorder.CENTER);

        JScrollPane jscpFriendList = new JScrollPane(jlstFriendList);
        jscpFriendList.setBorder(ttbdFriendInfo);
        GridBagConstraints gbc_jspFriendList = new GridBagConstraints();
        gbc_jspFriendList.weighty = 100.0;
        gbc_jspFriendList.weightx = 1.0;
        gbc_jspFriendList.insets = new Insets(0, 0, 5, 0);
        gbc_jspFriendList.fill = GridBagConstraints.BOTH;
        gbc_jspFriendList.gridx = 1;
        gbc_jspFriendList.gridy = 1;
        jplBody.add(jscpFriendList,gbc_jspFriendList);


        TitledBorder ttbdSystemInfo = new TitledBorder("系统消息");
        ttbdSystemInfo.setTitleFont(defaultFont);
        ttbdSystemInfo.setTitlePosition(TitledBorder.ABOVE_TOP);
        ttbdSystemInfo.setTitleJustification(TitledBorder.CENTER);
        ttbdSystemInfo.setTitleColor(Color.RED);

        jtxtSystemInfo = new JTextArea();
        jtxtSystemInfo.setFont(minFont);

        JScrollPane jspSystemInfo = new JScrollPane(jtxtSystemInfo);
        GridBagConstraints gbc_jspSystemInfo = new GridBagConstraints();
        gbc_jspSystemInfo.weighty = 1.0;
        gbc_jspSystemInfo.weightx = 1.0;
        gbc_jspSystemInfo.gridheight = 2;
        gbc_jspSystemInfo.fill = GridBagConstraints.BOTH;
        gbc_jspSystemInfo.gridx = 1;
        gbc_jspSystemInfo.gridy = 2;

        jspSystemInfo.setBorder(ttbdSystemInfo);
        jplBody.add(jspSystemInfo,gbc_jspSystemInfo);
    }

    @Override
    public JFrame getJFrame() {
        return jfmChatRoom;
    }

    //	public static void main(String[] args) {
//		try {
//			new ChatRoom().showView();
//		} catch (FrameIsNullException e) {
//			 Auto-generated catch block
//			e.printStackTrace();
//		}
//	}
    class ClientAction extends ClientActionProcessAdpter {
        @Override
        public void dealTalkToOthers(String sourceId, String message) {
            AppMessage appMessage = (AppMessage) ArgumentMaker.fromJson(message, AppMessage.class);
            UserInfo sourceUser = userPool.getUser(sourceId);
            switch (appMessage.getCommond()) {
                case I_AM_COMMING:
                    UserInfo user = (UserInfo) ArgumentMaker.fromJson(appMessage.getParameter(), UserInfo.class);
                    dlmFriendList.addElement(user);
                    userPool.addUser(user);
                    jtxtSystemInfo.append("好友 [" + user.getNick() + "]上线!\n");
                    jtxtSystemInfo.setCaretPosition(jtxtSystemInfo.getText().length());

                    AppMessage appMessage1 = new AppMessage().setCommond(EAppCommond.I_AM_HERE)
                            .setParameter(ArgumentMaker.toJson(userInfo));
                    client.talkToOne(sourceId, ArgumentMaker.toJson(appMessage1));
                    break;
                case I_AM_GONE:
                    jtxtSystemInfo.append("好友 [" + sourceUser + "]下线!\n");
                    jtxtSystemInfo.setCaretPosition(jtxtSystemInfo.getText().length());
                    if (jlstFriendList.isSelectedIndex(dlmFriendList.indexOf(sourceUser))) {
                        jlstFriendList.setSelectedIndex(0);
                    }
                    dlmFriendList.removeElement(sourceUser);
                    userPool.removeUser(sourceId);
                    break;
                case TO_OTHERS:
                    jtxtChatArea.append("[" + sourceUser.getNick() + "]对大家说:" + appMessage.getParameter() + "\n");
                    jtxtChatArea.setCaretPosition(jtxtChatArea.getText().length());
                    break;
                default:
                    break;
            }
        }

        @Override
        public void dealTalkToOne(String sourceId, String message) {
            AppMessage appMessage = (AppMessage) ArgumentMaker.fromJson(message, AppMessage.class);
            switch (appMessage.getCommond()) {
                case I_AM_HERE:
                    UserInfo user = (UserInfo) ArgumentMaker.fromJson(appMessage.getParameter(), UserInfo.class);
                    userPool.addUser(user);
                    dlmFriendList.addElement(user);
                    break;
                case TO_ONE:
                    UserInfo sourceUser = userPool.getUser(sourceId);
                    jtxtChatArea.append("[" + sourceUser.getNick() + "]对你说:" + appMessage.getParameter() + "\n");
                    jtxtChatArea.setCaretPosition(jtxtChatArea.getText().length());
                    break;
                default:
                    break;
            }

        }

        @Override
        public void offLine() {
            AppMessage appMessage = new AppMessage()
                    .setCommond(EAppCommond.I_AM_GONE);
            client.toOthers(ArgumentMaker.toJson(appMessage));
        }

        @Override
        public boolean confirmOffline() {
            int choice = ViewTool.getChoice(jfmChatRoom, "您真的退出?", JOptionPane.YES_NO_OPTION);

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
        public void serverForceDrop() {
            try {
                ViewTool.showErrorMessage(jfmChatRoom, "服务器强制宕机！连接终止");
                exitView();
            } catch (FrameIsNullException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void serverPeerDrop() {
            try {
                ViewTool.showErrorMessage(jfmChatRoom, "服务器宕机！连接终止");
                exitView();
            } catch (FrameIsNullException e) {
                e.printStackTrace();
            }
        }
    }
}
