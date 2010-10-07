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

import java.util.Random;
import javax.swing.ImageIcon;

public class EnemyShip extends Ship
{

  // Private Static Variables for dropping items
  private static final int totalDrop      = 23;
  private static final int chanceBlaster2 = 6;
  private static final int chanceBlaster3 = 3 + chanceBlaster2;
  private static final int chanceFuel1    = 6 + chanceBlaster3;
  private static final int chanceFuel2    = 3 + chanceFuel1;
  private static final int chanceFuel3    = 1 + chanceFuel2;
  private static final int chanceSpeed    = 4 + chanceFuel3;
  private static final double chanceDrop     = .08;

  // Private Variables
  private int armor;

  public EnemyShip(int x, int y, Environment env, int armorv)
  {
    setPositionX ( x );
    setPositionY ( y );
    setEnv ( env );
    //setSprite( new ImageIcon("c:/images/enemy.gif"));
    setSprite( new ImageIcon("images/enemy.gif"));
    armor = armorv;
  }

  public int getArmor() { return armor; }
  public void decreaseArmor() { armor--; }


  /*============== Item Drop Methods =================*/

  public Powerup turnToObject()
  {
    Random random = new Random();
    double itemChance = random.nextDouble();

    // If item drops, find which one
    if ( itemChance < chanceDrop )
    {
      return dropItem();
    }

    return dropPowerStar();
  }

  public void move(int x, int y)
  {
    setPositionX(getPositionX() + x);
    setPositionY(getPositionY() + y);
  }

  public Powerup dropItem()
  {
    Random random = new Random();
    double itemChance = random.nextInt(23)+1;

    // Find if it drops Double Blasters
    if( itemChance <= chanceBlaster2 )
    {
      return new Blaster2(getPositionX(), getPositionY());
    }

    // Find if it drops Triple Blasters
    if( itemChance <= chanceBlaster3 )
    {
      return new Blaster3(getPositionX(), getPositionY());
    }

    // Find if it drops small fuel tank
    if( itemChance <= chanceFuel1 )
    {
      return new Fuel1(getPositionX(), getPositionY());
    }

    // Find if it drops large fuel tank
    if( itemChance <= chanceFuel2 )
    {
      return new Fuel2(getPositionX(), getPositionY());
    }

    // Find if it drops giant fuel tank
    if( itemChance <= chanceFuel3 )
    {
      return new Fuel3(getPositionX(), getPositionY());
    }

    // Find if it drops speed upgrade
      return new Speed(getPositionX(), getPositionY());
  }

  public Powerup dropPowerStar()
  {
    return new PowerStar(getPositionX(), getPositionY());
  }

}