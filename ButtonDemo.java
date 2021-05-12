package uk.ac.cam.efxlb2.oop.tick5;

import javax.swing.*;
import javax.swing.border.*;
import java.awt.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class ButtonDemo extends JFrame implements ActionListener {
  
  public ButtonDemo() {
    JButton b = new JButton("Click me!");
    b.addActionListener(this);
    add(b);
  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    System.out.println("Button was clicked!");
  }
}

public class ButtonDemo extends JFrame  {
  
  public ButtonDemo() {
    JButton b = new JButton("Click me!");
    b.addActionListener(new ActionListener() {

      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println("Button was clicked!");
      }
    });
    add(b);
  }
}

public class ButtonDemo extends JFrame  {
  
  public ButtonDemo() {
    JButton b = new JButton("Click me!");
    b.addActionListener( e -> System.out.println("Button was clicked!"));
    add(b);
  }
}

//three button demo constructors made - slot them in where the buttons were?
//action needs to be: moveforward, then gamePanel.display() - will gamePanel be available?