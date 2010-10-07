/**
 * Title:        Invazn!
 * Description:  An uber game that is similar to Galaga.
 * Copyright:    2005
 * Company:      noobwareinc.
 * @author       Jeremy Badessa, Mike Molignano, Chris Steele
 * @version 1.0
 */

package invazn;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Display extends JFrame implements ActionListener, KeyListener
{
  //Variables
  private Environment env;
  private SplashScreen s;
  private static boolean isPaused;
  private static boolean multiplayer = false;
  private static boolean isStarted = false;
  private static boolean gameOver = false;
  private Container content;

  //Menu Items
  private JMenuItem[] Game = new JMenuItem [5];
  private JRadioButtonMenuItem[] Players = new JRadioButtonMenuItem[2];
  private JMenuItem[] Help = new JMenuItem [2];

  //Menus
  private JMenu gameMenu;
  private JMenu playersMenu;
  private JMenu helpMenu;
  private JMenuBar menuBar;
  private static Timer tmr;
  private static Timer tmrMove;

  //Constructor
  public Display()
  {
    isPaused = false;
    s = new SplashScreen();

    //Initialize Menu Items
    Game[0] = new JMenuItem ("Start New Game", KeyEvent.VK_N);
    Game[1] = new JMenuItem ("Pause Game", KeyEvent.VK_U);
    Game[2] = new JMenuItem ("Single High Scores", KeyEvent.VK_S);
    Game[3] = new JMenuItem ("Multi High Scores", KeyEvent.VK_M);
    Game[4] = new JMenuItem ("Quit Game", KeyEvent.VK_Q);
    Players[0] = new JRadioButtonMenuItem ("Start 1 Player");
    Players[1] = new JRadioButtonMenuItem ("Start 2 Players");
    Help[0] = new JMenuItem ("Instructions", KeyEvent.VK_I);
    Help[1] = new JMenuItem ("About Info", KeyEvent.VK_A);

    //Set Accelerators for Menu Items
    Game[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
    Game[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
    Game[2].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F7, 0));
    Game[3].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F8, 0));
    Game[4].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F4, ActionEvent.ALT_MASK));
    Players[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0));
    Players[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F6, 0));
    Help[0].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
    Help[1].setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F12, 0));

    //Initialize Menus
    gameMenu = new JMenu ("Game");
    gameMenu.setMnemonic(KeyEvent.VK_G);
    playersMenu = new JMenu ("Players");
    playersMenu.setMnemonic(KeyEvent.VK_P);
    helpMenu = new JMenu ("Help");
    helpMenu.setMnemonic(KeyEvent.VK_H);
    menuBar = new JMenuBar();

    //Add menu items to menu and add actionListeners
    for (int i = 0; i < Game.length; i++)
    {
      gameMenu.add(Game[i]);
      Game[i].addActionListener(this);
    }
    ButtonGroup group = new ButtonGroup();
    Players[0].setSelected(true);
    Players[0].setMnemonic(KeyEvent.VK_1);
    group.add(Players[0]);
    playersMenu.add(Players[0]);
    Players[0].addActionListener(this);
    Players[1].setMnemonic(KeyEvent.VK_2);
    group.add(Players[1]);
    playersMenu.add(Players[1]);
    Players[1].addActionListener(this);
    for (int i = 0; i < Help.length; i++)
    {
      helpMenu.add(Help[i]);
      Help[i].addActionListener(this);
    }
    this.addKeyListener(this);

    //Add menus to the bar
    menuBar.add(gameMenu);
    menuBar.add(playersMenu);
    menuBar.add(helpMenu);

    //Add the menu bar to the window
    setJMenuBar (menuBar);

    //Set Window Stuff
    content = this.getContentPane();
    content.setLayout(new BorderLayout());
    content.add(s, BorderLayout.CENTER);
    this.setTitle("Invazn!");
    this.pack();
    //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
  }

  public void actionPerformed (ActionEvent e)
  {
    JMenuItem menuItemObj = (JMenuItem)(e.getSource());
    if (menuItemObj == Game[0])
      startNewGame();
    else if (menuItemObj == Game[1])
    {
      pauseGame();
      if(isStarted)
        env.repaint();
    }
    else if (menuItemObj == Game[2])
      displayHighScores();
    else if (menuItemObj == Game[3])
      displayMultiScores();
    else if (menuItemObj == Game[4])
      System.exit(1);
    else if (menuItemObj == Players[0])
      startOnePlayer();
    else if (menuItemObj == Players[1])
      startTwoPlayer();
    else if (menuItemObj == Help[0])
      displayInstructions();
    else if (menuItemObj == Help[1])
      displayAboutInfo();
  }

  public static boolean getPaused()
  {
    return isPaused;
  }

  private void startNewGame()
  {
    if (isStarted)
    {
      content.remove(env);
      env.stopMusic();
      tmr.stop();
      tmrMove.stop();
      tmr = null;
      tmrMove = null;
    }
    content.remove(s);
    s.stopMusic();
    env = new Environment(multiplayer);
    content.add(env, BorderLayout.CENTER);

    //Timer for the Creating ships
    ActionListener taskPerformer = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        env.step();

      }
    };
    tmr = new Timer(1000,taskPerformer);
    tmr.start();

    //Timer for moving ships
    ActionListener taskMover = new ActionListener() {
      public void actionPerformed(ActionEvent evt) {
        env.step2();
      }
    };
    tmrMove = new Timer(50,taskMover);
    tmrMove.start();

    this.pack();
    this.setSize(900,700);
    isPaused = false;
    isStarted = true;
    gameOver = false;
  }

  public void pauseGame()
  {
    if (!isStarted)
      return;
    if (!isPaused)
      pause();
    else
    {
      unpause();
    }
  }

  public static void checkHighScore (int score, String player)
  {
    gameOver = true;
    tmr.stop();
    tmrMove.stop();
    int position = checkScore(score);
    if (position == -1)
    {
      JOptionPane.showMessageDialog(null, player + " Game Over. Your score was " + score + ". Please play again.", "Game Over", JOptionPane.PLAIN_MESSAGE);
      if (!multiplayer)
        displayHighScores();
      else
      {
        if(player.equals("Player 2"))
          displayMultiScores();
      }
      return;
    }
    String name = "";
    do
      name = JOptionPane.showInputDialog(null, player + " High Score! Please enter your name.", "High Score", JOptionPane.PLAIN_MESSAGE);
    while (name == null);
    if (!multiplayer)
    {
      IO.inputToFile(IO.scoresPath, position, name, score);
      displayHighScores();
    }
    else
    {
      IO.inputToFile(IO.multiPath, position, name, score);
      if(player.equals("Player 2"))
        displayMultiScores();
    }
  }

  private static int checkScore(int score)
  {
    int[] scores;
    if (!multiplayer)
      scores = IO.getScores(IO.scoresPath);
    else
      scores = IO.getScores(IO.multiPath);
    for (int i = 0; i < scores.length; i++)
    {
      if (score >= scores[i])
        return i;
    }
    return -1;
  }

  private static void displayHighScores()
  {
    if (isStarted && !isPaused)
      pause();
    HighScore scoreDisplay = new HighScore();
    scoreDisplay.setSize(230,350);
    scoreDisplay.setIconImage(new ImageIcon("images\\icon.gif").getImage());
    //scoreDisplay.setIconImage(new ImageIcon("C:\\images\\icon.gif").getImage());
    scoreDisplay.setResizable(false);
    scoreDisplay.setVisible(true);
  }

  private static void displayMultiScores()
  {
    if (isStarted && !isPaused)
      pause();
    MultiScore scoreDisplay = new MultiScore();
    scoreDisplay.setSize(230,350);
    scoreDisplay.setIconImage(new ImageIcon("images\\icon.gif").getImage());
    //scoreDisplay.setIconImage(new ImageIcon("C:\\images\\icon.gif").getImage());
    scoreDisplay.setResizable(false);
    scoreDisplay.setVisible(true);
  }


  private void startOnePlayer()
  {
    multiplayer = false;
    startNewGame();
  }

  private void startTwoPlayer()
  {
    multiplayer = true;
    startNewGame();
  }

  private void displayInstructions()
  {
    if (isStarted && !isPaused)
      pause();
    Instructions instructDisplay = new Instructions();
    instructDisplay.setSize(Instructions.textAreaWidth + 10,
      Instructions.textAreaHeight + 80);
    //instructDisplay.setIconImage(new ImageIcon("C:\\images\\icon.gif").getImage());
    instructDisplay.setIconImage(new ImageIcon("images\\icon.gif").getImage());
    instructDisplay.setResizable(false);
    instructDisplay.setVisible(true);
  }

  private void displayAboutInfo()
  {
    if (isStarted && !isPaused)
      pause();
    AboutInfo infoDisplay = new AboutInfo();
    infoDisplay.setSize(250,300);
    //infoDisplay.setIconImage(new ImageIcon("C:\\images\\icon.gif").getImage());
    infoDisplay.setIconImage(new ImageIcon("images\\icon.gif").getImage());
    infoDisplay.setResizable(false);
    infoDisplay.setVisible(true);
  }

  public static void pause()
  {
    tmr.stop();
    tmrMove.stop();
    isPaused = true;
  }

  public static void unpause()
  {
    if(gameOver)
      return;
    tmr.start();
    tmrMove.start();
    isPaused = false;
  }

  //Keyboard Methods
  public void keyTyped(KeyEvent e)
  {}

  public void keyPressed(KeyEvent e)
  {
    if (isStarted)
      env.keyCheck(e);
  }

  public void keyReleased(KeyEvent e)
  {}

  public static void main(String[] args)
  {
    Display window = new Display();
    window.setSize(900,700);
    //window.setIconImage(new ImageIcon("C:\\images\\icon.gif").getImage());
    window.setIconImage(new ImageIcon("images\\icon.gif").getImage());
    window.setResizable(false);
    window.setVisible(true);
  }
}
