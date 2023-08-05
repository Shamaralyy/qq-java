//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wdzl.util;

import java.awt.Graphics;
import java.awt.image.ImageObserver;
import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class BackgroundPanel extends JPanel {
    private ImageIcon bgImg;

    public BackgroundPanel(ImageIcon bgImg) {
        this.bgImg = bgImg;
    }

    protected void paintComponent(Graphics g) {
        g.drawImage(this.bgImg.getImage(), 0, 0, this.getWidth(), this.getHeight(), (ImageObserver)null);
    }
}
