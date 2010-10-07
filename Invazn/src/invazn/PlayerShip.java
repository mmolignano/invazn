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

public class PlayerShip extends Ship{

  ImageIcon[][] imgs = new ImageIcon[3][10];
  int curImage = 0;
  private int blasterType;
  private int fuel;
  private int animation;

  // Powerup Variables
  private boolean haveB2;
  private boolean haveB3;
  private boolean haveSpeed;

  // Variables that hold the timers for powerups
  private int B2Count;
  private int B3Count;
  private int SpeedCount;

  public PlayerShip(int x, int y, Environment env)
  {
    setPositionX(x);
    setPositionY(y);
    setEnv(env);

    // Set powerup booleans to false
    haveB2 = false;
    haveB3 = false;
    haveSpeed = false;

    // Set powerup timers all to 0
    B2Count = 0;
    B3Count = 0;
    SpeedCount = 0;

    //imgs[0][0] = new ImageIcon("c:/images/player2.gif");
    imgs[0][0] = new ImageIcon("images/player2.gif");
    imgs[0][1] = new ImageIcon("images/player1.gif");
    imgs[0][2] = new ImageIcon("images/player0.gif");
    imgs[0][3] = new ImageIcon("images/player1.gif");

    imgs[1][0] = new ImageIcon("images/powered0.gif");
    imgs[1][1] = new ImageIcon("images/powered1.gif");
    imgs[1][2] = new ImageIcon("images/powered2.gif");
    imgs[1][3] = new ImageIcon("images/powered3.gif");
    imgs[1][4] = new ImageIcon("images/powered4.gif");
    imgs[1][5] = new ImageIcon("images/powered5.gif");
    imgs[1][6] = new ImageIcon("images/powered6.gif");
    imgs[1][7] = new ImageIcon("images/powered7.gif");
    imgs[1][8] = new ImageIcon("images/powered8.gif");
    imgs[1][9] = new ImageIcon("images/powered9.gif");

    imgs[2][0] = new ImageIcon("images/damaged0.gif");
    imgs[2][1] = new ImageIcon("images/damaged1.gif");
    imgs[2][2] = new ImageIcon("images/damaged2.gif");
    imgs[2][3] = new ImageIcon("images/damaged3.gif");
    imgs[2][4] = new ImageIcon("images/damaged4.gif");
    imgs[2][5] = new ImageIcon("images/damaged5.gif");
    imgs[2][6] = new ImageIcon("images/damaged6.gif");
    imgs[2][7] = new ImageIcon("images/damaged7.gif");
    imgs[2][8] = new ImageIcon("images/damaged8.gif");
    imgs[2][9] = new ImageIcon("images/damaged9.gif");

    setSprite( imgs[0][0] );
  }

  public int getX() { return getPositionX(); }
  public int getY() { return getPositionY(); }

  public void move(int x, int y)
  {
    setPositionX(x);
    setPositionY(y);
  }

  public void relativeMove(int x, int y)
  {
    setPositionX(getPositionX() + x);
    setPositionY(getPositionY() + y);
  }

  public void cycleImage()
  {
    if(animation == 0 && curImage > 3)
      curImage = 0;
    else if(curImage > 9)
    {
      animation = 0;
      curImage = 0;
    }
    setSprite ( imgs[animation][curImage] );
    curImage++;
  }

  public int getCurImage()
  {
    return curImage;
  }

  public void setAnimation(int ani) { animation = ani; curImage=0; }
  public int getBlaster() { return blasterType; }
  public int getFuel()  { return fuel; }

  // --- Powerup Methods --- //
  public void setB2(boolean b2) { haveB2 = b2; }
  public void setB3(boolean b3) { haveB3 = b3; }
  public void setSpeed(boolean speed) { haveSpeed = speed; }
  public void setB2Count(int b2) { B2Count = b2; }
  public void setB3Count(int b3) { B3Count = b3; }
  public void setSpeedCount(int speed) { SpeedCount = speed; }

  public boolean getB2() { return haveB2; }
  public boolean getB3() { return haveB3; }
  public boolean getSpeed() { return haveSpeed; }
  public int getB2Count() { return B2Count; }
  public int getB3Count() { return B3Count; }
  public int getSpeedCount() { return SpeedCount; }
}