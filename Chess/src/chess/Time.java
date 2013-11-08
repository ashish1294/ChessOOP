package chess;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.Timer;


/**
 * This is the Time Class.
 * It contains all the required variables and functions related to the timer on the Main GUI
 * It uses a Timer Class
 *
 */
public class Time
{
    private JLabel label;
    Timer countdownTimer;
    int timeRemaining = 61;

    public Time(JLabel passedLabel) {
       countdownTimer = new Timer(1000, new CountdownTimerListener());
       this.label = passedLabel;
       
    }
    
    //A function that starts the timer
    public void start()
    {
    	countdownTimer.start();
    }
    
    //A function that resets the timer
    public void reset()
    {
    	timeRemaining=61;
    }
    
    //A function that is called after every second. It updates the timer and takes other necessary actions
    class CountdownTimerListener implements ActionListener
    {
		@Override
		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			 int min,sec;
        	 if (--timeRemaining > 0)
        	 {
            	min=timeRemaining/60;
            	sec=timeRemaining%60;
                label.setText(String.valueOf(min)+":"+(sec>=10?String.valueOf(sec):"0"+String.valueOf(sec)));
             }
        	 else 
        	 {
                label.setText("Time's up!");
                Main.Mainboard.changechance();
             }
		}
     }
 }