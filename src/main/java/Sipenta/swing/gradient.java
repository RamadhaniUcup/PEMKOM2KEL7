package Sipenta.swing;

import javax.swing.*;
import java.awt.*;

public class gradient extends JPanel {

    public gradient() {
        setOpaque(false);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int width = getWidth();
        int height = getHeight();

        // titik gradasi (smooth)
        float[] dist = {0.0f, 0.5f, 1.0f};

        // warna sesuai UI kamu (navy → sedikit lebih terang → putih halus)
           Color[] colors = {
            new Color(21, 19, 71),
            new Color(35, 33, 90),
            new Color(21, 19, 71)
        };

        // gradient horizontal (kiri ke kanan)
        LinearGradientPaint gradient = new LinearGradientPaint(
                0, 0,
                width, 0,
                dist,
                colors
        );

        g2.setPaint(gradient);
        g2.fillRect(0, 0, width, height);

        g2.dispose();
    }
}
