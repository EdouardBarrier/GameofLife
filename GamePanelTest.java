package uk.ac.cam.efxlb2.oop.tick5;

import java.awt.Color;
import javax.swing.*;

public class GamePanelTest extends JPanel {
  
  private World world = null;
  private int h;
  private int w;
  
  public void setWorld(World input) {//TESTER
	  world=input;
  }
  
  /*public GamePanel() {
	  super();
	  setDefaultCloseOperation(EXIT_ON_CLOSE);
  setSize(972,768);//1024 wanted*/
  
  @Override
  protected void paintComponent(java.awt.Graphics g) {
    // Paint the background white
    g.setColor(java.awt.Color.WHITE);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());//happens every time
	h=this.getHeight();
	w=this.getWidth();
	if (world==null) {}
	//else {
		int pixperheight = (h-20)/19;//CHANGED from using world data
		int pixperwidth = w/19;//CHANGED from using world data
		int pixperbox = 0;
		if (pixperheight>pixperwidth){
			pixperbox=pixperwidth;
		}
		else {
			pixperbox=pixperheight;
		}
		System.out.println(pixperbox);//TESTER this returns the value, but somewhere along the line somehting crashes
		g.setColor(Color.LIGHT_GRAY);
		//g.drawRect(0,20,pixperbox,pixperbox);
		for (int i=0;i<19;i++){//CHANGED from using world data
			System.out.println("Problem is here");//TESTER
			for (int j=0;j<19;j++) {//CHANGED from using world data
				g.drawRect(i*pixperbox,(j*pixperbox)+20,pixperbox,pixperbox);
				/*if ((i*j/2)==0) {//CHANGED from using world data
					g.setColor(Color.BLACK);
					g.fillRect(pixperbox,pixperbox,j*pixperbox,i*pixperbox);
				}*/
			}
		}
		g.drawString("Generation :"+2,(w*1/10),(h*9/10));
	//}
  }

  public void display(World wo) {
    world = wo;
	repaint();
  }
  
  /*public static void main(String[] args) {
	  GamePanel gamePanel*/
}