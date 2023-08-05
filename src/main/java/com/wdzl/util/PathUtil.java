//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wdzl.util;

import java.net.URL;

public class PathUtil {
    public PathUtil() {
    }

    public static String getRootPath(String dir) {
        URL url = PathUtil.class.getClassLoader().getResource(dir);
        if (url == null) {
            throw new RuntimeException("没有发现" + dir);
        } else {
            return url.getPath();
        }
    }

    public static String getFilePath(String filename) {
        URL url = PathUtil.class.getClassLoader().getResource(filename);
        if (url == null) {
            throw new RuntimeException("没有发现" + filename);
        } else {
            return url.getPath();
        }
    }
}
