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

import java.io.*;

public class IO
{
  //Path Variables
  //public static final String scoresPath = "C:\\files\\scores.txt";
  public static final String scoresPath = "files\\scores.txt";
  public static final String multiPath = "files\\multi.txt";
  public static final String tempPath = "files\\temp.txt";
  public static final String defaultPath = "files\\default.txt";
  public static final String instructionsPath = "files\\instructions.txt";

  //Constructor
  public IO()
  {
  }

  //Methods

  //Gets the scores
  public static int[] getScores(String path)
  {
    int[] scores = new int [10];
    try
    {
      FileReader streamRead = new FileReader(path);
      BufferedReader input = new BufferedReader(streamRead);
      StreamTokenizer reader = new StreamTokenizer(input);
      for (int i = 0; i < 10; i++)
        input.readLine();
      for (int i = 0; i < 10; i++)
      {
        reader.nextToken();
        scores[i] = (int)reader.nval;
      }
      input.close();
    }
    catch(FileNotFoundException e)
    {System.out.println("Error. File not found or cannot open.");}
    catch(IOException e)
    {System.out.println("Error reading from file.");}
    return scores;
  }

  //Gets the names
  public static String[] getNames(String path)
  {
    String[] names = new String[10];
    try
    {
      FileReader streamRead = new FileReader(path);
      BufferedReader input = new BufferedReader(streamRead);
      for (int i = 0; i < 10; i++)
      {
        String str = input.readLine();
        names[i] = str;
      }
      input.close();
    }
    catch(FileNotFoundException e)
    {System.out.println("Error. File not found or cannot open.");}
    catch(IOException e)
    {System.out.println("Error reading from file.");}
    return names;
  }

  //Copies the information from one file to another
  public static void copyFile(String path, String path2)
  {
    PrintWriter outputStream=null;
    try
    {
      FileOutputStream streamWrite=new FileOutputStream (path);
      outputStream=new PrintWriter(streamWrite);
    }catch (FileNotFoundException e)
    {
      System.out.println("File not found error.");
    }
    try
    {
      FileReader streamRead=new FileReader(path2);
      BufferedReader input=new BufferedReader(streamRead);
      String line = "";
      while(line!=null)
      {
        line=input.readLine();
        if (line!=null)
          outputStream.println(line);
      }
      streamRead.close();
    }
    catch(FileNotFoundException e)
    {System.out.println("Error. File not found or cannot open.");}
    catch(IOException e)
    {System.out.println("Error reading from file.");}
    outputStream.close();
  }

  public static void inputToFile(String path, int position, String name, int score)
  {
    copyFile(tempPath, path);
    PrintWriter outputStream = null;
    try
    {
      FileOutputStream streamWrite = new FileOutputStream (path);
      outputStream = new PrintWriter (streamWrite);
    }catch (FileNotFoundException e)
    {
      System.out.println("File not found error.");
    }
    try
    {
      FileReader streamRead = new FileReader (tempPath);
      BufferedReader input = new BufferedReader(streamRead);
      StreamTokenizer reader = new StreamTokenizer(input);
      String line = "";
      //Write up to point of insertion of name
      for (int i = 0; i < position; i++)
      {
        line = input.readLine();
        outputStream.println(line);
      }
      //Insert name
      outputStream.println(name);
      //Write up to end of names
      for (int i = position+1; i < 10; i++)
      {
        line = input.readLine();
        outputStream.println(line);
      }
      line = input.readLine();
      int num = 0;
      //Write up to point of insertion of score
      for (int i = 0; i < position; i++)
      {
        reader.nextToken();
        num = (int)reader.nval;
        outputStream.println(num);
      }
      //Insert score
      outputStream.println(score);
      //Write to end of scores
      for (int i = position+1; i < 10; i++)
      {
        reader.nextToken();
        num = (int)reader.nval;
        outputStream.println(num);
      }
      streamRead.close();
    }
    catch(FileNotFoundException e)
    {System.out.println("Error. File not found or cannot open.");}
    catch(IOException e)
    {System.out.println("Error reading from file.");}
    outputStream.close();
  }

  public static String getInstructions()
  {
    String text = "";
    try
    {
      FileReader streamRead = new FileReader(instructionsPath);
      BufferedReader input = new BufferedReader(streamRead);
      StreamTokenizer reader = new StreamTokenizer(input);
      reader.eolIsSignificant(true);
      reader.nextToken();
      while (reader.ttype!=reader.TT_EOF)
      {
        if (reader.ttype==reader.TT_EOL)
        {
          text += "\n";
        }
        else if (reader.ttype==reader.TT_WORD)
        {
          String token = reader.sval;
          text += " " + token;
        }
        else if (reader.ttype==reader.TT_NUMBER)
        {
          int token = (int)reader.nval;
          text += " " + token;
        }
        else
        {
          text += (char)reader.ttype;
        }
        reader.nextToken();
      }
      streamRead.close();
    }
    catch(FileNotFoundException e)
    {System.out.println("Error. File not found or cannot open.");}
    catch(IOException e)
    {System.out.println("Error reading from file.");}
    return text;
  }
}
