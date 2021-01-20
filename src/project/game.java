package project;

import java.util.Random;

public class game {
	static int x;
	 static int sum=0;
	 //text="";
	
	 public game() {
		 Random ra=new Random();
	     x=ra.nextInt(6)+1;
	 }
	 public static int judge(int x,int y,int z) {
		 sum=x+y+z;
		 if(x==y&&x==z&&x==3) {
			 return 15;
		 }else if(sum%10==9) {
			 return 10;
		 }else if(sum%10==8) {
			 return 9;
		 }else if(sum%10==7) {
			 return 8;
		 }else if(sum%10==6) {
			 return 7;
		 }else if(sum%10==5) {
			 return 6;
		 }else if(sum%10==4) {
			 return 5;
		 }else if(sum%10==3) {
			 return 4;
		 }else if(sum%10==2) {
			 return 3;
		 }else if(sum%10==1) {
			 return 2;
		 }else if(sum%10==0) {
			 return 1;
		 } 
		 return 0;
		 
	 }
	 public static int returnnum(){
		 return x;
	 }

}
