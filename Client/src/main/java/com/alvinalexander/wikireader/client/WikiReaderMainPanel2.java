package com.alvinalexander.wikireader.client;

import java.awt.*;

import javax.swing.*;

import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

// AJA
class IOS7ListCellRenderer extends DefaultListCellRenderer {
    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        Component c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
        if (isSelected) {
            c.setBackground(new Color(60, 170, 250));
            c.setBackground(new Color(50, 150, 250));
            c.setBackground(new Color(60, 150, 250));
        }
        return c;
    }
}

/**
 * @author Alvin Alexander
 */
public class WikiReaderMainPanel2 extends JPanel {
    public WikiReaderMainPanel2() {
        initComponents();
    }

    public JLabel getUrlLabel() {
        return urlLabel;
    }

    public JScrollPane getUrlsListScrollPane() {
        return urlsListScrollPane;
    }

    // AJA - added String type
    public JList<String> getUrlsList() {
        return urlsList;
    }

    public JPanel getButtonPanel() {
        return buttonPanel;
    }

    public JLabel getBeginningBtnLabel() {
        return beginningBtnLabel;
    }

    public JLabel getRewindBtnLabel() {
        return rewindBtnLabel;
    }

    public JLabel getPlayPauseBtnLabel() {
        return playPauseBtnLabel;
    }

    public JLabel getFastForwardBtnLabel() {
        return fastForwardBtnLabel;
    }

    public JLabel getEndBtnLabel() {
        return endBtnLabel;
    }

    public JScrollPane getWikiOutputAreaScrollPane() {
        return wikiOutputAreaScrollPane;
    }

    public JEditorPane getWikiOutputArea() {
        return wikiOutputArea;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY  //GEN-BEGIN:initComponents
        urlLabel = new JLabel();
        urlsListScrollPane = new JScrollPane();
        // AJA - added String type
        urlsList = new JList<String>();
        buttonPanel = new JPanel();
        beginningBtnLabel = new JLabel();
        rewindBtnLabel = new JLabel();
        playPauseBtnLabel = new JLabel();
        fastForwardBtnLabel = new JLabel();
        endBtnLabel = new JLabel();
        wikiOutputAreaScrollPane = new JScrollPane();
        wikiOutputArea = new JEditorPane();
        CellConstraints cc = new CellConstraints();

        //======== this ========
        setLayout(new FormLayout(
            new ColumnSpec[] {
                FormFactory.UNRELATED_GAP_COLSPEC,
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                new ColumnSpec(ColumnSpec.RIGHT, Sizes.PREFERRED, FormSpec.NO_GROW),
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                new ColumnSpec(ColumnSpec.FILL, Sizes.PREFERRED, FormSpec.DEFAULT_GROW),
                FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                FormFactory.UNRELATED_GAP_COLSPEC
            },
            new RowSpec[] {
                FormFactory.PARAGRAPH_GAP_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.RELATED_GAP_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.PARAGRAPH_GAP_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                FormFactory.DEFAULT_ROWSPEC,
                FormFactory.LINE_GAP_ROWSPEC,
                new RowSpec(RowSpec.FILL, Sizes.dluY(12), FormSpec.NO_GROW)
            }));

        //---- urlLabel ----
        urlLabel.setText("URLs:");
        urlLabel.setToolTipText("The Wikipedia URL to read");
        // AJA
        urlLabel.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
        urlLabel.setForeground(new Color(20, 20, 20));

        add(urlLabel, cc.xy(5, 3));

        //======== urlsListScrollPane ========
        {
            // AJA never
            urlsListScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            urlsListScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            urlsListScrollPane.setViewportBorder(null);

            //---- urlsList ----
            urlsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            urlsList.setToolTipText("Select a URL to read");
            urlsList.setVisibleRowCount(6);
            // AJA font, color
            urlsList.setFont(new Font("Helvetica Neue", Font.PLAIN, 14));
            urlsList.setForeground(new Color(40, 40, 40));
            urlsList.setCellRenderer(new IOS7ListCellRenderer());

//            urlsList.setModel(new AbstractListModel() {
//                String[] values = {
//                    "URL-1",
//                    "URL-2",
//                    "URL-3"
//                };
//                public int getSize() { return values.length; }
//                public Object getElementAt(int i) { return values[i]; }
//            });
            urlsListScrollPane.setViewportView(urlsList);
        }
        add(urlsListScrollPane, cc.xy(5, 5));

        //======== buttonPanel ========
        {
            // AJA
            buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 10));

            //---- beginningBtnLabel ----
            java.net.URL beginningUrl = getClass().getResource("/beginning.png");
            beginningBtnLabel.setIcon(new ImageIcon((beginningUrl)));
            //beginningBtnLabel.setIcon(new ImageIcon("/Users/al/Projects/Scala/WikipediaReader/Client/src/main/resources/beginning.png"));
            buttonPanel.add(beginningBtnLabel);

            //---- rewindBtnLabel ----
            java.net.URL rewindUrl = getClass().getResource("/rewind.png");
            rewindBtnLabel.setIcon(new ImageIcon((rewindUrl)));
            //rewindBtnLabel.setIcon(new ImageIcon("/Users/al/Projects/Scala/WikipediaReader/Client/src/main/resources/rewind.png"));
            buttonPanel.add(rewindBtnLabel);

            //---- playPauseBtnLabel ----
            java.net.URL playPauseUrl = getClass().getResource("/play.png");
            playPauseBtnLabel.setIcon(new ImageIcon((playPauseUrl)));
            //playPauseBtnLabel.setIcon(new ImageIcon("/Users/al/Projects/Scala/WikipediaReader/Client/src/main/resources/play.png"));
            buttonPanel.add(playPauseBtnLabel);

            //---- fastForwardBtnLabel ----
            java.net.URL fastForwardUrl = getClass().getResource("/ff.png");
            fastForwardBtnLabel.setIcon(new ImageIcon((fastForwardUrl)));
            //fastForwardBtnLabel.setIcon(new ImageIcon("/Users/al/Projects/Scala/WikipediaReader/Client/src/main/resources/ff.png"));
            buttonPanel.add(fastForwardBtnLabel);

            //---- endBtnLabel ----
            java.net.URL endUrl = getClass().getResource("/end.png");
            endBtnLabel.setIcon(new ImageIcon((endUrl)));
            //endBtnLabel.setIcon(new ImageIcon("/Users/al/Projects/Scala/WikipediaReader/Client/src/main/resources/end.png"));
            buttonPanel.add(endBtnLabel);

        }
        // AJA - made this change to center the controls
        //add(buttonPanel, cc.xy(5, 9));
        add(buttonPanel, cc.xyw(5, 9, 3));

        //======== wikiOutputAreaScrollPane ========
        {
            // AJA never
            wikiOutputAreaScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
            wikiOutputAreaScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
            wikiOutputAreaScrollPane.setViewportBorder(null);

            //---- wikiOutputArea ----
            wikiOutputArea.setEditable(false);
            wikiOutputArea.setPreferredSize(new Dimension(420, 96));
            wikiOutputArea.setToolTipText("Text from the Wikipedia page will appear here");
            // AJA was gray
            wikiOutputArea.setForeground(Color.DARK_GRAY);
            wikiOutputArea.setBackground(new Color(240, 240, 240));
            // AJA
            wikiOutputArea.setFont(new Font("Helvetica Neue", Font.PLAIN, 17));
            wikiOutputArea.setMinimumSize(new Dimension(420, 36));
            wikiOutputAreaScrollPane.setViewportView(wikiOutputArea);
        }
        add(wikiOutputAreaScrollPane, cc.xy(5, 13));
        // JFormDesigner - End of component initialization  //GEN-END:initComponents
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY  //GEN-BEGIN:variables
    private JLabel urlLabel;
    private JScrollPane urlsListScrollPane;
    // AJA - added String type here
    private JList<String> urlsList;
    private JPanel buttonPanel;
    private JLabel beginningBtnLabel;
    private JLabel rewindBtnLabel;
    private JLabel playPauseBtnLabel;
    private JLabel fastForwardBtnLabel;
    private JLabel endBtnLabel;
    private JScrollPane wikiOutputAreaScrollPane;
    private JEditorPane wikiOutputArea;
    // JFormDesigner - End of variables declaration  //GEN-END:variables
}


