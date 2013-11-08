package statistics;

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
	private String firstname;
	private String lastname;
	private Integer gamesplayed;
	private Integer gameswon;
	
	//Constructor
	Player(String fname,String lname)
	{
		this.firstname = new String(fname);
		this.lastname = new String(lname);
		gamesplayed = new Integer(0);
		gameswon = new Integer(0);
	}
	
	//Name Getter
	public String name()
	{
		return new String(firstname + " " +lastname);
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
	
	public ArrayList<Player> fetch_players()         //Function to fetch the list of the players
	{
		ObjectInputStream input = null;
		ArrayList<Player> players = new ArrayList<Player>();
		try
		{
			input = new ObjectInputStream(new FileInputStream("chessgamedata.dat"));
			while(input.available()>0)
				players.add((Player)input.readObject());
			input.close();
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
		}
		return players;
	}
	
	private void Update_Player()            //Function to update the statistics of a player
	{
		ObjectInputStream input = null;
		ObjectOutputStream output = null;
		Player temp_player;
		File inputfile = new File("gamefiles/chessgamedata.dat");
		File outputfile = new File("gamefiles/tempfile");
		boolean playerdonotexist;
		try
		{
			if(inputfile.exists()==false)
				inputfile.createNewFile();
			if(outputfile.exists()==false)
				outputfile.createNewFile();
			input = new ObjectInputStream(new FileInputStream(inputfile));
			output = new ObjectOutputStream(new FileOutputStream(outputfile));
			playerdonotexist=true;
			while(input.available()>0)
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
			if(playerdonotexist)
				output.writeObject(this);
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
		finally
		{
			try {input.close();output.close();}
			catch (IOException e) {e.printStackTrace();}
		}
	}
}
