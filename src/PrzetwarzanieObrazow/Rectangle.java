package PrzetwarzanieObrazow;

import java.awt.Color;
import java.awt.image.BufferedImage;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Rectangle {

	double x1;
	double y1;
	double x2;
	double y2;
	double x3;
	double y3;
	double x4;
	double y4;	

	public Rectangle(){
		x1 = 0;
		y1 = 0;
		x2 = 0;
		y2 = 0;
		x3 = 0;
		y3 = 0;
		x4 = 0;
		y4 = 0;
	}
	public double area (){
		double a,b;
		a=y3-y1;
		b=x2-x1;
		double area= a*b;	
		//System.out.println(area);
		return area;
		
	}
	public boolean pusty(ObrazPanel image) {
		Boolean result = true;
		
		double area = this.area();
		int whitePixels = 0;
		
		for (int x = (int)this.x1*3; x < this.x2*3; x+=3) {
			for (int y = (int)this.y1; y < this.y3; y++) {
				int pixel = image.obraz[x][y];
				if(pixel == 255){
					whitePixels ++;
				}
			}
		}
		
		if(whitePixels > area/3.5) {result = false;}
		
		return result;
	}
}

