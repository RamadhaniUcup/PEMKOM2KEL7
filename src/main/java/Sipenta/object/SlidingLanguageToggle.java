package Sipenta.object;

import Sipenta.Services.I18nService;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JToggleButton;

/**
 * Sliding Toggle 3 Bahasa
 * Indonesia - English - 日本語
 */
public class SlidingLanguageToggle extends JToggleButton {

    // ==========================
    // WARNA
    // ==========================

    private final Color COLOR_BG = new Color(39,45,54);
    private final Color COLOR_ACTIVE = new Color(30,144,255);
    private final Color COLOR_TEXT = Color.WHITE;
    private final Color COLOR_TEXT_INACTIVE = new Color(160,160,160);

    private final int radius = 24;

    /**
     * 0 = Indonesia
     * 1 = English
     * 2 = Japanese
     */
    private int selectedIndex = 0;

    public SlidingLanguageToggle() {

        setOpaque(false);
        setBorderPainted(false);
        setContentAreaFilled(false);
        setFocusPainted(false);

        setCursor(new Cursor(Cursor.HAND_CURSOR));
        setFont(new Font("Segoe UI", Font.BOLD, 13));

        addMouseListener(new MouseAdapter() {

            @Override
            public void mousePressed(MouseEvent e) {

                int width = getWidth();

                if (e.getX() < width / 3) {

                    setSelectedLanguageIndex(0);

                } else if (e.getX() < (width / 3) * 2) {

                    setSelectedLanguageIndex(1);

                } else {

                    setSelectedLanguageIndex(2);

                }

                fireActionPerformed(null);
            }

        });

    }

    // ====================================================
    // Getter Setter
    // ====================================================

    public int getSelectedLanguageIndex() {
        return selectedIndex;
    }

    public void setSelectedLanguageIndex(int index) {

        selectedIndex = index;

        switch (index) {

            case 0 ->
                I18nService.setLocale(java.util.Locale.of("id"));

            case 1 ->
                I18nService.setLocale(java.util.Locale.ENGLISH);

            case 2 ->
                I18nService.setLocale(java.util.Locale.JAPANESE);

        }

        repaint();
    }

    public void setSelectedLanguageIndexByString(String lang) {

        switch (lang) {

            case "id" ->
                selectedIndex = 0;

            case "en" ->
                selectedIndex = 1;

            case "ja" ->
                selectedIndex = 2;

            default ->
                selectedIndex = 0;

        }

        repaint();
    }

    public String getSelectedLanguageString() {

        return switch (selectedIndex) {
            case 1 ->
                "en";
            case 2 ->
                "ja";
            default ->
                "id";
        };

    }

    // ====================================================
    // PAINT
    // ====================================================

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

        int margin = 5;

        int sliderWidth = (w / 3) - margin - 2;

        int sliderHeight = h - margin * 2;

        // Background

        g2.setColor(COLOR_BG);
        g2.fillRoundRect(0, 0, w, h, radius, radius);

        // Slider

        int sliderX;

        if (selectedIndex == 0) {

            sliderX = margin;

        } else if (selectedIndex == 1) {

            sliderX = (w / 3) + 2;

        } else {

            sliderX = (w / 3) * 2;

        }

        g2.setColor(COLOR_ACTIVE);
        g2.fillRoundRect(
                sliderX,
                margin,
                sliderWidth,
                sliderHeight,
                radius - 6,
                radius - 6);

        FontMetrics fm = g2.getFontMetrics();

        int textY = h / 2 + fm.getAscent() / 2 - 2;

        String[] labels = {
            I18nService.get("ui.lang.id"),
            I18nService.get("ui.lang.en"),
            I18nService.get("ui.lang.ja")
        };

        for (int i = 0; i < labels.length; i++) {

            int centerX = (w / 6) + (i * (w / 3));

            int textX = centerX - fm.stringWidth(labels[i]) / 2;

            if (i == selectedIndex) {

                g2.setColor(COLOR_TEXT);

            } else {

                g2.setColor(COLOR_TEXT_INACTIVE);

            }

            g2.drawString(labels[i], textX, textY);

        }

        g2.dispose();

    }

}