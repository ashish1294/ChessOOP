package chess;
import javax.swing.*;

import pieces.*;
import statistics.Player;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.ListIterator;

/**
 * @author Ashish Kedia and Adarsh Mohata
 *
 */


/**
 * This is the Main Class of our project.
 * All GUI Elements are declared, initialized and used in this class itself.
 * It is inherited from the JFrame Class of Java's Swing Library. 
 * 
 */

public class Main extends JFrame implements MouseListener{
	private static final long serialVersionUID = 1L;
	
	//Variable Declaration
	private static final int Height=700;
	private static final int Width=1000;
	private static Rook wr01,wr02,br01,br02;
	private static Knight wk01,wk02,bk01,bk02;
	private static Bishop wb01,wb02,bb01,bb02;
	private static Pawn wp[],bp[];
	private static Queen wq,bq;
	private static King wk,bk;
	private Cell c,previous;
	private Player WhitePlayer;
	private Player BlackPlayer;
	private int chance=0;
	private Cell boardState[][];
	private ArrayList<Cell> destinationlist = new ArrayList<Cell>();
	private JPanel board=new JPanel(new GridLayout(8,8));
	private JPanel controlPanel,score;
	private JSplitPane split;
	private JLabel label;
	private Time timer;
	public static Main Mainboard;
	private static ArrayList<Player> playerlist;
	                     
	public static void main(String[] args){
	
	//variable initialization
	wr01=new Rook("WR01","White_Rook.png",0);
	wr02=new Rook("WR02","White_Rook.png",0);
	br01=new Rook("BR01","Black_Rook.png",1);
	br02=new Rook("BR02","Black_Rook.png",1);
	wk01=new Knight("WK01","White_Knight.png",0);
	wk02=new Knight("WK02","White_Knight.png",0);
	bk01=new Knight("BK01","Black_Knight.png",1);
	bk02=new Knight("BK02","Black_Knight.png",1);
	wb01=new Bishop("WB01","White_Bishop.png",0);
	wb02=new Bishop("WB02","White_Bishop.png",0);
	bb01=new Bishop("BB01","Black_Bishop.png",1);
	bb02=new Bishop("BB02","Black_Bishop.png",1);
	wq=new Queen("WQ","White_Queen.png",0);
	bq=new Queen("BQ","Black_Queen.png",1);
	wk=new King("WK","White_King.png",0,7,3);
	bk=new King("BK","Black_King.png",1,0,3);
	wp=new Pawn[8];
	bp=new Pawn[8];
	for(int i=0;i<8;i++)
	{
		wp[i]=new Pawn("WP0"+(i+1),"White_Pawn.png",0);
		bp[i]=new Pawn("BP0"+(i+1),"Black_Pawn.png",1);
	}
	
	//Setting up the board
	Mainboard = new Main();
	Mainboard.setVisible(true);	
	Mainboard.setResizable(false);
	}
	
	//Constructor
	private Main()
    {
		Cell cell;
		Piece P;		
		
		//Fetching Player Information
		playerlist = WhitePlayer.fetch_players();
		
		//Defining the Contents of Board (Left Side of Main GUI)
		boardState=new Cell[8][8];
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
			{	
				P=null;
				if(i==0&&j==0)
					P=br01;
				else if(i==0&&j==7)
					P=br02;
				else if(i==7&&j==0)
					P=wr01;
				else if(i==7&&j==7)
					P=wr02;
				else if(i==0&&j==1)
					P=bk01;
				else if (i==0&&j==6)
					P=bk02;
				else if(i==7&&j==1)
					P=wk01;
				else if (i==7&&j==6)
					P=wk02;
				else if(i==0&&j==2)
					P=bb01;
				else if (i==0&&j==5)
					P=bb02;
				else if(i==7&&j==2)
					P=wb01;
				else if(i==7&&j==5)
					P=wb02;
				else if(i==0&&j==3)
					P=bk;
				else if(i==0&&j==4)
					P=bq;
				else if(i==7&&j==3)
					P=wk;
				else if(i==7&&j==4)
					P=wq;
				else if(i==1)
				P=bp[j];
				else if(i==6)
					P=wp[j];
				cell=new Cell(i,j,P);
				cell.addMouseListener(this);
				board.add(cell);
				boardState[i][j]=cell;
			}
		board.setBorder(BorderFactory.createLoweredBevelBorder());
		board.setMinimumSize(new Dimension(700,700));
		
		//Defining Properties of Control Panel(Right Side of the Main GUI)
		score=new JPanel();
		score.setBorder(BorderFactory.createTitledBorder("Scores:"));
		score.setLayout(new GridLayout(2,2));
		score.add(new JLabel("Player 1:"));
		score.add(new JLabel("Player 2:"));
		label = new JLabel("Time Starts now", JLabel.CENTER);
	    timer = new Time(label);
	    timer.start();
	    
	    controlPanel=new JPanel();
		controlPanel.setLayout(new BorderLayout());
		controlPanel.add(score,BorderLayout.NORTH);
	    controlPanel.add(label,BorderLayout.CENTER);
	    controlPanel.add(new JButton("Back"),BorderLayout.SOUTH);
		controlPanel.setMinimumSize(new Dimension(200,700));
		
		//Defining the Main Content Pane
		Container content=getContentPane();
		setSize(Width,Height);
		setTitle("Chess");
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,board, controlPanel);
		content.setBackground(Color.black);
		content.setLayout(new BorderLayout());
		content.add(split);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setAlwaysOnTop(true);
    }
	
	// A function to change the chance ffrom White Player to Black Player or vice verse
	// It is made public because it is to be accessed in the Time Class
	public void changechance()
	{
		if (boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck())
		{
			System.out.println("Game End!!");
		}
		if(destinationlist.isEmpty()==false)
			cleandestinations(destinationlist);
		if(previous!=null)
			previous.deselect();
		previous=null;
		chance^=1;
		timer.reset();
		timer.start();
	}
	
	//A function to retrieve the Black King or White King
	private King getKing(int color)
	{
		if (color==0)
			return wk;
		else
			return bk;
	}
	
	//A function to clean the highlights of possible destination cells
    private void cleandestinations(ArrayList<Cell> destlist)      //Function to clear the last move's destinations
    {
    	ListIterator<Cell> it = destlist.listIterator();
    	while(it.hasNext())
    		it.next().removepossibledestination();
    }
    
    //A function that indicates the possible moves by highlighting the Cells
    private void highlightdestinations(ArrayList<Cell> destlist)
    {
    	ListIterator<Cell> it = destlist.listIterator();
    	while(it.hasNext())
    		it.next().setpossibledestination();
    }
    
    
  //Function to check if the king will be in danger if the given move is made
    private boolean willkingbeindanger(Cell fromcell,Cell tocell)
    {
    	Cell newboardstate[][] = new Cell[8][8];
    	for(int i=0;i<8;i++)
    		for(int j=0;j<8;j++)
    		{	try { newboardstate[i][j] = new Cell(boardState[i][j]);} catch (CloneNotSupportedException e){e.printStackTrace(); System.out.println("There is a problem with cloning !!"); }}
    	
    	if(newboardstate[tocell.x][tocell.y].getpiece()!=null)
			newboardstate[tocell.x][tocell.y].removePiece();
    	
		newboardstate[tocell.x][tocell.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
		if(newboardstate[tocell.x][tocell.y].getpiece() instanceof King)
		{
			((King)(newboardstate[tocell.x][tocell.y].getpiece())).setx(tocell.x);
			((King)(newboardstate[tocell.x][tocell.y].getpiece())).sety(tocell.y);
		}
		newboardstate[fromcell.x][fromcell.y].removePiece();
		if (((King)(newboardstate[getKing(chance).getx()][getKing(chance).gety()].getpiece())).isindanger(newboardstate)==true)
			return true;
		else
			return false;
    }
    
    //A function to eliminate the possible moves that will put the King in danger
    private ArrayList<Cell> filterdestination (ArrayList<Cell> destlist, Cell fromcell)
    {
    	ArrayList<Cell> newlist = new ArrayList<Cell>();
    	Cell newboardstate[][] = new Cell[8][8];
    	ListIterator<Cell> it = destlist.listIterator();
    	int x,y;
    	while (it.hasNext())
    	{
    		for(int i=0;i<8;i++)
        		for(int j=0;j<8;j++)
        		{	try { newboardstate[i][j] = new Cell(boardState[i][j]);} catch (CloneNotSupportedException e){e.printStackTrace();}}
    		
    		Cell tempc = it.next();
    		if(newboardstate[tempc.x][tempc.y].getpiece()!=null)
    			newboardstate[tempc.x][tempc.y].removePiece();
    		newboardstate[tempc.x][tempc.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
    		x=getKing(chance).getx();
    		y=getKing(chance).gety();
    		if(newboardstate[fromcell.x][fromcell.y].getpiece() instanceof King)
    		{
    			((King)(newboardstate[tempc.x][tempc.y].getpiece())).setx(tempc.x);
    			((King)(newboardstate[tempc.x][tempc.y].getpiece())).sety(tempc.y);
    			x=tempc.x;
    			y=tempc.y;
    		}
    		newboardstate[fromcell.x][fromcell.y].removePiece();
    		if ((((King)(newboardstate[x][y].getpiece())).isindanger(newboardstate)==false))
    			newlist.add(tempc);
    	}
    	return newlist;
    }
    
    //A Function to filter the possible moves when the king of the current player is under Check 
    private ArrayList<Cell> incheckfilter (ArrayList<Cell> destlist, Cell fromcell, int color)
    {
    	ArrayList<Cell> newlist = new ArrayList<Cell>();
    	Cell newboardstate[][] = new Cell[8][8];
    	ListIterator<Cell> it = destlist.listIterator();
    	int x,y;
    	while (it.hasNext())
    	{
    		for(int i=0;i<8;i++)
        		for(int j=0;j<8;j++)
        		{	try { newboardstate[i][j] = new Cell(boardState[i][j]);} catch (CloneNotSupportedException e){e.printStackTrace();}}
    		Cell tempc = it.next();
    		if(newboardstate[tempc.x][tempc.y].getpiece()!=null)
    			newboardstate[tempc.x][tempc.y].removePiece();
    		newboardstate[tempc.x][tempc.y].setPiece(newboardstate[fromcell.x][fromcell.y].getpiece());
    		x=getKing(color).getx();
    		y=getKing(color).gety();
    		if(newboardstate[tempc.x][tempc.y].getpiece() instanceof King)
    		{
    			((King)(newboardstate[tempc.x][tempc.y].getpiece())).setx(tempc.x);
    			((King)(newboardstate[tempc.x][tempc.y].getpiece())).sety(tempc.y);
    			x=tempc.x;
    			y=tempc.y;
    		}
    		newboardstate[fromcell.x][fromcell.y].removePiece();
    		if ((((King)(newboardstate[x][y].getpiece())).isindanger(newboardstate)==false))
    			newlist.add(tempc);
    	}
    	return newlist;
    }
    
    //A function to check if the King is check-mate. The Game Ends if this function returns true.
    public boolean checkmate(int color)
    {
    	ArrayList<Cell> dlist = new ArrayList<Cell>();
    	for(int i=0;i<8;i++)
    	{
    		for(int j=0;j<8;j++)
    		{
    			if (boardState[i][j].getpiece()!=null && boardState[i][j].getpiece().getcolor()==color)
    			{
    				dlist.clear();
    				dlist=boardState[i][j].getpiece().move(boardState, i, j);
    				dlist=incheckfilter(dlist,boardState[i][j],color);
    				if(dlist.size()!=0)
    					return false;
    			}
    		}
    	}
    	return true;
    }
	
    
    //These are the abstract function of the parent class. Only relevant method here is the On-Click Fuction
    //which is called when the user clicks on a particular cell
    @Override
	public void mouseClicked(MouseEvent arg0){
		// TODO Auto-generated method stub
		c=(Cell)arg0.getSource();
		if (previous==null)
		{
			if(c.getpiece()!=null)
			{
				if(c.getpiece().getcolor()!=chance)
					return;
				c.select();
				previous=c;
				destinationlist.clear();
				destinationlist=c.getpiece().move(boardState, c.x, c.y);
				if(c.getpiece() instanceof King)
					destinationlist=filterdestination(destinationlist,c);
				else
				{
					if(boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck())
						destinationlist = new ArrayList<Cell>(filterdestination(destinationlist,c));
					else if(destinationlist.isEmpty()==false && willkingbeindanger(c,destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
		}
		else
		{
			if(c.x==previous.x && c.y==previous.y)
			{
				c.deselect();
				cleandestinations(destinationlist);
				destinationlist.clear();
				previous=null;
			}
			else if(c.getpiece()==null||previous.getpiece().getcolor()!=c.getpiece().getcolor())
			{
				if(c.ispossibledestination())
				{
					if(c.getpiece()!=null)
						c.removePiece();
					c.setPiece(previous.getpiece());
					
					if (previous.ischeck())
						previous.removecheck();
					previous.removePiece();
					if(getKing(chance^1).isindanger(boardState))
					{
						boardState[getKing(chance^1).getx()][getKing(chance^1).gety()].setcheck();
						if (checkmate(getKing(chance^1).getcolor()))
						{
							System.out.println("Game End!!");
						}
					}
					if(getKing(chance).isindanger(boardState)==false)
						boardState[getKing(chance).getx()][getKing(chance).gety()].removecheck();
					if(c.getpiece() instanceof King)
					{
						((King)c.getpiece()).setx(c.x);
						((King)c.getpiece()).sety(c.y);
					}
					changechance();
				}
				if(previous!=null)
				{
					previous.deselect();
					previous=null;
				}
				cleandestinations(destinationlist);
				destinationlist.clear();
			}
			else if(previous.getpiece().getcolor()==c.getpiece().getcolor())
			{
				previous.deselect();
				cleandestinations(destinationlist);
				destinationlist.clear();
				c.select();
				previous=c;
				destinationlist=c.getpiece().move(boardState, c.x, c.y);
				if(c.getpiece() instanceof King)
					destinationlist=filterdestination(destinationlist,c);
				else
				{
					if(boardState[getKing(chance).getx()][getKing(chance).gety()].ischeck())
						destinationlist = new ArrayList<Cell>(filterdestination(destinationlist,c));
					else if(destinationlist.isEmpty()==false && willkingbeindanger(c,destinationlist.get(0)))
						destinationlist.clear();
				}
				highlightdestinations(destinationlist);
			}
		}
		if(c.getpiece()!=null && c.getpiece() instanceof King)
		{
			((King)c.getpiece()).setx(c.x);
			((King)c.getpiece()).sety(c.y);
		}
	}
    
    //Other Irrelevant abstract function. Only the Click Event is captured.
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub		
	}
}