/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.object;

import Sipenta.Services.I18nService;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;

/**
 * Toggle Button Modern
 * False = Masuk
 * True  = Keluar
 *
 * @author Yusuf
 */
public class ToggleButton extends JToggleButton {

    // =========================
    // COLOR THEME
    // =========================

    private final Color COLOR_MASUK = new Color(25, 135, 84);
    private final Color COLOR_MASUK_HOVER = new Color(21, 115, 71);

    private final Color COLOR_KELUAR = new Color(220, 53, 69);
    private final Color COLOR_KELUAR_HOVER = new Color(187, 45, 59);

    private final Color BORDER_COLOR = new Color(255, 255, 255, 60);

    private final int CORNER_RADIUS = 10;

    private boolean hover = false;

    public ToggleButton() {

        super();

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setForeground(Color.WHITE);
        setFont(new Font("SansSerif", Font.BOLD, 14));

        updateText();

        addActionListener(e -> {
            updateText();
            repaint();
        });

        addMouseListener(new MouseAdapter() {

            @Override
            public void mouseEntered(MouseEvent e) {
                hover = true;
                repaint();
            }

            @Override
            public void mouseExited(MouseEvent e) {
                hover = false;
                repaint();
            }

        });
    }

    /**
     * Mengubah tulisan sesuai bahasa aktif
     */
    private void updateText() {

        if (isSelected()) {
            setText(I18nService.get("ui.absensi.status.out"));
        } else {
            setText(I18nService.get("ui.absensi.status.in"));
        }

    }

    /**
     * Dipanggil ketika bahasa berubah
     */
    public void refreshLanguage() {
        updateText();
        repaint();
    }

    /**
     * Mengambil status saat ini
     */
    public String getStatusString() {
        return isSelected()
                ? I18nService.get("ui.absensi.status.out")
                : I18nService.get("ui.absensi.status.in");
    }

    /**
     * Mengubah status berdasarkan String
     */
    public void setStatusByString(String status) {

        if (status == null)
            return;

        if (status.equalsIgnoreCase(I18nService.get("ui.absensi.status.out"))) {
            setSelected(true);
        } else {
            setSelected(false);
        }

        updateText();
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        Color bg;

        if (isSelected()) {
            bg = hover ? COLOR_KELUAR_HOVER : COLOR_KELUAR;
        } else {
            bg = hover ? COLOR_MASUK_HOVER : COLOR_MASUK;
        }

        g2.setColor(bg);
        g2.fillRoundRect(
                0,
                0,
                getWidth(),
                getHeight(),
                CORNER_RADIUS,
                CORNER_RADIUS);

        // Border tipis
        g2.setStroke(new BasicStroke(1f));
        g2.setColor(BORDER_COLOR);
        g2.drawRoundRect(
                0,
                0,
                getWidth() - 1,
                getHeight() - 1,
                CORNER_RADIUS,
                CORNER_RADIUS);

        g2.dispose();

        super.paintComponent(g);
    }

}