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

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

import sun.audio.*;
import java.io.*;

public class Environment extends JPanel implements MouseListener, MouseMotionListener
{
  // Width and Height
  private static final int HEIGHT = 700;
  private static final int WIDTH = 900;

  // Mouse Variables
  private static int mouseX;
  private static int mouseY;
  private static int mouseClickedX;
  private static int mouseClickedY;

  // Count of enemy ships
  private static int numEnemies;

  // Holds whether player one and two are alive
  private static boolean player1Alive;
  private static boolean player2Alive;

  // Player and ship objects
  private PlayerShip player;
  private PlayerShip2 player2;
  private boolean is2play;
  private ArrayList enemy;
  private Boss boss;

  // Chance for normal enemy to fire a laser
  private int enChance;

  // Lasers ArrayList
  private ArrayList lasers;
  private ArrayList enlasers;

  // Dropped Objects ArrayList
  private ArrayList dropObject;

  // Stars Array
  private ArrayList stars;

  // Armor variable
  private int armorvalue;

  // Int that holds how long the game has been running in seconds
  private int secondcounter;

  // Counter Classes
  private Counter counter;
  private Counter counter2;

  // Audio Stream for main background music
  //ContinuousAudioDataStream cas;
//  private AudioStream cas;

  // --- Constructor --- //
  public Environment( boolean play2 )
  {
    // Set environment settings
    setPreferredSize( new Dimension(WIDTH, HEIGHT) );
    setBackground( Color.black );

    // Add the mouse listeners
    this.addMouseListener(this);
    this.addMouseMotionListener(this);

    // Set the variable holding if it is a single or double player game
    is2play = play2;

    // Set the first and second player as being alive
    player1Alive = true;
    player2Alive = play2;

    // Set the number of enemies to 0
    numEnemies = 0;

    // Set the armor value at 1
    armorvalue = 1;

    // Start the seconds counter at 0
    secondcounter = 0;

    // Enemy starts off with a 1/200 chance to shoot every .05 seconds
    enChance = 200;

    // Create new ship Objects
    player = new PlayerShip(450, 550, this);
    player2 = new PlayerShip2(500, 550, this);

    // Create new enemy, laser, star, and powerups ArrayLists
    enemy = new ArrayList();
    lasers = new ArrayList();
    enlasers = new ArrayList();
    dropObject = new ArrayList();
    stars = new ArrayList();

    // Create both counter Classes
    counter = new Counter();
    counter2 = new Counter();

    // Create the 20 stars in random locations with random speeds across the environment
    Random rand2 = new Random();
    Random random = new Random();
    while(stars.size() < 20)
    {
      stars.add(new Star(rand2.nextInt(WIDTH),rand2.nextInt(HEIGHT), random.nextInt(3)+6));
    }

    // Start the background music
    startMusic();

    // Set the cursor image to a blank cursor while in the game screen
    //Image cursorImage = Toolkit.getDefaultToolkit().getImage("c:/images/cursor.gif");
    Image cursorImage = Toolkit.getDefaultToolkit().getImage("images/cursor.gif");
    Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point( 0, 0), "" );
    setCursor( blankCursor );
  }

  // --- Calls to repaint the screen --- //
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    // Downcast to Graphics2D
    Graphics2D g2 = (Graphics2D)g;

    // Draw the background stars
    for(int i=0; i < stars.size();i++)
    {
      g2.drawImage(((Star)stars.get(i)).getSprite().getImage(), ((Star)stars.get(i)).getPositionX(), ((Star)stars.get(i)).getPositionY(), null);
    }

    // Draw the lasers
    for(int i=0; i < lasers.size();i++)
    {
      g2.drawImage(((Laser)lasers.get(i)).getSprite().getImage(), ((Laser)lasers.get(i)).getPositionX(), ((Laser)lasers.get(i)).getPositionY(), null);
    }

    // Draw the second player ship
    if( player2Alive )
      g2.drawImage(player2.getSprite().getImage(), player2.getPositionX(), player2.getPositionY(), null);

    // Draw the player ship
    if( player1Alive )
      g2.drawImage(player.getSprite().getImage(), player.getPositionX(), player.getPositionY(), null);

    // Draw the boss
    if(secondcounter >= 240)
      g2.drawImage((boss).getSprite().getImage(), boss.getPositionX(), boss.getPositionY(), null);

    //Draw enemy lasers
    for(int i=0; i < enlasers.size();i++)
    {
      g2.drawImage(((Laser)enlasers.get(i)).getSprite().getImage(), ((Laser)enlasers.get(i)).getPositionX(), ((Laser)enlasers.get(i)).getPositionY(), null);
    }

    //Draw the enemy ships
    for(int i=0; i < enemy.size();i++)
    {
      g2.drawImage(((EnemyShip)enemy.get(i)).getSprite().getImage(), ((EnemyShip)enemy.get(i)).getPositionX(), ((EnemyShip)enemy.get(i)).getPositionY(), null);
    }

    //Draw the powerups
    for(int i=0; i < dropObject.size();i++)
    {
      g2.drawImage(((Powerup)dropObject.get(i)).getSprite().getImage(), ((Powerup)dropObject.get(i)).getPositionX(), ((Powerup)dropObject.get(i)).getPositionY(), null);
    }

    // Check if player 1 is Alive
    if( player1Alive )
    {
      // Draw Fuel for player 1
      g2.setColor(new Color(255-(counter.getFuel()*255/1000),(counter.getFuel()*255/1000),0));
      g2.drawRect(20,630,300,15);
      g2.fillRect(20,630,(int)(counter.getFuel()*.30),15);

      // Draw Points for player 1
      g2.drawString(""+counter.getScore(),335,642);

      // Draw the amount of lives player 1 has
      for(int i=0;i<counter.getLives();i++)
      {
    	//g2.drawImage(new ImageIcon("c:/images/icon.gif").getImage(), 20+25*i, 600, null);
        g2.drawImage(new ImageIcon("images/icon.gif").getImage(), 20+25*i, 600, null);
      }
    }

    // If there is 2 players, draw its fuel, points, and lives
    if( player2Alive)
    {
      // Draw Fuel for player 2
      g2.setColor(new Color(255-(counter2.getFuel()*255/1000),(counter2.getFuel()*255/1000),0));
      g2.drawRect(500,630,300,15);
      g2.fillRect(500,630,(int)(counter2.getFuel()*.30),15);

      // Draw Points for player 2
      g2.drawString(""+counter2.getScore(),815,642);

      // Draw the amount of lives player 2 has
      for(int i=0;i<counter2.getLives();i++)
      {
        //g2.drawImage(new ImageIcon("c:/images/iconb.gif").getImage(), 500+25*i, 600, null);
    	  g2.drawImage(new ImageIcon("images/iconb.gif").getImage(), 500+25*i, 600, null);
      }
    }

    // Draw the Titles of each wave for 1 second each
    g2.setColor(Color.orange);
    Font myFont = new Font("fixedsys",Font.BOLD,30);
    g2.setFont(myFont);
    if(secondcounter <=1)
    {
      g2.drawString("WAVE 1",400,300);
    }
    if(secondcounter >= 60 && secondcounter <= 61)
    {
      g2.drawString("WAVE 2",400,300);
    }
    if(secondcounter >= 120 && secondcounter <= 121)
    {
      g2.drawString("WAVE 3",400,300);
    }
    if(secondcounter >= 180 && secondcounter <= 181)
    {
      g2.drawString("WAVE 4",400,300);
    }
    if(secondcounter >= 240 && secondcounter <= 241)
    {
      g2.drawString("FINAL WAVE",370,300);
    }

    // If paused, draw a paused string
    myFont = new Font("fixedsys",Font.BOLD,80);
    g2.setFont(myFont);
    if(Display.getPaused())
    {
      g2.drawString("PAUSED",300,350);
    }
  }

  // --- Accessor Methods for Environment Dimensions --- //
  public int getWidth()  {return WIDTH;}
  public int getHeight() {return HEIGHT;}

  // --- Accessor Methods for Mouse Position --- //
  public static int  getMouseX() {return mouseX;}
  public static int  getMouseY() {return mouseY;}

  public static int  getMouseClickedX() {return mouseClickedX;}
  public static int  getMouseClickedY() {return mouseClickedY;}

  // --- Mouse Drag, Move, and Pressed Methods --- //
  public void mouseDragged(MouseEvent e)
  {
    mouseX = e.getX();
    mouseY = e.getY();
  }

  public void mouseMoved(MouseEvent e)
  {
    mouseX = e.getX();
    mouseY = e.getY();
  }

  public void mousePressed(MouseEvent e)
  {
    mouseClickedX = e.getX();
    mouseClickedY = e.getY();
    fireLaser(player, counter, player.getPositionX(), player.getPositionY());
  }

  public void mouseExited  (MouseEvent e)
  {
    Display.pause();
    repaint();
  }

  public void mouseEntered (MouseEvent e)
  {
    Display.unpause();
  }

  // --- Ignored Mouse Listeners --- //
  public void mouseReleased(MouseEvent e) {}
  public void mouseClicked (MouseEvent e) {}

  // --- Step method every second --- //
  public void step()
  {
    // Increment the second counter
    secondcounter++;

    // Create enemies up to a maximum of 10
    Random rand = new Random();
    if(numEnemies<10)
    {
      enemy.add(new EnemyShip(rand.nextInt(WIDTH),0,this,armorvalue));
      numEnemies++;
    }

    // Create a boss at the beginning of the final wave
    if(secondcounter==240)
      boss = new Boss(WIDTH/2-125, -150, this, 300);

    // Increment the armor value by 1 and chance to shoot by 1.5 of all new ships every new wave
    if(secondcounter%60==0 && secondcounter > 0)
    {
      armorvalue++;
      enChance=(int)(enChance/1.5);
    }

    // Set the armor of all normal enemy ships to 2 while the boss is out
    if(secondcounter>=240)
    {
	armorvalue = 2;
    }

    // Subtract 2 fuel every second and check to make sure they don't lose a fuel tank
    if(player1Alive)
    {
      counter.subtractFuel(2);
      counter.checkFuel();
      if(counter.getLives() < 0)
      {
        player1Alive = false;
      }
    }
    if(player2Alive)
    {
      counter2.subtractFuel(2);
      counter2.checkFuel();
      if(counter2.getLives()<0)
      {
        player2Alive = false;
      }
    }

    // Change boss direction after he moves down for 3 seconds
    if(secondcounter==243)
      boss.setDirection(1);

    // Change boss direction after he moves to the right the first time from the middle
    if(secondcounter==245)
	boss.setDirection(2);

    // Change the boss direction from left to right every 4 seconds
    if(secondcounter>=249 && (secondcounter + 3)%4 == 0)
	boss.setDirection(boss.getDirection()+1);

    // Loop Music after 176 seconds
    //if( secondcounter%176 == 0 && secondcounter < 240)
      //startMusic();

    // Start Boss Music
    if( secondcounter == 240 )
      startBossMusic();

  }

  // --- Step Method every .05 seconds --- //
  public void step2()
  {
    // Check if both players are out of fuel, call game over sequence
    if( !player1Alive && !player2Alive )
      gameOver();

    // Move all the objects on ths screen
    moveStuff();

    // Delete all the objects that are off the screen
    deleteStuff();

    if(player1Alive)
    {
      // Check collision with objects on the screen for player 1
      checkStuff(player, counter, true);
      // Change palyer 1's ship image
      player.cycleImage();
    }

    if(player2Alive)
    {
      // Check collision with objects on the screen for player 2
      checkStuff(player2, counter2, false);
      // Change player 2's ship image
      player2.cycleImage();
    }

    // Cylce the boss's image if he has 50 armor or less
    if(secondcounter >= 240 && boss.getArmor() <= 50)
      boss.cycleImage();

    // Random chance for each enemy to fire a laser
    Random rand = new Random();
    for(int i=0;i<enemy.size();i++)
    {
      if(rand.nextInt(enChance)==0)
        fireEnemyLaser(((EnemyShip)enemy.get(i)).getPositionX(), ((EnemyShip)enemy.get(i)).getPositionY());
    }

    // Random Chance for Boss to fire a laser
    Random rand2 = new Random();
    if(secondcounter>=240)
      if(rand.nextInt(10)==0)
        fireBossLaser(boss.getPositionX(), boss.getPositionY());

    // Check if there is laser collision with enemy ships
    for(int i=0;i<lasers.size();i++)
    {
      for(int j=0;j<enemy.size();j++)
      {
        int lasX = ((Laser)lasers.get(i)).getPositionX();
        int lasY = ((Laser)lasers.get(i)).getPositionY();
        int enX = ((EnemyShip)enemy.get(j)).getPositionX();
        int enY = ((EnemyShip)enemy.get(j)).getPositionY();

        if(lasX >= (enX -4) && lasX <= (enX+32) && lasY >= (enY-10) && lasY <= (enY+32)){

          // Play sound effect for hitting a ship with a laser
//          try{
//            InputStream in = new FileInputStream("c:/sounds/beep0.wav");
//            AudioStream as = new AudioStream(in);
//            AudioPlayer.player.start(as);
//          }catch(FileNotFoundException e){
//          }catch(IOException e){}

          // Remove the laser that collides
          lasers.remove(i);
          i--;
          // Decrease the enemy ships's armor by 1
          ((EnemyShip)enemy.get(j)).decreaseArmor();
          // If enemy ship's armor is at 0 then delete it and drop a powerup
          if(((EnemyShip)enemy.get(j)).getArmor()==0)
          {
            dropObject.add(((EnemyShip)enemy.get(j)).turnToObject());
            enemy.remove(j);
            j--;
            numEnemies--;
          }
          break;
        }
      }
    }

    // Check if player 1 has no lives left
    if(counter.getLives()<0)
      player1Alive = false;
    // Check if player 2 has no lives left
    if(counter2.getLives()<0)
      player2Alive = false;

    repaint();
  }

  //Check Collision for player
  public void checkStuff(PlayerShip p, Counter c, boolean whichPlayer)
  {
    //Check if there is laser collision with player ship 1 or 2
    for(int i=0;i<enlasers.size();i++)
    {
      int lasX = ((Laser)enlasers.get(i)).getPositionX();
      int lasY = ((Laser)enlasers.get(i)).getPositionY();
      int plX = p.getPositionX();
      int plY = p.getPositionY();

      if(lasX >= (plX -4) && lasX <= (plX+50) && lasY >= (plY-10) && lasY <= (plY+50)){
        p.setAnimation(2);
        enlasers.remove(i);
        i--;
        c.subtractFuel(100);
        c.checkFuel();
      }
    }

    //Check collision with powerups
    for(int i=0;i<dropObject.size();i++)
    {
      int plX = p.getPositionX();
      int plY = p.getPositionY();
      int pwX = ((Powerup)dropObject.get(i)).getPositionX();
      int pwY = ((Powerup)dropObject.get(i)).getPositionY();

      if(plX >= (pwX -50) && plX <= (pwX+16) && plY >= (pwY-50) && plY <= (pwY+16)){
        checkPowerup(p, c, ((Powerup)dropObject.get(i)).getValue());
        dropObject.remove(i);
        i--;
      }
    }

    //Check collision with enemy ships to player ship
    for(int i=0;i<enemy.size();i++)
    {
      int plX = p.getPositionX();
      int plY = p.getPositionY();
      int enX = ((EnemyShip)enemy.get(i)).getPositionX();
      int enY = ((EnemyShip)enemy.get(i)).getPositionY();

      if(plX >= (enX -50) && plX <= (enX+32) && plY >= (enY-50) && plY <= (enY+32)){
        p.setAnimation(2);
        enemy.remove(i);
        i--;
        numEnemies--;
        c.subtractFuel(500);
        c.checkFuel();
      }
    }

    // Check Collision with Boss
    if(secondcounter>=240)
    {
      for(int i=0;i<lasers.size();i++)
      {
        int lasX = ((Laser)lasers.get(i)).getPositionX();
        int lasY = ((Laser)lasers.get(i)).getPositionY();
        int bossX = boss.getPositionX();
        int bossY = boss.getPositionY();

	if(lasX >= (bossX -4) && lasX <= (bossX+250) && lasY >= (bossY-10) && lasY <= (bossY+150)){
	  boss.decreaseArmor();
          lasers.remove(i);
          i--;
	  if(boss.getArmor()==0)
	  {
	    c.addToScore(10000);
            dropObject.add(new Fuel3(boss.getPositionX()+120,boss.getPositionY()+50));
            startOver();
	  }
	}
      }
    }

    // Check powerup Timers
    checkPowerupTimers(p);
  }

  // --- Check powerup timers --- //
  public void checkPowerupTimers(PlayerShip p)
  {
    // Count Powerups
    if(p.getB2Count()>0)
    {
      p.setB2Count(p.getB2Count()-1);
      if(p.getB2Count()==0)
        p.setB2(false);
    }
    if(p.getB3Count()>0)
    {
      p.setB3Count(p.getB3Count()-1);
      if(p.getB3Count()==0)
        p.setB3(false);
    }
    if(p.getSpeedCount()>0)
    {
      p.setSpeedCount(p.getSpeedCount()-1);
      if(p.getSpeedCount()==0)
        p.setSpeed(false);
    }
  }

  // --- Move objects on the screen --- //
  public void moveStuff()
  {
    //Move the stars
    for(int i=0;i<stars.size();i++)
    {
      ((Star)stars.get(i)).move();
    }

    //Move the ship based on mouse
    if(mouseX > 840)
      player.setPositionX(840);
    else if(mouseX < 10)
      player.setPositionX(10);
    else
      player.setPositionX(mouseX);

    //Move the enemy ships
    Random rand = new Random();
    for(int i=0;i<enemy.size();i++)
    {
      ((EnemyShip)enemy.get(i)).move((rand.nextInt(16)-8),5);

      //Make sure they're not off the screen
      if(((EnemyShip)enemy.get(i)).getPositionX()<20)
        ((EnemyShip)enemy.get(i)).setPositionX(20);
      if(((EnemyShip)enemy.get(i)).getPositionX()>(WIDTH-32-20))
        ((EnemyShip)enemy.get(i)).setPositionX(WIDTH-32-20);
    }

    //Move the boss
    if(secondcounter>=240)
      boss.move();

    //Move the Player's Lasers
    for(int i=0;i<lasers.size();i++)
    {
      ((Laser)lasers.get(i)).move();
    }

    //Move the Enemy's Lasers
    for(int i=0;i<enlasers.size();i++)
    {
      ((Laser)enlasers.get(i)).move();
    }

    //Move a dropped Object
    for(int i=0;i<dropObject.size();i++)
    {
      ((Powerup)dropObject.get(i)).move();
    }

    //Rotate Stars
    for(int i=0;i<dropObject.size();i++)
    {
      if(((Powerup)(dropObject.get(i))).getValue()==0)
        ((PowerStar)(dropObject.get(i))).cycleImage();
    }
  }

  // --- Delete objects off the screen --- //
  public void deleteStuff()
  {
    // Delete enemies off the screen
    for(int i=0;i<enemy.size();i++)
    {
      if(((EnemyShip)enemy.get(i)).getPositionY()>HEIGHT)
      {
        enemy.remove(i);
        numEnemies--;
        i--;
      }
    }

    // Delete lasers off the screen
    for(int i=0;i<lasers.size();i++)
    {
      if(((Laser)lasers.get(i)).getPositionY()<100)
      {
        lasers.remove(i);
        i--;
      }
    }

    // Delete enenmy lasers off the screen
    for(int i=0;i<enlasers.size();i++)
    {
      if(((Laser)enlasers.get(i)).getPositionY()>HEIGHT)
      {
        enlasers.remove(i);
        i--;
      }
    }

    // Delete powerups off the screen
    for(int i=0;i<dropObject.size();i++)
    {
      if(((Powerup)dropObject.get(i)).getPositionY()>HEIGHT)
      {
        dropObject.remove(i);
        i--;
      }
    }

    // Move stars off screen to top
    for(int i=0;i<stars.size();i++)
    {
      if(((Star)stars.get(i)).getPositionY()>HEIGHT)
      {
        ((Star)stars.get(i)).setPositionY(0);
        i--;
      }
    }
  }

  // --- Fire Player's Laser --- //
  public void fireLaser(PlayerShip p, Counter c, int x, int y)
  {
    if(!Display.getPaused())
    {
      /**try{
        InputStream in = new FileInputStream("c:/sounds/zap.wav");
        AudioStream as = new AudioStream(in);
        AudioPlayer.player.start(as);
      }catch(FileNotFoundException e){
      }catch(IOException e){}*/

      int laserSpeed = -10;
      if(p.getSpeed())
        laserSpeed = -20;
      if(p.getB2())
      {
        lasers.add( new Laser(x+10,y,laserSpeed) );
        lasers.add( new Laser(x+36,y,laserSpeed) );
      }
      else if(p.getB3())
      {
        lasers.add( new Laser(x+10,y,laserSpeed) );
        lasers.add( new Laser(x+23,y,laserSpeed) );
        lasers.add( new Laser(x+36,y,laserSpeed) );
      }
      else
        lasers.add( new Laser(x+23,y,laserSpeed) );

      // Subtract 1 fuel
      c.subtractFuel(1);
      c.checkFuel();
    }
  }

  // --- Fire an Enemy's Laser --- //
  public void fireEnemyLaser(int x, int y)
  {
    enlasers.add( new Laser(x+14,y+32,10) );
    //((Laser)enlasers.get(enlasers.size()-1)).setSprite( new ImageIcon("c:/images/laser2.gif") );
    ((Laser)enlasers.get(enlasers.size()-1)).setSprite( new ImageIcon("images/laser2.gif") );
  }

  // --- Fire a Boss's Laser --- //
  public void fireBossLaser(int x, int y)
  {
    enlasers.add( new Laser(x+105, y+91, 10) );
    //((Laser)enlasers.get(enlasers.size()-1)).setSprite( new ImageIcon("c:/images/laser2.gif") );
    ((Laser)enlasers.get(enlasers.size()-1)).setSprite( new ImageIcon("images/laser2.gif") );
    enlasers.add( new Laser(x+141, y+91, 10) );
    ((Laser)enlasers.get(enlasers.size()-1)).setSprite( new ImageIcon("images/laser2.gif") );
    if(boss.getArmor()<=150)
    {
	enlasers.add( new Laser(x+76, y+73, 10) );
	((Laser)enlasers.get(enlasers.size()-1)).setSprite( new ImageIcon("images/laser3.gif") );
	enlasers.add( new Laser(x+76, y+73, 10) );
	((Laser)enlasers.get(enlasers.size()-1)).setSprite( new ImageIcon("images/laser3.gif") );
	enlasers.add( new Laser(x+168, y+73, 10) );
	((Laser)enlasers.get(enlasers.size()-1)).setSprite( new ImageIcon("images/laser3.gif") );
	enlasers.add( new Laser(x+168, y+73, 10) );
      ((Laser)enlasers.get(enlasers.size()-1)).setSprite( new ImageIcon("images/laser3.gif") );
    }
  }

  // --- Make the Powerup collected work --- //
  public void checkPowerup(PlayerShip p, Counter c, int value)
  {
    switch(value)
    {
      case 0:
//        try{
//          InputStream in = new FileInputStream("c:/sounds/beep1.wav");
//          AudioStream as = new AudioStream(in);
//          AudioPlayer.player.start(as);
//        }catch(FileNotFoundException e){
//        }catch(IOException e){}
        c.addToScore(100);
        c.addFuel(10);
        break;
      case 1:
        p.setAnimation(1);
//        try{
//          InputStream in = new FileInputStream("c:/sounds/blaster.wav");
//          AudioStream as = new AudioStream(in);
//          AudioPlayer.player.start(as);
//        }catch(FileNotFoundException e){
//        }catch(IOException e){}
        c.addToScore(500);
        p.setB2(true);
        if(p.getB3())
        {
          p.setB3(false);
          p.setB3Count(0);
        }
        p.setB2Count(300);
        break;
      case 2:
        p.setAnimation(1);
//        try{
//          InputStream in = new FileInputStream("c:/sounds/blaster.wav");
//          AudioStream as = new AudioStream(in);
//          AudioPlayer.player.start(as);
//        }catch(FileNotFoundException e){
//        }catch(IOException e){}
        c.addToScore(750);
        p.setB3(true);
        if(p.getB2())
        {
          p.setB2(false);
          p.setB2Count(0);
        }
        p.setB3Count(300);
        break;
      case 3:
//        try{
//          InputStream in = new FileInputStream("c:/sounds/fuel.wav");
//          AudioStream as = new AudioStream(in);
//          AudioPlayer.player.start(as);
//        }catch(FileNotFoundException e){
//        }catch(IOException e){}
        p.setAnimation(1);
        c.addFuel(500);
        c.addToScore(500);
        break;
      case 4:
//        try{
//          InputStream in = new FileInputStream("c:/sounds/fuel.wav");
//          AudioStream as = new AudioStream(in);
//          AudioPlayer.player.start(as);
//        }catch(FileNotFoundException e){
//        }catch(IOException e){}
        p.setAnimation(1);
        c.addFuel(1000);
        c.addToScore(750);
        break;
      case 5:
//        try{
//          InputStream in = new FileInputStream("c:/sounds/1up.mid");
//          AudioStream as = new AudioStream(in);
//          AudioPlayer.player.start(as);
//        }catch(FileNotFoundException e){
//        }catch(IOException e){}
        p.setAnimation(1);
        c.addLife();
        c.addToScore(1000);
        break;
      case 6:
        p.setAnimation(1);
//        try{
//          InputStream in = new FileInputStream("c:/sounds/beep2.wav");
//          AudioStream as = new AudioStream(in);
//          AudioPlayer.player.start(as);
//        }catch(FileNotFoundException e){
//        }catch(IOException e){}
        p.setSpeed(true);
        c.addToScore(500);
        p.setSpeedCount(300);
        break;
    }
  }

  // --- Check if a key is pressed to move the second player --- //
  public void keyCheck(KeyEvent e)
  {
    if(player2Alive)
    {
      int key = e.getKeyCode();
      int mod = ((InputEvent)e).getModifiers();

      // Move the player right if the right arrow key is pressed
      if( key == e.VK_RIGHT )
        if( mod == e.SHIFT_MASK )
          player2.relativeMove(25,0);
        else
          player2.relativeMove(10,0);

      // Move the player left if the left arrow key is pressed
      if( key == e.VK_LEFT )
        if( mod == e.SHIFT_MASK )
          player2.relativeMove(-25,0);
        else
          player2.relativeMove(-10,0);

      // Fire a laser for player 2 if space is pressed
      if( key == e.VK_SPACE )
        fireLaser(player2, counter2, player2.getPositionX(), player2.getPositionY());

      // Check to make sure player 2 is not off the screen
      if(player2.getPositionX() > 840)
        player2.setPositionX(840);
      else if (player2.getPositionX() < 10)
        player2.setPositionX(10);
    }
  }

  // --- Stop the music if the game is restarted --- //
  public void stopMusic()
  {
//    try{
//      AudioPlayer.player.stop(cas);
//    }catch(IllegalStateException e){}
  }

  // --- Start the music --- //
  public void startMusic()
  {
//    try{
//      InputStream in = new FileInputStream("c:/sounds/obsession.mid");
//      cas = new AudioStream(in);
//      AudioPlayer.player.start(cas);
//    }catch(FileNotFoundException e){
//    }catch(IOException e){}
  }

  // --- When either one or both players run out of fuel, call the game over sequence --- //
  public void gameOver()
  {
    stopMusic();
    Display.checkHighScore(counter.getScore(), "Player 1");
    if(is2play)
    {
      Display.checkHighScore(counter2.getScore(), "Player 2");
    }
  }

  // --- Reset variables when a new game is looped --- //
  public void startOver()
  {
    secondcounter = 0;
    armorvalue = 1;
    enChance = 200;
    stopMusic();
    startMusic();
  }

  // --- Start the boss music --- //
  public void startBossMusic()
  {
    stopMusic();
//    try{
//      InputStream in = new FileInputStream("c:/sounds/Kevtech.mid");
//      cas = new AudioStream(in);
//      AudioPlayer.player.start(cas);
//    }catch(FileNotFoundException e){
//    }catch(IOException e){}
  }
}
