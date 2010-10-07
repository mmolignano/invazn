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

public class Counter
{
  //Private Variables
  private int fuel;
  private int lives;
  private int score;

  //Constructor
  public Counter()
  {
    fuel = 1000;
    lives = 5;
    score = 0;
  }

  //Methods
  public void addToScore(int pts)
  { score += pts; }

  public void subtractLife()
  { lives--; }

  public void addLife()
  { lives++; }

  public void subtractFuel(int amt)
  { fuel -= amt; }

  public void addFuel(int amt)
  {
    fuel += amt;
    if(fuel>1000)
      fuel = 1000;
  }

  public int getScore()
  { return score; }

  public int getLives()
  { return lives; }

  public int getFuel()
  { return fuel; }

  public void checkFuel()
  {
    if(fuel<=0)
    {
      subtractLife();
      fuel = 1000;
    }
  }
}
