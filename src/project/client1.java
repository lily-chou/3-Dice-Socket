package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.Socket;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import org.json.JSONObject;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;

public class client1 implements ActionListener, Runnable{
	//遊戲畫面
	static JFrame mainJframe;
	static JButton start_b;
	static JButton stop_b;
	static JLabel back;
	String[] images = new String[]{
			"src\\project\\picture\\one.png", 
			"src\\project\\picture\\two.png", 
			"src\\project\\picture\\three.png",
			"src\\project\\picture\\four.png", 
			"src\\project\\picture\\five.png",
			"src\\project\\picture\\six.png"};
	JLabel MyImage1;
	JLabel MyImage2;
	JLabel MyImage3;
	JTextArea showArea;
	static JTextPane textPane1;
	static JTextPane textPane;
	static JTextPane[] panedata=new JTextPane[5];
	static JLabel show2;
	static boolean press=false;
	/******************************/
	//聊天室
	static JFrame chatframe;
	static JButton chat_b;
	static JLabel back2;
	JTextField msgText;
	JTextArea chatArea;
	JButton send_b;
	JScrollPane JSPane;
    JLabel show1;
	/*****************************/
    //遊戲資訊(分數 玩家)
	static String user=selete.getname();
	static int x,y,z;
	static int score=0;
	static int otherscore;
	
	//socket資訊
	String data="";
	Socket socket;
	DataInputStream fromserver;
    DataOutputStream toserver;
    Thread thread=null;
	
    //音樂
    private Player prehravac;
    String fileLocation="src\\project\\picture\\stop.mp3";
    Player player;
    String file="src\\project\\picture\\shake.mp3";
    
    //json資訊
    Map<String,String>map=new HashMap<String,String>();
    Map<String,String>map2=new HashMap<String,String>();
    static Map<String, Integer> scoredata = new HashMap<String, Integer>();
    
	public client1() {
		
		//背景
		ImageIcon background=new ImageIcon("src\\project\\picture\\game3.jpg");
        Image img=background.getImage();
        Image temp=img.getScaledInstance(800,500,Image.SCALE_SMOOTH);
        background=new ImageIcon(temp);
        back=new JLabel(background);
        
        //聊天室 button
        chat_b=new JButton("System");
        chat_b.setBounds(300, 2, 80, 35);
        back.add(chat_b);
        
        //開始骰色子button
        start_b=new JButton("start");
        start_b.setBounds(120, 370, 60, 35);
        start_b.addActionListener(this);
        back.add(start_b);
        
        //停止骰色子 button
        stop_b=new JButton("stop");
        stop_b.setBounds(400, 370, 60, 35);
        stop_b.addActionListener(this);
        back.add(stop_b);
        
        //3個色子
    	MyImage1 = new JLabel(new ImageIcon(images[0]));
    	MyImage1.setBounds(10, 175, 150, 150);
    	back.add(MyImage1);
    	MyImage2 = new JLabel(new ImageIcon(images[0]));
    	MyImage2.setBounds(210, 175, 150, 150);
    	back.add(MyImage2);
    	MyImage3 = new JLabel(new ImageIcon(images[0]));
    	MyImage3.setBounds(410, 175, 150, 150);
    	back.add(MyImage3);
    	
    	//次數順序
    	show2=new JLabel("");
        show2.setForeground(Color.black);
        show2.setBounds(0, 8, 250, 30);
        show2.setFont(new Font("Times New Roman", Font.PLAIN, 26));
        back.add(show2);	
    	
        mainJframe = new JFrame("socket");
        mainJframe.setSize(800, 500);
        mainJframe.add(back);        //mainJframe.getContentPane().add(start_b);
        mainJframe.setResizable(false);
        mainJframe.setVisible(true);
        mainJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        /****************************system & chat********************************/
        //背景
        background=new ImageIcon("src\\project\\picture\\chat.jpg");
        img=background.getImage();
        temp=img.getScaledInstance(500,600,Image.SCALE_SMOOTH);
        background=new ImageIcon(temp);
        back2=new JLabel(background);
        back2.setBounds(0, 0, 500, 600);
        
        //資訊欄
        Font font = new Font("標楷體", Font.PLAIN, 13);
        chatArea=new JTextArea();
        chatArea.setEditable(false);
        chatArea.setLineWrap(true);
        chatArea.setFont(font);
        JSPane=new JScrollPane(chatArea);
        JSPane.setBounds(30, 60, 420, 410);
        back2.add(JSPane);
        
        //輸入
        msgText=new JTextField();
        msgText.setColumns(30);
        msgText.setBounds(30, 500, 350, 35);
        //msgText.addActionListener(this);
        back2.add(msgText);
        //msgText.addActionListener(this);
        
        //傳送
        send_b=new JButton("send");
        send_b.setBounds(400, 500, 65, 40);
        send_b.addActionListener(this);
        back2.add(send_b);
        //sentBtn.addActionListener(this);  
        
        //輸出title
        show1=new JLabel("<html><body>"+user+"'s System </body></html>");
        show1.setForeground(Color.black);
        //show1.setBackground(Color.white);
        show1.setBounds(200, 8, 400, 30);
        show1.setFont(new Font("Times New Roman", Font.PLAIN, 26));
        //show1.setOpaque(true);
        back2.add(show1);
        
        chatframe = new JFrame("System");
        chatframe.setSize(500, 600);
        chatframe.add(back2);  
        chatframe.setResizable(false);
        chatframe.setVisible(false);
        //chatframe.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //chatframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        chatframe.addWindowListener(new WindowAdapter() {
            @Override
          public void windowClosing(WindowEvent evt) {
            	chatframe.setVisible(false);
          }
        });
        
        //若按chat 則frame設為可見
        chat_b.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ae) {
				chatframe.setVisible(true);
			}
		});
        

        /********************************Socket*************************************/
        try{
		    socket=new Socket("127.0.0.1",8888);
		    fromserver=new DataInputStream(socket.getInputStream());
		    toserver=new DataOutputStream(socket.getOutputStream());
		    
		    //玩家名字
		    map2.put("name", user);
		    JSONObject json = new JSONObject(map2);
			String jsonString = "";
	        jsonString = json.toString();
	        try {
				toserver.writeUTF(jsonString);
				//System.out.println(jsonString);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		    
		    //建thread
		    thread=new Thread(this);
            thread.setPriority(Thread.MIN_PRIORITY);
            thread.start();
                       
		  }catch(Exception e){
		    System.out.println(e);
		  }	
        msgText.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                JTextField textField = (JTextField) e.getSource();
                String s= textField.getText();
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                	if(s.length()>0) {
                	try{
                    	map.put("button", "chat");
                    	//map.put("type", "chat");
            			map.put("name", user);
            			map.put("text", s);
            			JSONObject json = new JSONObject(map);
            			String jsonString = "";
            	        jsonString = json.toString();
            	        toserver.writeUTF(jsonString);
                    	toserver.flush();
                    } catch (IOException e1){
                        chatArea.append("你的消息未發送出去\n");
                    }
                	msgText.setText("");}
                }
            	
              }

              public void keyTyped(KeyEvent e) {
              }

              public void keyPressed(KeyEvent e) {
              }
        });
        
     }
        
        
       /* msgText.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
            	String s=msgText.getText();
            	try{
                	map.put("button", "chat");
                	//map.put("type", "chat");
        			map.put("name", user);
        			map.put("text", s);
        			JSONObject json = new JSONObject(map);
        			String jsonString = "";
        	        jsonString = json.toString();
        	        toserver.writeUTF(jsonString);
                	toserver.flush();
                } catch (IOException e1){
                    chatArea.append("你的消息未發送出去\n");
                }
                msgText.setText("");

            }});*/
        
	
	public static void main(String[] args){
		new client1();
	}
	
	public static String rename() {
		return user;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {

		JButton b=(JButton)e.getSource();
		if(b==start_b&&press==false) {
			data="start";
			press=true;
			
			map.put("button", data);
			map.put("name", user);
			JSONObject json = new JSONObject(map);
			String jsonString = "";
	        jsonString = json.toString();
	        
	        try {
				toserver.writeUTF(jsonString);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		if(b==stop_b&&press==true) {
			data="stop";
			press=false;
			
			map.put("button", data);
			map.put("name", user);
			JSONObject json = new JSONObject(map);
			String jsonString = "";
	        jsonString = json.toString();
	      
	        try {
				toserver.writeUTF(jsonString);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
		//系統
		if(b==send_b) {
		String s=msgText.getText();
		if (s.length()>0){
            try{
            	map.put("button", "chat");
            	//map.put("type", "chat");
    			map.put("name", user);
    			map.put("text", s);
    			JSONObject json = new JSONObject(map);
    			String jsonString = "";
    	        jsonString = json.toString();
    	        toserver.writeUTF(jsonString);
            	toserver.flush();
            } catch (IOException e1){
                chatArea.append("你的消息未發送出去\n");
            }
            msgText.setText("");
        }}
	}

	@Override
	public void run() {
		try{
            while (true){ 
            	
            	String inputmess=fromserver.readUTF();           	
            	JSONObject jsonrec = new JSONObject(inputmess);       
            	String usermessage="",scoremessage="",sitex="";
            	
            	/*msgText.addActionListener(new ActionListener(){
                    public void actionPerformed(ActionEvent e){
                    	String s=msgText.getText();
                    	try{
                        	map.put("button", "chat");
                        	//map.put("type", "chat");
                			map.put("name", user);
                			map.put("text", s);
                			JSONObject json = new JSONObject(map);
                			String jsonString = "";
                	        jsonString = json.toString();
                	        toserver.writeUTF(jsonString);
                        	toserver.flush();
                        } catch (IOException e1){
                            chatArea.append("你的消息未發送出去\n");
                        }
                        msgText.setText("");

                    }});*/
            	
            	
            	
            	
            	//顯示使用者
            	if(jsonrec.has("clientnum")) {
            		//System.out.println("client has clientnum");
            		sitex=jsonrec.getString("clientnum");
            		usermessage=jsonrec.getString("name");
            		scoremessage=jsonrec.getString("score");
            		showuser1(Integer.parseInt(sitex),usermessage,scoremessage);
            	}else if(jsonrec.has("button")){
	            	String butmessage=jsonrec.getString("button");
	                if(butmessage.equals("start")) {
	                	usermessage=jsonrec.getString("name");
	                	Thread playThread = new Thread(runnablePlay);
	            		playThread.start();
	                	randomshow();
	                	chatArea.append("<System> "+usermessage+" start \n");
	                	//Thread playThread = new Thread(runnablePlay);
	            		//playThread.start();
	                	
	                }else if (butmessage.equals("stop")) {
	                	player.close();
	                    playmusic();
	                	inputmess=fromserver.readUTF();
	                	while(inputmess.length()==0) {
	                		inputmess=fromserver.readUTF();
	                	}
	                	jsonrec = new JSONObject(inputmess);
	            		x=Integer.parseInt(jsonrec.getString("x"));
	            		y=Integer.parseInt(jsonrec.getString("y"));
	            		z=Integer.parseInt(jsonrec.getString("z"));
	            		usermessage=jsonrec.getString("name");
	            		scoremessage=jsonrec.getString("score");
	            		sitex=jsonrec.getString("num");
	            		if(usermessage.equals(user)) {
	            			score=Integer.parseInt(jsonrec.getString("score"));
	            		}else {
	            			otherscore=Integer.parseInt(jsonrec.getString("score"));
	            			press=false;
	            		}press=false;
	                	showresult(x,y,z);
	                	showuser2(Integer.parseInt(sitex),usermessage,scoremessage);
	                	int n=Integer.parseInt(scoremessage);
	                	if(scoredata.containsKey(usermessage))
	                		n=Integer.parseInt(scoremessage)-scoredata.get(usermessage);
	                	store(usermessage, scoremessage);
	                	chatArea.append("<System> "+usermessage+" dice "+x+", "+y+", "+z+"\n");
	                	/*int n=Integer.parseInt(scoremessage);
	                	if(scoredata.containsKey(usermessage))
	                		n=Integer.parseInt(scoremessage)-scoredata.get(usermessage);*/
	                	chatArea.append("<System> "+usermessage+" got "+n+" points\n");
	                }else if(butmessage.equals("exit")) {
	                	map.put("button", "exit");
	                	JSONObject json = new JSONObject(map);
	        			String jsonString = "";
	        	        jsonString = json.toString();
	        	       
	        	        try {
	        				toserver.writeUTF(jsonString);
	        			} catch (IOException e1) {
	        				e1.printStackTrace();
	        			}
	                	//socket.close();
	        	        TimeUnit.SECONDS.sleep(2);
	        	        mainJframe.dispose();
	        	        chatframe.dispose();
	                	new ranking(scoredata,user);
	                	break;
	                }else if(butmessage.equals("chat")) {
	                	usermessage=jsonrec.getString("name");
	                	String text=jsonrec.getString("text");
	                	chatArea.append(usermessage+" say: "+text+"\n");
	                }else if(butmessage.equals("turn")) {
	                	String text=jsonrec.getString("text");
	                	showturn(text);
	                }
                 
                }
            	else {
            		String text=jsonrec.getString("text");
                	chatArea.append("<System> "+usermessage+text+"\n");
            	}
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
       
	public void store(String usermessage,String scoremessage) {
		scoredata.put(usermessage, Integer.parseInt(scoremessage));
	}
	
	//骰色子
	public void randomshow() throws InterruptedException, MalformedURLException {
		//System.out.println("show");
		back.remove(MyImage1);
		back.remove(MyImage2);
		back.remove(MyImage3);
		
		Icon imgIcon = new ImageIcon(this.getClass().getResource("randomshow.gif"));
		MyImage1 = new JLabel(imgIcon);
		imgIcon = new ImageIcon(this.getClass().getResource("randomshow1.gif"));
		MyImage2 = new JLabel(imgIcon);
		imgIcon = new ImageIcon(this.getClass().getResource("randomshow2.gif"));
		MyImage3 = new JLabel(imgIcon);
        MyImage1.setBounds(10, 175, 150, 150);
        MyImage2.setBounds(210, 175, 150, 150);
        MyImage3.setBounds(410, 175, 150, 150);        
        back.add(MyImage1);
        back.add(MyImage2);
        back.add(MyImage3);
    	
        back.revalidate();
    	back.repaint();
	}
	
	//輸出結果
	public void showresult(int x,int y,int z) throws InterruptedException {
		//TimeUnit.SECONDS.sleep(1);
		back.remove(MyImage1);
		back.remove(MyImage2);
		back.remove(MyImage3);
		MyImage1 = new JLabel(new ImageIcon(images[x-1]));
    	MyImage1.setBounds(10, 175, 150, 150);
    	back.add(MyImage1);
    	MyImage2 = new JLabel(new ImageIcon(images[y-1]));
    	MyImage2.setBounds(210, 175, 150, 150);
    	back.add(MyImage2);
    	MyImage3 = new JLabel(new ImageIcon(images[z-1]));
    	MyImage3.setBounds(410, 175, 150, 150);
    	back.add(MyImage3);
    	//System.out.println("score");
    	//System.out.println(score);
        back.revalidate();
    	back.repaint();
    	TimeUnit.SECONDS.sleep((long)0.5);
    	
	}
	
	//第一次輸出使用者
	public static void showuser1(int num,String username,String userscore) throws InterruptedException {
		//TimeUnit.SECONDS.sleep((long) 0.5);
		textPane1 = new JTextPane();
		if(username.equals(user))
			textPane1.setBackground(Color.cyan);
		else
			textPane1.setBackground(Color.lightGray);
        textPane1.setText("username: "+username+"\n"+"score: "+userscore); 
        Font font = new Font("Verdana", Font.PLAIN, 15);
        textPane1.setFont(font);
        textPane1.setBounds(600, 10+80*(num-1), 200, 60);
        textPane1.setEditable(false);
        panedata[num-1]=textPane1;
        back.add(textPane1);
        back.revalidate();
    	back.repaint();
    	
	}
	
	//之後更新使用者資訊
	public static void showuser2(int num,String username,String userscore) throws InterruptedException {
		//back.remove(textPane1);
		//TimeUnit.SECONDS.sleep((long) 0.5);
		//textPane=panedata.get(num-1);
		textPane=panedata[num-1];
		back.remove(textPane);
		textPane1 = new JTextPane();
		if(username.equals(user))
			textPane1.setBackground(Color.cyan);
		else
			textPane1.setBackground(Color.lightGray);
        textPane1.setText("username: "+username+"\n"+"score: "+userscore); 
        Font font = new Font("Verdana", Font.PLAIN, 15);
        textPane1.setFont(font);
        textPane1.setBounds(600, 10+80*(num-1), 200, 60);
        //textPane1.setFocusableWindowState(false);
        //panedata.set(num-1, textPane1);
        panedata[num-1]=textPane1;
        back.add(textPane1);
        back.revalidate();
    	back.repaint();	
	}
	
	//輸出換誰
	public void showturn(String text) {
		back.remove(show2);
		show2=new JLabel(text);
        show2.setForeground(Color.black);
        show2.setBounds(0, 8, 300, 30);
        show2.setFont(new Font("Times New Roman", Font.PLAIN, 26));
        back.add(show2);
        back.revalidate();
    	back.repaint();	
	}
	
	//放結束骰色子音樂
	public void playmusic(){
		try {
           
                FileInputStream buff = new FileInputStream(fileLocation);
                prehravac = new Player(buff);
                prehravac.play();
        } catch (Exception ioe) {
            // TODO error handling
        }
	}
	
	//在thread放音樂
	Runnable runnablePlay=new Runnable() {
	      @Override
	      public void run() {
	          try {
	              FileInputStream fileInputStream = new FileInputStream(file);
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


class ranking {
	JFrame mainJframe;
	JLabel show1;
	JLabel show;
	JLabel MyImage1;
	JTextPane textPane1;
	String[] images = new String[]{
			"src\\project\\picture\\first.png", 
			"src\\project\\picture\\second.png", 
			"src\\project\\picture\\third.png",
			"src\\project\\picture\\fourth.png", 
			"src\\project\\picture\\fifth.png"};
	
	public ranking(Map scoredata,String user) {
		//將map由大到小排順序
		Map<String, Integer> unSortedMap=scoredata;
		LinkedHashMap<String, Integer> reverseSortedMap = new LinkedHashMap<>();
		unSortedMap.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
         .forEachOrdered(x -> reverseSortedMap.put(x.getKey(), x.getValue()));
				
         //背景圖片
         ImageIcon background=new ImageIcon("src\\project\\picture\\rank.jpg");
         Image img=background.getImage();
         Image temp=img.getScaledInstance(500,600,Image.SCALE_SMOOTH);
         background=new ImageIcon(temp);
         JLabel back=new JLabel(background);
         back.setBounds(0, 0, 500, 600);
         
         //開頭
         show1=new JLabel("Score  Ranking ");
         show1.setForeground(Color.yellow);
         show1.setBounds(90, 5, 300, 100);
         show1.setFont(new Font(Font.SERIF, Font.PLAIN, 40));
         back.add(show1);         
         
        //輸出ranking 
        Set<String> set = reverseSortedMap.keySet();
 		Iterator<String> it = set.iterator();
 		int counter=0;
 		Font font = new Font(Font.DIALOG, Font.PLAIN, 16);
 		while (it.hasNext()) {
 			String key = it.next();
 			 if(counter==5) {
        		 break;
        	 }
        	 MyImage1 = new JLabel(new ImageIcon(new ImageIcon(images[counter]).getImage().getScaledInstance(110, 110, Image.SCALE_SMOOTH)));
        	 show=new JLabel();
        	 show.setText("<html><body> name: "+key+"<br>score: "+reverseSortedMap.get(key)+"</body></html>"); 
     		if(key.equals(user)) {
     			show.setBackground(Color.green);
     			show.setForeground(Color.black);
     		}	
     		else {
     			show.setBackground(Color.white);
     			show.setForeground(Color.black);
     		}
            show.setOpaque(true);
            show.setFont(font);
            show.setBounds(130, 110+84*counter, 200, 55);
            back.add(show);
            MyImage1.setBounds(40, 75*(counter+1)+10*counter, 110, 110);
     		back.add(MyImage1);
     		back.add(show);
     		counter++;
 			
 		}
         mainJframe = new JFrame("socket");
         mainJframe.setSize(500, 600);
         mainJframe.add(back);        
         mainJframe.setResizable(false);
         mainJframe.setVisible(true);
         mainJframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);   
	}

}
