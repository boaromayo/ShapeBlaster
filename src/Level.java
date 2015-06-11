package ShapeBlaster;

import java.io.*;
import java.util.*;

public class Level {

	private int level;
	protected ShapeParticle[][] shapeArray;
	private int row, col;
	
	public Level() {
		level = 0;
		
		try {
			readLevels();
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getStackTrace());
		}
	}
	
	public Level(int newLevel) {
		level = newLevel;
		
		try {
			readLevels();
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getStackTrace());
			System.exit(0);
		}
		
	}
	
	public Level(String fileName) {
		
		try {
			readLevelFile(fileName);
		} catch (Exception levele) {
			System.err.println("ERROR: " + levele.getStackTrace());
			System.exit(0);
		}
	}
	
	private void readLevels() throws IOException {
		
		switch(level){
		case 0:
			row = 1;
			col = 10;
			break;
		case 1:
			row = col = 9;
			break;
		case 2:
			row = 6;
			col = 13;
			break;
		case 3:
			row = col = 8;
			break;
		case 4:
			row = 6;
			col = 8;
			break;
		case 5:
			//readLevelFile("level5.txt");
			break;
		default:
			break;
		}
		
		shapeArray = new ShapeParticle[row][col];
	}
	
	public ShapeParticle[][] readLevelFile(String fileName) throws IOException {
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		ArrayList<String> lines = new ArrayList<String>();
		int sep = 18;
		
		for (int yrow = 0; yrow < lines.size(); yrow++) {
			String line = br.readLine();
			
			for (int xcol = 0; xcol < line.length(); xcol++) {
				
				if (!line.startsWith("#") || !line.startsWith("//")) {
					char [] charArray = line.toCharArray();
					
					switch(charArray[xcol]) {
					case 'O':
						shapeArray[yrow][xcol] = new CircleParticle((xcol * sep), (yrow * sep));
						break;
					case 'S':
						shapeArray[yrow][xcol] = new SquareParticle((xcol * sep), (yrow * sep));
						break;
					case 'T':
						shapeArray[yrow][xcol] = new TriParticle((xcol * sep), (yrow * sep));
						break;
					case 'R':
						shapeArray[yrow][xcol] = new RhombParticle((xcol * sep), (yrow * sep));
						break;
					case ' ':
						// Add nothing.
						break;
					}
				
				}
				
				lines.add(line);
			}
			
			col = line.length();
		}
		
		row = lines.size();
		
		shapeArray = new ShapeParticle[row][col];
		
		br.close(); // Close the file.
		
		return shapeArray;
	}
	
	public ShapeParticle[][] loadParticles(int level) {
		switch (level){
		case 0:
			for (int yrow = 0; yrow < shapeArray.length; yrow++) {
				for (int xcol = 0; xcol < shapeArray[yrow].length - 1; xcol++)
					shapeArray[yrow][xcol] = new CircleParticle(190 + (xcol * 18), 180 + (yrow * 20));
				
				for (int xcol = shapeArray[yrow].length - 1; 
						xcol < shapeArray[yrow].length; xcol++)
					shapeArray[yrow][xcol] = new SquareParticle(190 + (xcol * 18), 180 + (yrow * 20));
			}
			
			return shapeArray;
		case 1:
			for (int yrow = 0; yrow < 1; yrow++)
				for (int xcol = 0; xcol < shapeArray[yrow].length; xcol++)
					shapeArray[yrow][xcol] = new SquareParticle(220 + (xcol * 20), 80 + (yrow * 20));
				
			for (int yrow = 1; yrow < shapeArray.length; yrow++)
				for (int xcol = 0; xcol < shapeArray[yrow].length; xcol++)
					shapeArray[yrow][xcol] = new CircleParticle(220 + (xcol * 20), 80 + (yrow * 20));
			
			return shapeArray;
		case 2:
			for (int yrow = 0; yrow < 1; yrow++) 
				for (int xcol = 0; xcol < shapeArray[yrow].length; xcol++) 
					shapeArray[yrow][xcol] = new SquareParticle(180 + (xcol * 20), 80 + (yrow * 20));	
				
			for (int yrow = 1; yrow < shapeArray.length; yrow++) 
				for (int xcol = 0; xcol < shapeArray[yrow].length; xcol++) 
					shapeArray[yrow][xcol] = new CircleParticle(180 + (xcol * 20), 80 + (yrow * 20));
				
			return shapeArray;
		case 3:
			for (int yrow = 0; yrow < 4; yrow++) 
				for (int xcol = 0; xcol < shapeArray[yrow].length; xcol++) 
					shapeArray[yrow][xcol] = new TriParticle(210 + (xcol * 20), 80 + (yrow * 20));
				
			for (int yrow = 4; yrow < shapeArray.length; yrow++) 
				for (int xcol = 0; xcol < shapeArray[yrow].length; xcol++) 
					shapeArray[yrow][xcol] = new CircleParticle(210 + (xcol * 20), 80 + (yrow * 20));
				
			return shapeArray;
		case 4:
			for (int yrow = 0; yrow < shapeArray.length; yrow++) {
				for (int xcol = 0; xcol < 4; xcol++)
					shapeArray[yrow][xcol] = new SquareParticle(180 + (xcol * 20), 100 + (yrow * 20));
				
				for (int xcol = 4; xcol < shapeArray[yrow].length; xcol++)
					shapeArray[yrow][xcol] = new SquareParticle(300 + (xcol * 20), 100 + (yrow * 20));
			}
			
			return shapeArray;
		default:
			return null;
		}
		
	}
	
	public void setLevel(int newLevel) {
		level = newLevel;
		
		try {
			readLevels();
		} catch (Exception levele) {
			System.err.println("ERROR: " + levele.getStackTrace());
		}
	}
	
	public int getLevel() {
		return level;
	}
	
}
