//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wdzl.util;

import java.awt.Font;
import java.awt.font.FontRenderContext;
import java.awt.geom.AffineTransform;

public class FontUtil {
    private static AffineTransform affineTransform = new AffineTransform();
    private static FontRenderContext fontRenderContext;

    public FontUtil() {
    }

    public static int getStringHeight(String str, Font font) {
        return str != null && !str.isEmpty() && font != null ? (int)font.getStringBounds(str, fontRenderContext).getHeight() : 0;
    }

    public static int getStringWidth(String str, Font font) {
        return str != null && !str.isEmpty() && font != null ? (int)font.getStringBounds(str, fontRenderContext).getWidth() : 0;
    }

    static {
        fontRenderContext = new FontRenderContext(affineTransform, true, true);
    }
}
