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

public class AboutInfo extends JFrame implements ActionListener
{
	/** */
	private static final long serialVersionUID = 1L;
	
	// Window Variables
	private JTextArea info;
  private JButton okButton;

  //Constructor
  public AboutInfo()
  {
    //Initialize
    info = new JTextArea("Invazn! v1.0\tPresented by noobware\n\nProgrammers:\n"
    + "Jeremy Badessa\nMike Molignano\nChris Steele\n\nSpecial Thanks To:\n"
    + "Stowe Games Distributor International\n\nCopyright 2005\t       All Rights Reserved");
    info.setPreferredSize(new Dimension(240, 220));
    okButton = new JButton("OK");

    //Layout Window
    FlowLayout layout = new FlowLayout();
    Container content = this.getContentPane();
    content.setLayout(layout);
    content.add(info);
    content.add(okButton);

    //Set Action Listeners
    okButton.addActionListener(this);

    //Prepare Window for Display
    info.setLineWrap(true);
    info.setWrapStyleWord(true);
    info.setEditable(false);
    info.append("");
    this.setTitle("About");
    this.pack();
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setLocation(300, 200);
  }

  //Methods
  public void actionPerformed (ActionEvent e)
  {
    JButton source = (JButton)(e.getSource());
    if (source == okButton)
      this.dispose();
  }
}