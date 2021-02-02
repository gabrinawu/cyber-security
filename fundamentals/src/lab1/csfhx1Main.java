package lab1;

import java.awt.*;//gui component package
import java.awt.event.*;//提供处理由 AWT 组件所激发的各类事件的接口和类，包含用来检测并对时间做出反应的三个组成元素：源对象、监视器对象和事件对象。
import java.io.*;
import java.security.*;//加密解密
import java.util.*;//集合类、时间处理模式、日期时间工具等各类常用工具包

import javax.crypto.*;//加密解密算法
import javax.crypto.spec.*;//Provides classes and interfaces for key specifications and algorithm parameter specifications.
import javax.swing.*;//Swing是一个用于开发Java应用程序用户界面的开发工具包。

public class csfhx1Main extends JFrame implements ActionListener {
	//GUI程序的基本思路是以JFrame为基础，它是屏幕上window的对象，能够最大化、最小化、关闭。

	public static void main(String[] args) {
		// TODO Auto-generated method stub
       new csfhx1Main();
	}
	public csfhx1Main() {
		     //button
		//JPanel 是 Java图形用户界面(GUI)工具包swing中的面板容器类，包含在javax.swing 包中，可以进行嵌套，功能是对窗体中具有相同逻辑功能的组件进行组合。
		Jpanel p_buttons = new JPanel(new GridLayout(6,1));
		//GridLayout(int rows, int cols) 创建具有指定行数和列数的网格布局。
		b_key = create_button(p_buttons, "Create Key");
		b_enc_file = create_button(p_buttons,"Encrypt Key File");
		b_dec_file = create_button(p_buttons,"Decrypt Key File");
		b_enc_string = create_button(p_buttons,"Encrypt String Key");
		b_dec_string = create_button(p_buttons,"Decrypt String key");
		b_quit = create_button(p_buttons,"Quit");
		add(p_buttons,"West");
		    //text fields
		JPanel p_tf = new JPanel(new GridLayout(2,2));
		p_tf.add(new JLabel("Key (file or string)"));
		tf_key = new JTextField(20);
		p_tf.add(tf_key);
		p_tf.add(new JLabel("Data (plain or cipher text)"));
		tf_data = new JTextField(20);
		p_tf.add(tf_data);
		add(p_tf,"East");
		  //finish off
		setTitle("CSFH 1");
		setSize(600,400);
		setLocation(600,300);
		setVisble(true);
		tf_key.requestFocus();
	}
	private JButton create_button(JPanel p, String text)
	{
		JButton b = new JButton(text);
		b.addActionListener(this);
		//b.addActionListener(this)添加指定的动作侦听器，以接收发自此按钮的动作事件。当用户在此按钮上按下或释放鼠标时，发生动作事件。
		p.add(b);
		return b;
	}
	
	public void actionPerformed(ActionEvent e) {
		try {
			Object b = e.getSource();
			if(b == b_quit)
				System.exit(0);
			else if(b == b_key)
			{
				KeyGenerator kg = KeyGenerator.getInstance("AES");
				//Creates a new KeyGenerator instance that provides the specified key algorithm
				kg.init(128);//set the key size
				Key aes_key = kg.generateKey();// generate secrete key
				byte[] out = aes_key.getEncoded();//返回基本编码格式的密钥，如果此密钥不支持编码，则返回 null。
				write_file(tf_key.getText(),out);
			}
			else if(b == b_enc_file) {
				String pt_file = tf_data.getText();
				enc_dec(Cipher.ENCRYPT_MODE,)
			}
		}
	}
}
