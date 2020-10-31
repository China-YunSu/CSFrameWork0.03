package edu.xupt.cs.test;

import com.mec.dataBase.core.MECDataBase;
import com.mec.util.FrameIsNullException;
import edu.xupt.cs.factory.xml.XMLActionBeanFactory;
import edu.xupt.cs.netWork.view.server.ServerView;

import java.io.IOException;
import java.sql.SQLException;

public class DemoServer {


    public static void main(String[] args) throws IOException, SQLException {

        MECDataBase.loadMECDataBaseConfigure("/config/TableMapping.properties");
        XMLActionBeanFactory xmlActionBeanFactory = new XMLActionBeanFactory();

        xmlActionBeanFactory.scannActionMapping("/config/server_action_mapping.xml");
        try {
            new ServerView(8806,xmlActionBeanFactory).showView();
        } catch (FrameIsNullException e) {
            e.printStackTrace();
        }
    }
}