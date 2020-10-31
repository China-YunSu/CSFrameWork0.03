package edu.xupt.cs.test;

import com.mec.util.FrameIsNullException;
import edu.xupt.cs.factory.anno.AnnoActionBeanFactory;
import edu.xupt.cs.core.Client;
import edu.xupt.cs.netWork.view.client.ClientConectionView;

public class DemoClient {
    public static void main(String[] args) throws FrameIsNullException {
        AnnoActionBeanFactory annoActionBeanFactory = new AnnoActionBeanFactory();
        annoActionBeanFactory.scannActionMapping("edu.xupt.cs.netWork.view.client");
        Client.loadNetConfigure("/config/NetConfigure.properties");
        new ClientConectionView(annoActionBeanFactory).showView();
    }
}
