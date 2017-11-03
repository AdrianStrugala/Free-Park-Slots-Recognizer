package PrzetwarzanieObrazow;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class Windows {

	JFrame frmZajebistyProgramGraficzny;
	static Przetwarzania przetwarzania = new Przetwarzania();
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
		frmZajebistyProgramGraficzny.setTitle("Program graficzny v.2.0");
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

		btnNegatyw.setBounds(52, 140, 129, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnNegatyw);

		JLabel label = new JLabel("");

		label.setBounds(229, 11, 147, 226);
		frmZajebistyProgramGraficzny.getContentPane().add(label);

		JLabel lblOperacjeNaObrazie = new JLabel("Operacje na obrazie:");
		lblOperacjeNaObrazie.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblOperacjeNaObrazie.setBounds(52, 115, 147, 23);
		frmZajebistyProgramGraficzny.getContentPane().add(lblOperacjeNaObrazie);

		JLabel lblNazwa = new JLabel("");
		lblNazwa.setBounds(109, 15, 121, 14);
		frmZajebistyProgramGraficzny.getContentPane().add(lblNazwa);

		JButton btnProgowanie = new JButton("Progowanie");

		btnProgowanie.setBounds(52, 180, 129, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnProgowanie);

		JButton btnReset = new JButton("Reset");
		btnReset.setFont(new Font("Tahoma", Font.BOLD, 11));

		btnReset.setBounds(10, 81, 89, 23);
		frmZajebistyProgramGraficzny.getContentPane().add(btnReset);

		JButton btnKontur = new JButton("Konturowanie");

		btnKontur.setBounds(52, 220, 129, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnKontur);

		JButton btnCzarnobiay = new JButton("Czarno-Bia\u0142y");

		btnCzarnobiay.setBounds(52, 260, 129, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnCzarnobiay);

		JButton btnPoprogBieli = new JButton("P\u00F3\u0142prog. bieli");

		btnPoprogBieli.setBounds(52, 460, 129, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnPoprogBieli);

		JButton btnKorGamma = new JButton("Kor. gamma");

		btnKorGamma.setBounds(52, 380, 129, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnKorGamma);

		JButton btnRozmywanie = new JButton("Rozmywanie");

		btnRozmywanie.setBounds(52, 340, 129, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnRozmywanie);

		JButton btnRozHistogramu = new JButton("Roz. histogramu");

		btnRozHistogramu.setBounds(52, 300, 129, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnRozHistogramu);

		JButton btnPprogCzerni = new JButton("P\u00F3\u0142prog. czerni");

		btnPprogCzerni.setBounds(52, 420, 129, 29);
		frmZajebistyProgramGraficzny.getContentPane().add(btnPprogCzerni);
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
			}
		});

		btnKontur.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				przetwarzania.kontur(tymczas);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));
			}
		});

		btnCzarnobiay.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				przetwarzania.Czarnobialy(tymczas);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));

			}
		});

		btnRozHistogramu.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				przetwarzania.rozhist(tymczas);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));

			}
		});

		btnRozmywanie.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				przetwarzania.rozmywanie(tymczas);
				tymczas.zmien();
				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));

			}
		});

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

		btnPoprogBieli.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				// int prog =
				// Integer.parseInt(JOptionPane.showInputDialog("Wprowadz
				// wartosc progu z zakresu 0-100"));
				// przetwarzania.progowaniewh(tymczas, prog);
				// tymczas.zmien();
				// g.drawImage(tymczas.image, 0, 0, w, h, null);
				// label.setIcon(new ImageIcon(bi));

				przetwarzania.kontur(tymczas);
				przetwarzania.progowanie(tymczas, 90);
				przetwarzania.negatyw(tymczas);
				tymczas.zmien();

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

				Vector<HoughLine> lines = new Vector<HoughLine>();
				Vector<HoughLine> lines2 = new Vector<HoughLine>();
				HoughTransform ht = new HoughTransform(tymczas.image);
				lines = ht.getLines(20, 0);

				try {
					zapis = new PrintWriter("linie.txt");
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				for (int y = 0; y < lines.size(); y++) {
					if (lines.get(y).x1 >= 0 && lines.get(y).x2 >= 0 && lines.get(y).y1 >= 0 && lines.get(y).y2 >= 0) {
						lines2.add(lines.get(y));
					}

				}

				for (int y = 0; y < lines2.size(); y++) {
					zapis.print("r: " + lines2.get(y).r);
					zapis.print(" score: " + lines2.get(y).score);
					zapis.print(" theta: " + lines2.get(y).theta);
					zapis.print(" x1: " + lines2.get(y).x1);
					zapis.print(" x2: " + lines2.get(y).x2);
					zapis.print(" y1: " + lines2.get(y).y1);
					zapis.print(" y2: " + lines2.get(y).y2);
					zapis.print("\n");
				}

				zapis.close();

				g.drawImage(tymczas.image, 0, 0, w, h, null);
				label.setIcon(new ImageIcon(bi));

				g.setColor(Color.RED);

				for (int y = 0; y < lines2.size(); y++) {
					g.drawLine((int) lines2.get(y).x1, (int) lines2.get(y).y1, (int) lines2.get(y).x2,
							(int) lines2.get(y).y2);
				}

				int i = 3;
			}
		});

		// 2 koncowe nawiasy
	}
}
