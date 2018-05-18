import javax.swing.JFrame;
import javax.swing.JTextField;

public class PongFrame {
	public static void main(String[] args) {

		JFrame frame = new JFrame();
		JTextField field = new JTextField(10);
		frame.setTitle("Pong!");
		frame.setSize(800, 600);
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(frame.EXIT_ON_CLOSE);
		frame.add(new PongComp());
		frame.setVisible(true);
	}
}
