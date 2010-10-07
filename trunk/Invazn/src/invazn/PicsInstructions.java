/*******************************************************************************
 * Copyright (c) 2010 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package invazn;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class PicsInstructions extends JFrame implements ActionListener
{
  //Window Variables
  ImageIcon[] icons = new ImageIcon[10];
  JLabel[] labels = new JLabel[20];
  JButton okButton;

  //Constructor
  public PicsInstructions()
  {
    //Initialize Icons
	//icons[0] = new ImageIcon("C:\\images\\2.gif");
    icons[0] = new ImageIcon("images\\2.gif");
    icons[1] = new ImageIcon("images\\3.gif");
    icons[2] = new ImageIcon("images\\fuel1.gif");
    icons[3] = new ImageIcon("images\\fuel2.gif");
    icons[4] = new ImageIcon("images\\1up.gif");
    icons[5] = new ImageIcon("images\\speed.gif");
    icons[6] = new ImageIcon("images\\powerstar0.gif");
    icons[7] = new ImageIcon("images\\player.gif");
    icons[8] = new ImageIcon("images\\playerb.gif");
    icons[9] = new ImageIcon("images\\enemy.gif");

    //Initialize Labels
    labels[0] = new JLabel(icons[0]);
    labels[1] = new JLabel("Double Blasters");
    labels[2] = new JLabel(icons[1]);
    labels[3] = new JLabel("Triple Blasters");
    labels[4] = new JLabel(icons[2]);
    labels[5] = new JLabel("Half Fuel Tank");
    labels[6] = new JLabel(icons[3]);
    labels[7] = new JLabel("Full Fuel Tank");
    labels[8] = new JLabel(icons[4]);
    labels[9] = new JLabel("Extra Fuel Tank");
    labels[10] = new JLabel(icons[5]);
    labels[11] = new JLabel("Speed");
    labels[12] = new JLabel(icons[6]);
    labels[13] = new JLabel("Power Star");
    labels[14] = new JLabel(icons[7]);
    labels[15] = new JLabel("Player 1 Ship");
    labels[16] = new JLabel(icons[8]);
    labels[17] = new JLabel("Player 2 Ship");
    labels[18] = new JLabel(icons[9]);
    labels[19] = new JLabel("Enemy Ship");

    //Initialize Button
    okButton = new JButton("OK");

    //Layout Window
    Container content = this.getContentPane();
    content.setLayout(new GridLayout(11,2));
    for (int i = 0; i < labels.length; i++)
      content.add(labels[i]);
    content.add(okButton);

    //Set Action Listeners
    okButton.addActionListener(this);

    //Set Window Stuff
    this.setTitle("Game Pictures");
    this.pack();
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setLocation(50, 50);
  }

  //Methods
  public void actionPerformed (ActionEvent e)
  {
    JButton source = (JButton)(e.getSource());
    if (source == okButton)
      this.dispose();
  }
}
