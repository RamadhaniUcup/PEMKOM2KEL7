/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.Services;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class DigitalClockService implements Runnable {

    private JLabel labelTarget;
    private String formatWaktu;
    private Thread thread;
    private volatile boolean running = true;

    public DigitalClockService() {
        this.formatWaktu = "HH:mm:ss";
    }

    public DigitalClockService(JLabel labelTarget, String formatWaktu) {
        this.labelTarget = labelTarget;
        this.formatWaktu = formatWaktu;
        this.thread = new Thread(this);
    }

    public Thread getThread() {
        if (thread == null) {
            thread = new Thread(this);
        }
        return thread;
    }

    public void stopClock() {
        running = false;
        if (thread != null) {
            thread.interrupt();
        }
    }

    @Override
    public void run() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                formatWaktu,
                new Locale("id", "ID")
        );

        while (running) {
            String waktuSekarang = LocalDateTime.now().format(formatter);

            if (labelTarget != null) {
                SwingUtilities.invokeLater(() -> {
                    labelTarget.setText(waktuSekarang);
                });
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }
}
