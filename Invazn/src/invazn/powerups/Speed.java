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
package invazn.powerups;

import invazn.Powerup;

import javax.swing.*;

public class Speed extends Powerup
{

  public Speed(int x, int y)
  {
    super( x, y, 6 );
    //setSprite ( new ImageIcon("c:/images/speed.gif") );
    setSprite ( new ImageIcon("images/speed.gif") );
  }
}