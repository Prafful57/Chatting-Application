package c.chatting.application;

import java.awt.BorderLayout;
import java.util.*;
import java.text.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;


public class Client implements ActionListener {
	
	 JTextField text1;
	  static JPanel a1;
	 static Box vertical = Box.createVerticalBox();
	 
	 static JFrame f = new JFrame();
	 
	 static DataOutputStream dout;
	
	Client(){
		
		//For layout creation
		f.setLayout(null);
		
		JPanel panel1 = new JPanel();
		panel1.setBackground(new Color(7, 94, 84));
		panel1.setBounds(0,0,450,70);
		panel1.setLayout(null);
		f.add(panel1);
		
		//Add icons
		ImageIcon icon1 = new ImageIcon(ClassLoader.getSystemResource("icons/3.png"));
		Image image = icon1.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT);
		ImageIcon icon2 = new ImageIcon(image);
		JLabel back = new JLabel(icon2);
		back.setBounds(5,20,25,25);
		panel1.add(back);
		
		//Add action when click on icon
		back.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				System.exit(0);
			}
		});
		
		ImageIcon icon3 = new ImageIcon(ClassLoader.getSystemResource("icons/2.png"));
		Image image2 = icon3.getImage().getScaledInstance(50, 50, Image.SCALE_DEFAULT);
		ImageIcon icon4 = new ImageIcon(image2);
		JLabel profile = new JLabel(icon4);
		profile.setBounds(40,10,50,50);
		panel1.add(profile);
		
		ImageIcon icon5 = new ImageIcon(ClassLoader.getSystemResource("icons/video.png"));
		Image image3 = icon5.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon icon6 = new ImageIcon(image3);
		JLabel video = new JLabel(icon6);
		video.setBounds(300,20,30,30);
		panel1.add(video);
		
		ImageIcon icon7 = new ImageIcon(ClassLoader.getSystemResource("icons/phone.png"));
		Image image4 = icon7.getImage().getScaledInstance(30, 30, Image.SCALE_DEFAULT);
		ImageIcon icon8 = new ImageIcon(image4);
		JLabel phone = new JLabel(icon8);
		phone.setBounds(360,20,30,30);
		panel1.add(phone);
		
		ImageIcon icon9 = new ImageIcon(ClassLoader.getSystemResource("icons/3icon.png"));
		Image image5 = icon9.getImage().getScaledInstance(10,25, Image.SCALE_DEFAULT);
		ImageIcon icon10 = new ImageIcon(image5);
		JLabel more = new JLabel(icon10);
		more.setBounds(420,20,10,25);
		panel1.add(more);
		
		//Display Name and Label
		JLabel name = new JLabel("Aishwariya");
		name.setBounds(110,15,100,18);
		name.setForeground(Color.white);
		name.setFont(new Font("SAN_SERIF",Font.BOLD,18));
		panel1.add(name);
		
		JLabel status = new JLabel("Active Now");
		status.setBounds(110,35,100,18);
		status.setForeground(Color.white);
		status.setFont(new Font("SAN_SERIF",Font.BOLD,14));
		panel1.add(status);
		
		
		a1 = new JPanel();
		a1.setBounds(5, 75, 440, 570);
		f.add(a1);
		
		//Create Text Field
		 text1 = new JTextField();
	        text1.setBounds(10,555,310,40);
	        text1.setFont(new Font("SAN_SERIF",Font.BOLD,16));
	        f.add(text1);
	        
	        
	        f.setSize(450, 600);
	        f.setLocation(800, 50);
	        f.setUndecorated(true);
	        f.getContentPane().setBackground(Color.white);
	        f.setVisible(true);
        
		//Create Button
        JButton send = new JButton("Send");
        send.setBounds(320,555,122,40);
        send.setBackground(new Color(7,94,84));
        send.setForeground(Color.white);
        send.addActionListener(this);
        send.setFont(new Font("SAN_SERIF",Font.BOLD,16));
        f.add(send);
        
        
       
	}
	 
	//Method is getting the text what we are entering in text field and reflecting it or placing it in proper way
	 public void actionPerformed(ActionEvent e) {
		 try{
			 String outPut = text1.getText();
				JPanel p2= formatLabel(outPut);
				
				a1.setLayout(new BorderLayout());
				
				JPanel right = new JPanel(new BorderLayout());
				right.add(p2, BorderLayout.LINE_END);
				vertical.add(right);
				vertical.add(Box.createVerticalStrut(15));
				
				a1.add(vertical ,BorderLayout.PAGE_START);
				
				dout.writeUTF(outPut);
				
				text1.setText("");
				f.repaint();
				f.invalidate();
				f.validate(); 
		 }catch (Exception e1) {
			 e1.printStackTrace();
		 }
		
		
	 }
	 //Place the incoming or outgoing text
	  public static JPanel formatLabel(String outPut) {
		  JPanel panel = new JPanel();
		  panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		  
		  JLabel output = new JLabel("<html><p style =\"width: 150px\">"+outPut+"</p></html>");
		  output.setFont(new Font("Tahoma",Font.PLAIN,16));
		  output.setBackground(new Color(37 ,211,102));
		  output.setOpaque(true);
		  output.setBorder(new EmptyBorder(15, 15,15,50));
		  
		  panel.add(output);
		  
		  Calendar cal = Calendar.getInstance();
		  SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
		  
		  JLabel time = new JLabel();
		  time.setText(sdf.format(cal.getTime()));
		  panel.add(time);
		  
		  return panel;
	  }
	  
	  //Main method in which we created socket to attach server and client
	public static void main(String[] args) {
		new Client();
		try {
			Socket s = new Socket("127.0.0.1",6001);
			DataInputStream din= new DataInputStream(s.getInputStream());
			 dout= new DataOutputStream(s.getOutputStream());
			 
			 while(true) {
				 a1.setLayout(new BorderLayout());
					String msg= din.readUTF();
					JPanel panel = formatLabel(msg);
					
					JPanel left = new JPanel(new BorderLayout());
					left.add(panel, BorderLayout.LINE_START);
					vertical.add(left);
					
					vertical.add(Box.createVerticalStrut(15));
					a1.add(vertical, BorderLayout.PAGE_START);
					f.validate();
				}
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}