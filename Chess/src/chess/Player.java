package chess;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.swing.JOptionPane;


/**
 * This is the Player Class
 * It provides the functionality of keeping track of all the users
 * Objects of this class is updated and written in the Game's Data Files after every Game
 *
 */
public class Player implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	private Integer gamesplayed;
	private Integer gameswon;
	
	//Constructor
	public Player(String name)
	{
		this.name = name.trim();
		//this.lname = lname.trim();
		gamesplayed = new Integer(0);
		gameswon = new Integer(0);
	}
	
	//Name Getter
	public String name()
	{
		return name;
	}
	
	//Returns the number of games played
	public Integer gamesplayed()
	{
		return gamesplayed;
	}
	
	//Returns the number of games won
	public Integer gameswon()
	{
		return gameswon;
	}
	
	//Calculates the win percentage of the player
	public Integer winpercent()
	{
		return new Integer((gameswon*100)/gamesplayed);
	}
	
	//Increments the number of games played
	public void updateGamesPlayed()
	{
		gamesplayed++;
	}
	
	//Increments the number of games won
	public void updateGamesWon()
	{
		gameswon++;
	}
	
	
	public static ArrayList<Player> fetch_players()         //Function to fetch the list of the players
	{
		Player tempplayer;
		ObjectInputStream input = null;
		ArrayList<Player> players = new ArrayList<Player>();
		try
		{
			File infile = new File(System.getProperty("user.dir")+ File.separator + "chessgamedata.dat");
			input = new ObjectInputStream(new FileInputStream(infile));
			try
			{
				while(true)
				{
					tempplayer = (Player) input.readObject();
					players.add(tempplayer);
				}
			}
			catch(EOFException e)
			{
				input.close();
			}
		}
		catch (FileNotFoundException e)
		{
			players.clear();
			return players;
		}
		catch (IOException e)
		{
			e.printStackTrace();
			try {input.close();} catch (IOException e1) {}
			JOptionPane.showMessageDialog(null, "Unable to read the required Game files !!");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Game Data File Corrupted !! Click Ok to Continue Builing New File");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return players;
	}
	
	public void Update_Player()            //Function to update the statistics of a player
	{
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		Player temp_player;
		File inputfile=null;
		File outputfile=null;
		try
		{
			inputfile = new File(System.getProperty("user.dir")+ File.separator + "chessgamedata.dat");
			outputfile = new File(System.getProperty("user.dir")+ File.separator + "tempfile.dat");
		} catch (SecurityException e)
		{
			JOptionPane.showMessageDialog(null, "Read-Write Permission Denied !! Program Cannot Start");
			System.exit(0);
		} 
		boolean playerdonotexist;
		try
		{
			if(outputfile.exists()==false)
				outputfile.createNewFile();
			if(inputfile.exists()==false)
			{
					output = new ObjectOutputStream(new java.io.FileOutputStream(outputfile,true));
					output.writeObject(this);
			}
			else
			{
				input = new ObjectInputStream(new FileInputStream(inputfile));
				output = new ObjectOutputStream(new FileOutputStream(outputfile));
				playerdonotexist=true;
				try
				{
				while(true)
				{
					temp_player = (Player)input.readObject();
					if (temp_player.name().equals(name()))
					{
						output.writeObject(this);
						playerdonotexist = false;
					}
					else
						output.writeObject(temp_player);
				}
				}
				catch(EOFException e){
					input.close();
				}
				if(playerdonotexist)
					output.writeObject(this);
			}
			inputfile.delete();
			output.close();
			File newf = new File(System.getProperty("user.dir")+ File.separator + "chessgamedata.dat");
			if(outputfile.renameTo(newf)==false)
				System.out.println("File Renameing Unsuccessful");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Unable to read/write the required Game files !! Press ok to continue");
		}
		catch (ClassNotFoundException e) 
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Game Data File Corrupted !! Click Ok to Continue Builing New File");
		}
		catch (Exception e)
		{
			
		}
	}
}
