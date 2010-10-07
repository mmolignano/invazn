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

import javax.swing.ImageIcon;

public class Boss extends Ship{

  // Private Variables
  private int armor;
  private int direction;
  ImageIcon[] imgs = new ImageIcon[20];
  int curImage = 0;

  public Boss(int x, int y, Environment env, int armorv)
  {
    setPositionX ( x );
    setPositionY ( y );
    setEnv ( env );

    imgs[0] = new ImageIcon("images\\boss0.gif");
    imgs[1] = new ImageIcon("images\\boss1.gif");
    imgs[2] = new ImageIcon("images\\boss2.gif");
    imgs[3] = new ImageIcon("images\\boss3.gif");
    imgs[4] = new ImageIcon("images\\boss4.gif");
    imgs[5] = new ImageIcon("images\\boss5.gif");
    imgs[6] = new ImageIcon("images\\boss6.gif");
    imgs[7] = new ImageIcon("images\\boss7.gif");
    imgs[8] = new ImageIcon("images\\boss8.gif");
    imgs[9] = new ImageIcon("images\\boss9.gif");
    imgs[10] = new ImageIcon("images\\boss10.gif");
    imgs[11] = new ImageIcon("images\\boss11.gif");
    imgs[12] = new ImageIcon("images\\boss12.gif");
    imgs[13] = new ImageIcon("images\\boss13.gif");
    imgs[14] = new ImageIcon("images\\boss14.gif");
    imgs[15] = new ImageIcon("images\\boss15.gif");
    imgs[16] = new ImageIcon("images\\boss16.gif");
    imgs[17] = new ImageIcon("images\\boss17.gif");
    //imgs[17] = new ImageIcon("C:\\images\\boss17.gif");


    setSprite( imgs[0] );
    armor = armorv;
    direction = 0;
    }

    public void cycleImage()
    {
      curImage++;
      if(curImage > 17)
        curImage = 0;
      setSprite ( imgs[curImage] );
    }

    public int getCurImage()
    {
      return curImage;
    }

  public int  getArmor() { return armor; }
  public void decreaseArmor() { armor--; }
  public void setDirection(int dir) { direction = dir; }
  public int  getDirection() { return direction; }

  public void move()
  {
    switch(direction)
    {
      case 0: move(0, 5);
              break;
      case 1: move(10, 0);
              break;
      case 2: move(-10, 0);
              break;
      case 3: move(10, 0);
		  direction = 1;
              break;
    }
  }

  public void move(int x, int y)
  {
    setPositionX(getPositionX() + x);
    setPositionY(getPositionY() + y);
  }
}
