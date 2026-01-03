package main;
import javax.swing.JFrame;

public class Main {
	public static void main(String[] args) {
		JFrame window = new JFrame("2D RPG Game");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setResizable(false);

		Gamepanel gamePanel = new Gamepanel();
		window.add(gamePanel);
		window.pack();

		window.setLocationRelativeTo(null); 
		window.setVisible(true);

		gamePanel.startGameThread();
	}
}
