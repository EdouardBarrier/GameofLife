package uk.ac.cam.efxlb2.oop.tick5;

public abstract class World implements Cloneable{
	
	//It should be world[row][col] and so created as new boolean[HEIGHT][WIDTH]
	
	private int generation;
	private Pattern pattern;
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		World cloned = (World) super.clone();
		//Pattern can use a shallow copy as it is immutable
		return cloned;
	}
	
	public World(String format) throws PatternFormatException{
		this.pattern = new Pattern(format);
		generation=0;
	}

	public World(Pattern format) throws PatternFormatException{
		this.pattern=format;
		generation=0;
	}
	
	public World(World w) {
		this.pattern = w.getPattern();//does it work? need a .clone()?
		generation=w.getGenerationCount();
	}
	
	public int getWidth() {
		return pattern.getWidth();
	}
	
	public int getHeight() {
		return pattern.getHeight();
	}
	
	public int getGenerationCount() {
		return generation;
	}
	public int getStartRow(){
		return pattern.getStartRow();
	}
	
	public int getStartCol(){
		return pattern.getStartCol();
	}
	
	public String getCells() {
		return pattern.getcells();
	}
	
	protected void incrementGenerationCount() {
		generation++;
	}
	
	protected Pattern getPattern() {
		return pattern;
	}
	
	protected int countNeighbours(int row, int col) {
		boolean left=getCell(row, col-1);
		int left1=left?1:0;
		boolean topleft=getCell(row-1, col-1);
		int topleft1=topleft?1:0;
		boolean top=getCell(row-1, col); 
		int top1=top?1:0;
		boolean topright=getCell(row-1, col+1);
		int topright1=topright?1:0;
		boolean right=getCell(row, col+1);
		int right1=right?1:0;
		boolean bottomright=getCell(row+1, col+1);
		int bottomright1=bottomright?1:0;
		boolean bottom=getCell(row+1, col);
		int bottom1=bottom?1:0;
		boolean bottomleft=getCell(row+1, col-1);
		int bottomleft1=bottomleft?1:0;
		int numneighbours=left1+topleft1+top1+topright1+right1+bottomright1+bottom1+bottomleft1;
		return numneighbours;
	}
	
	public abstract boolean getCell(int row, int col);
	
	public abstract void setCell(int row, int col, boolean newval);
	
	public void nextGeneration() {
		nextGenerationImpl();
		generation++;
	}
	
	protected abstract void nextGenerationImpl();
	
	//CLONE MISSING?
	
	protected boolean computeCell(int x, int y) {
		boolean nextCell;
		int neighbours = countNeighbours(x,y);//UA
		if (((neighbours < 2)|(neighbours>3))|(((getCell(x,y))==false)&(neighbours==2))) {//UA
			nextCell = false;
		}
		else {
			nextCell = true;
		}
		return nextCell;
	}
}