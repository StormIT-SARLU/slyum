﻿package swing.propretiesView;

import graphic.textbox.TextBoxMultiplicity;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

import swing.FlatPanel;
import utility.Utility;
import classDiagram.components.Visibility;
import classDiagram.relationships.Multiplicity;
import classDiagram.relationships.Role;

/**
 * Represent a JPanel containing all Swing components for edit role.
 * 
 * @author David Miserez
 * @version 1.0 - 28.07.2011
 */
public class SlyumRolePanel extends FlatPanel implements Observer {
  private static final long serialVersionUID = -8176389461299256256L;
  private JComboBox<Multiplicity> comboBoxMultiplicity = Utility
          .getMultiplicityComboBox();
  private final JComboBox<String> comboBoxVisibility = Utility
          .getVisibilityComboBox();
  private final JLabel lblRolename;
  private final Role role;
  private final JTextField txtRolename;

  /**
   * Create the panel.
   */
  public SlyumRolePanel(final Role role) {
    if (role == null) throw new IllegalArgumentException("role is null");

    setMaximumSize(new Dimension(200, Short.MAX_VALUE));

    // Generated by WindowBuilder from Google
    this.role = role;
    role.addObserver(this);
    role.getMultiplicity().addObserver(this);

    final GridBagLayout gridBagLayout = new GridBagLayout();
    gridBagLayout.columnWidths = new int[] { 0, 0 };
    gridBagLayout.rowHeights = new int[] { 0, 0, 0, 0, 0 };
    gridBagLayout.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
    gridBagLayout.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0,
            Double.MIN_VALUE };
    setLayout(gridBagLayout);

    lblRolename = new JLabel(role.getEntity().getName());
    lblRolename.setFont(new Font("Tahoma", Font.PLAIN, 13));
    final GridBagConstraints gbc_lblRolename = new GridBagConstraints();
    gbc_lblRolename.insets = new Insets(0, 0, 5, 0);
    gbc_lblRolename.gridx = 0;
    gbc_lblRolename.gridy = 0;
    add(lblRolename, gbc_lblRolename);

    txtRolename = new JTextField();
    txtRolename.setText(role.getName());
    final GridBagConstraints gbc_txtRolename = new GridBagConstraints();
    gbc_txtRolename.insets = new Insets(0, 0, 5, 0);
    gbc_txtRolename.fill = GridBagConstraints.HORIZONTAL;
    gbc_txtRolename.gridx = 0;
    gbc_txtRolename.gridy = 1;
    add(txtRolename, gbc_txtRolename);
    txtRolename.setColumns(10);
    txtRolename.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        role.setName(txtRolename.getText());
        role.notifyObservers();
      }
    });

    comboBoxMultiplicity = Utility.getMultiplicityComboBox();
    comboBoxMultiplicity.setSelectedItem(role.getMultiplicity().toString());
    final GridBagConstraints gbc_comboBoxMultiplicity = new GridBagConstraints();
    gbc_comboBoxMultiplicity.insets = new Insets(0, 0, 5, 0);
    gbc_comboBoxMultiplicity.fill = GridBagConstraints.HORIZONTAL;
    gbc_comboBoxMultiplicity.gridx = 0;
    gbc_comboBoxMultiplicity.gridy = 2;
    add(comboBoxMultiplicity, gbc_comboBoxMultiplicity);
    comboBoxMultiplicity.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        role.setMultiplicity(TextBoxMultiplicity
                .convertStringToMultiplicity(comboBoxMultiplicity
                        .getSelectedItem().toString()));
        role.getMultiplicity().notifyObservers();
      }
    });

    final GridBagConstraints gbc_comboBoxVisibility = new GridBagConstraints();
    comboBoxVisibility.setSelectedItem(role.getVisibility().getName());
    gbc_comboBoxVisibility.fill = GridBagConstraints.HORIZONTAL;
    gbc_comboBoxVisibility.gridx = 0;
    gbc_comboBoxVisibility.gridy = 3;
    add(comboBoxVisibility, gbc_comboBoxVisibility);
    comboBoxVisibility.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        role.setVisibility(Visibility.valueOf(comboBoxVisibility
                .getSelectedItem().toString().toUpperCase()));
        role.notifyObservers();
      }
    });
  }

  public void confirm() {
    role.setName(txtRolename.getText());
    role.notifyObservers();
  }

  /**
   * Remove this observator from the observators list of the role.
   */
  public void stopObserving() {
    role.deleteObserver(this);
    role.getMultiplicity().deleteObserver(this);
  }

  @Override
  public void update(Observable arg0, Object arg1) {
    txtRolename.setText(role.getName());
    comboBoxMultiplicity.setSelectedItem(role.getMultiplicity().toString());
    comboBoxVisibility.setSelectedItem(role.getVisibility().getName());
  }

}
