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
	Vector<HoughLine> linie(int w, int h, ObrazPanel tymczas){
	
		limitX = w - limitSize;
		limitY = h - limitSize;
		
		Vector<HoughLine> lines = new Vector<HoughLine>();
		Vector<HoughLine> linesFiltered = new Vector<HoughLine>();
			
	
		HoughTransform ht = new HoughTransform(tymczas.image);
		lines = ht.getLines(20, 0);
	
		// filtering
		for (int y = 0; y < lines.size(); y++) {
			if (lines.get(y).x1 >= 0  && lines.get(y).x2 >= 0 && lines.get(y).y1 >= 0 && lines.get(y).y2 >= 0) {
				if(lines.get(y).x1 <limitX && lines.get(y).y1 <limitY)
					{linesFiltered.add(lines.get(y));}
			}
		}
		
		return linesFiltered;
	}
	
	/*
	 * PRZECIECIA
	 */

	public Vector<Point> przeciecia(Vector<HoughLine> linesHorizontal, Vector<HoughLine> linesVertical) {
		
		Vector<Point> przeciecia = new Vector<Point>();
	
		for (int i=0; i<linesHorizontal.size(); i++) {
			for (int j=0; j<linesVertical.size(); j++)  {
				
	
				Point przeciecie = lineIntersect(linesVertical.get(j).x1, linesVertical.get(j).y1, linesVertical.get(j).x2, linesVertical.get(j).y2,
						linesHorizontal.get(i).x1, linesHorizontal.get(i).y1, linesHorizontal.get(i).x2, linesHorizontal.get(i).y2);
				
//				if(przeciecie.getX() > 0 && przeciecie.getX() < limitX ){
					przeciecia.add(przeciecie);
					
//				}
					
			}
		}
		przeciecia = sort(przeciecia);
		
		return przeciecia;
	}
	
	public Vector<Rectangle> prostokaty(Vector<HoughLine> linesHorizontal, Vector<HoughLine> linesVertical, Vector<Point> przeciecia) {

		Vector<Rectangle> prostokatyVec = new Vector<Rectangle>();
		Rectangle[] prostokaty = new Rectangle[(linesHorizontal.size())*(linesVertical.size())];
		
		for(int i=0;i<prostokaty.length;i++){
			prostokaty[i] = new Rectangle();
		}
		
		for (int i=0; i<linesHorizontal.size(); i++) {
			for (int j=0; j<linesVertical.size(); j++)  {
				
			
				int iterator = (j)+(i*linesVertical.size());
				int iteratorProstokataNaDole = (j)+((i-1)*linesVertical.size());
				
				Point przeciecie = przeciecia.get(iterator);
				
				prostokaty[iterator].x1=przeciecie.getX();
				prostokaty[iterator].y1=przeciecie.getY();
				
				if(j>0){	
					prostokaty[iterator-1].x2=przeciecie.getX();
					prostokaty[iterator-1].y2=przeciecie.getY();
					
				}
					
				if(i>0){
						prostokaty[iteratorProstokataNaDole].x3=przeciecie.getX();
						prostokaty[iteratorProstokataNaDole].y3=przeciecie.getY();
										
				}
				
				if(j>0 && i>0){
					prostokaty[iteratorProstokataNaDole-1].x4=przeciecie.getX();
					prostokaty[iteratorProstokataNaDole-1].y4=przeciecie.getY();
				}
				
			}
		}
						
	    for (int i = 0; i < prostokaty.length; i++) {
	    	
	    	if(prostokaty[i].x1> 0 && prostokaty[i].x2> 0 && prostokaty[i].x3> 0 && prostokaty[i].x4> 0 )		        	
	    	prostokatyVec.add(prostokaty[i]);
	    }
	    return prostokatyVec;
	}
		
		public static Point lineIntersect(float x1, float y1, float x2, float y2, float x12, float y12, float x22,
				float y22) {
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
	 
		public static Vector<Point> sort(Vector<Point> c) {
	        Vector<Point> p = new Vector<Point>();
	        Point[] ppoints = c.toArray(new Point[c.size()]);
	        Point temp;
	        int zmiana = 1;
	        while (zmiana > 0) {
	            zmiana = 0;
	            for (int i = 0; i < ppoints.length - 1; i++) {
	                if (ppoints[i].getY() > ppoints[i + 1].getY()) {
	                    temp = ppoints[i + 1];
	                    ppoints[i + 1] = ppoints[i];
	                    ppoints[i] = temp;
	                    zmiana++;
	                }
	                else if(ppoints[i].getY() == ppoints[i + 1].getY()&& ppoints[i].getX() > ppoints[i + 1].getX())
	                {
	                    temp = ppoints[i + 1];
	                    ppoints[i + 1] = ppoints[i];
	                    ppoints[i] = temp;
	                    zmiana++;
	                }
	            }
	        }
	        for (int i = 0; i < ppoints.length; i++) {
	            p.add(ppoints[i]);
	        }
	 
	        return p;
	 
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
	
	void writePrzeciecia(Vector<Point> przeciecia) {
		PrintWriter zapis = null;

		try {
			zapis = new PrintWriter("przeciecia.txt");
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		for (Point przeciecie : przeciecia) {
			zapis.print(przeciecie.x + " " + przeciecie.y);
			zapis.print("\n");
		}

		zapis.close();
		
	}



	
}
