package mbeans;

import javax.management.*;

import jakarta.ejb.Schedule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

@Named
@ApplicationScoped
public class AverageClickInterval implements NotificationBroadcaster, AverageClickIntervalMBean, Serializable {
    private Queue<Long> clickTimes = new LinkedList<>();
    NotificationBroadcasterSupport broadcaster = new NotificationBroadcasterSupport();
    private static final int MAX_RECORDS = 10;

    @Schedule(second = "*/5", minute = "*", hour = "*")
    public void updateMetrics() {
        getAverageInterval();
        recordClick();
    }

    @Override
    public double getAverageInterval() {
        if (clickTimes.size() < 2) return 0.0;
        Long[] times = clickTimes.toArray(new Long[0]);
        long total = 0;
        for (int i = 1; i < times.length; i++) {
            total += (times[i] - times[i-1]);
        }
        return total / (double)(times.length - 1);
    }

    @Override
    public void recordClick() {
        clickTimes.add(System.currentTimeMillis());
        if (clickTimes.size() > MAX_RECORDS) {
            clickTimes.poll();
        }
    }

    @Override
    public void addNotificationListener(NotificationListener listener, NotificationFilter filter, Object handback) throws IllegalArgumentException {
        broadcaster.addNotificationListener(listener, filter, handback);
    }

    @Override
    public void removeNotificationListener(NotificationListener listener) throws ListenerNotFoundException {
        broadcaster.removeNotificationListener(listener);
    }

    @Override
    public MBeanNotificationInfo[] getNotificationInfo() {
        String[] types = new String[] { "consecutive.average.click.interval" };
        String name = Notification.class.getName();
        String description = "Notification sent when 5 consecutive try are recorded";
        return new MBeanNotificationInfo[] { new MBeanNotificationInfo(types, name, description) };
    }

}