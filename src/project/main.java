package project;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class main {
    JFrame mainJframe;
    JButton start_b;
    JButton exit_b;
    static Thread playThread;
    static Player player;
    //static String file="C:\\\\Users\\\\user\\\\Desktop\\\\project\\\\src\\\\project\\\\music.mp3";
    static String file="src\\project\\music.mp3";
    
	public main()  {
		//播音樂
		playThread=new Thread(runnablePlay);
		playThread.start();
		
        //背景
        ImageIcon background=new ImageIcon("src\\project\\picture\\main1.jpg");
        Image img=background.getImage();
        Image temp=img.getScaledInstance(800,500,Image.SCALE_SMOOTH);
        background=new ImageIcon(temp);
        JLabel back=new JLabel(background);
        back.setBounds(0, 0, 800, 500);
        
        //add start button
        start_b=new JButton("start");
        start_b.setBounds(250, 375, 75, 50);
        back.add(start_b);
        
        //add exit button
        exit_b=new JButton("exit");
        exit_b.setBounds(450, 375, 70, 50);
        back.add(exit_b);
        
        mainJframe = new JFrame("socket");
        mainJframe.setSize(800, 500);
        mainJframe.add(back);        //mainJframe.getContentPane().add(start_b);
        mainJframe.setResizable(false);
        mainJframe.setVisible(true);
        mainJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
        //進入第2個介面
        start_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mainJframe.dispose();
				new selete();
			}
		});
        
        //關閉
        exit_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mainJframe.dispose();
			}
		});
        
        
        
	}
	
	public static void main(String[] args) {
		new main();
		
	}
	
	Runnable runnablePlay=new Runnable() {
	      @Override
	      public void run() {
	          try {
	              FileInputStream fileInputStream = new FileInputStream(file);
	              //System.out.println("in");
	              BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
	              player = new Player(bufferedInputStream);
	              player.play();//starting music
	          } catch (FileNotFoundException e) {
	              e.printStackTrace();
	          } catch (JavaLayerException e) {
	              e.printStackTrace();
	          } catch (IOException e) {
	              e.printStackTrace();
	          }
	      }
	  };
	  
}
