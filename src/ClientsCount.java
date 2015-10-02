import java.awt.BorderLayout;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.StringTokenizer;

import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeriesDataItem;

import java.util.Scanner;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import java.awt.event.*;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.geom.*;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.*;
import java.io.*;



class CustomComparator implements Comparator<Data> {
    @Override
    public int compare(Data o1, Data o2) {
    	if (o1.getDate().compareTo(o2.getDate())==0)
    	{
    		return o1.getIp().compareTo(o2.getIp());
    	}
    	else
    		return o1.getDate().compareTo(o2.getDate());
    }
}

class Data
{
	private String ip;
	private Date date;
	private int count;
	
	

	public String getIp() {
		return ip;
	}



	public void setIp(String ip) {
		this.ip = ip;
	}



	public Date getDate() {
		return date;
	}



	public void setDate(Date date) {
		this.date = date;
	}



	public int getCount() {
		return count;
	}



	public void setCount(int count) {
		this.count = count;
	}



	public boolean equals(Object other)
    {
	
		if (!(other instanceof Data))
			return false;
		
		Data data = (Data)other;
		
		
		if ((this.date.compareTo(data.date)==0))
	    	return true;
	    else
	    	return false;
    }
	
}
class ClientsCount 
{
	
	 private  JTextField startDate = new JTextField(20);
	 private  JTextField endDate = new JTextField(20);	
	 
	 
	 public ClientsCount()
	 {
		 
		 
		 JPanel panel=new JPanel();
		 final JFrame myFrame = new JFrame("log stats");
		 myFrame.setSize(300,400);
		 myFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		 myFrame.setVisible(true);
		 myFrame.setContentPane(panel);
		 panel.setLayout(new BoxLayout(panel,BoxLayout.PAGE_AXIS));
		 JLabel startLabel = new JLabel();
		 startLabel.setText("Give start date (dd/mm/yyyy):");
		 JLabel endLabel = new JLabel();
		 endLabel.setText("Give end date (dd/mm/yyyy):");
		 

		 panel.add(startLabel);
		 panel.add(startDate);
		 panel.add(endLabel);
		 panel.add(endDate);
		 JButton buttonChoice=new JButton("ok");
		 panel.add(buttonChoice);
		 buttonChoice.addActionListener(new ActionListener() {
			 
	         public void actionPerformed(ActionEvent e)
	         {
	             //Execute when button is pressed
	        	 Chart demo;
				try {
					;
					demo = new Chart("#ip - date/time",startDate.getText(),endDate.getText());

					 demo.pack();
		             demo.setVisible(true);
				} catch (ParseException e1) {

					e1.printStackTrace();
		        	System.out.println("Date is in the wrong format, can not proceed");
		        	Object[] options = {"OK"};
		            int n = JOptionPane.showOptionDialog(myFrame,
		                           "Date is in wrong format!","Wrong Format",
		                           JOptionPane.PLAIN_MESSAGE,
		                           JOptionPane.QUESTION_MESSAGE,
		                           null,
		                           options,
		                           options[0]); 
		        	 		
				}


		        
				
	            
	         }
	     });      
		 
		 myFrame.pack(); 
		 myFrame.setVisible(true); 
		 
	    
		 
	 }
	 
 public static void main(String args[]) throws ParseException
  {
	
	 ClientsCount cc = new ClientsCount();
	  
	 
  }
}


