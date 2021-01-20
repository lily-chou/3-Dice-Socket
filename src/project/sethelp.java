package project;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Insets;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextPane;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

public class sethelp {
	static JFrame frame;
	JTextArea showArea;
	//static JPanel showpane;
    static JTextPane show;
    JLabel show1;

	public sethelp()  {
		ImageIcon background=new ImageIcon("src\\project\\picture\\help.jfif");
        Image img=background.getImage();
        Image temp=img.getScaledInstance(500,500,Image.SCALE_SMOOTH);
        background=new ImageIcon(temp);
        JLabel back=new JLabel(background);
        back.setBounds(0, 0, 500, 500);
        //showArea=new JTextArea();
        //showArea.setEditable(false);
        //showArea.setLineWrap(true);
        show1=new JLabel();
        show1.setText("<html><body>三公骰子<br>玩家將會有三個骰子，按下start按鈕將會將3顆骰子轉動，<br>按下stop按鈕骰子停止轉動。"
        		+ "<br>玩家將會骰出3顆骰子，將3顆骰子相加，尾數越大分數越高。<br>經過3局，分數最高著勝<br>"
        		+ "例如: <br>1 + 2 + 3 => 7<br>	4 + 6 + 6 => 7<br><br>規則:<br><br>"
        		+ "全部為3 -> 15分<br>尾數為9 -> 10分<br>尾數為8 -> 9分<br>.<br>.<br>.<br>以此類推。</body></html>");
        /*showArea.append("玩家將會骰出3顆骰子，將3顆骰子相加，尾數越大分數越高。\n如若3顆的點數都為3，分數則最高。\n經過3局，分數最高著勝\n");
        showArea.append("例如: 1 + 2 + 3 = 6\n  4 + 6 + 6 = 6\n=>兩者分數相同\n\n規則:");
        showArea.append("\n\n全部為3 -> 15分\n尾數為9 -> 10分\n尾數為8 -> 9分\n.\n.\n.\n以此類推。\n\n\n\n\n\n\n");
        showArea.setBounds(20, 20, 450, 450);
        showArea.setForeground(Color.black);
        showArea.setBackground(new Color(0, 0, 0, 0)); 
        Font font = new Font("標楷體", Font.PLAIN, 15);
        showArea.setFont(font);
        back.add(showArea);*/
        show1.setForeground(Color.black);
        show1.setBounds(20, 20, 450, 325);
        Font font = new Font("標楷體", Font.PLAIN, 15);
        show1.setFont(font);
        
        back.add(show1);
        
        

        
        frame = new JFrame("help");
	    frame.setSize(500, 500);
	    frame.add(back); 
	    //mainJframe.getContentPane().add(textPane);
        //frame.pack();
	    //mainJframe.add(textPane);
	    frame.setResizable(false);
	    frame.setVisible(true);
	    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
	}
	public static void main(String[] args) {
		new sethelp();
	}

}
