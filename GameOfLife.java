package uk.ac.cam.efxlb2.oop.tick5;

import java.io.*;
import java.net.*;
import java.util.*;

public class GameOfLife {
	
	private World world;
	private PatternStore store;
	private ArrayList<World> cachedWorlds;
	
	public GameOfLife(PatternStore ps) throws PatternFormatException {
		store=ps;
		cachedWorlds=new ArrayList<World>();
	}
	
	private World copyWorld(boolean useCloning) throws CloneNotSupportedException {
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
	
	public void play() throws java.io.IOException,PatternFormatException,CloneNotSupportedException {//assigning/initialising world happens in here
		String response="";
		BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
    
		System.out.println("Please select a pattern to play (l to list:)");
		while (!response.equals("q")) {
    		response = in.readLine();
			System.out.println("Your input was "+response);//modified
			if (response.equals("f")) {
				if (world == null) {
					System.out.println("Please select a pattern to play (l to list):");
				}
				else {//BIT BELOW CHANGED
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
						print();
					}//the current world object must be added to the list, then its reference can be changed
					else {
						world=copyWorld(true);
						world.nextGeneration();
						cachedWorlds.add(world);
						print();
					}
				}
			}
			else if (response.equals("l")) {
				List<Pattern> names = store.getPatternsNameSorted();
				int i = 0;
				for (Pattern p : names) {
					System.out.println(i+" "+p.getName()+"  ("+p.getAuthor()+")");
					i++;
				}
			}
			else if (response.startsWith("p")) {
				List<Pattern> names = store.getPatternsNameSorted();
				String[] args = response.split(" ");
				int patternnumber = Integer.parseInt(args[1]);
				Pattern pattern = names.get(patternnumber);
				if ((pattern.getHeight()*pattern.getWidth())>64) {
					world = new PackedWorld(pattern);//initialising happens in here
				}
				else {
					world = new ArrayWorld(pattern);//and here
				}
				print();
				cachedWorlds.add(world);//CHANGE
			}
			else if (response.equals("b")) {
				int desiredGen = world.getGenerationCount()-1;
				for (int i=0;i<cachedWorlds.size();i++) {
					if ((cachedWorlds.get(i).getGenerationCount())==desiredGen) {
						world=cachedWorlds.get(i);
					}
				}//this depends heavily on the copyworld - not finished
				print();
			}
		}
	}

	
	public void print() {
		System.out.println("- "+world.getGenerationCount());
		for (int row=0; row<world.getHeight(); row++) {
			for (int col=0; col<world.getWidth(); col++) {
				System.out.print(world.getCell(row, col) ? "#" : "_");//world from getCell
			}
			System.out.println();
		}
	}
	
	public static void main(String[] args) throws java.io.IOException,Exception,PatternFormatException {
		if (args.length!=1) {
			System.out.println("Usage: java GameOfLife <path/url to store>");
			return;
		}
  
		try {
			PatternStore ps = new PatternStore(args[0]);
			GameOfLife gol = new GameOfLife(ps);   			
			gol.play();
		}
		catch (IOException ioe) {
			System.out.println("Failed to load pattern store");
		}
		catch (PatternFormatException p) {
			System.out.println(p.getMessage());
		}
	}
} 