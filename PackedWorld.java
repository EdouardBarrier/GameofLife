package uk.ac.cam.efxlb2.oop.tick5; 
import java.io.IOException;

public class PackedWorld extends World implements Cloneable{

	private long world;
	
	@Override
	public Object clone() throws CloneNotSupportedException{
		PackedWorld cloned = (PackedWorld) super.clone();
		cloned.world=0L;
		for (int i=0;i<this.getHeight();i++) {
			for (int j=0;j<this.getWidth();j++) {
				cloned.setCell(i,j,getCell(i,j));
			}
		}
		return cloned;
	}
	
	public PackedWorld(World pw) {
		super(pw);//possibly covariant parameter types
		world=0L;
		for (int i=0;i<this.getHeight();i++) {
			for (int j=0;j<this.getWidth();j++) {
				setCell(i,j,pw.getCell(i,j));
			}
		}
	}
	
	public PackedWorld(String format) throws Exception,PatternFormatException {
		super(format);
		String[] splitarray = format.split(":");
		world=0L;
		try{
			if(getHeight() * getWidth() > 64){
				throw new Exception("Too long.");
			}
			getPattern().initialise(this);
		}catch(Exception e){
			System.out.println(e.getMessage()); 
		}
	}
	
	public PackedWorld packedcopy(PackedWorld input) throws PatternFormatException {
		PackedWorld output = new PackedWorld(input.getPattern());
		return output;
	}
	
	public PackedWorld(Pattern pattern) throws PatternFormatException{
		super(pattern);
		world = 0L;
		getPattern().initialise(this);
	}
	
	public boolean getCell(int row, int col) {
		int position=col+row*getWidth();
		return ((1 & (world>>position))==1);//modify the stuff
	}
	
	public void setCell(int row, int col, boolean val) {
		if (((col<0)|(col>getWidth()))|((row<0)|(row>getHeight()))) System.out.println("Values need checking");
		else {
			int position=col+row*getWidth();
			if (val==true) {
				long modifyingagent1=(1L<<position);
				world=(world|modifyingagent1);
			}
			else {
				world=~world;
				long modifyingagent=(1L<<position);
				world=(world|modifyingagent);
				world=~world;
			}
		}
	}
	
	public void nextGenerationImpl() {
		long newWorld=0L;
		for(int i = 0; i < getHeight(); i++) {//i is the row count
			for(int j = 0; j < getWidth(); j++) {//j is column
				int position=j+i*getWidth();
				if (computeCell(j,i)) {
					long modifyingagent1=(1L<<position);
					newWorld=(newWorld|modifyingagent1);
				}
				else {
					newWorld=~newWorld;
					long modifyingagent=(1L<<position);
					newWorld=(newWorld|modifyingagent);
					newWorld=~newWorld;
				}
			}
		}
		world=newWorld;
	}
}