package uk.ac.cam.efxlb2.oop.tick5;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;

public class GUILife extends JFrame {
	
	private World world;
	private PatternStore store;
	private ArrayList<World> cachedWorlds;
	protected GamePanel gamePanel;
	private JButton playButton;
	private java.util.Timer timer = new java.util.Timer(true);//CHANGE
	private boolean playing;
    
  public GUILife(PatternStore ps) throws PatternFormatException{
	super("Game of Life");
	try {
		store=ps;
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(1024,768);

		add(createPatternsPanel(),BorderLayout.WEST);
		add(createControlPanel(),BorderLayout.SOUTH);
		gamePanel = createGamePanel();//DIFFERENT THAN THE OTHER PANELS BECAUSE HAS ITS OWN STATE
  
		ArrayWorld world2 = new ArrayWorld(ps.getPatternsNameSorted().get(10));//TESTER
		add(gamePanel,BorderLayout.CENTER);
	}
	catch (PatternFormatException e) {
		System.out.print("Another exception...");
	}
}
  
private void addBorder(JComponent component, String title) {
  Border etch = BorderFactory.createEtchedBorder(EtchedBorder.LOWERED);
  Border tb = BorderFactory.createTitledBorder(etch,title);
  component.setBorder(tb);
}

	private GamePanel createGamePanel() {
		GamePanel gamePanel = new GamePanel();
		addBorder(gamePanel,"Game Panel");
		return gamePanel;
	}

  private Pattern[] makearray(java.util.List<Pattern> input) {
	Pattern[] output = new Pattern[input.size()];
	for (int i=0;i<input.size();i++) {
		output[i]=input.get(i);
	}
	return output;
  }
 
private JPanel createPatternsPanel() {
  JPanel patt = new JPanel();
  addBorder(patt,"Patterns");
  GridLayout layout = new GridLayout(1,0);
  patt.setLayout(layout);//sets a correct new layout
  java.util.List<Pattern> prenames = store.getPatternsNameSorted();
  Pattern[] names = makearray(prenames);//uses the helper function to transfer information across to get the types right
  JList list = new JList(names);
  list.addListSelectionListener(new ListSelectionListener() {//WHERE THE LISTENER IS ADDED
	  @Override
	  public void valueChanged(ListSelectionEvent e) {
		  JList<Pattern> listII = (JList<Pattern>) e.getSource();
		  Pattern p = listII.getSelectedValue();
		  ArrayList<World> newlist = new ArrayList<>(); //using clear() caused a nullpointer exeption, easy workaround
		  cachedWorlds = newlist;
		  if ((p.getHeight()*p.getWidth())>64) {
			  try {
				  ArrayWorld arrayinput = new ArrayWorld(p);
				  world=arrayinput;
				  cachedWorlds.add(world);
			  }
			  catch (PatternFormatException pfe) {
				  System.out.println("Oops, a Pattern Format Exception was detected!");
			  }
		  }
		  else {
			  try {
				  PackedWorld packedinput = new PackedWorld(p);
				  world=packedinput;
				  cachedWorlds.add(world);
			  }
			  catch (PatternFormatException pf) {
				  System.out.println("Oops, a Pattern Format Exception was detected!");
			  }
		  }
		  playing=true;
		  runOrPause();
		  gamePanel.display(world);
	  }
    });
  patt.add(new JScrollPane(list));
  return patt; 
}

private JPanel createControlPanel() {
  JPanel ctrl =  new JPanel();
  addBorder(ctrl,"Controls");
  GridLayout layout = new GridLayout(1,0);
  ctrl.setLayout(layout);//sets a new layout so the buttons are the right size
  
  JButton backbutton = new JButton("< Back");//Adding the listener directly in this bit
		backbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				playing=true;
				runOrPause();
				moveBack();
			}
		});
  ctrl.add(backbutton, BorderLayout.WEST);
  
  playButton = new JButton("Play");
	playButton.addActionListener(new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
			runOrPause();
		}
	});
  ctrl.add(playButton, BorderLayout.CENTER);
  
  JButton forwardbutton = new JButton("Forward >");//Direct addition of listener again
		forwardbutton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					playing=true;
					runOrPause();
					moveForward();
				}
				catch(CloneNotSupportedException exc) {
					System.out.print("Oops, a CloneNotSupportedException was thrown!");
				}
			}
		});
  ctrl.add(forwardbutton, BorderLayout.EAST);//adds the buttons needed
  return ctrl;
}

	private void runOrPause() {
		if (playing) {
			timer.cancel();
			playing=false;
			playButton.setText("Play");
		}
		else {
			playing=true;
			playButton.setText("Stop");
			timer = new java.util.Timer(true);
			timer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					try {
						moveForward();
					}
					catch (CloneNotSupportedException e) {
						System.out.println("Oops, a Clone Not Supported Exception was caught!");
					}
				}
			}, 0, 500);
		}
	}
	
  public static void main(String[] args) throws PatternFormatException , IOException{
	try {
		String url = "https://www.cl.cam.ac.uk/teaching/1819/OOProg/ticks/life.txt"; 
		PatternStore patterns = new PatternStore(url);
		GUILife gui = new GUILife(patterns);
		gui.setVisible(true);
	}
	catch (PatternFormatException e) {
		System.out.println("Another exception...");
	}
  }
  
  private World copyWorld(boolean useCloning) throws CloneNotSupportedException {//copypasted
		World copy = null;
		if (useCloning==false) {
			if (world instanceof PackedWorld) {
				copy = new PackedWorld(world);
			}
			else if (world instanceof ArrayWorld){
				ArrayWorld aw = (ArrayWorld)world;
				copy = new ArrayWorld(aw);
			}
		}
		else {
			copy = (World) world.clone();
		}
		return copy;
	}
  
	public void moveBack() {
		int desiredGen = world.getGenerationCount()-1;
		for (int i=0;i<cachedWorlds.size();i++) {
			if ((cachedWorlds.get(i).getGenerationCount())==desiredGen) {
				world=cachedWorlds.get(i);
			}
		}
		gamePanel.display(world);
	}
  
	public void moveForward() throws CloneNotSupportedException{
		int desiredGen=world.getGenerationCount()+1;
		boolean found=false;
		World newworld=null;
		for (int i=0;i<cachedWorlds.size();i++) {
			if ((cachedWorlds.get(i).getGenerationCount())==desiredGen) {
				world=cachedWorlds.get(i);
				found=true;
			}
		}
		if (found) {
		}
		else {
			world=copyWorld(true);
			world.nextGeneration();
			cachedWorlds.add(world);//REMOVED THE PRINT METHOD FROM THESE 
		}
		gamePanel.display(world);
	}
 }