package com.vehicleservice.thread;

import com.vehicleservice.dao.DatabaseManager;
import com.vehicleservice.model.ServiceAppointment;
import com.vehicleservice.model.ServiceStatus;

/**
 * Background Daemon Thread demonstrating Multithreading concepts (Unit 3).
 * Periodically monitors active workshop appointments, bays, and warns of backlog.
 */
public class WorkloadMonitorThread implements Runnable {
    private final DatabaseManager db = DatabaseManager.getInstance();
    private volatile boolean running = true;
    private final int intervalMillis;

    public WorkloadMonitorThread(int intervalMillis) {
        this.intervalMillis = intervalMillis;
    }

    public void stopMonitoring() {
        this.running = false;
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(intervalMillis);
                long pendingJobs = db.getAllAppointments().stream()
                        .filter(a -> a.getStatus() == ServiceStatus.SCHEDULED)
                        .count();

                if (pendingJobs > 0) {
                    System.out.printf("%n[BACKGROUND MONITOR (Thread: %s | Priority: %d)] Alert: %d pending service appointments queued in workshop.%n",
                            Thread.currentThread().getName(), Thread.currentThread().getPriority(), pendingJobs);
                }
            } catch (InterruptedException e) {
                // Thread interrupted on shutdown
                break;
            }
        }
    }
}
