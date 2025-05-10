package mbeans;

public interface AverageClickIntervalMBean {
    double getAverageInterval();
    void recordClick();
}