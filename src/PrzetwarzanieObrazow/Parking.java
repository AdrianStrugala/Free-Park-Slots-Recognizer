package PrzetwarzanieObrazow;

import java.awt.Point;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Vector;

public class Parking {
	
	private static final int limitSize = 50;
	
	private static int limitX = 0;
	private static int limitY = 0;
	
	/*
	 * LINIE HOUGHA
	 */
	Vector<HoughLine> linie(int w, int h, ObrazPanel tymczas, String sasiedztwoTxt){
	
		limitX = w - limitSize;
		limitY = h - limitSize;
		
		Vector<HoughLine> lines = new Vector<HoughLine>();
		Vector<HoughLine> linesFiltered = new Vector<HoughLine>();
		
		int sasiedztwo = 0;
		if(w>=h){sasiedztwo = h/30;}
		else{sasiedztwo = w/30;}
	
		sasiedztwo = Integer.parseInt(sasiedztwoTxt);
		
		HoughTransform ht = new HoughTransform(tymczas.image, sasiedztwo);
		lines = ht.getLines(50, 0);
	
		// filtering
		for (int y = 0; y < lines.size(); y++) {
			if (lines.get(y).x1 >= 0  && lines.get(y).x2 >= 0 && lines.get(y).y1 >= 0 && lines.get(y).y2 >= 0) {//czy nie wychodza poza zakres obrazka
				if(lines.get(y).x1 <limitX && lines.get(y).y1 <limitY){ //czy nie sa rysowane po krawedzie
					if(!((Math.abs(lines.get(y).x2-lines.get(y).x1) > w/5) && (Math.abs(lines.get(y).y2-lines.get(y).y1) > h/5))) //czy nie sa ukosne
					linesFiltered.add(lines.get(y));
					
					}
			}
		}
		
		return linesFiltered;
	}
	

	
	public Vector<Rectangle> prostokaty(Vector<HoughLine> linesHorizontal, Vector<HoughLine> linesVertical) {

		Vector<Rectangle> prostokatyVec = new Vector<Rectangle>();
		
		
		for (int i=0; i<linesHorizontal.size()-1; i++) {
			for (int j=0; j<linesVertical.size()-1; j++)  {
				
				Rectangle prostokat = new Rectangle();
				
				prostokat.x1 = lineIntersect(linesHorizontal.get(i),linesVertical.get(j)).getX();
				prostokat.y1 = lineIntersect(linesHorizontal.get(i),linesVertical.get(j)).getY();
				
				prostokat.x2 = lineIntersect(linesHorizontal.get(i),linesVertical.get(j+1)).getX();
				prostokat.y2 = lineIntersect(linesHorizontal.get(i),linesVertical.get(j+1)).getY();
				
				prostokat.x3 = lineIntersect(linesHorizontal.get(i+1),linesVertical.get(j)).getX();
				prostokat.y3 = lineIntersect(linesHorizontal.get(i+1),linesVertical.get(j)).getY();
				
				prostokat.x4 = lineIntersect(linesHorizontal.get(i+1),linesVertical.get(j+1)).getX();
				prostokat.y4 = lineIntersect(linesHorizontal.get(i+1),linesVertical.get(j+1)).getY();
				
				prostokatyVec.add (prostokat);
								
			}
		}
		
		return prostokatyVec;
	}
		
		public static Point lineIntersect(HoughLine horizontal, HoughLine vertical){
			float x1 = vertical.x1;
			float y1 = vertical.y1;
			float x2 = vertical.x2;
			float y2 = vertical.y2;
			float x12 = horizontal.x1;
			float y12 = horizontal.y1;
			float x22 = horizontal.x2;
			float y22 = horizontal.y2;

			double denom = (y22 - y12) * (x2 - x1) - (x22 - x12) * (y2 - y1);
			if (denom == 0.0) { // Lines are parallel.
				return new Point(0, 0);
			}
			double ua = ((x22 - x12) * (y1 - y12) - (y22 - y12) * (x1 - x12)) / denom;
			double ub = ((x2 - x1) * (y1 - y12) - (y2 - y1) * (x1 - x12)) / denom;
			if (ua >= 0.0f && ua <= 1.0f && ub >= 0.0f && ub <= 1.0f) {
				// Get the intersection point.
				return new Point((int) (x1 + ua * (x2 - x1)), (int) (y1 + ua * (y2 - y1)));
			}

			return new Point(0, 0);
		}

	 void writeLines(Vector<HoughLine> linesFiltered) {
		PrintWriter zapis = null;
		
		try {
			zapis = new PrintWriter("linie.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		for (int y = 0; y < linesFiltered.size(); y++) {
			zapis.print("r: " + linesFiltered.get(y).r);
			zapis.print(" score: " + linesFiltered.get(y).score);
			zapis.print(" theta: " + linesFiltered.get(y).theta);
			zapis.print(" x1: " + linesFiltered.get(y).x1);
			zapis.print(" x2: " + linesFiltered.get(y).x2);
			zapis.print(" y1: " + linesFiltered.get(y).y1);
			zapis.print(" y2: " + linesFiltered.get(y).y2);
			zapis.print("\n");
		}

		zapis.close();
	}
	

	void writePixels(int w, int h, ObrazPanel tymczas) {
		int[][] table = new int[w][h];

		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				// Find non-black pixels
				table[x][y] = tymczas.image.getRGB(x, y);
			}
		}

		PrintWriter zapis = null;
		try {
			zapis = new PrintWriter("pixele.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (int x = 0; x < w; x++) {
			for (int y = 0; y < h; y++) {
				zapis.print(table[x][y]);
			}
			zapis.print("\n");
		}

		zapis.close();
		
	}

	
}
