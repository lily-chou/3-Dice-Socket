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
        show1.setText("<html><body>�T����l<br>���a�N�|���T�ӻ�l�A���Ustart���s�N�|�N3����l��ʡA<br>���Ustop���s��l������ʡC"
        		+ "<br>���a�N�|��X3����l�A�N3����l�ۥ[�A���ƶV�j���ƶV���C<br>�g�L3���A���Ƴ̰��۳�<br>"
        		+ "�Ҧp: <br>1 + 2 + 3 => 7<br>	4 + 6 + 6 => 7<br><br>�W�h:<br><br>"
        		+ "������3 -> 15��<br>���Ƭ�9 -> 10��<br>���Ƭ�8 -> 9��<br>.<br>.<br>.<br>�H�������C</body></html>");
        /*showArea.append("���a�N�|��X3����l�A�N3����l�ۥ[�A���ƶV�j���ƶV���C\n�p�Y3�����I�Ƴ���3�A���ƫh�̰��C\n�g�L3���A���Ƴ̰��۳�\n");
        showArea.append("�Ҧp: 1 + 2 + 3 = 6\n  4 + 6 + 6 = 6\n=>��̤��ƬۦP\n\n�W�h:");
        showArea.append("\n\n������3 -> 15��\n���Ƭ�9 -> 10��\n���Ƭ�8 -> 9��\n.\n.\n.\n�H�������C\n\n\n\n\n\n\n");
        showArea.setBounds(20, 20, 450, 450);
        showArea.setForeground(Color.black);
        showArea.setBackground(new Color(0, 0, 0, 0)); 
        Font font = new Font("�з���", Font.PLAIN, 15);
        showArea.setFont(font);
        back.add(showArea);*/
        show1.setForeground(Color.black);
        show1.setBounds(20, 20, 450, 325);
        Font font = new Font("�з���", Font.PLAIN, 15);
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
