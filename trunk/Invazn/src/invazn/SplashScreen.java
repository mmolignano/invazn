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

public class SplashScreen extends JPanel
{
  private ImageIcon sprite;

  private static final int WIDTH = 900;
  private static final int HEIGHT = 700;

//  private AudioStream cas;

  //Constructor
  public SplashScreen()
  {
    this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
    this.setBackground(Color.black);
    sprite = new ImageIcon("images/splash.gif");

//    //Play Background Music
//    try{
//      InputStream in = new FileInputStream("c:/sounds/firehawk.mid");
//      cas = new AudioStream(in);
//      AudioPlayer.player.start(cas);
//    }catch(FileNotFoundException e){
//    }catch(IOException e){}
  }

  // --- Calls to repaint the screen --- //
  public void paintComponent(Graphics g)
  {
    super.paintComponent(g);

    // Downcast to Graphics2D
    Graphics2D g2 = (Graphics2D)g;
    g2.drawImage(sprite.getImage(), (WIDTH-300)/2, ((HEIGHT-200)/2)-50, null);
  }

  public void stopMusic()
  {
//    try{
//      AudioPlayer.player.stop(cas);
//    }catch(IllegalStateException e){}
//    try{cas.close();
//    }catch(FileNotFoundException e){
//    }catch(IOException e){}

  }
}
