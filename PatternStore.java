package uk.ac.cam.efxlb2.oop.tick5;

import java.io.*;
import java.net.*;
import java.util.*;

public class PatternStore {
	
	private List<Pattern> patterns = new LinkedList<>();
	private Map<String,List<Pattern>> mapAuths = new HashMap<>();
	private Map<String,Pattern> mapName = new HashMap<>();
	
	public List<Pattern> getpatterns(){
		return patterns;
	}
	
	public Map<String,List<Pattern>> getmapAuths(){
		return mapAuths;
	}
	
	public Map<String,Pattern> getmapName(){
		return mapName;
	}
	
	public PatternStore(String source) throws IOException {
		try {
			if (source.startsWith("https://")||source.startsWith("http://")) {
				loadFromURL(source);
			}
			else {
				loadFromDisk(source);
			}
		}
		catch (PatternFormatException p) {
			System.out.println(p.getMessage());
		}
	}
	
	public PatternStore(Reader source) throws IOException {
		try {
			load(source);
		}
		catch (PatternFormatException p) {
			System.out.println(p.getMessage());
		}
	}
	
	private void load(Reader r) throws IOException,PatternFormatException{
		BufferedReader b = new BufferedReader(r);
		String line;
		while ( (line = b.readLine()) != null) {
			try {
				Pattern pattern = new Pattern(line);
				patterns.add(pattern);
				mapName.put(pattern.getName(),pattern);
				authormap(pattern);	
			}
			catch(PatternFormatException p) {
				System.out.println("Warning, "+line+"is a malformed pattern");
			}
		}
	}
	
	private void authormap(Pattern pattern){
			if (mapAuths.get(pattern.getAuthor())==null){
				List<Pattern> authlist = new ArrayList<>();
				authlist.add(pattern);
				mapAuths.put(pattern.getAuthor(),authlist);
			}
			else {
				List<Pattern> authlist = new ArrayList<>(mapAuths.get(pattern.getAuthor()));
				authlist.add(pattern);
				mapAuths.put(pattern.getAuthor(),authlist);
			}
	}
	
	private void loadFromURL(String url) throws IOException,PatternFormatException {
		try {
			URL destination = new URL(url);
			URLConnection conn = destination.openConnection();
			Reader r = new InputStreamReader(conn.getInputStream());
			load(r);
		}
		catch (PatternFormatException p) {
			System.out.println(p.getMessage());
		}
	}
	
	private void loadFromDisk(String filename) throws IOException,PatternFormatException {
		try {
			Reader r = new FileReader(filename);
			load(r);
		}
		catch (PatternFormatException p) {
			System.out.println(p.getMessage());
		}
	}
	
	public static void main(String[] args) throws IOException,PatternFormatException{
		PatternStore ps = new PatternStore("https://www.cl.cam.ac.uk/teaching/1819/OOProg/ticks/lifetest.txt");
	}
	
	
	
	public List<Pattern> getPatternsNameSorted() {
		List<Pattern> output = new LinkedList<>(patterns);
		Collections.sort(output);
		return output;
	}

	public List<Pattern> getPatternsAuthorSorted() throws PatternNotFound{
		try {
			List<String> authors = new LinkedList<>(getPatternAuthors());
			List<Pattern> output = new LinkedList<>();
			for (int i=0;i<authors.size();i++) {
				if ((getPatternsByAuthor(authors.get(i)))==null) {
					throw new PatternNotFound("There are no patterns for this author");
				}
				output.addAll(getPatternsByAuthor(authors.get(i)));
			}
			return output;
		}
		catch (PatternNotFound p) {
			System.out.println(p.getMessage());
			List<Pattern> noutput = new LinkedList<>();
			return noutput;
		}
	}

	public List<Pattern> getPatternsByAuthor(String author) throws PatternNotFound {
		try {
			List<Pattern> output = new LinkedList<>();
			if (mapAuths.get(author)==null) {
				throw new PatternNotFound("No patterns were found for this author");
			}
			output = mapAuths.get(author);
			Collections.sort(output);
			return output;
		}
		catch (PatternNotFound p) {
			System.out.println(p.getMessage());
			List<Pattern> output = new LinkedList<>();
			return output;
		}
	}

	public Pattern getPatternByName(String name) throws PatternNotFound {
		try{
			if (mapName.get(name)==null) {
				throw new PatternNotFound("No patterns were found with this name");
			}
			Pattern output = mapName.get(name);
			return output;
		}
		catch (PatternNotFound p) {
			System.out.println(p.getMessage());
			Pattern pat =null;
			return pat;
		}
	}

	public List<String> getPatternAuthors() {
		List<String> output = new LinkedList<>();
		output.addAll(mapAuths.keySet());
		Collections.sort(output);
		return output;
	}

	public List<String> getPatternNames() {
		List<String> output = new LinkedList<>();
		output.addAll(mapName.keySet());
		Collections.sort(output);
		return output;
	}
}
			