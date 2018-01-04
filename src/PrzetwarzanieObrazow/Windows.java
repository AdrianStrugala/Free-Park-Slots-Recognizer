package PrzetwarzanieObrazow;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Windows {

	JFrame frmZajebistyProgramGraficzny;
	static Przetwarzania przetwarzania = new Przetwarzania();
	static Parking parking = new Parking();
	static ObrazPanel obraz; // plik obrazu
	static ObrazPanel tymczas; // plik obrazu tymczasowego
	String nazwa; // nazwa wybranego pliku
	String katalog;
	public int w = 0; // szerokosc
	public int h = 0; // wysokosc
	BufferedImage bi; // pliki potrzebe do scrollowania
	Graphics g; // up
	public int x = 0; // do przesuwania
	public int y = 0; // do przesuwania
	public int x0 = 0; // do przesuwania
	public int y0 = 0; // do przesuwania

	// DO PARKINGU
	Vector<HoughLine> houghLines = new Vector<HoughLine>();
	Vector<HoughLine> linesHorizontal = new Vector<HoughLine>();
	Vector<HoughLine> linesVertical = new Vector<HoughLine>();
	Vector<Rectangle> prostokatyVec = new Vector<Rectangle>();
	private JTextField txtSasiedztwo;
	

	/*
	 * Uruchomienie aplikacji
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Windows window = new Windows();
					window.frmZajebistyProgramGraficzny.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Windows() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmZajebistyProgramGraficzny = new JFrame();
		frmZajebistyProgramGraficzny.setTitle("Park slot recognizer v.0.1.4");
		frmZajebistyProgramGraficzny.setBounds(100, 100, 408, 539);
		frmZajebistyProgramGraficzny.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmZajebistyProgramGraficzny.getContentPane().setLayout(null);

		JButton btnWczytaj = new JButton("Wczytaj");
		btnWczytaj.setForeground(Color.ORANGE);
		btnWczytaj.setBackground(Color.RED);
		btnWczytaj.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnWczytaj.setBounds(10, 11, 89, 23);
		frmZajebistyProgramGraficzny.getContentPane().add(btnWczytaj);

		JButton btnZapisz = new JButton("Zapisz");
		btnZapisz.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnZapisz.setBounds(10, 45, 89, 23);
		frmZajebistyProgramGraficzny.getContentPane().add(btnZapisz);

		JButton btnZapiszJako = new JButton("Zapisz jako...");
		btnZapiszJako.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnZapiszJako.setBounds(109, 45, 110, 23);
		frmZajebistyProgramGraficzny.getContentPane().add(btnZapiszJako);

		JButton btnNegatyw = new JButton("Negatyw");
		btnNegatyw.setBounds(10, 141, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnNegatyw);

		JLabel label = new JLabel("");
		label.setBounds(229, 11, 147, 226);
		frmZajebistyProgramGraficzny.getContentPane().add(label);

		JLabel lblOperacjeNaObrazie = new JLabel("Operacje na obrazie:");
		lblOperacjeNaObrazie.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblOperacjeNaObrazie.setBounds(10, 117, 129, 23);
		frmZajebistyProgramGraficzny.getContentPane().add(lblOperacjeNaObrazie);

		JLabel lblNazwa = new JLabel("");
		lblNazwa.setBounds(109, 15, 121, 14);
		frmZajebistyProgramGraficzny.getContentPane().add(lblNazwa);

		JButton btnProgowanie = new JButton("Progowanie");
		btnProgowanie.setBounds(10, 180, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnProgowanie);

		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 11));
		btnReset.setBounds(10, 81, 89, 23);
		frmZajebistyProgramGraficzny.getContentPane().add(btnReset);

		JButton btnKontur = new JButton("Kontur");
		btnKontur.setBounds(10, 218, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnKontur);

		JButton btnCzarnobiay = new JButton("Szarosci");
		btnCzarnobiay.setBounds(10, 258, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnCzarnobiay);

		JButton btnPoprogBieli = new JButton("Pol. bieli");
		btnPoprogBieli.setBounds(10, 463, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnPoprogBieli);

		JButton btnKorGamma = new JButton("Gamma");
		btnKorGamma.setBounds(10, 382, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnKorGamma);

		JButton btnRozmywanie = new JButton("Rozmyj");
		btnRozmywanie.setBounds(10, 340, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnRozmywanie);

		JButton btnRozHistogramu = new JButton("Histogram");
		btnRozHistogramu.setBounds(10, 298, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnRozHistogramu);

		JButton btnPprogCzerni = new JButton("Pol. czerni");
		btnPprogCzerni.setBounds(10, 424, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnPprogCzerni);

		JButton btnConvert = new JButton("Konwersja");
		btnConvert.setBounds(119, 141, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnConvert);

		JButton btnHough = new JButton("Hough");
		btnHough.setBounds(119, 218, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnHough);
		
		JButton btnPogrub = new JButton("Pogrub");
		btnPogrub.setBounds(119, 180, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnPogrub);
		
		JButton btnProstokaty = new JButton("Prostokaty");
		btnProstokaty.setBounds(120, 258, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnProstokaty);
		
		JButton btnParking = new JButton("Parking");
		btnParking.setBounds(119, 298, 100, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnParking);
		
		txtSasiedztwo = new JTextField();
		txtSasiedztwo.setText("50");
		txtSasiedztwo.setBounds(133, 362, 86, 20);
		frmZajebistyProgramGraficzny.getContentPane().add(txtSasiedztwo);
		txtSasiedztwo.setColumns(10);
		
		JLabel lblSasiedztwo = new JLabel("Sasiedztwo:");
		lblSasiedztwo.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblSasiedztwo.setBounds(141, 342, 89, 23);
		frmZajebistyProgramGraficzny.getContentPane().add(lblSasiedztwo);
		// Image img = new
		// ImageIcon(this.getClass().getResource("tymczas.jpg")).getImage();

		/*
		 * $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		 * OBSLUGA AKCJI
		 * $$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$
		 */

		btnWczytaj.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Frame a = new Frame("Okno macierzyste");
				a.setBounds(20, 20, 400, 500);
				// a.setVisible(true);
				FileDialog fd = new FileDialog(a, "Wczytaj", FileDialog.LOAD);
				fd.setVisible(true);
				katalog = fd.getDirectory();
				nazwa = fd.getFile();

				// nazwa = JOptionPane.showInputDialog("Podaj nazwe pliku");

				if (katalog != null) {
					obraz = new ObrazPanel(katalog + nazwa);
					tymczas = obraz;

					w = obraz.wymx;
					h = obraz.wymy;
					/*
					 * WCZYTAJ
					 */
					btnWczytaj.setBackground(Color.LIGHT_GRAY);
					btnWczytaj.setForeground(Color.BLACK);
					frmZajebistyProgramGraficzny.setBounds(100, 100, Math.min(1280, Math.max(260 + obraz.wymx, 408)),
							Math.min(1024, Math.max(70 + obraz.wymy, 540)));
					label.setBounds(229, 11, 229 + obraz.wymx, 11 + obraz.wymy);

					bi = new BufferedImage(tymczas.image.getWidth(null), tymczas.image.getHeight(null),
							BufferedImage.TYPE_INT_ARGB);
					g = bi.createGraphics();
					g.drawImage(tymczas.image, 0, 0, w, h, null);
					label.setIcon(new ImageIcon(bi));

					lblNazwa.setText(nazwa);

					parking = new Parking();
					houghLines = new Vector<HoughLine>();
					linesHorizontal = new Vector<HoughLine>();
					linesVertical = new Vector<HoughLine>();
					prostokatyVec = new Vector<Rectangle>();
				}
			}
		});
		/*
		 * ZAPISZ
		 */
		btnZapisz.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					tymczas.zapisz(katalog + nazwa);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		});

		// NEGATYW

		btnNegatyw.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				przetwarzania.negatyw(tymczas);
				tymczas.zmien();
				// label.setIcon(new ImageIcon(main.tymczas.image));
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));
			}
		});
		/*
		 * ZAPISZ JAKO
		 */
		btnZapiszJako.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				String nazwaZap;
				Frame a = new Frame("Okno macierzyste");
				a.setBounds(20, 20, 400, 500);
				// a.setVisible(true);
				FileDialog fd = new FileDialog(a, "Zapisz", FileDialog.SAVE);
				fd.setVisible(true);
				String katalog2 = fd.getDirectory();
				nazwaZap = fd.getFile();
				try {
					tymczas.zapisz(katalog2 + nazwaZap);
				} catch (IOException e) {
					e.printStackTrace();
				}

				obraz = new ObrazPanel(katalog2 + nazwaZap);
				lblNazwa.setText(nazwaZap);
			}
		});

		/*
		 * PROGOWANIE
		 */
		btnProgowanie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int prog = Integer.parseInt(JOptionPane.showInputDialog("Wprowadz wartosc progu z zakresu 0-100"));
				przetwarzania.progowanie(tymczas, prog);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));
			}
		});
		/*
		 * RESET
		 */
		btnReset.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				obraz = new ObrazPanel(katalog + nazwa);
				tymczas = obraz;
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));
				
				parking = new Parking();
				houghLines = new Vector<HoughLine>();
				linesHorizontal = new Vector<HoughLine>();
				linesVertical = new Vector<HoughLine>();
				prostokatyVec = new Vector<Rectangle>();
			}
		});

		/*
		 * KONTUR
		 */
		btnKontur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				przetwarzania.kontur(tymczas);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));
			}
		});

		/*
		 * CZARNO BIALY
		 */
		btnCzarnobiay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				przetwarzania.Czarnobialy(tymczas);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));

			}
		});
		/*
		 * ROZCIAGNIECIE HISTOGRAMU
		 */

		btnRozHistogramu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				przetwarzania.rozhist(tymczas);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));

			}
		});
		
		/*
		 * ROZMYWANIE
		 */

		btnRozmywanie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				przetwarzania.rozmywanie(tymczas);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));

			}
		});
		/*
		 * KOREKTA GAMMA
		 */
		btnKorGamma.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				float prog = Float.parseFloat(JOptionPane.showInputDialog(
						"Wprowadz wartos� parametru gamma z zakresu 0-100 (liczba zmiennoprzecinkowa"));
				przetwarzania.kgamma(tymczas, prog);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));

			}
		});
		/*
		 * POLPROGOWANIE CZERNI
		 */
		btnPprogCzerni.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int prog = Integer.parseInt(JOptionPane.showInputDialog("Wprowadz wartosc progu z zakresu 0-100"));
				przetwarzania.progowaniebl(tymczas, prog);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));
			}
		});

		/*
		 * ZOOM
		 */

		label.addMouseWheelListener(new MouseWheelListener() {
			public void mouseWheelMoved(MouseWheelEvent arg0) {

				x0 = 0;
				y0 = 0;

				int zoom = (arg0.getWheelRotation());

				if (zoom == -1) {
					w = (int) (w * 1.25);
					h = (int) (h * 1.25);
				} else if (zoom == 1) {
					w = (int) (w * 0.8);
					h = (int) (h * 0.8);
				}

				bi = new BufferedImage(tymczas.image.getWidth(null), tymczas.image.getHeight(null),
						BufferedImage.TYPE_INT_ARGB);
				g = bi.createGraphics();

				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));
			}
		});

		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent arg0) {
				x = arg0.getX() - x0;
				y = arg0.getY() - y0;
			}

		});

		label.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseDragged(MouseEvent arg0) {
				// System.out.println(arg0.getX());

				bi = new BufferedImage(tymczas.image.getWidth(null), tymczas.image.getHeight(null),
						BufferedImage.TYPE_INT_ARGB);
				g = bi.createGraphics();
				x0 = arg0.getX() - x;
				y0 = arg0.getY() - y;
				g.drawImage(tymczas.image, x0, y0, w, h, null);
				label.setIcon(new ImageIcon(bi));
			}
		});
		/*
		 * POLPROGOWANIE BIELI
		 */
		btnPoprogBieli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				int prog = Integer.parseInt(JOptionPane.showInputDialog("Wprowadz wartosc progu z zakresu 0-100"));
				przetwarzania.progowaniewh(tymczas, prog);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));

			}
		});
		
		/*********************************************************************/
		/*************************PARKING*************************************/
		/*********************************************************************/
		/*
		 * PRZYGOTOWANIE DO HOGHA
		 */
		btnConvert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				przetwarzania.kontur(tymczas);
				przetwarzania.progowanie(tymczas, 90);
				przetwarzania.negatyw(tymczas);
				tymczas.zmien();

				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));

			}
		});
		/*
		 * POGRUBIENIE
		 */
		btnPogrub.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				przetwarzania.pogrub(tymczas);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));
				
			}
		});
		
		/*
		 * HOUGH
		 */
		btnHough.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				parking.writePixels(w, h, tymczas);
				
				houghLines = parking.linie(w, h, tymczas, txtSasiedztwo.getText());
			
				parking.writeLines(houghLines);
				
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));

				g.setColor(Color.RED);

				for (int y = 0; y < houghLines.size(); y++) {
					g.drawLine((int) houghLines.get(y).x1, (int) houghLines.get(y).y1,
							(int) houghLines.get(y).x2, (int) houghLines.get(y).y2);
				}

			}
		});
		/*
		 * PROSOTKATY
		 */
		btnProstokaty.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				// get Horizontal
				for (int y = 0; y < houghLines.size(); y++) {
					if (houghLines.get(y).x1 == 0 || houghLines.get(y).x2 == 0) {
						linesHorizontal.add(houghLines.get(y));
					}

				}
				
				// get vertical
				for (HoughLine line : houghLines) {
					if (line.y1 == 0 || line.y2 == 0) {
						linesVertical.add(line);
					}
				}
				
				Vector<Rectangle> prostokaty = new Vector<Rectangle>();
				
				prostokaty = parking.prostokaty(linesHorizontal, linesVertical);
				
				for(Rectangle prostokat : prostokaty){
					
					if(prostokat.area() > w*h/100 ){
						prostokatyVec.add(prostokat);
					}
				}
				
		        
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));
				
				g.setColor(Color.MAGENTA);
				
				for (Rectangle prostokat : prostokatyVec) {
					g.drawLine((int) prostokat.x1, (int) prostokat.y1, (int) prostokat.x2, (int) prostokat.y2);
					g.drawLine((int) prostokat.x1, (int) prostokat.y1, (int) prostokat.x3, (int) prostokat.y3);
					g.drawLine((int) prostokat.x2, (int) prostokat.y2, (int) prostokat.x4, (int) prostokat.y4);
					g.drawLine((int) prostokat.x4, (int) prostokat.y4, (int) prostokat.x3, (int) prostokat.y3);
				}
			}
		});
		
		/*
		 * ZNAJDOWANIE WOLNYCH MIEJSC PARKINGOWYCH
		 */
		btnParking.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));
				
				
				for(Rectangle prostokat : prostokatyVec){
					
					if(!prostokat.pusty(tymczas)){		
						g.setColor(Color.RED);
						
		                Graphics2D g2 = (Graphics2D) g;
		                g2.setStroke(new BasicStroke(5));
		                g2.draw(new Line2D.Float((int) prostokat.x1, (int) prostokat.y1, (int) prostokat.x2, (int) prostokat.y2));
		                g2.draw(new Line2D.Float((int) prostokat.x1, (int) prostokat.y1, (int) prostokat.x3, (int) prostokat.y3));
		                g2.draw(new Line2D.Float((int) prostokat.x2, (int) prostokat.y2, (int) prostokat.x4, (int) prostokat.y4));
		                g2.draw(new Line2D.Float((int) prostokat.x4, (int) prostokat.y4, (int) prostokat.x3, (int) prostokat.y3));

					
					}
				}
				
			}
		});
		
		// 2 koncowe nawiasy
	}
}
