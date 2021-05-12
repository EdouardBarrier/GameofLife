package uk.ac.cam.efxlb2.oop.tick5;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class BackButton extends JFrame {//needs to be activated when the button is pressed
	
	public BackButton() {
		JButton backbutton = new JButton("< Back");
		backbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				GUILife.moveBack();
			}
		});
		add(backbutton);
	}
}