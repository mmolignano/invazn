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

public class PlayerShip2 extends PlayerShip {

  public PlayerShip2(int x, int y, Environment env)
  {
    super(x,y,env);

    //imgs[0][0] = new ImageIcon("c:/images\\playerb2.gif");
    imgs[0][0] = new ImageIcon("images\\playerb2.gif");
    imgs[0][1] = new ImageIcon("images\\playerb1.gif");
    imgs[0][2] = new ImageIcon("images\\playerb0.gif");
    imgs[0][3] = new ImageIcon("images\\playerb1.gif");

    imgs[1][0] = new ImageIcon("images\\poweredb0.gif");
    imgs[1][1] = new ImageIcon("images\\poweredb1.gif");
    imgs[1][2] = new ImageIcon("images\\poweredb2.gif");
    imgs[1][3] = new ImageIcon("images\\poweredb3.gif");
    imgs[1][4] = new ImageIcon("images\\poweredb4.gif");
    imgs[1][5] = new ImageIcon("images\\poweredb5.gif");
    imgs[1][6] = new ImageIcon("images\\poweredb6.gif");
    imgs[1][7] = new ImageIcon("images\\poweredb7.gif");
    imgs[1][8] = new ImageIcon("images\\poweredb8.gif");
    imgs[1][9] = new ImageIcon("images\\poweredb9.gif");

    imgs[2][0] = new ImageIcon("images\\damagedb0.gif");
    imgs[2][1] = new ImageIcon("images\\damagedb1.gif");
    imgs[2][2] = new ImageIcon("images\\damagedb2.gif");
    imgs[2][3] = new ImageIcon("images\\damagedb3.gif");
    imgs[2][4] = new ImageIcon("images\\damagedb4.gif");
    imgs[2][5] = new ImageIcon("images\\damagedb5.gif");
    imgs[2][6] = new ImageIcon("images\\damagedb6.gif");
    imgs[2][7] = new ImageIcon("images\\damagedb7.gif");
    imgs[2][8] = new ImageIcon("images\\damagedb8.gif");
    imgs[2][9] = new ImageIcon("images\\damagedb9.gif");

    setSprite( imgs[0][0] );
  }

}
