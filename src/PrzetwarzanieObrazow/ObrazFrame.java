package PrzetwarzanieObrazow;

import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class ObrazFrame extends JFrame {

	public ObrazFrame(String nazwa) {
		super(nazwa);

		JPanel obrazPanel = new ObrazPanel(nazwa);
		add(obrazPanel);

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		pack();
		setVisible(true);
	}
}