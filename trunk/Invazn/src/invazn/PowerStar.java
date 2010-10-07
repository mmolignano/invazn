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

import javax.swing.*;

public class PowerStar extends Powerup{

  ImageIcon[] imgs = new ImageIcon[20];
  int curImage = 0;

  public PowerStar(int x, int y)
  {
    super(x, y, 0);
    setVelocity ( 10 );

    //imgs[0] = new ImageIcon("c:/images/powerstar0.gif");
    imgs[0] = new ImageIcon("images/powerstar0.gif");
    imgs[1] = new ImageIcon("images/powerstar1.gif");
    imgs[2] = new ImageIcon("images/powerstar2.gif");
    imgs[3] = new ImageIcon("images/powerstar3.gif");
    imgs[4] = new ImageIcon("images/powerstar4.gif");
    imgs[5] = new ImageIcon("images/powerstar5.gif");
    imgs[6] = new ImageIcon("images/powerstar6.gif");
    imgs[7] = new ImageIcon("images/powerstar7.gif");
    imgs[8] = new ImageIcon("images/powerstar8.gif");
    imgs[9] = new ImageIcon("images/powerstar9.gif");
    imgs[10] = new ImageIcon("images/powerstar10.gif");
    imgs[11] = new ImageIcon("images/powerstar11.gif");
    imgs[12] = new ImageIcon("images/powerstar12.gif");
    imgs[13] = new ImageIcon("images/powerstar13.gif");
    imgs[14] = new ImageIcon("images/powerstar14.gif");
    imgs[15] = new ImageIcon("images/powerstar15.gif");
    imgs[16] = new ImageIcon("images/powerstar16.gif");
    imgs[17] = new ImageIcon("images/powerstar17.gif");
    imgs[18] = new ImageIcon("images/powerstar18.gif");
    imgs[19] = new ImageIcon("images/powerstar19.gif");

    setSprite ( imgs[0] );
  }

  public void cycleImage()
  {
    curImage++;
    if(curImage > 19)
      curImage = 0;
    setSprite ( imgs[curImage] );
  }

  public int getCurImage()
  {
    return curImage;
  }
}