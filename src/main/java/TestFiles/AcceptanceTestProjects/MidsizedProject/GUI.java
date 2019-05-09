package TestFiles.AcceptanceTestProjects.MidsizedProject;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;

public class GUI {

	public static final int DELAY = 100;
	URL url;
	Icon icon;
	JLabel label;

	public GUI(URL url) {
		this.url = url;
		this.icon = new ImageIcon(url);
		this.label = new JLabel(icon);

	}

	public void createGUI() {
		JFrame f = new JFrame("Animation");
		JButton closeAnimation = new JButton("Begin");
		closeAnimation.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				f.dispose();
				JFrame frame = new JFrame("Bubble Bobble");
				frame.setBounds(0, 0, 1140, 1020);

				Player player1 = new Player(0, 0, "Dragon");
				Player player2 = new Player(0, 0, "Blue");
				GameComponent component = new GameComponent();
				frame.add(component, BorderLayout.CENTER);
				frame.pack();
				Game game = new Game(player1, player2, component);
				component.addKeyListener(new Player1Listener(player1, game));
				component.addKeyListener(new Player2Listener(player2, game));
				component.addMouseListener(new StartGameListener(game));
				frame.setVisible(true);
				frame.setLocationRelativeTo(null);
				frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

				Timer t = new Timer(DELAY, new TimerListener(game));
				t.start();
			}
		});
		closeAnimation.setSize(200, 80);
		closeAnimation.setVisible(true);
		JPanel enterGame = new JPanel();
		enterGame.add(closeAnimation, BorderLayout.CENTER);
		enterGame.add(label);
		f.add(enterGame, BorderLayout.CENTER);
		f.add(label, BorderLayout.PAGE_END);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.pack();
		f.setLocationRelativeTo(null);
		f.setVisible(true);
	}

}
