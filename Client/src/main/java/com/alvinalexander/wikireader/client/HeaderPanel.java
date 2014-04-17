package com.alvinalexander.wikireader.client;

// "/Users/al/Projects/Scala/WikipediaReader/Client/src/main/resources/RedCircle18.png"

import java.awt.*;
import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;
/*
 * Created by JFormDesigner on Wed Nov 20 19:59:40 MST 2013
 */



/**
 * @author Alvin Alexander
 */
public class HeaderPanel extends JPanel {
    public HeaderPanel() {
        initComponents();
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public JLabel getCloseButton() {
        return closeButton;
    }

    public JLabel getMinimizeButton() {
        return minimizeButton;
    }

    public JLabel getMaximizeButton() {
        return maximizeButton;
    }

    public JLabel getTitle() {
        return title;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        buttonPanel = new JPanel();
        closeButton = new JLabel();
        minimizeButton = new JLabel();
        maximizeButton = new JLabel();
        title = new JLabel();
        CellConstraints cc = new CellConstraints();

        //======== this ========
        // AJA: changed "100px" to "75px"
        setLayout(new FormLayout(
            new ColumnSpec[] {
                new ColumnSpec(ColumnSpec.LEFT, Sizes.DLUX1, FormSpec.NO_GROW),
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                FormFactory.DEFAULT_COLSPEC,
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                new ColumnSpec(ColumnSpec.CENTER, Sizes.DEFAULT, FormSpec.DEFAULT_GROW),
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                new ColumnSpec("75px")
            },
            new RowSpec[] {
                FormFactory.NARROW_LINE_GAP_ROWSPEC,
                new RowSpec("36px")
            }));

        //======== buttonPanel ========
        {
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 2, 2));

            //---- closeButton ----
            closeButton.setIcon(new ImageIcon("/Users/al/Projects/Scala/WikipediaReader/Client/src/main/resources/RedCircle18.png"));
            buttonPanel.add(closeButton);

            //---- minimizeButton ----
            minimizeButton.setIcon(new ImageIcon("/Users/al/Projects/Scala/WikipediaReader/Client/src/main/resources/OrangeCircle18.png"));
            buttonPanel.add(minimizeButton);

            //---- maximizeButton ----
            maximizeButton.setIcon(new ImageIcon("/Users/al/Projects/Scala/WikipediaReader/Client/src/main/resources/GreenCircle18.png"));
            buttonPanel.add(maximizeButton);
        }
        add(buttonPanel, cc.xy(3, 2));

        //---- title ----
        title.setText("Web Page Reader");
        title.setFont(new Font("Helvetica Neue", Font.PLAIN, 16));
        title.setForeground(new Color(8, 8, 8));
        add(title, cc.xy(5, 2));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JPanel buttonPanel;
    private JLabel closeButton;
    private JLabel minimizeButton;
    private JLabel maximizeButton;
    private JLabel title;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}


