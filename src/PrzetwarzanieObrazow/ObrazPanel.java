package PrzetwarzanieObrazow;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ObrazPanel extends JPanel {

	BufferedImage image;
	public int alpha[][];
	public int obraz[][];
	public int wymy;
	public int wymx;
	private Color mycolor;

	public ObrazPanel(String nazwa) {
		super();

		
		File imageFile = new File(nazwa);
		try {
			image = ImageIO.read(imageFile);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "Nieprawodlowa nazwa pliku");
			System.err.println("Nieorawidlowa nazwa pliku");
			e.printStackTrace();
		}

		Dimension dimension = new Dimension(image.getWidth(), image.getHeight());
		setPreferredSize(dimension);
		
	
	//WCZYTYWANIE
			wymy = image.getHeight();
			wymx = image.getWidth();
			
			alpha = new int[wymx][wymy];
			obraz = new int [wymx * 3][wymy];
			
			for (int i = 0; i<wymx; i++){
				for (int j=0; j<wymy; j++){
			
			mycolor = new Color(image.getRGB(i, j));
			alpha[i][j] = mycolor.getAlpha();
			obraz[3*i][j] = mycolor.getRed();
			obraz[3*i+1][j] = mycolor.getGreen();
			obraz[3*i+2][j] = mycolor.getBlue();
				}
			}	

	}

	//ZAPIS DO OBRAZKA
	public void zmien(){
			//	int pixelARGB = a << 24 | (red << 16)| (green << 8) | blue;
			for (int i = 0; i<wymx; i++){
				for (int j=0; j<wymy; j++){
					image.setRGB(i, j, alpha[i][j] << 24 | (obraz[3*i][j] << 16)| (obraz[3*i+1][j] << 8) | obraz[3*i+2][j]);
				}
			}
	}
	//ZAPIS DO PLIKU
	public void zapisz(String nazwa) throws IOException{
		
		File plikzapisowy = new File(nazwa);
		ImageIO.write(image, "jpg", plikzapisowy);
		
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		g2d.drawImage(image, 0, 0, this);
	}
}