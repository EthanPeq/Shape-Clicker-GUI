import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import javax.swing.*;
import java.lang.*;

import java.util.*;


public class assign3 {

	public static void main(String[] args) {
		JFrame frame = new AppFrame("PopUP");
	}
}

class AppFrame extends JFrame{
	public AppFrame(String title) {
		super(title);
		
		//add panels
		Info i = new Info();
		this.setLayout(new BorderLayout());
		this.add(new DrawPanel(i));
		
		this.setSize(600, 300);
		this.setLocation(300, 300);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setVisible(true);
	}	
}

class DrawPanel extends JPanel{
	Canvas canvas;
	JButton cirButton,recButton,triButton;
	JTextArea txtAreaColor, txtAreaSize, txtAreaLocation;
	
	public DrawPanel(Info i) {
		super();
		canvas = new Canvas(i);
		this.setLayout(new BorderLayout());
		
		//--- creating textBoxes ---
		txtAreaColor = new JTextArea();
		txtAreaColor.setBounds(120, 150, 85, 60);
		this.add(txtAreaColor,"Center");
		
		txtAreaSize = new JTextArea();
		txtAreaSize.setBounds(220,150,85,60);
		this.add(txtAreaSize,"Center");
		
		txtAreaLocation = new JTextArea();
		txtAreaLocation.setBounds(320,150,85,60);
		this.add(txtAreaLocation);
		
		//--- pTop Buttons ---
		cirButton = new JButton("Circle");
		cirButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeInt(0);
			}			
		});
		recButton = new JButton("Rectangle");
		recButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeInt(1);
			}			
		});
		triButton = new JButton("Triangle");
		triButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setShapeInt(2);
			}			
		});
		JButton undoButton = new JButton("Undo");
		undoButton.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.undo();
			}
		});
		//--- pBottom buttons ---
		JButton colorButton = new JButton("Color");
		colorButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String colorStr = txtAreaColor.getText();
				int r = colorStr.indexOf('.');
				int g = colorStr.indexOf('.', r+1);
				int b = colorStr.indexOf('.', g+1);
				
				b = Integer.valueOf(colorStr.substring(g+1, colorStr.length()));
				g = Integer.valueOf(colorStr.substring(r+1,g));
				r = Integer.valueOf(colorStr.substring(0,r));
				
				canvas.setColor(new Color(r, g, b));
			}
		});
		JButton sizeButton  = new JButton("Size");
		sizeButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				canvas.setSize(Integer.parseInt(txtAreaSize.getText()));
			}
		});
		JButton locationButton = new JButton("Location");
		locationButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtAreaLocation.append(i.toString());
			}
		});
		JButton eraseButton = new JButton("Erase Boxes");
		eraseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				txtAreaColor.setText("");
				txtAreaSize.setText("");
				txtAreaLocation.setText("");
			}
		});
		
		
		//--- pTop Panels ---
		JPanel pTop = new JPanel();
		pTop.setBorder(BorderFactory.createTitledBorder(""));
		pTop.add(cirButton);
		pTop.add(recButton);
		pTop.add(triButton);
		pTop.add(undoButton);		
		
		
		//--- pBottom Panels ---
		JPanel pBottom = new JPanel();
		pBottom.setBorder(BorderFactory.createTitledBorder(""));
		pBottom.add(colorButton);		
		pBottom.add(sizeButton);
		pBottom.add(locationButton);
		pBottom.add(eraseButton);
		
		
		this.add(pTop,"North");
		this.add(pBottom,"South");
		this.add(canvas, "Center");
		
	}
}

class Canvas extends Panel{
	java.util.List<Shape> shapes;
	java.util.List<Color> colors;
	Info inf;
	int shapeInt, size;
	Color c;
	


	public Canvas(Info i) {
		super();
		inf = i;
		size = 20;
		shapeInt = 0;
		c = new Color(0,0,0);
		shapes = new ArrayList<Shape>();
		colors = new ArrayList<Color>();
		this.addMouseListener(new MsListener());
		
	}
	
	public void paint(Graphics g) {
		super.paintComponents(g);
		Graphics2D g2 = (Graphics2D) g.create();
		
		for(Shape s : shapes) {
			g2.draw(s);
		}
	}
	
	public void setShapeInt(int i) {
		shapeInt = i;
	}
	
	public void setSize(int i) {
		size = i;
	}
	
	public void setColor(Color c) {
		this.c = c;
	}
	
	public void undo() {
		if(shapes.size() != 0) {
			shapes.remove(0);
			repaint();
		}
	}
	class MsListener extends MouseAdapter{
		Shape s;
		int index;
		
		@Override
		public void mouseClicked(MouseEvent e) {
			Graphics2D g2 = (Graphics2D) getGraphics().create();
			
			if(shapeInt == 0) {     //circle
				s = new Ellipse2D.Double(e.getX(), e.getY(), size, size);
			}
			else if(shapeInt == 1) {//rectangle
				s = new Rectangle(e.getX(), e.getY(), size, size +10);
			}
			else if(shapeInt == 2) {   //triangle
				s = new Polygon(new int [] {e.getX(),e.getX()-20,e.getX()+20}, 
								new int [] {e.getY(),e.getY()+20,e.getY()+20},3);
			}
			else 
				
			index ++;
			shapes.add(index, s);
			colors.add(index, c);
			g2.setColor(c);
			g2.draw(s);
			inf.setInfo("(" + e.getX() + "," + e.getY() + ")");
		
		}		
	}
}

class Info{
	String info;

	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}	
	
	public String toString() {
		return info;
	}
}
