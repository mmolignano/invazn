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

public class Ship
{

  // Private Variables
  private int positionX;
  private int positionY;
  private ImageIcon sprite;
  private Environment theEnv;

  public int getPositionX () { return positionX; }
  public int getPositionY () { return positionY; }
  public ImageIcon getSprite () { return sprite; }
  public Environment getEnv () { return theEnv; }

  public void setPositionX (int x) { positionX = x; }
  public void setPositionY (int y) { positionY = y; }
  public void setSprite (ImageIcon icon) { sprite = icon; }
  public void setEnv (Environment env) { theEnv = env; }

}