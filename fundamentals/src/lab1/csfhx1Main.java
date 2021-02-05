package lab1;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.security.*;
import java.util.*;

import javax.crypto.*;
import javax.crypto.spec.*;
import javax.swing.*;

public class csfhx1Main extends JFrame implements ActionListener
	{

public static void main(String[] args) 
	{
	new csfhx1Main();
	}

public	csfhx1Main()
	{
		// button
	JPanel	p_buttons = new JPanel(new GridLayout(6,1));
	b_key = create_button(p_buttons, "Create Key");
	b_enc_file = create_button(p_buttons, "Encrypt Key File");
	b_dec_file = create_button(p_buttons, "Decrypt Key File");
	b_enc_string = create_button(p_buttons, "Encrypt String Key");
	b_dec_string = create_button(p_buttons, "Decrypt String Key");
	b_quit = create_button(p_buttons, "Quit");
	add(p_buttons, "West");
	
		// text fields
	JPanel p_tf = new JPanel(new GridLayout(2, 2));
	p_tf.add(new JLabel("Key (file or string)"));
	tf_key = new JTextField(20);
	p_tf.add(tf_key);
	p_tf.add(new JLabel("Data (plain or cipher text)"));
	tf_data = new JTextField(20);
	p_tf.add(tf_data);
	add(p_tf, "East");
	
		// finish off
	setTitle("CSFH 1");
	setSize(600, 400);
	setLocation(600,300);
	setVisible(true);
	tf_key.requestFocus();
	}

private	JButton	create_button(JPanel p, String text)
	{
	JButton	b = new JButton(text);
	b.addActionListener(this);
	p.add(b);
	return b;
	}

public void actionPerformed(ActionEvent e) 
	{
try
	{
	Object b = e.getSource();
	if (b == b_quit)
		System.exit(0);
	
	else if (b == b_key)
		{
		KeyGenerator	kg = KeyGenerator.getInstance("AES");
		kg.init(128);	// key length 128 bits (16 bytes)
		Key	aes_key = kg.generateKey();
		byte[]	out = aes_key.getEncoded();
		write_file(tf_key.getText(), out);
		}
	else if (b == b_enc_file)
		{
		String	pt_file = tf_data.getText();
		enc_dec(Cipher.ENCRYPT_MODE, read_file(tf_key.getText()), pt_file, pt_file+"_rc");
		erase_file(pt_file);
		}
	else if (b == b_dec_file)
		{
		String	pt_file = tf_data.getText();
		enc_dec(Cipher.DECRYPT_MODE, read_file(tf_key.getText()), pt_file+"_rc", pt_file);	
		}
	else if (b == b_enc_string)
		{
		String	pt_file = tf_data.getText();
		enc_dec(Cipher.ENCRYPT_MODE, pad_key(tf_key.getText()).getBytes(), pt_file, pt_file+"_rc");
		erase_file(pt_file);	
		}
	else if (b == b_dec_string)
		{
		String	pt_file = tf_data.getText();
		enc_dec(Cipher.DECRYPT_MODE, pad_key(tf_key.getText()).getBytes(), pt_file+"_rc", pt_file);			
		}
	}
catch (Exception x)
	{
	System.err.println(x.toString());
	System.exit(1);
	}
	}

private	String	pad_key(String k)
	{
	int	klen = k.length();
	if (klen > 16)
		return k.substring(0,16);
	for (int i = klen; i < 16; i++)
		k += 'z';
	return k;
	}

private	void	enc_dec(int mode, byte[] kb, String infile, String outfile)
	{
try
	{
	Key	k = new SecretKeySpec(kb, "AES");
	byte[]	in = read_file(infile);
	Cipher	c = Cipher.getInstance("AES");
	c.init(mode, k);
	byte[]	out = c.doFinal(in);
	write_file(outfile, out);
	}
catch(Exception x)
	{
	System.err.println(x.toString());
	System.exit(1);
	}
	}

private	void	write_file(String fname, byte[] data)
	{
try
	{
	File	f = new File(fname);
	FileOutputStream fos = new FileOutputStream(f);
	fos.write(data);
	fos.close();
	}
catch (Exception x)
	{
	System.err.println(x.toString());
	System.exit(1);
	}
	}

private	byte[]	read_file(String fname)
	{
try
	{
	File	f = new File(fname);
	byte[]	data = new byte[(int)f.length()];
	FileInputStream fis = new FileInputStream(f);
	fis.read(data);
	fis.close();
	return data;
	}
catch (Exception x)
	{
	System.err.println(x.toString());
	System.exit(1);
	}
return null;
	}

private	void	erase_file(String fname)
	{
	File	f = new File(fname);
	Random	rand = new Random();
	byte[]	b = new byte[(int)f.length()];
	rand.nextBytes(b);
	write_file(fname, b);
	f.delete();
	}

private	JButton b_key, b_enc_file, b_dec_file, b_enc_string, b_dec_string, b_quit;
private	JTextField	tf_key, tf_data;
}
