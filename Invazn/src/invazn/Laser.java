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

public class Laser extends PathedItem
{

  public Laser(int x, int y, int vel)
  {
    super(x,y);
    //setSprite(new ImageIcon("c:/images/laser.gif"));
    setSprite(new ImageIcon("images/laser.gif"));
    setVelocity ( vel );
  }
}