package project;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;
import org.json.JSONObject;

public class server {
	static ServerSocket serversocket;
	static Socket socket;
	Thread thread = null;
	static List<Socket> sockets = new Vector<>();
    static DataInputStream fromclient;
    static DataOutputStream outtoclient;

    public static void main(String[] args) throws Exception {
	    try{
	      serversocket=new ServerSocket(8888);
	      int counter=0;
	      System.out.println("server started ....");
	      while(true){
	        counter++;
	        Socket socket=serversocket.accept(); 
	        synchronized (sockets){
                sockets.add(socket);
            }
	        System.out.println( "client num:" + counter + " start");
	        serverthread sct = new serverthread(socket,counter,sockets); 
	        //send  the request to a separate thread
	        sct.start();
	      }
	    }catch(Exception e){
	      System.out.println(e);
	    }
	  }    
}

class serverthread extends Thread {
	  Socket socket;
	  int clientnum;
	  static int turn=1;
	  static int round=0;
	  int score=0;
	  static int x,y,z;
	  Map<String, String> map = new HashMap<String, String>();
	  Map<String, String> map2 = new HashMap<String, String>();
	  static List<Socket> sockets = new Vector<>();
	  static List<String> namedata= new Vector<>();

	  serverthread(Socket inSocket,int counter,List insockets){
		  socket = inSocket;
		  clientnum=counter;
		  sockets.add(socket);
		  //sockets=insockets;
	  }
	  
	public void addname(String username) {
		namedata.add(username);
	}
	
	public void addturn() {
		turn++;
	}
	
	public void addround() {
		round++;
		turn=1;
	}
	
	public void clear() {
		namedata.clear();
		sockets.clear();
		round=0;
		turn=1;
	}
	
	public void run(){
	    try{
	      DataInputStream inStream = new DataInputStream(socket.getInputStream());
	      DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
	      String inputmess="",butmessage="",usermessage="";
	     
	      //收使用者資訊
	      inputmess=inStream.readUTF();
	      while(inputmess.length()==0) {
	    	  inputmess=inStream.readUTF();
	      }
	      JSONObject jsonrec = new JSONObject(inputmess); 
	      usermessage=jsonrec.getString("name");
	      map2.put("name", usermessage);
	      clientnum=namedata.size()+1;
	      map2.put("clientnum", Integer.toString(clientnum));
	      map2.put("score", "0");
	      JSONObject userjson = new JSONObject(map2);
	      String jsonString = "";
	      jsonString = userjson.toString();
	      addname(usermessage);
	      synchronized (sockets){
	            for (Socket sc : sockets){
	            	outStream = new DataOutputStream(sc.getOutputStream());
	            	outStream.writeUTF(jsonString);
	            	System.out.println("server");
	            	System.out.println(jsonString);
	                outStream.flush();
	            }
	      }

	      //剩下的使用者
	      outStream = new DataOutputStream(socket.getOutputStream());
	      for(int i=0;i<clientnum-1;i++) {
	    	  map2.put("name", namedata.get(i));
		      map2.put("clientnum", Integer.toString(i+1));
		      map2.put("score", "0");
		      userjson = new JSONObject(map2);
		      jsonString = "";
		      jsonString = userjson.toString();
	          outStream.writeUTF(jsonString);
              outStream.flush();
	      }
	      
/***********************遊戲訊息*****************************/
	      while(!butmessage.equals("exit")){
	    	  //傳輪到誰
	    	  synchronized (sockets){
	    		  map.put("text", "It's "+namedata.get(turn-1)+"'s turn");
		    	  map.put("button", "turn");
		    	  JSONObject json = new JSONObject(map);
					jsonString = "";
			        jsonString = json.toString();
		            for (Socket sc : sockets){
		            	outStream = new DataOutputStream(sc.getOutputStream());
		            	outStream.writeUTF(jsonString);
		                outStream.flush();		                
		            }
		        }
	    	  
	    	  //收訊息
	    	  inputmess=inStream.readUTF();
		      jsonrec = new JSONObject(inputmess); 
		      usermessage=jsonrec.getString("name");
	    	  butmessage=jsonrec.getString("button");
	       
	    	  System.out.println("From Client-" +clientnum );

	        String text="";
	        //聊天資訊
	        if( butmessage.equals("chat")) {
	        	text=jsonrec.getString("text");
	        	map.put("text", text);
	        	map.put("name", usermessage);
	        	map.put("button", "chat");
	        	//System.out.println(text);
	        	JSONObject json = new JSONObject(map);
				jsonString = "";
		        jsonString = json.toString();
	        	synchronized (sockets){
		            for (Socket sc : sockets){
		            	outStream = new DataOutputStream(sc.getOutputStream());
		            	outStream.writeUTF(jsonString);
		                outStream.flush();		                
		            }
		        }
	        }//遊戲資訊
	        else if(clientnum==turn) {
	        	//輸出 json
		    	map.put("button", butmessage);
		        map.put("name", usermessage);
		       // map.put("num", Integer.toString(clientnum));
		        JSONObject json = new JSONObject(map);
				jsonString = "";
		        jsonString = json.toString();
	        	synchronized (sockets){
		            for (Socket sc : sockets){
		            	outStream = new DataOutputStream(sc.getOutputStream());
		            	outStream.writeUTF(jsonString);
		                outStream.flush();		                
		            }
		        }
	        	if(butmessage.equals("stop")) {
	        		//System.out.println(clientnum+" in stop");
                	givenum();
	        		map.put("x",Integer.toString(x));
	        		map.put("y",Integer.toString(y) );
	        		map.put("z", Integer.toString(z));
	        		map.put("score", Integer.toString(score));
	        		map.put("num", Integer.toString(clientnum));
	        		map.put("name", usermessage);
	        		json = new JSONObject(map);
	    			jsonString = "";
	    	        jsonString = json.toString();
	    	        synchronized (sockets){
			            for (Socket sc : sockets){
			            	outStream = new DataOutputStream(sc.getOutputStream());
			            	outStream.writeUTF(jsonString);
			                outStream.flush(); 
			            }
	        		addturn();
	    	        }
	        	}
	        }else {//其他資訊
	        	map.put("text","Not your turn!");
	        	JSONObject json = new JSONObject(map);
    			jsonString = "";
    	        jsonString = json.toString();
    	        outStream.writeUTF(jsonString);
                outStream.flush(); 
	        }
	        
	       //重新一輪
	       if(turn==namedata.size()+1) {
	        	addround();
	        }
	        if(round==3) {
	        	map.put("button", "exit");
	        	JSONObject json = new JSONObject(map);
    			jsonString = "";
    	        jsonString = json.toString();
	        	synchronized (sockets){
		            for (Socket sc : sockets){
		            	outStream = new DataOutputStream(sc.getOutputStream());
		            	outStream.writeUTF(jsonString);
		                outStream.flush(); 
		            }
    	        }
	        }
	 
		  }
	      clear();
	      inStream.close();
	      outStream.close();
	      socket.close();
	    }catch(Exception ex){
	      System.out.println(ex);
	    }finally{
	      System.out.println("Client -" + clientnum + " exit!! ");
	    }
	  }
	
	//骰子數字
	public void givenum() {
		game dice1=new game();
		x=dice1.returnnum();
		game dice2=new game();
		y=dice2.returnnum();
		game dice3=new game();
		z=dice3.returnnum();
		score+=dice1.judge(x,y,z);
		
	}
}

