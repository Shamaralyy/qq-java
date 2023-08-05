//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.wdzl.util;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Insets;
import java.awt.LayoutManager;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.LineBorder;

public class VerticalFlowLayout implements LayoutManager, Serializable {
    private static final long serialVersionUID = 1L;
    public static final int CENTER = 0;
    public static final int TOP = 1;
    public static final int BOTTOM = 2;
    public static final int LEFT = 3;
    public static final int RIGHT = 4;
    private int hAlign;
    private int vAlign;
    private int hPadding;
    private int vPadding;
    private int hGap;
    private int vGap;
    private boolean fill;
    private boolean wrap;

    public VerticalFlowLayout(int hAlign, int vAlign, int hPadding, int vPadding, int hGap, int vGap, boolean fill, boolean wrap) {
        this.hAlign = hAlign;
        this.vAlign = vAlign;
        this.hPadding = hPadding;
        this.vPadding = vPadding;
        this.hGap = hGap;
        this.vGap = vGap;
        this.fill = fill;
        this.wrap = wrap;
    }

    public VerticalFlowLayout() {
        this(3, 1, 5, 5, 5, 5, true, false);
    }

    public VerticalFlowLayout(int padding, int gap) {
        this(3, 1, padding, padding, gap, gap, true, false);
    }

    public VerticalFlowLayout(int padding) {
        this(3, 1, padding, padding, 5, 5, true, false);
    }

    public int getHAlign() {
        return this.hAlign;
    }

    public void setHAlign(int hAlign) {
        this.hAlign = hAlign;
    }

    public int getVAlign() {
        return this.vAlign;
    }

    public void setVAlign(int vAlign) {
        this.vAlign = vAlign;
    }

    public int getHPadding() {
        return this.hPadding;
    }

    public void setHPadding(int hPadding) {
        this.hPadding = hPadding;
    }

    public int getVPadding() {
        return this.vPadding;
    }

    public void setVPadding(int vPadding) {
        this.vPadding = vPadding;
    }

    public int getHGap() {
        return this.hGap;
    }

    public void setHGap(int hGap) {
        this.hGap = hGap;
    }

    public int getVGap() {
        return this.vGap;
    }

    public void setVGap(int vGap) {
        this.vGap = vGap;
    }

    public boolean isFill() {
        return this.fill;
    }

    public void setFill(boolean fill) {
        this.fill = fill;
    }

    public boolean isWrap() {
        return this.wrap;
    }

    public void setWrap(boolean wrap) {
        this.wrap = wrap;
    }

    public void addLayoutComponent(String name, Component comp) {
    }

    public void removeLayoutComponent(Component comp) {
    }

    public Dimension preferredLayoutSize(Container container) {
        synchronized(container.getTreeLock()) {
            int width = 0;
            int height = 0;
            List<Component> components = this.getVisibleComponents(container);

            Dimension dimension;
            for(Iterator var6 = components.iterator(); var6.hasNext(); height += dimension.height) {
                Component component = (Component)var6.next();
                dimension = component.getPreferredSize();
                width = Math.max(width, dimension.width);
            }

            if (0 < components.size()) {
                height += this.vGap * (components.size() - 1);
            }

            Insets insets = container.getInsets();
            width += insets.left + insets.right;
            height += insets.top + insets.bottom;
            if (0 < components.size()) {
                width += this.hPadding * 2;
                height += this.vPadding * 2;
            }

            return new Dimension(width, height);
        }
    }

    public Dimension minimumLayoutSize(Container parent) {
        synchronized(parent.getTreeLock()) {
            int width = 0;
            int height = 0;
            List<Component> components = this.getVisibleComponents(parent);

            Dimension dimension;
            for(Iterator var6 = components.iterator(); var6.hasNext(); height += dimension.height) {
                Component component = (Component)var6.next();
                dimension = component.getMinimumSize();
                width = Math.max(width, dimension.width);
            }

            if (0 < components.size()) {
                height += this.vGap * (components.size() - 1);
            }

            Insets insets = parent.getInsets();
            width += insets.left + insets.right;
            height += insets.top + insets.bottom;
            if (0 < components.size()) {
                width += this.hPadding * 2;
                height += this.vPadding * 2;
            }

            return new Dimension(width, height);
        }
    }

    public void layoutContainer(Container container) {
        synchronized(container.getTreeLock()) {
            this.preferredLayoutSize(container);
            Dimension size = container.getSize();
            Insets insets = container.getInsets();
            int availableWidth = size.width - insets.left - insets.right - this.hPadding * 2;
            int availableHeight = size.height - insets.top - insets.bottom - this.vPadding * 2;
            List<Component> components = this.getVisibleComponents(container);
            int xBase = insets.left + this.hPadding;
            List<Component> list = new LinkedList();
            Iterator var11 = components.iterator();

            while(var11.hasNext()) {
                Component component = (Component)var11.next();
                list.add(component);
                if (this.wrap && list.size() > 1 && availableHeight + this.vPadding < this.getPreferredHeight(list)) {
                    list.remove(component);
                    this.batch(insets, availableWidth, availableHeight, xBase, list, components);
                    xBase += this.hGap + this.getPreferredWidth(list);
                    list.clear();
                    list.add(component);
                }
            }

            if (!list.isEmpty()) {
                this.batch(insets, availableWidth, availableHeight, xBase, list, components);
            }

        }
    }

    private void batch(Insets insets, int availableWidth, int availableHeight, int xBase, List<Component> list, List<Component> components) {
        int preferredWidth = this.getPreferredWidth(list);
        int preferredHeight = this.getPreferredHeight(list);
        int y;
        if (this.vAlign == 1) {
            y = insets.top + this.vPadding;
        } else if (this.vAlign == 0) {
            y = (availableHeight - preferredHeight) / 2 + insets.top + this.vPadding;
        } else if (this.vAlign == 2) {
            y = availableHeight - preferredHeight + insets.top + this.vPadding;
        } else {
            y = insets.top + this.vPadding;
        }

        for(int i = 0; i < list.size(); ++i) {
            Component item = (Component)list.get(i);
            int x;
            if (this.fill) {
                x = xBase;
            } else if (this.hAlign == 3) {
                x = xBase;
            } else if (this.hAlign == 0) {
                x = xBase + (preferredWidth - item.getPreferredSize().width) / 2;
            } else if (this.hAlign == 4) {
                x = xBase + preferredWidth - item.getPreferredSize().width;
            } else {
                x = xBase;
            }

            int width;
            if (this.fill) {
                width = this.wrap ? preferredWidth : availableWidth;
                if (list.size() == components.size()) {
                    width = availableWidth;
                }
            } else {
                width = item.getPreferredSize().width;
            }

            if (i != 0) {
                y += this.vGap;
            }

            item.setBounds(x, y, width, item.getPreferredSize().height);
            y += item.getHeight();
        }

    }

    private List<Component> getVisibleComponents(Container container) {
        List<Component> list = new ArrayList();
        Component[] var3 = container.getComponents();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            Component component = var3[var5];
            if (component.isVisible()) {
                list.add(component);
            }
        }

        return list;
    }

    private int getPreferredWidth(List<Component> components) {
        int width = 0;

        Component component;
        for(Iterator var3 = components.iterator(); var3.hasNext(); width = Math.max(width, component.getPreferredSize().width)) {
            component = (Component)var3.next();
        }

        return width;
    }

    private int getPreferredHeight(List<Component> components) {
        int height = 0;

        Component component;
        for(Iterator var3 = components.iterator(); var3.hasNext(); height += component.getPreferredSize().height) {
            component = (Component)var3.next();
        }

        if (0 < components.size()) {
            height += this.vGap * (components.size() - 1);
        }

        return height;
    }

    public String toString() {
        return "VerticalFlowLayout{hAlign=" + this.hAlign + ", vAlign=" + this.vAlign + ", hPadding=" + this.hPadding + ", vPadding=" + this.vPadding + ", hGap=" + this.hGap + ", vGap=" + this.vGap + ", fill=" + this.fill + ", wrap=" + this.wrap + "}";
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("VerticalFlowLayout Test");
        frame.setDefaultCloseOperation(3);
        JPanel panel = new JPanel();
        frame.getContentPane().add(panel, "Center");
        panel.setBorder(new LineBorder(Color.white, 10));
        VerticalFlowLayout layout = new VerticalFlowLayout();
        panel.setLayout(layout);
        panel.add(new JButton("00000000000000000000000000000000000000000000000000"));
        panel.add(new JButton("1"));
        panel.add(new JButton("22"));
        panel.add(new JButton("333"));
        panel.add(new JButton("4444"));
        panel.add(new JButton("55555"));
        panel.add(new JButton("666666"));
        panel.add(new JButton("7777777"));
        panel.add(new JButton("88888888"));
        panel.add(new JButton("999999999999999999999999999999999999999999999"));
        LineBorder border = new LineBorder(Color.gray, 1);
        JLabel label = new JLabel("hello world");
        label.setBorder(border);
        panel.add(label);
        JRadioButton radioButton = new JRadioButton("select me");
        radioButton.setBorder(border);
        panel.add(radioButton);
        JCheckBox checkBox = new JCheckBox("select me");
        checkBox.setBorder(border);
        panel.add(checkBox);
        JTextField textField = new JTextField();
        textField.setBorder(border);
        panel.add(textField);
        JLabel label2 = new JLabel("hello world");
        label2.setBorder(border);
        panel.add(label2);
        JPanel control = new JPanel();
        control.setLayout(new VerticalFlowLayout());
        frame.getContentPane().add(control, "South");
        JPanel borderPanel = new JPanel();
        control.add(borderPanel);
        borderPanel.setLayout(new FlowLayout(0));
        borderPanel.add(new JLabel("border"));
        JSpinner borderSpinner = new JSpinner(new SpinnerNumberModel(10, 0, 100, 5));
        borderPanel.add(borderSpinner);
        borderSpinner.addChangeListener((e) -> {
            panel.setBorder(new LineBorder(Color.white, (Integer)borderSpinner.getValue()));
            panel.revalidate();
        });
        JPanel hAlignPanel = new JPanel();
        control.add(hAlignPanel);
        hAlignPanel.setLayout(new FlowLayout(0));
        ButtonGroup hAlign = new ButtonGroup();
        JRadioButton hLeft = new JRadioButton("Left");
        hLeft.setSelected(true);
        hLeft.addActionListener((e) -> {
            layout.setHAlign(3);
            panel.revalidate();
            System.out.println(layout);
        });
        hAlign.add(hLeft);
        JRadioButton hCenter = new JRadioButton("Center");
        hCenter.addActionListener((e) -> {
            layout.setHAlign(0);
            panel.revalidate();
            System.out.println(layout);
        });
        hAlign.add(hCenter);
        JRadioButton hRight = new JRadioButton("Right");
        hRight.addActionListener((e) -> {
            layout.setHAlign(4);
            panel.revalidate();
            System.out.println(layout);
        });
        hAlign.add(hRight);
        hAlignPanel.add(new JLabel("hAlign"));
        hAlignPanel.add(hLeft);
        hAlignPanel.add(hCenter);
        hAlignPanel.add(hRight);
        JPanel vAlignPanel = new JPanel();
        control.add(vAlignPanel);
        vAlignPanel.setLayout(new FlowLayout(0));
        ButtonGroup vAlign = new ButtonGroup();
        JRadioButton vTop = new JRadioButton("Top");
        vTop.setSelected(true);
        vTop.addActionListener((e) -> {
            layout.setVAlign(1);
            panel.revalidate();
            System.out.println(layout);
        });
        vAlign.add(vTop);
        JRadioButton vCenter = new JRadioButton("Center");
        vCenter.addActionListener((e) -> {
            layout.setVAlign(0);
            panel.revalidate();
            System.out.println(layout);
        });
        vAlign.add(vCenter);
        JRadioButton vBottom = new JRadioButton("Bottom");
        vBottom.addActionListener((e) -> {
            layout.setVAlign(2);
            panel.revalidate();
            System.out.println(layout);
        });
        vAlign.add(vBottom);
        vAlignPanel.add(new JLabel("vAlign"));
        vAlignPanel.add(vTop);
        vAlignPanel.add(vCenter);
        vAlignPanel.add(vBottom);
        JPanel hPaddingPanel = new JPanel();
        control.add(hPaddingPanel);
        hPaddingPanel.setLayout(new FlowLayout(0));
        hPaddingPanel.add(new JLabel("hPadding"));
        JSpinner hPaddingSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 100, 5));
        hPaddingPanel.add(hPaddingSpinner);
        hPaddingSpinner.addChangeListener((e) -> {
            layout.setHPadding((Integer)hPaddingSpinner.getValue());
            panel.revalidate();
            System.out.println(layout);
        });
        JPanel vPaddingPanel = new JPanel();
        control.add(vPaddingPanel);
        vPaddingPanel.setLayout(new FlowLayout(0));
        vPaddingPanel.add(new JLabel("vPadding"));
        JSpinner vPaddingSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 100, 5));
        vPaddingPanel.add(vPaddingSpinner);
        vPaddingSpinner.addChangeListener((e) -> {
            layout.setVPadding((Integer)vPaddingSpinner.getValue());
            panel.revalidate();
            System.out.println(layout);
        });
        JPanel hGapPanel = new JPanel();
        control.add(hGapPanel);
        hGapPanel.setLayout(new FlowLayout(0));
        hGapPanel.add(new JLabel("hGap"));
        JSpinner hGapSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 100, 5));
        hGapPanel.add(hGapSpinner);
        hGapSpinner.addChangeListener((e) -> {
            layout.setHGap((Integer)hGapSpinner.getValue());
            panel.revalidate();
            System.out.println(layout);
        });
        JPanel vGapPanel = new JPanel();
        control.add(vGapPanel);
        vGapPanel.setLayout(new FlowLayout(0));
        vGapPanel.add(new JLabel("vGap"));
        JSpinner vGapSpinner = new JSpinner(new SpinnerNumberModel(5, 0, 100, 5));
        vGapPanel.add(vGapSpinner);
        vGapSpinner.addChangeListener((e) -> {
            layout.setVGap((Integer)vGapSpinner.getValue());
            panel.revalidate();
            System.out.println(layout);
        });
        JPanel fillPanel = new JPanel();
        control.add(fillPanel);
        fillPanel.setLayout(new FlowLayout(0));
        ButtonGroup fillGroup = new ButtonGroup();
        JRadioButton fillTrue = new JRadioButton("true");
        fillTrue.setSelected(true);
        fillGroup.add(fillTrue);
        fillTrue.addActionListener((e) -> {
            layout.setFill(true);
            panel.revalidate();
            System.out.println(layout);
        });
        JRadioButton fillFalse = new JRadioButton("false");
        fillGroup.add(fillFalse);
        fillFalse.addActionListener((e) -> {
            layout.setFill(false);
            panel.revalidate();
            System.out.println(layout);
        });
        fillPanel.add(new JLabel("fill"));
        fillPanel.add(fillTrue);
        fillPanel.add(fillFalse);
        JPanel wrapPanel = new JPanel();
        control.add(wrapPanel);
        wrapPanel.setLayout(new FlowLayout(0));
        ButtonGroup wrapGroup = new ButtonGroup();
        JRadioButton wrapTrue = new JRadioButton("true");
        wrapGroup.add(wrapTrue);
        wrapTrue.addActionListener((e) -> {
            layout.setWrap(true);
            panel.revalidate();
            System.out.println(layout);
        });
        JRadioButton wrapFalse = new JRadioButton("false");
        wrapFalse.setSelected(true);
        wrapGroup.add(wrapFalse);
        wrapFalse.addActionListener((e) -> {
            layout.setWrap(false);
            panel.revalidate();
            System.out.println(layout);
        });
        wrapPanel.add(new JLabel("wrap"));
        wrapPanel.add(wrapTrue);
        wrapPanel.add(wrapFalse);
        frame.pack();
        frame.setSize(frame.getSize().width + 1000, frame.getSize().height + 200);
        frame.setLocationRelativeTo((Component)null);
        frame.setVisible(true);
        System.out.println(layout);
    }
}
