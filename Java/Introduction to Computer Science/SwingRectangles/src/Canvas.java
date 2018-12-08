import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import javax.swing.JPanel;

public class Canvas extends JPanel implements MouseListener{

	private ArrayList<Rectangle> rectangles;
	private Point mouseStart;
	private Point mouseEnd;
	
	public Canvas(){
		super();
		rectangles = new ArrayList<>();
		addMouseListener(this);
		
	}
	
	@Override
	public void paint(Graphics g){
		g.setColor(Color.RED);
		g.fillRect(0, 0, getWidth(), getHeight());
		for(Rectangle r: rectangles){
			g.setColor(new Color((float) Math.random(), (float) Math.random(), (float) Math.random()));
			g.fillRect(r.x, r.y, r.width, r.height);
		}
	}
	
	public void clear(){
		rectangles.clear();
		repaint();
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		mouseStart = e.getPoint();	
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		mouseEnd = e.getPoint();
		rectangles.add(new Rectangle(
				Math.min(mouseStart.x,  mouseEnd.x),
				Math.min(mouseStart.y, mouseEnd.y),
				Math.abs(mouseEnd.x - mouseStart.x),
				Math.abs(mouseEnd.y - mouseStart.y)));
		repaint(); //ask swing to call paint()
		System.out.println("mouse up event processed, " + rectangles.size());
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
	}


}