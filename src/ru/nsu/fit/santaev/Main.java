package ru.nsu.fit.santaev;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import ru.nsu.fit.santaev.Model3d.Camera;
import ru.nsu.fit.santaev.Model3d.Light;
import ru.nsu.fit.santaev.Model3d.Material;
import ru.nsu.fit.santaev.Model3d.Renderer;
import ru.nsu.fit.santaev.Model3d.Wall;
import ru.nsu.fit.santaev.Model3d.World;

public class Main {

	public static class MySettings extends JFrame {

		private static final long serialVersionUID = 1L;

		public static final int MIN_WIDTH = 400;
		public static final int MIN_HEIGHT = 300;

		private JSlider sliderZoom = null;
		private JSlider sliderMove = null;
		private JButton defaultButton = null;

		private JPanel panel = new JPanel();

		public MySettings(String title) {
			super(title);
			setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
			Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

			this.setLocation(mainFrame.getLocation().x + mainFrame.getWidth(),
					mainFrame.getLocation().y);
			JTextField text1 = new JTextField("Zoom step");
			JTextField text2 = new JTextField("Move step");

			defaultButton = new JButton();
			defaultButton.setText("Set default params");

			text1.setEditable(false);
			text1.setPreferredSize(new Dimension(100, 30));
			text2.setEditable(false);
			text2.setPreferredSize(new Dimension(100, 30));

			add(panel);

			panel.setLayout(new GridLayout(5, 1));
			panel.add(defaultButton);

		}

		@Override
		public void setVisible(boolean arg0) {
			this.setLocation(mainFrame.getLocation().x + mainFrame.getWidth(),
					mainFrame.getLocation().y);
			super.setVisible(arg0);
		}
	}

	public static final String MAIN_FRAME_TITLE = "Lab #3";

	private static Action settings;
	private static BufferedImage imgOrigin = null;
	private static BufferedImage imgResized = null;
	private static BufferedImage imgFiltered = null;
	private static BufferedImage imgBig = null;
	private static BufferedImage copy = null;
	private static double alpha = 0;
	private static double alpha1 = 0;
	private static double height = 2;
	private static double height2 = 2;
	private static double a = 0;
	
	private static int fps = 0;
	private static long time1 = 0;
    static World world = new World();
    private static final MainFrame mainFrame = new MainFrame(MAIN_FRAME_TITLE, world);
	
	public static void main(String[] args) throws IOException {

		final MySettings settingsFrame = new MySettings("Settings");

		ImageIcon iconNew = new ImageIcon("res/images/draw_square.png");

		Action actionDrawSquare = new AbstractAction("New", iconNew) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				mainFrame.repaint();
			}
		};

		ImageIcon iconSettings = new ImageIcon("res/images/settings.png");
		settings = new AbstractAction("New", iconSettings) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				settingsFrame.setVisible(true);
			}
		};

		mainFrame.addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent arg0) {
			}

			@Override
			public void componentResized(ComponentEvent arg0) {
			}

			@Override
			public void componentMoved(ComponentEvent arg0) {

				settingsFrame.setLocation(
						mainFrame.getLocation().x + mainFrame.getWidth(),
						mainFrame.getLocation().y);
				settingsFrame.invalidate();
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {

			}
		});
		mainFrame.setOnClickListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// onMouseEvent(e);
			}
		}, new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// onMouseEvent(e);
			}
		});

		mainFrame.addSeparatorToToolbar();
		// mainFrame.addButtonToToolbar(settings);

		Wall wall = new Wall();
		Material m1 = new Material();
		Material m2 = new Material();
		Material m3 = new Material();
		Material m4 = new Material();
		m3.setkReflection(0.4);
		m3.setkDiffusion(0.6);

		m4.setkReflection(0.4);
		m4.setkDiffusion(0.6);
		m4.setColor(Color.RED);

		m3.setColor(Color.BLUE);
		m1.setColor(Color.BLUE);
		m2.setColor(Color.GRAY);

		double h = 1;
		double y = 1;
		wall.p1 = new MyPoint(h, y, h);
		wall.p2 = new MyPoint(-h, y, h);
		wall.p3 = new MyPoint(-h, y, -h);
		wall.p4 = new MyPoint(h, y, -h);
		wall.setABCD();
		wall.setMaterial(m3);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = -1;
		wall.p1 = new MyPoint(h, y, h);
		wall.p2 = new MyPoint(-h, y, h);
		wall.p3 = new MyPoint(-h, y, -h);
		wall.p4 = new MyPoint(h, y, -h);
		wall.setABCD();
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = -1;
		wall.p1 = new MyPoint(h + 2 * h, y, h);
		wall.p2 = new MyPoint(-h + 2 * h, y, h);
		wall.p3 = new MyPoint(-h + 2 * h, y, -h);
		wall.p4 = new MyPoint(h + 2 * h, y, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = -1;
		wall.p1 = new MyPoint(3 * h, h, h);
		wall.p2 = new MyPoint(3 * h, -h, h);
		wall.p3 = new MyPoint(3 * h, h, -h);
		wall.p4 = new MyPoint(3 * h, -h, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.GREEN);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = -1;
		wall.p1 = new MyPoint(3 * h, h + 2 * h, h);
		wall.p2 = new MyPoint(3 * h, -h + 2 * h, h);
		wall.p3 = new MyPoint(3 * h, h + 2 * h, -h);
		wall.p4 = new MyPoint(3 * h, -h + 2 * h, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = -1;
		wall.p1 = new MyPoint(1 * h, h + 2 * h, h);
		wall.p2 = new MyPoint(1 * h, -h + 2 * h, h);
		wall.p3 = new MyPoint(1 * h, h + 2 * h, -h);
		wall.p4 = new MyPoint(1 * h, -h + 2 * h, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m4);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = -1;
		wall.p1 = new MyPoint(-1 * h, h + 2 * h, h);
		wall.p2 = new MyPoint(-1 * h, -h + 2 * h, h);
		wall.p3 = new MyPoint(-1 * h, h + 2 * h, -h);
		wall.p4 = new MyPoint(-1 * h, -h + 2 * h, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.RED);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = -1;
		wall.p1 = new MyPoint(-1 * h, h - 2 * h, h);
		wall.p2 = new MyPoint(-1 * h, -h - 2 * h, h);
		wall.p3 = new MyPoint(-1 * h, h - 2 * h, -h);
		wall.p4 = new MyPoint(-1 * h, -h - 2 * h, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = -1;
		wall.p1 = new MyPoint(-3 * h, h - 2 * h, h);
		wall.p2 = new MyPoint(-3 * h, -h - 2 * h, h);
		wall.p3 = new MyPoint(-3 * h, h - 2 * h, -h);
		wall.p4 = new MyPoint(-3 * h, -h - 2 * h, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = -1;
		wall.p1 = new MyPoint(-3 * h, h - 0 * h, h);
		wall.p2 = new MyPoint(-3 * h, -h - 0 * h, h);
		wall.p3 = new MyPoint(-3 * h, h - 0 * h, -h);
		wall.p4 = new MyPoint(-3 * h, -h - 0 * h, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.GREEN);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = -1;
		wall.p1 = new MyPoint(-3 * h, h + 2 * h, h);
		wall.p2 = new MyPoint(-3 * h, -h + 2 * h, h);
		wall.p3 = new MyPoint(-3 * h, h + 2 * h, -h);
		wall.p4 = new MyPoint(-3 * h, -h + 2 * h, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.BLUE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(h - 2 * h, y, h);
		wall.p2 = new MyPoint(-h - 2 * h, y, h);
		wall.p3 = new MyPoint(-h - 2 * h, y, -h);
		wall.p4 = new MyPoint(h - 2 * h, y, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h - 6 * h;
		wall.p1 = new MyPoint(h - 2 * h, y, h);
		wall.p2 = new MyPoint(-h - 2 * h, y, h);
		wall.p3 = new MyPoint(-h - 2 * h, y, -h);
		wall.p4 = new MyPoint(h - 2 * h, y, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(h + 2 * h, y, h);
		wall.p2 = new MyPoint(-h + 2 * h, y, h);
		wall.p3 = new MyPoint(-h + 2 * h, y, -h);
		wall.p4 = new MyPoint(h + 2 * h, y, -h);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		// Пол
		double z = -h;

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(3 * h, 3 * h, z);
		wall.p2 = new MyPoint(3 * h, h, z);
		wall.p3 = new MyPoint(h, 3 * h, z);
		wall.p4 = new MyPoint(h, h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.BLUE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(3 * h, 3 * h - 2 * h, z);
		wall.p2 = new MyPoint(3 * h, h - 2 * h, z);
		wall.p3 = new MyPoint(h, 3 * h - 2 * h, z);
		wall.p4 = new MyPoint(h, h - 2 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(3 * h - 2 * h, 3 * h - 2 * h, z);
		wall.p2 = new MyPoint(3 * h - 2 * h, h - 2 * h, z);
		wall.p3 = new MyPoint(h - 2 * h, 3 * h - 2 * h, z);
		wall.p4 = new MyPoint(h - 2 * h, h - 2 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(3 * h - 4 * h, 3 * h - 2 * h, z);
		wall.p2 = new MyPoint(3 * h - 4 * h, h - 2 * h, z);
		wall.p3 = new MyPoint(h - 4 * h, 3 * h - 2 * h, z);
		wall.p4 = new MyPoint(h - 4 * h, h - 2 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(3 * h - 6 * h, 3 * h - 2 * h, z);
		wall.p2 = new MyPoint(3 * h - 6 * h, h - 2 * h, z);
		wall.p3 = new MyPoint(h - 6 * h, 3 * h - 2 * h, z);
		wall.p4 = new MyPoint(h - 6 * h, h - 2 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(-3 * h, -3 * h, z);
		wall.p2 = new MyPoint(-3 * h, -h, z);
		wall.p3 = new MyPoint(-h, -3 * h, z);
		wall.p4 = new MyPoint(-h, -1 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.BLUE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(-3 * h, -3 * h + 4 * h, z);
		wall.p2 = new MyPoint(-3 * h, -h + 4 * h, z);
		wall.p3 = new MyPoint(-h, -3 * h + 4 * h, z);
		wall.p4 = new MyPoint(-h, -1 * h + 4 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.BLUE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		// Потолок
		z = h;

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(3 * h, 3 * h, z);
		wall.p2 = new MyPoint(3 * h, h, z);
		wall.p3 = new MyPoint(h, 3 * h, z);
		wall.p4 = new MyPoint(h, h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.BLUE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(3 * h, 3 * h - 2 * h, z);
		wall.p2 = new MyPoint(3 * h, h - 2 * h, z);
		wall.p3 = new MyPoint(h, 3 * h - 2 * h, z);
		wall.p4 = new MyPoint(h, h - 2 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(3 * h - 2 * h, 3 * h - 2 * h, z);
		wall.p2 = new MyPoint(3 * h - 2 * h, h - 2 * h, z);
		wall.p3 = new MyPoint(h - 2 * h, 3 * h - 2 * h, z);
		wall.p4 = new MyPoint(h - 2 * h, h - 2 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(3 * h - 4 * h, 3 * h - 2 * h, z);
		wall.p2 = new MyPoint(3 * h - 4 * h, h - 2 * h, z);
		wall.p3 = new MyPoint(h - 4 * h, 3 * h - 2 * h, z);
		wall.p4 = new MyPoint(h - 4 * h, h - 2 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m4);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(3 * h - 6 * h, 3 * h - 2 * h, z);
		wall.p2 = new MyPoint(3 * h - 6 * h, h - 2 * h, z);
		wall.p3 = new MyPoint(h - 6 * h, 3 * h - 2 * h, z);
		wall.p4 = new MyPoint(h - 6 * h, h - 2 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.WHITE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(-3 * h, -3 * h, z);
		wall.p2 = new MyPoint(-3 * h, -h, z);
		wall.p3 = new MyPoint(-h, -3 * h, z);
		wall.p4 = new MyPoint(-h, -1 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.BLUE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		wall = new Wall();
		h = 1;
		y = 3 * h;
		wall.p1 = new MyPoint(-3 * h, -3 * h + 4 * h, z);
		wall.p2 = new MyPoint(-3 * h, -h + 4 * h, z);
		wall.p3 = new MyPoint(-h, -3 * h + 4 * h, z);
		wall.p4 = new MyPoint(-h, -1 * h + 4 * h, z);
		wall.setABCD();
		m1 = new Material();
		m1.setColor(Color.BLUE);
		wall.setMaterial(m1);
		world.getObjects().add(wall);

		final Camera camera = new Camera(new MyPoint(0, 0, 0), new MyPoint(0,
				1, -0.3));
		world.setCamera(camera);

//		final Light light = new Light(new MyPoint(2 * h, 0, 0));
//		light.color = new Color(0, 0, 255);
//		light.brightness = 0.7;
//		world.getLights().add(light);
//
//		final Light light2 = new Light(new MyPoint(-2 * h, 0, 0));
//		light2.brightness = 0.7;
//		light2.color = new Color(0, 255, 0);
//		world.getLights().add(light2);

		final Renderer renderer = new Renderer();
		renderer.setWorld(world);
		imgOrigin = renderer.render();
		mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
		mainFrame.repaint();

		ImageIcon iconMove = new ImageIcon("res/images/str_right.png");
		AbstractAction move = new AbstractAction("New", iconMove) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MyPoint dir = camera.getDirection();
				MyPoint pos = camera.getPosition();
				double h = 10;
				pos.x = Math.sin(alpha) * h;
				pos.y = Math.cos(alpha) * h;
				pos.z = 0;
				alpha += 0.1;
				dir = MyPoint.subPointToPoint(new MyPoint(0, 0, 0), pos);
				camera.setDirection(dir);
				camera.setPosition(pos);
				renderer.setWorld(world);
				imgOrigin = renderer.render();
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
		ImageIcon iconMove2 = new ImageIcon("res/images/str_up.png");
		AbstractAction move2 = new AbstractAction("New", iconMove2) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				MyPoint dir = camera.getDirection();
				MyPoint pos = camera.getPosition();
				pos.x = Math.sin(alpha) * height;
				pos.y = Math.cos(alpha) * height;
				pos.z = 0;
				height += 1;
				dir = MyPoint.subPointToPoint(new MyPoint(0, 0, 0), pos);
				camera.setDirection(dir);
				camera.setPosition(pos);
				renderer.setWorld(world);
				imgOrigin = renderer.render();
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
		ImageIcon iconMove3 = new ImageIcon("res/images/str_left.png");
		AbstractAction move3 = new AbstractAction("New", iconMove3) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// MyPoint dir = camera.getDirection();
//				MyPoint pos = light.position;
//				pos.x = Math.sin(alpha1) * height2;
//				pos.y = Math.cos(alpha1) * height2;
//				pos.z = 0;
//				alpha1 += 0.2;
//				// dir = MyPoint.subPointToPoint(new MyPoint(0, 0, 0), pos);
//				// camera.setDirection(dir);
//				// camera.setPosition(pos);
//				// light.position = p
				renderer.setWorld(world);
				imgOrigin = renderer.render();
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		};
//		mainFrame.addButtonToToolbar(move);
//		mainFrame.addButtonToToolbar(move2);
//		mainFrame.addButtonToToolbar(move3);
		mainFrame.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent arg0) {
				MyPoint dir = camera.getDirection();
				MyPoint pos = camera.getPosition();
				alpha += 0.1;
				pos.x = Math.sin(alpha) * height;
				pos.y = Math.cos(alpha) * height;
				pos.z = 5;
				dir = MyPoint.subPointToPoint(new MyPoint(0, 0, 0), pos);
				camera.setDirection(dir);
				camera.setPosition(pos);
				renderer.setWorld(world);
				imgOrigin = renderer.render();
				mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		});
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					fps++;
					if (System.currentTimeMillis() - time1 > 1000){
						time1 = System.currentTimeMillis();
						mainFrame.setTitle("fps = " + fps);
						fps = 0;
					}
//					MyPoint pos = light.position;
//					pos.x = Math.sin(alpha1) * height2;
					// pos.y = Math.cos(alpha1) * height2;
					//pos.y = 0;
					//pos.z = 0;
					alpha1 += 0.2;
					// dir = MyPoint.subPointToPoint(new MyPoint(0, 0, 0), pos);
					// camera.setDirection(dir);
					// camera.setPosition(pos);
					// light.position = p
					//renderer.setWorld(world);
					imgOrigin = renderer.render();
					SwingUtilities.invokeLater(new Runnable() {

						@Override
						public void run() {
							mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
							mainFrame.repaint();
						}
					});


					/*
					 * try { Thread.sleep(1000); } catch (InterruptedException
					 * e) { // TODO Auto-generated catch block
					 * e.printStackTrace(); }
					 */
				}
			}
		});
		t.start();

        mainFrame.requestFocus();
		mainFrame.setFocusable(true);
		mainFrame.addKeyListener(new KeyListener() {

			@Override
			public void keyTyped(KeyEvent arg0) {
				// TODO Auto-generated method stub
			//	System.out.println("  --1" + arg0.getKeyChar());
			}

			@Override
			public void keyReleased(KeyEvent arg0) {
				// TODO Auto-generated method stub
			//	System.out.println("  --2" + arg0.getKeyChar());
			}

			@Override
			public void keyPressed(KeyEvent arg0) {
				MyPoint dir = camera.getDirection();
				MyPoint pos = camera.getPosition();
				double k = 0.1;
			//	System.out.println("  --" + arg0.getKeyChar());
				switch (arg0.getKeyChar()) {
				case 'w':
            		pos.x = pos.x + dir.x * k;
					pos.y = pos.y + dir.y * k;
					// pos.z = pos.z + dir.z * k;
					camera.setDirection(dir);
					camera.setPosition(pos);
					renderer.setWorld(world);

					break;
				case 'd':
					a -= 0.1;
					dir.x = Math.sin(a);
					dir.y = Math.cos(a);
					camera.setDirection(dir);
					camera.setPosition(pos);
					renderer.setWorld(world);
					break;
				case 'a':
					dir.x = Math.sin(a);
					dir.y = Math.cos(a);
					a += 0.1;
					camera.setDirection(dir);
					camera.setPosition(pos);
					renderer.setWorld(world);
					break;
				case 's':
					pos.x = pos.x - dir.x * k;
					pos.y = pos.y - dir.y * k;
					// pos.z = pos.z + dir.z * k;
					camera.setDirection(dir);
					camera.setPosition(pos);
					renderer.setWorld(world);

					break;
				default:
					break;
				}
				//imgOrigin = renderer.render();
				//mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
				mainFrame.repaint();
			}
		});
	}

	public static void onMouseEvent(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		int maxSize = Math.max(imgBig.getHeight(), imgBig.getWidth());
		int xWidth = 0;
		int yHeight = 0;
		if (imgBig.getHeight() > imgBig.getWidth()) {
			yHeight = 256;
			xWidth = (int) ((double) imgBig.getWidth() / imgBig.getHeight() * 256);
		} else {
			xWidth = 256;
			yHeight = (int) ((double) imgBig.getHeight() / imgBig.getWidth() * 256);
		}
		int width = (int) (256f / maxSize * 256);
		int xRect = x - width / 2;
		int yRect = y - width / 2;
		if (xRect + width >= xWidth) {
			xRect = xWidth - 1 - width;
		}
		if (yRect + width >= yHeight) {
			yRect = yHeight - 1 - width;
		}
		if (xRect < 0) {
			xRect = 0;
		}
		if (yRect < 0) {
			yRect = 0;
		}

		imgResized = imgBig.getSubimage(
				(int) ((double) xRect * imgBig.getWidth() / xWidth),
				(int) ((double) yRect / (yHeight) * imgBig.getHeight()), 256,
				256);

		mainFrame.drawRect(true, new Rectangle(xRect, yRect, width, width));
		mainFrame.setImgs(imgOrigin, imgResized, imgFiltered);
		mainFrame.repaint();
		System.out.println(" " + ((double) yRect / 256) + " "
				+ ((double) (yRect + width) / 256));
	}
}

