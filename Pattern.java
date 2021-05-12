package uk.ac.cam.efxlb2.oop.tick5; 

public class Pattern implements Comparable<Pattern>{
	
	private String[] splitarray;
	private String name;
	private String author;
	private int width;
	private int height;
	private int StartCol;
	private int StartRow;
	private String cells;//fields
	
	public String getName() {
		return name;
	}
	public String getAuthor() {
		return author;
	}
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	public int getStartCol() {
		return StartCol;
	}
	public int getStartRow() {
		return StartRow;
	}
	public String getcells() {
		return cells;
	}
	
	@Override
	public int compareTo(Pattern o) {
		return getName().compareTo(o.getName());
	}
	
	@Override
	public String toString() {
		return getName()+" ("+getAuthor()+")";
	}
	
	public Pattern(String format) throws PatternFormatException{ //constructor
		try {
				if (format==null) {
					throw new PatternFormatException("Please specify a pattern.");
				}
			splitarray = format.split(":");
				if (splitarray.length!=7) {
					throw new PatternFormatException("Invalid pattern format: Incorrect number of fields in pattern (found "+splitarray.length+").");
				}
				if (splitarray[2].matches("\\d+")==false) {
					throw new PatternFormatException("Invalid pattern format:Could not interpret the width field as a number ('"+splitarray[2]+"' given).");
				}
				if (splitarray[3].matches("\\d+")==false) {
					throw new PatternFormatException("Invalid pattern format:Could not interpret the height field as a number ('"+splitarray[3]+"' given).");
				}
				if (splitarray[4].matches("\\d+")==false) {
					throw new PatternFormatException("Invalid pattern format:Could not interpret the startX field as a number ('"+splitarray[4]+"' given).");
				}
				if (splitarray[5].matches("\\d+")==false) {
					throw new PatternFormatException("Invalid pattern format:Could not interpret the startY field as a number ('"+splitarray[5]+"' given).");
				}
			name=splitarray[0];
			author=splitarray[1];
			width=Integer.parseInt(splitarray[2]);
			height=Integer.parseInt(splitarray[3]);
			StartCol=Integer.parseInt(splitarray[4]);
			StartRow=Integer.parseInt(splitarray[5]);
			cells=splitarray[6];
			String[] cellsplit=cells.split(" ");
				for (int i=0;i<cellsplit.length;i++) {
					if (cellsplit[0].matches("\\d+")==false) {
						throw new PatternFormatException("Invalid pattern format:Malformed pattern '"+cells+"'.");
					}
				}
			}
		catch (PatternFormatException p) {
			System.out.println(p.getMessage());
		}
	}
	
	
	public void initialise(World world) {
		cells=splitarray[6];
		try {
			if (splitarray==null) {//DOESN'T WORK
				throw new PatternFormatException("Please specify a pattern.");
			}
			if (splitarray.length!=7) {
				throw new PatternFormatException("Invalid pattern format: Incorrect number of fields in pattern (found "+splitarray.length+").");
			}
			if (splitarray[4].matches("\\d+")==false) {
				throw new PatternFormatException("Invalid pattern format:Could not interpret the startX field as a number ('"+splitarray[4]+"' given).");
			}
			if (splitarray[5].matches("\\d+")==false) {
				throw new PatternFormatException("Invalid pattern format:Could not interpret the startY field as a number ('"+splitarray[5]+"' given).");
			}
			String[] cellsplit=cells.split(" ");
			for (int i=0;i<cellsplit.length;i++) {
				if (cellsplit[i].matches("\\d+")==false) {
					throw new PatternFormatException("Invalid pattern format:Malformed pattern '"+cells+"'.");
				}
			}
			
			for(int i=0;i<cells.split(" ").length;i++){//row
				for(int j=0;j<cells.split(" ")[i].toCharArray().length;j++){//col
					boolean val=(Character.getNumericValue(cells.split(" ")[i].toCharArray()[j])==1);
					world.setCell((i+StartRow),(j+StartCol),val);
				}
			}
		}
		catch (PatternFormatException p) {
			System.out.println(p.getMessage());
		}
	}

}