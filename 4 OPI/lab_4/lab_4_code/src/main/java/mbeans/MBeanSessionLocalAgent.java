package mbeans;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.context.Initialized;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.inject.Named;

import javax.management.MBeanServer;
import javax.management.ObjectName;
import java.lang.management.ManagementFactory;


@Named
@ApplicationScoped
public class MBeanSessionLocalAgent {

    @Inject
    private Count countMBean;

    @Inject
    private AverageClickInterval averageClickInterval;

    private static final MBeanServer server;

    static {
        server = ManagementFactory.getPlatformMBeanServer();
    }

    @PostConstruct
    public void initAgentCount() {
        ObjectName countmBean;
        ObjectName averageClickIntervalMBean;
        try {
            countmBean = new ObjectName("mbeans:name=MBeanCount");
            averageClickIntervalMBean = new ObjectName("mbeans:name=MBeanAverageClickInterval");
            if (!server.isRegistered(countmBean)) {
                server.registerMBean(countMBean, countmBean);
            }
            if (!server.isRegistered(averageClickIntervalMBean)) {
                server.registerMBean(averageClickInterval, averageClickIntervalMBean);
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void logSimpleAgentStarted() {
        System.out.println("CountMBean.logSimpleAgentStarted");
        System.out.println("AverageClickInterval.logSimpleAgentStarted");
    }

    public void startupCount(@Observes @Initialized(ApplicationScoped.class) Object context) {
        MBeanSessionLocalAgent a = new MBeanSessionLocalAgent();
        a.logSimpleAgentStarted();
    }


}