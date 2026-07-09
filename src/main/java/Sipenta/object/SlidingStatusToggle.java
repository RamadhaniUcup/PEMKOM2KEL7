/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Sipenta.object;

import Sipenta.Services.I18nService;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JToggleButton;

/**
 * Sliding Toggle Status Absensi
 * False = Masuk
 * True  = Keluar
 *
 * @author Yusuf
 */
public class SlidingStatusToggle extends JToggleButton {

    // =========================
    // COLOR THEME
    // =========================
    private final Color COLOR_BG = new Color(39, 45, 54);

    private final Color COLOR_MASUK = new Color(25, 135, 84);
    private final Color COLOR_KELUAR = new Color(220, 53, 69);

    private final Color COLOR_TEXT_ACTIVE = Color.WHITE;
    private final Color COLOR_TEXT_INACTIVE = new Color(140, 140, 140);

    private final int CORNER_RADIUS = 24;

    public SlidingStatusToggle() {

        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setOpaque(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(new Font("SansSerif", Font.BOLD, 13));

        addActionListener(e -> repaint());
    }

    /**
     * Mengembalikan status dalam String sesuai bahasa aktif
     */
    public String getStatusString() {
        if (isSelected()) {
            return I18nService.get("ui.absensi.status.out");
        } else {
            return I18nService.get("ui.absensi.status.in");
        }
    }

    /**
     * Mengubah posisi toggle berdasarkan String
     */
    public void setStatusByString(String status) {

        if (status == null) {
            return;
        }

        String masuk = I18nService.get("ui.absensi.status.in");
        String keluar = I18nService.get("ui.absensi.status.out");

        if (status.equalsIgnoreCase(keluar)) {
            setSelected(true);
        } else if (status.equalsIgnoreCase(masuk)) {
            setSelected(false);
        }

        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2 = (Graphics2D) g.create();

        g2.setRenderingHint(
                RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setRenderingHint(
                RenderingHints.KEY_TEXT_ANTIALIASING,
                RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        int w = getWidth();
        int h = getHeight();

        int margin = 4;

        int sliderWidth = (w / 2) - margin;
        int sliderHeight = h - (margin * 2);

        // Background
        g2.setColor(COLOR_BG);
        g2.fillRoundRect(0, 0, w, h, CORNER_RADIUS, CORNER_RADIUS);

        // Slider
        int sliderX;

        if (isSelected()) {
            sliderX = w / 2;
            g2.setColor(COLOR_KELUAR);
        } else {
            sliderX = margin;
            g2.setColor(COLOR_MASUK);
        }

        g2.fillRoundRect(
                sliderX,
                margin,
                sliderWidth,
                sliderHeight,
                CORNER_RADIUS,
                CORNER_RADIUS);

        // ==========================
        // TEXT
        // ==========================

        FontMetrics fm = g2.getFontMetrics();

        int textY = (h / 2)
                + (fm.getAscent() / 2)
                - 2;

        String masuk = I18nService.get("ui.absensi.status.in");
        String keluar = I18nService.get("ui.absensi.status.out");

        // LEFT
        int leftX = (w / 4) - (fm.stringWidth(masuk) / 2);

        if (!isSelected()) {
            g2.setColor(COLOR_TEXT_ACTIVE);
        } else {
            g2.setColor(COLOR_TEXT_INACTIVE);
        }

        g2.drawString(masuk, leftX, textY);

        // RIGHT
        int rightX = ((w * 3) / 4) - (fm.stringWidth(keluar) / 2);

        if (isSelected()) {
            g2.setColor(COLOR_TEXT_ACTIVE);
        } else {
            g2.setColor(COLOR_TEXT_INACTIVE);
        }

        g2.drawString(keluar, rightX, textY);

        g2.dispose();
    }
}