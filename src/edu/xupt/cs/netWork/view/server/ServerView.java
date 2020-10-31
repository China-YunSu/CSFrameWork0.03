package edu.xupt.cs.netWork.view.server;

import com.mec.util.FrameIsNullException;
import com.mec.util.IMecView;
import com.mec.util.ViewTool;
import edu.xupt.cs.action.abstract_.ActionBeanFactory;
import edu.xupt.cs.action.processer.ActionProcess;
import edu.xupt.cs.core.IListener;
import edu.xupt.cs.core.Server;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ServerView implements IMecView, IListener {
    JFrame jfmServerView;
    JTextArea jttaMessage;
    JTextField jtxtCmd;
    Server server;

    public ServerView(int port,ActionBeanFactory factory) {
        initView();
        server = new Server();
        server.setActionProcess(new ActionProcess(factory));
        server.addListener(this);
    }

    //	public void initServer(String configPath) {
//		server.initServer(configPath);
//	}
//
    private void closeServer() {
        if (server.isStartUp()) {
            ViewTool.showWarnningMessage(jfmServerView, "未关闭服务器");
        } else {
            try {
                exitView();
            } catch (FrameIsNullException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void dealEvent() {

        jfmServerView.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                closeServer();
            }

        });

        jtxtCmd.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                String message = jtxtCmd.getText();
                if (message.length() <= 0) {
                    return;
                }
                dealMessageToAction(message);
                jtxtCmd.setText("");
            }

            private void dealMessageToAction(String message) {
                if (message.equalsIgnoreCase("st")) {
                    server.openServer();
                } else if (message.equalsIgnoreCase("fd")) {
                    server.forceDown();
                } else if (message.equalsIgnoreCase("sd")) {
                    server.shutDown();
                } else if (message.equalsIgnoreCase("exit")) {
                    try {
                        if (!server.isStartUp()) {
                            exitView();
                            return;
                        }
                        server.publishMessage("未关闭服务器！");
                    } catch (FrameIsNullException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void reinit() {

    }

    @Override
    public void init() {
        jfmServerView = new JFrame();
        jfmServerView.setSize(new Dimension(1000,700));
        jfmServerView.setLocationRelativeTo(null);
        jfmServerView.setTitle("服务器");
        jfmServerView.setFont(defaultFont);
        jfmServerView.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        /* 顶部  */
        JLabel topTitle = new JLabel("聊天室服务器界面",JLabel.CENTER);
        topTitle.setFont(topticFont);
        topTitle.setForeground(topticColor);
        jfmServerView.add(topTitle,BorderLayout.NORTH);

        /*右边界*/
        JLabel jlbEastBlank = new JLabel(" ");
        jfmServerView.add(jlbEastBlank,BorderLayout.EAST);
        jlbEastBlank.setFont(defaultFont);

        /*左边界*/
        JLabel jlbWestBlank = new JLabel(" ");
        jfmServerView.add(jlbWestBlank,BorderLayout.WEST);
        jlbWestBlank.setFont(defaultFont);

        /*中部*/

        jttaMessage = new JTextArea();
        jttaMessage.setFont(defaultFont);
        jttaMessage.setEditable(false);
        jttaMessage.setFocusable(false);

        JScrollPane jspMessage = new JScrollPane(jttaMessage);
        jfmServerView.add(jspMessage,BorderLayout.CENTER);


        /*底部*/
        JPanel jplCmd = new JPanel();
        jfmServerView.add(jplCmd,BorderLayout.SOUTH);

        JLabel jlbCmdName = new JLabel("命 令 :");
        jplCmd.add(jlbCmdName);
        jlbCmdName.setFont(defaultFont);

        jtxtCmd = new JTextField(textFieldLength);
        jplCmd.add(jtxtCmd);
        jtxtCmd.setFont(defaultFont);

    }

    @Override
    public JFrame getJFrame() {
        return jfmServerView;
    }

    @Override
    public void processMessage(String message) {
        jttaMessage.append(message + "\n");
        jttaMessage.setCaretPosition(jttaMessage.getText().length());

    }
}
