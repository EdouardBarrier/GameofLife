package uk.ac.cam.efxlb2.oop.tick5;

import java.awt.Color;
import javax.swing.*;

public class GamePanel extends JPanel {
  
  private World world = null;
  private int h = this.getHeight();
  private int w = this.getWidth();
  
  public void setWorld(World input) {//TESTER
	  world=input;
  }
  
  @Override
  protected void paintComponent(java.awt.Graphics g) {
    // Paint the background white
    g.setColor(java.awt.Color.WHITE);
    g.fillRect(0, 0, this.getWidth(), this.getHeight());//happens every time
	h=this.getHeight();
	w=this.getWidth();
	if (world==null) {}
	else {
		//System.out.println("Problem is here");//TESTER
		int pixperheight = (h-20)/world.getHeight();
		int pixperwidth = (w-10)/world.getWidth();
		int pixperbox = 0;
		if (pixperheight>pixperwidth){
			pixperbox=pixperwidth;
		}
		else {
			pixperbox=pixperheight;
		}
		//System.out.println(pixperbox);//TESTER this returns the value, but somewhere along the line somehting crashes
		g.setColor(Color.LIGHT_GRAY);
		for (int i=0;i<world.getHeight();i++){
			for (int j=0;j<world.getWidth();j++) {
				g.drawRect((i*pixperbox)+10,(j*pixperbox)+20,pixperbox,pixperbox);
				if (world.getCell(i,j)) {
					g.setColor(Color.BLACK);
					g.fillRect((i*pixperbox)+10,(j*pixperbox)+20,pixperbox,pixperbox); 
					g.setColor(Color.LIGHT_GRAY);
					g.drawRect((i*pixperbox)+10,(j*pixperbox)+20,pixperbox,pixperbox);
				}
			}
		}
		g.setColor(Color.BLACK);
		g.drawString("Generation :"+world.getGenerationCount(),(w*1/10),(h-20));
	}
  }

  public void display(World wo) {
    world = wo;
	repaint();
  }
}