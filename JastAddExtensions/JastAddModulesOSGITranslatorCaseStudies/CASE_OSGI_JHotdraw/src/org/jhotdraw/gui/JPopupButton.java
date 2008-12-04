/*
 * @(#)JPopupButton.java  1.1  2006-06-25
 *
 * Copyright (c) 1996-2006 by the original authors of JHotDraw
 * and all its contributors.
 * All rights reserved.
 *
 * The copyright of this software is owned by the authors and  
 * contributors of the JHotDraw project ("the copyright holders").  
 * You may not use, copy or modify this software, except in  
 * accordance with the license agreement you entered into with  
 * the copyright holders. For details see accompanying license terms. 
 */

package org.jhotdraw.gui;

import java.awt.*;
import java.beans.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.event.*;
import org.jhotdraw.draw.action.*;
/**
 * JPopupButton provides a popup menu.
 *
 * @author  Werner Randelshofer
 * @version 1.1 2006-06-25 Added more "add" methods.
 */
public class JPopupButton extends javax.swing.JButton {
    private JPopupMenu popupMenu;
    private int columnCount = 1;
    private Action action;
    private Rectangle actionArea;
    private Font itemFont;
    public final static Font ITEM_FONT = new Font("Dialog", Font.PLAIN, 10);
    
    private class ActionPropertyHandler implements PropertyChangeListener {
        public void propertyChange(PropertyChangeEvent evt) {
            if (evt.getPropertyName().equals("enabled")) {
                setEnabled(((Boolean) evt.getNewValue()).booleanValue());
            } else {
                repaint();
            }
        }
    };
    private ActionPropertyHandler actionPropertyHandler = new ActionPropertyHandler();
    
    /** Creates new form JToolBarMenu */
    public JPopupButton() {
        initComponents();
        setFocusable(false);
        itemFont = ITEM_FONT;
    }
    
    public void setItemFont(Font newValue) {
        itemFont = newValue;
        if (popupMenu != null) {
            updateFont(popupMenu);
        }
    }
    
    public void setAction(Action action, Rectangle actionArea) {
        if (this.action != null) {
            this.action.removePropertyChangeListener(actionPropertyHandler);
        }
        
        this.action = action;
        this.actionArea = actionArea;
        
        if (action != null) {
            action.addPropertyChangeListener(actionPropertyHandler);
        }
    }
    
    public int getColumnCount() {
        return columnCount;
    }
    public void setColumnCount(int count, boolean isVertical) {
        columnCount = count;
        getPopupMenu().setLayout(new VerticalGridLayout(0, getColumnCount(), isVertical));
    }
    
    public AbstractButton add(Action action) {
        JMenuItem item = getPopupMenu().add(action);
        if (getColumnCount() > 1) {
            item.setUI(new PaletteMenuItemUI());
        }
        item.setFont(itemFont);
        return item;
    }
    public void add(JMenu submenu) {
        JMenuItem item = getPopupMenu().add(submenu);
        updateFont(submenu);
    }
    public void add(JComponent submenu) {
        getPopupMenu().add(submenu);
    }
    private void updateFont(MenuElement menu) {
        menu.getComponent().setFont(itemFont);
        for (MenuElement child : menu.getSubElements()) {
            updateFont(child);
        }
    }
    
    public void add(JMenuItem item) {
        getPopupMenu().add(item);
        item.setFont(itemFont);
    }
    public void addSeparator() {
        getPopupMenu().addSeparator();
    }
    
    public void setPopupMenu(JPopupMenu popupMenu) {
        this.popupMenu = popupMenu;
    }
    public JPopupMenu getPopupMenu() {
        if (popupMenu == null) {
            popupMenu = new JPopupMenu();
            popupMenu.setLayout(new VerticalGridLayout(0, getColumnCount()));
        }
        return popupMenu;
    }
    
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    private void initComponents() {//GEN-BEGIN:initComponents

        FormListener formListener = new FormListener();

        addMouseListener(formListener);

    }

    // Code for dispatching events from components to event handlers.

    private class FormListener implements java.awt.event.MouseListener {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
        }

        public void mouseEntered(java.awt.event.MouseEvent evt) {
        }

        public void mouseExited(java.awt.event.MouseEvent evt) {
        }

        public void mousePressed(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == JPopupButton.this) {
                JPopupButton.this.showPopup(evt);
            }
        }

        public void mouseReleased(java.awt.event.MouseEvent evt) {
            if (evt.getSource() == JPopupButton.this) {
                JPopupButton.this.performAction(evt);
            }
        }
    }//GEN-END:initComponents
    
    private void performAction(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_performAction
        // Add your handling code here:
        if (actionArea != null && actionArea.contains(evt.getX()-getInsets().left, evt.getY()-getInsets().top)) {
            action.actionPerformed(
                    new ActionEvent(this,
                    ActionEvent.ACTION_PERFORMED,
                    null,
                    evt.getWhen(),
                    evt.getModifiers())
                    );
            
        }
    }//GEN-LAST:event_performAction
    
    private void showPopup(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_showPopup
        // Add your handling code here:
        if (popupMenu != null
                && (actionArea == null || ! actionArea.contains(evt.getX()-getInsets().left, evt.getY()-getInsets().top))
                ) {
            int x, y;
            
            x = 0;
            y = getHeight();
            if (getParent() instanceof JToolBar) {
                JToolBar toolbar = (JToolBar) getParent();
                if (toolbar.getOrientation() == JToolBar.VERTICAL) {
                    y = 0;
                    if (toolbar.getX() > toolbar.getParent().getInsets().left) {
                        x = -popupMenu.getPreferredSize().width;
                    } else {
                        x = getWidth();
                    }
                } else {
                    if (toolbar.getY() > toolbar.getParent().getInsets().top) {
                        y = -popupMenu.getPreferredSize().height;
                    }
                }
            }
            
            popupMenu.show(this, x, y);
        }
    }//GEN-LAST:event_showPopup
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    
}
