package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Vector;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

public class selete implements ActionListener{
    JFrame mainJframe;
    JButton back_b;
    JButton help_b;
    JButton exit_b;
    JButton next_b;
    JLabel show1;
    JTextField nameText;
    JCheckBox checkbox1 ;
    JCheckBox checkbox2; 
    //JPanel pane;
    ButtonGroup chkbox ;
    static String text="";
    static Player player;
    static Thread playThread;
    boolean closemusic=false;
    boolean resumemusic=false;
    static boolean music=true;
    String file=main.file;
    boolean namelen=false;
    static List<Player> playerdata = new Vector<>();
	public selete() {
        
        //背景圖片
        ImageIcon background=new ImageIcon("src\\project\\picture\\frame2.jpg");
        Image img=background.getImage();
        Image temp=img.getScaledInstance(800,500,Image.SCALE_SMOOTH);
        background=new ImageIcon(temp);
        JLabel back=new JLabel(background);
        back.setBounds(0, 0, 800, 500);
        
       //back 按鈕
        back_b=new JButton("back");
        back_b.setBounds(0, 0, 60, 35);
        back.add(back_b);
       
        //user name
        show1=new JLabel("User name: ");
        show1.setForeground(Color.white);
        show1.setBounds(80, 50, 150, 100);
        show1.setFont(new Font("Verdana", Font.PLAIN, 20));
        back.add(show1);
        
        //輸入名字
        nameText=new JTextField();
        nameText.setColumns(10);
        nameText.setBounds(210, 85, 150, 35);
        back.add(nameText);
        
        //next 按鈕
        next_b=new JButton("next");
        next_b.setBounds(725, 425, 60, 35);
        back.add(next_b);
        
        
        //help按鈕
        show1=new JLabel("Help 	:");
        show1.setForeground(Color.white);
        show1.setBounds(80, 120, 150, 100);
        show1.setFont(new Font("Verdana", Font.PLAIN, 20));
        back.add(show1);
        
        //幫助
        help_b=new JButton("help");
        help_b.setBounds(210, 150, 60, 35);
        back.add(help_b);
        
        //音樂
        show1=new JLabel("Music		:");
        show1.setForeground(Color.white);
        show1.setBounds(80, 183, 150, 100);
        show1.setFont(new Font("Verdana", Font.PLAIN, 20));
        back.add(show1);
        checkbox1 = new JCheckBox("on");    
        checkbox1.setBounds(210,220, 50,25);  
        checkbox1.setBackground( Color.black);
        checkbox1.setForeground(Color.white);
        Font font = new Font("Verdana", Font.PLAIN, 15);
        checkbox1.setFont(font);
        back.add(checkbox1); 
        checkbox2 = new JCheckBox("off");    
        checkbox2.setBounds(270,220, 50,25); 
        checkbox2.setBackground( Color.black);
        checkbox2.setForeground(Color.white);  
        back.add(checkbox2); 
        
        //checkbox只能選一個   
        chkbox = new ButtonGroup();
		chkbox.add(checkbox1);
		chkbox.add(checkbox2);
		checkbox1.addActionListener(this);
		checkbox2.addActionListener(this);
		
        //frame 頁面
        mainJframe = new JFrame("socket");
        mainJframe.setSize(800, 500);
        mainJframe.add(back);        
        mainJframe.setResizable(false);
        mainJframe.setVisible(true);
        mainJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
              
        //回去第一個頁面
        back_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mainJframe.dispose();
				new main();
			}
		});
        
        //去遊戲畫面
        next_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				mainJframe.dispose();
				new client1();
			}
		});
        
        //遊戲說明
        help_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				new sethelp();
			}
		});
        
        //輸入姓名
        nameText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                text = textField.getText();
                //System.out.println(text);
              }

              public void keyTyped(KeyEvent e) {
              }

              public void keyPressed(KeyEvent e) {
              }
        });
        
     }
	
	public static void main() {
		new selete();
	}
	public static String getname() {
		return text;
	}

	
	@Override
	public void actionPerformed(ActionEvent e) {
		JCheckBox chkbox=(JCheckBox)e.getSource();
	      if(chkbox == checkbox1) {
	    	 if(closemusic) {
	    		 closemusic=false;
	    		 playThread = new Thread(runnablePlay);
	    		 playThread.start();
	    		 
	    		 resumemusic=true;
	    	 }
	    	 music=true;
	      }else if(chkbox == checkbox2) {
	    	  //System.out.println("2");
	    	  if(!resumemusic) {
	    		  player=main.player;
	    		  main.playThread.stop();;
	    	  }
	    	  player.close();
	    	  closemusic=true;
	    	  music=false;
	      }
		
	}
	
	Runnable runnablePlay=new Runnable() {
	      @Override
	      public void run() {
	          try {
	              FileInputStream fileInputStream = new FileInputStream(file);
	              //System.out.println("in");
	              BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
	              player = new Player(bufferedInputStream);
	              playerdata.add(player);
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
	  
	  public static Player returnplayer() {
		  return player;
	  }
}
