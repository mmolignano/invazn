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

public class HighScore extends JFrame implements ActionListener
{
  //Window Variables
  private JLabel[] nameLabels = new JLabel [11];
  private JLabel[] scoreLabels = new JLabel [11];
  private JButton okButton;
  private JButton resetButton;

  //Constructor
  public HighScore()
  {
    //Initialize labels
    String[] names = IO.getNames(IO.scoresPath);
    int[] scores = IO.getScores(IO.scoresPath);
    nameLabels[0] = new JLabel ("Names");
    for (int i = 1; i < nameLabels.length; i++)
      nameLabels[i] = new JLabel (names[i-1]);
    scoreLabels[0] = new JLabel ("Scores");
    for (int i = 1; i < scoreLabels.length; i++)
      scoreLabels[i] = new JLabel ("" + scores[i-1]);

    //Initialize buttons
    okButton = new JButton("OK");
    resetButton = new JButton("Reset Scores");

    //Layout Window
    Container content = this.getContentPane();
    content.setLayout(new GridLayout(12,2));
    for (int i = 0; i < 11; i++)
    {
      content.add(nameLabels[i]);
      content.add(scoreLabels[i]);
    }
    content.add(okButton);
    content.add(resetButton);
    //Set Action Listeners
    okButton.addActionListener(this);
    resetButton.addActionListener(this);
    //Set Window Stuff
    this.setTitle("Single High Scores");
    this.pack();
    this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    this.setLocation(350, 200);
  }

  //Methods
  public void actionPerformed (ActionEvent e)
  {
    JButton source = (JButton)(e.getSource());
    if (source == resetButton)
      resetScores();
    else
      this.dispose();
  }

  private void resetScores()
  {
    IO.copyFile(IO.scoresPath, IO.defaultPath);
    String[] names = IO.getNames(IO.scoresPath);
    int[] scores = IO.getScores(IO.scoresPath);
    for (int i = 1; i < nameLabels.length; i++)
    {
      nameLabels[i].setText(names[i-1]);
      scoreLabels[i].setText("" + scores[i-1]);
    }
  }
}
