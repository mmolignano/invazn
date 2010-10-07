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

public class Instructions extends JFrame implements ActionListener
{
  //Static Variables
  public static int textAreaWidth = 580;
  public static int textAreaHeight = 670;

  //Window Variables
  private JTextArea area;
  private JButton okButton;
  private JButton showPics;

  //Constructor
  public Instructions()
  {
    //Initialize Window Objects
    String text = IO.getInstructions();
    area = new JTextArea(text);
    area.setPreferredSize(new Dimension(textAreaWidth, textAreaHeight));
    okButton = new JButton("OK");
    showPics = new JButton("Show Pictures");
    //Layout Window
    FlowLayout layout = new FlowLayout();
    Container content = this.getContentPane();
    content.setLayout(layout);
    content.add(area);
    content.add(okButton);
    content.add(showPics);
    //Add Action Listeners
    okButton.addActionListener(this);
    showPics.addActionListener(this);
    //Prepare Window for Display
    area.setLineWrap(true);
    area.setWrapStyleWord(true);
    area.setEditable(false);
    area.append("");
    this.setTitle("Instructions");
    this.pack();
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
  }

  public void actionPerformed (ActionEvent e)
  {
    JButton source = (JButton)(e.getSource());
    if (source == showPics)
      displayPics();
    else
      this.dispose();
  }

  private void displayPics()
  {
    PicsInstructions picsDisplay = new PicsInstructions();
    picsDisplay.setSize(230,400);
    //picsDisplay.setIconImage(new ImageIcon("C:\\images\\icon.gif").getImage());
    picsDisplay.setIconImage(new ImageIcon("images\\icon.gif").getImage());
    picsDisplay.setResizable(false);
    picsDisplay.setVisible(true);
  }
}
