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

public class PathedItem
{

  private int positionX;
  private int positionY;
  private int velocity;
  private ImageIcon sprite;

  public PathedItem(int x, int y)
  {
    positionX = x;
    positionY = y;
  }

  public int getPositionX () { return positionX; }
  public int getPositionY () { return positionY; }
  public int getVelocity () { return velocity; }
  public ImageIcon getSprite () { return sprite; }

  public void setPositionX ( int x ) { positionX = x; }
  public void setPositionY ( int y ) { positionY = y; }
  public void setVelocity ( int vel ) { velocity = vel; }
  public void setSprite ( ImageIcon spr ) { sprite = spr; }

  public void move () { setPositionY(getPositionY() + getVelocity()); }
}