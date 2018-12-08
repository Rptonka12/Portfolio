import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class MainWindow extends JFrame {
	
	public MainWindow() {
		super("Drawing Windows");
		setSize(800,800);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		
		//Panel
		Canvas canvas = new Canvas();
		add(canvas, BorderLayout.CENTER);
		
		//Button
		JButton clearButton = new JButton("clear");
		clearButton.setPreferredSize(new Dimension(800,100));
		add(clearButton, BorderLayout.NORTH);
		clearButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e){
				canvas.clear();
			}
		});
		
		setVisible(true);
	}
	
	public static void main(String[] args){
		new MainWindow();
	}
}