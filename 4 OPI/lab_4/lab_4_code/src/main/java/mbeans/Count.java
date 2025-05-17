package mbeans;

import javax.management.*;
import java.io.Serializable;
import java.util.List;

import bd.PointsBDManager;
import jakarta.ejb.Schedule;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Named;
import point.Point;

@Named
@ApplicationScoped
public class Count implements NotificationBroadcaster, CountMBean, Serializable {

    private final PointsBDManager pointsBDManager = new PointsBDManager();
    private final NotificationBroadcasterSupport broadcaster = new NotificationBroadcasterSupport();
    private long sequenceNumber = 0;

    @Schedule(second = "*/5", minute = "*", hour = "*")
    public void updateMetrics() {
        getNumberOfAllPoints();
        getNumberOfFailedPoints();
    }

    @Override
    public long getNumberOfAllPoints() {
        List<Point> points = pointsBDManager.getAllPoints();
        long count = 0;
        for (Point point : points) {
            if (point.getX() < 0) count++;
        }
        if (count % 5 == 0 && count != 0) {
            broadcaster.sendNotification(new Notification(
                    "Points multiple of 5", this.getClass().getName(), sequenceNumber++,
                    "Total points: " + count));
        }
        return count;
    }

    @Override
    public long getNumberOfFailedPoints() {
        return pointsBDManager.getFailedPoints().size();
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
        String[] types = new String[] { "consecutive.try" };
        String name = Notification.class.getName();
        String description = "Notification sent when 5 consecutive try are recorded";
        return new MBeanNotificationInfo[] { new MBeanNotificationInfo(types, name, description) };
    }
}