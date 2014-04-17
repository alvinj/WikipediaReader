package com.alvinalexander.wikireader.client;

import javax.swing.*;
import com.jgoodies.forms.factories.*;
import com.jgoodies.forms.layout.*;

/*
 * Created by JFormDesigner on Tue Apr 15 09:52:10 MDT 2014
 */

/**
 * @author Alvin Alexander
 */
public class WikiReaderVoicesPanel2 extends JPanel {
    public WikiReaderVoicesPanel2() {
        initComponents();
    }

    public JRadioButton getUseVoiceRadioButton() {
        return useVoiceRadioButton;
    }

    public JTextField getVoiceTextField() {
        return voiceTextField;
    }

    public JRadioButton getAlternateVoicesRadioButton() {
        return alternateVoicesRadioButton;
    }

    public JScrollPane getAlternateVoicesScrollPane() {
        return alternateVoicesScrollPane;
    }

    public JTextArea getVoicesTextArea() {
        return voicesTextArea;
    }

    private void initComponents() {
        // JFormDesigner - Component initialization - DO NOT MODIFY
        // //GEN-BEGIN:initComponents
        useVoiceRadioButton = new JRadioButton();
        voiceTextField = new JTextField();
        alternateVoicesRadioButton = new JRadioButton();
        alternateVoicesScrollPane = new JScrollPane();
        voicesTextArea = new JTextArea();
        CellConstraints cc = new CellConstraints();

        // ======== this ========
        setLayout(new FormLayout(new ColumnSpec[] { FormFactory.UNRELATED_GAP_COLSPEC,
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC, FormFactory.DEFAULT_COLSPEC,
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        new ColumnSpec(ColumnSpec.FILL, Sizes.DEFAULT, 0.9),
                        FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
                        new ColumnSpec(ColumnSpec.FILL, Sizes.DLUX8, 0.5) }, new RowSpec[] {
                        FormFactory.PARAGRAPH_GAP_ROWSPEC, FormFactory.LINE_GAP_ROWSPEC,
                        FormFactory.DEFAULT_ROWSPEC, FormFactory.LINE_GAP_ROWSPEC,
                        new RowSpec(RowSpec.TOP, Sizes.DEFAULT, FormSpec.NO_GROW),
                        FormFactory.LINE_GAP_ROWSPEC,
                        new RowSpec(RowSpec.FILL, Sizes.DLUY9, FormSpec.DEFAULT_GROW) }));

        // ---- useVoiceRadioButton ----
        useVoiceRadioButton.setText("Use One Voice:");
        useVoiceRadioButton.setSelected(true);
        useVoiceRadioButton.setToolTipText("Use only this one voice when reading.");
        add(useVoiceRadioButton, cc.xy(3, 3));

        // ---- voiceTextField ----
        voiceTextField.setToolTipText("One voice you want to listen to, like 'Alex'");
        voiceTextField.setColumns(20);
        add(voiceTextField, cc.xywh(5, 3, 1, 1, CellConstraints.FILL, CellConstraints.FILL));

        // ---- alternateVoicesRadioButton ----
        alternateVoicesRadioButton.setText("Alternate Voices:");
        alternateVoicesRadioButton.setToolTipText("Read each paragraph with a different voice");
        add(alternateVoicesRadioButton, cc.xy(3, 5));

        // ======== alternateVoicesScrollPane ========
        {

            // ---- voicesTextArea ----
            voicesTextArea.setRows(10);
            voicesTextArea.setToolTipText("Enter multiple voices, one per row.");
            voicesTextArea.setColumns(20);
            alternateVoicesScrollPane.setViewportView(voicesTextArea);
        }
        add(alternateVoicesScrollPane, cc.xy(5, 5));
        // JFormDesigner - End of component initialization
        // //GEN-END:initComponents
        
        // AJA
        radioButtonGroup.add(useVoiceRadioButton);
        radioButtonGroup.add(alternateVoicesRadioButton);
    }

    // AJA
    private ButtonGroup radioButtonGroup = new ButtonGroup();
    public ButtonGroup getRadioButtonGroup() {
        return this.radioButtonGroup;
    }

    // JFormDesigner - Variables declaration - DO NOT MODIFY
    // //GEN-BEGIN:variables
    private JRadioButton useVoiceRadioButton;
    private JTextField voiceTextField;
    private JRadioButton alternateVoicesRadioButton;
    private JScrollPane alternateVoicesScrollPane;
    private JTextArea voicesTextArea;
    // JFormDesigner - End of variables declaration //GEN-END:variables
}




