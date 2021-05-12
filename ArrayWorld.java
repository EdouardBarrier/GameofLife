package uk.ac.cam.efxlb2.oop.tick5;

public class ArrayWorld extends World implements Cloneable{
	 
	private boolean[][] world;
	private boolean[] deadRow;
	
	public boolean[] getdeadRow() {
		return deadRow;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException{ //cloning constructor
		ArrayWorld cloned = (ArrayWorld) super.clone();
		cloned.deadRow = this.getdeadRow();
		cloned.world=new boolean[this.getHeight()][this.getWidth()];
		int counter=0;
		for (int i=0;i<this.getHeight();i++) {
			for (int j=0;j<this.getWidth();j++) {
				cloned.setCell(i,j,getCell(i,j));
				if (getCell(i,j)==true) {counter++;}
			}
			if (counter==0) {
				cloned.world[i]=deadRow;
			}
			counter=0;
		}
		return cloned;
	}
	
	public ArrayWorld(ArrayWorld aw) {//cloning constructor
		super(aw);//possibly covariant parameter types
		deadRow=aw.getdeadRow();
		world=new boolean[this.getHeight()][this.getWidth()];
		int counter=0;
		for (int i=0;i<this.getHeight();i++) {
			for (int j=0;j<this.getWidth();j++) {
				setCell(i,j,aw.getCell(i,j));
				if (aw.getCell(i,j)==true) {counter++;}
			}
			if (counter==0) {
				world[i]=deadRow;
			}
			counter=0;
		}
	}
		
	
	public ArrayWorld(String format) throws PatternFormatException{ //constructor
		super(format);
		deadRow = new boolean[this.getWidth()];
		world =new boolean[this.getHeight()][this.getWidth()];
		getPattern().initialise(this);
		int counter=0;
		for (int i=0;i<this.getHeight();i++) {
			for (int j=0;j<this.getWidth();j++) {
				if (this.getCell(i,j)==true) {counter++;}
			}
			if (counter==0) {
				world[i]=deadRow;
			}
			counter=0;
		}
	}
	
	public ArrayWorld(Pattern pattern) throws PatternFormatException{//constructor
		super(pattern);
		deadRow = new boolean[this.getWidth()];
		world = new boolean[this.getHeight()][this.getWidth()];
		getPattern().initialise(this);
		int counter=0;
		for (int i=0;i<this.getHeight();i++) {
			for (int j=0;j<this.getWidth();j++) {
				if (this.getCell(i,j)==true) {counter++;}
			}
			if (counter==0) {
				world[i]=deadRow;
			}
			counter=0;
		}
	}
	
	public boolean getCell(int row, int col) {
		if (row<0||row>=this.getHeight()) return false;
		if (col<0||col>=this.getWidth()) return false;
		
		return world[row][col];
	}
	
	public ArrayWorld arraycopy(ArrayWorld input) throws PatternFormatException {
		ArrayWorld output = new ArrayWorld(input.getPattern());
		//need to copy some state - what state?
		return output;
	}
	
	public void setCell(int row, int col, boolean newval) {
		if (row<0||row>=this.getHeight()) System.out.println("Values need checking");
		if (col<0||col>=this.getWidth()) System.out.println("Values need checking");
		
		world[row][col]=newval;
	}
	
	public void nextGenerationImpl() {
		boolean[][] World=new boolean[this.getHeight()][];
		for(int i = 0; i < this.getHeight(); i++) {
			World[i] = new boolean[this.getWidth()];
			for(int j = 0; j < this.getWidth(); j++) {
				World[i][j] = computeCell(i,j);
			}
		}
		world=World; //loops back to the main one
	}
}