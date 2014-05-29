package ru.nsu.fit.santaev;

import ru.nsu.fit.santaev.Model3d.Light;
import ru.nsu.fit.santaev.Model3d.World;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainFrame extends JFrame {

	private static final long serialVersionUID = 1L;

	public static final int MIN_WIDTH = 1200;
	public static final int MIN_HEIGHT = 700;

	private static String menuFileTitle = "File";
	private static String subMenuFileTitle = "New file";
	private static String subMenuEXitTitle = "Exit";
	private static String menuHelpTitle = "Help";
	private static String subMenuAbouTitle = "About";

	private static String aboutString = "About";

	public JToolBar toolBar = null;
	private JPanel drawPanel = null;

	private int width = 0;
	private int height = 0;
	
	private boolean isDrawRect = false;
	private Rectangle rect = new Rectangle(0, 0, 100, 100);
	
	private BufferedImage img1 = null;
	private BufferedImage img2 = null;
	private BufferedImage img3 = null;

	private BufferedImage imgOriginal1 = null;
	private BufferedImage imgOriginal2 = null;
	private BufferedImage imgOriginal3 = null;
	
	public JPanel panel1 = null;
	private JPanel panel2 = null;
	private JPanel panel3 = null;
    private World world;
    JFrame me;

	public MainFrame(String title, World world) {
		super(title);
        me = this;
        this.world = world;
		setMinimumSize(new Dimension(MIN_WIDTH, MIN_HEIGHT));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		createGUI();
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		this.setLocation((int) dim.getWidth() / 2
				- (int) this.getSize().getWidth() / 2, (int) dim.getHeight()
				/ 2 - (int) this.getSize().getHeight() / 2);
		this.setVisible(true);
		height = getHeight();
		width = getWidth();
		img1 = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

		addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// clickedPointPref = e.getPoint();
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {

			}

			@Override
			public void mouseDragged(MouseEvent e) {

			}
		});
		addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(MouseWheelEvent e) {

			}
		});
		img1 = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		Graphics graphImg = img1.createGraphics();
		graphImg.setColor(Color.RED);
		graphImg.drawLine(0, 0, 100, 100);
		img2 = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		img3 = new BufferedImage(width, height,
				BufferedImage.TYPE_3BYTE_BGR);
		
	}

	protected void createGUI() {
		createMenu();
		createToolBar();
		JPanel p1 = new JPanel();
		JPanel p2 = new JPanel();
		JPanel p3 = new JPanel();
		
		panel1 = new DrawJPanel(PanelType.ORIGINAL);
		panel2 = new MapRoom();
		panel3 = new DrawJPanel(PanelType.FILTERED);
		
		p1.add(panel1);
		p1.setLayout(new BoxLayout(p1, BoxLayout.X_AXIS));
		p2.add(panel2);
		p2.setLayout(new BoxLayout(p2, BoxLayout.X_AXIS));
		//p3.add(panel3);
		//p3.setLayout(new BoxLayout(p3, BoxLayout.X_AXIS));
		panel2.setMaximumSize(new Dimension(500, 500));
		panel1.setMaximumSize(new Dimension(512, 512));
		//panel3.setMaximumSize(new Dimension(256, 256));
		
		panel2.setMinimumSize(new Dimension(500, 500));
		panel1.setMinimumSize(new Dimension(512, 512));
		//panel3.setMinimumSize(new Dimension(256, 256));
		
		
		
		drawPanel = new JPanel();
		getContentPane().add(drawPanel);
		drawPanel.setLayout(new GridLayout(1, 2));
		drawPanel.setMaximumSize(new Dimension(768, 768));
		drawPanel.setMinimumSize(new Dimension(768, 768));
		
		drawPanel.add(p1);
		drawPanel.add(p2);
		//drawPanel.add(p3);
		
		
	}
	
	protected void createMenu() {
		JMenuBar menuBar = new JMenuBar();

		JMenu fileMenu = new JMenu(menuFileTitle);
		menuBar.add(fileMenu);

		JMenuItem fileMenuSubItem1 = new JMenuItem(subMenuFileTitle);
		fileMenu.add(fileMenuSubItem1);
		JMenuItem fileMenuSubItem3 = new JMenuItem(subMenuEXitTitle);
		fileMenu.add(fileMenuSubItem3);
		fileMenuSubItem3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(NORMAL);
			}
		});
		JMenu helpMenu = new JMenu(menuHelpTitle);
		JMenuItem fileMenuSubItem2 = new JMenuItem(subMenuAbouTitle);
		helpMenu.add(fileMenuSubItem2);
		fileMenuSubItem2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				JOptionPane.showMessageDialog(MainFrame.this, aboutString);
			}
		});
		menuBar.add(helpMenu);

		setJMenuBar(menuBar);
	}

	protected void createToolBar() {
		toolBar = new JToolBar();
		toolBar.setFloatable(false);
		ImageIcon iconNew = new ImageIcon("res/images/new_file.png");
		ImageIcon iconSave = new ImageIcon("res/images/save_file.png");
		ImageIcon iconAbout = new ImageIcon("res/images/about.png");
		ImageIcon iconExit = new ImageIcon("res/images/exit.png");

		Action actionNew = new AbstractAction("New", iconNew) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setTitle("Clicked " + "new file");
			}
		};
		Action actionSave = new AbstractAction("Save", iconSave) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				setTitle("Clicked " + "save file");
			}
		};
		Action actionAbout = new AbstractAction("About", iconAbout) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				JOptionPane.showMessageDialog(MainFrame.this, aboutString);
			}
		};
		Action actionExit = new AbstractAction("Exit", iconExit) {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.exit(NORMAL);
			}
		};
		// addButtonToToolbar(actionNew);
		// addButtonToToolbar(actionSave);
		addButtonToToolbar(actionAbout);
		addButtonToToolbar(actionExit, 1);
		getContentPane().add(toolBar, BorderLayout.NORTH);
	}
	public void setOnClickListener(MouseListener m, MouseMotionListener mm){
		panel1.addMouseListener(m);
		panel1.addMouseMotionListener(mm);
	}
	public void addButtonToToolbar(Action action) {
		JButton button = new SmallButton(action);
		button.setToolTipText((String) action.getValue("tip"));
		toolBar.add(button, 0);
		toolBar.repaint();
		revalidate();
	}

	public void addSeparatorToToolbar() {
		JSeparator sep = new JToolBar.Separator();
		sep.setSize(10, 30);
		toolBar.add(sep, 0);
		toolBar.repaint();
		revalidate();
	}

	public void addButtonToToolbar(Action action, int index) {
		JButton button = new SmallButton(action);
		toolBar.add(button, index);
	}
	public void setImgs(BufferedImage img1, BufferedImage img2, BufferedImage img3){
		//this.imgOriginal1 = img1;
		//this.imgOriginal2 = img2;
		//this.imgOriginal3 = img3;
		this.img1 = img1;
		//this.img1.setRGB(100, 100, Color.BLUE.getRGB());
		this.img2 = img2;
		this.img3 = img3;
		
		//this.img1 = null;
		//this.img2 = null;
		//this.img3 = null;
	}
	public void drawRect(boolean isDraw, Rectangle rect){
		this.isDrawRect = isDraw;
		this.rect = rect;
	}
	public void updateImgs(){
		img1 = imgOriginal1;
		img2 = imgOriginal2;
		img3 = imgOriginal3;
	}
	class SmallButton extends JButton {

		private static final long serialVersionUID = 1L;

		public SmallButton(Action act) {
			super((Icon) act.getValue(Action.SMALL_ICON));

			setMargin(new Insets(1, 1, 1, 1));
			addActionListener(act);
			// addMouseListener(act);
		}

		public float getAlignmentY() {
			return 0.5f;
		}

	}

	enum PanelType {
		ORIGINAL, SCALED, FILTERED,
	}

    class MapRoom extends JPanel implements MouseListener {
        ArrayList<Integer[]> lightsArray = new ArrayList<Integer[]>();

        MapRoom(){
            super();
            setPreferredSize( new Dimension( 500, 500));
            setBackground( Color.BLACK);
            addMouseListener(this);
        }

        protected void paintComponent(Graphics graphics){
            Graphics2D graphics2D = (Graphics2D) graphics;
            graphics2D.drawLine( 400, 100, 200, 100);
            graphics2D.drawLine( 200, 100, 200, 300);
            graphics2D.drawLine( 200, 300, 100, 300);
            graphics2D.drawLine( 100, 400, 100, 300);
            graphics2D.drawLine( 100, 400, 400, 400);
            graphics2D.drawLine( 400, 400, 400, 300);
            graphics2D.drawLine( 400, 300, 300, 300);
            graphics2D.drawLine( 300, 200, 300, 300);
            graphics2D.drawLine( 300, 200, 400, 200);
            graphics2D.drawLine( 400, 100, 400, 200);

            for (int i = 0; i < lightsArray.size(); i++){
                graphics2D.fillRect(lightsArray.get(i)[0].intValue(), lightsArray.get(i)[1].intValue(), 5, 5);
            } 
            me.repaint();
            panel1.repaint();


        }

        @Override
        public void mouseClicked(MouseEvent e) {
            double x = (e.getX() - 250)/50.0;
            double y = (250 - e.getY())/50.0;
            Integer[] l = new Integer[2];
            l[0] = e.getX();
            l[1] = e.getY();


            lightsArray.add(l);

            OptionPanel optionPanel = new OptionPanel(x,y);

            System.out.println(x+" "+ y);
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }



    class DrawJPanel extends JPanel {

		private static final long serialVersionUID = 1L;

		public PanelType type = PanelType.ORIGINAL;

		private int height = 0;
		private int width = 0;

		public DrawJPanel(PanelType type) {
			this.type = type;
			setBackground(Color.WHITE);
			System.out.println("OnCreate");
			addComponentListener(new ComponentListener() {
				
				@Override
				public void componentShown(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void componentResized(ComponentEvent arg0) {
					if (height > 0 || width > 0){
						//setSize(256, 256);
					}
				}
				
				@Override
				public void componentMoved(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}
				
				@Override
				public void componentHidden(ComponentEvent arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}

		protected void updateGraphics(Graphics graph) {
			//log("Update " + type);
			if (!(height == getHeight() && width == getWidth())) {
				height = getHeight();
				width = getWidth();
				return;
				/*switch (type) {
				case ORIGINAL:
					log("Update " + height);
					img1 = new BufferedImage(width, height,
							BufferedImage.TYPE_3BYTE_BGR);
					break;
				case SCALED:
					img2 = new BufferedImage(width, height,
							BufferedImage.TYPE_3BYTE_BGR);
					break;
				case FILTERED:
					img3 = new BufferedImage(width, height,
							BufferedImage.TYPE_3BYTE_BGR);
					break;
				}
				*/
			
			}
			graph.drawImage(getImg(), 0, 0, null);
			height = getHeight();
			width = getWidth();
			if (isDrawRect && type == PanelType.ORIGINAL){
				Graphics gr = graph;
				gr.setColor(Color.RED);
				gr.drawLine(rect.x, rect.y, rect.x, rect.y + rect.height);
				gr.drawLine(rect.x, rect.y, rect.x + rect.width, rect.y);
				gr.drawLine(rect.x + rect.width, rect.y, rect.x + rect.width, rect.y + rect.height);
				gr.drawLine(rect.x, rect.y + rect.height, rect.x + rect.width, rect.y + rect.height);
			}

			//log("Update!! " + getImg().getWidth());
			//graph.drawLine(0, 0, 100, 100);
			
			
		}
		public BufferedImage getImg(){
			switch (type) {
			case ORIGINAL:
				return img1;
			case SCALED:
				return img2;
			case FILTERED:
				return img3;
			}
			return img1;
		}
		@Override
		public void paint(Graphics g) {
			super.paint(g);
			updateGraphics(g);
		}
	}

	public static void log(String str) {
		System.out.println(str);
	}



    public class OptionPanel extends JFrame {
        private int lower_limit = 1;
        private int upper_limit = 255;
        private int init_limit = 64;
        private int red_value = init_limit;
        private int green_value = init_limit;
        private int blue_value = init_limit;

        private JSlider slider;
        private JSlider sliderRed;
        private JSlider sliderGreen;
        private JSlider sliderBlue;
        private JPanel okAndCancel;
        private JButton apply;
        private JButton cancel;
        private JFrame me;

        public OptionPanel(final double x, final double y){
            super("Options");
            setMinimumSize(new Dimension(400, 600));
            setMaximumSize(new Dimension(400, 500));
            setLayout( new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

            me = this;
            apply = new JButton("Apply");
            apply.addActionListener( new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    final Light light = new Light(new MyPoint(y, x, 0));
                    light.color = new Color(red_value, green_value, blue_value);
                    synchronized (world.getLights()){
                        world.getLights().add(light);
                    }

                    me.dispose();
                }
            });
            cancel = new JButton("Cancel");
            cancel.addActionListener( new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    me.dispose();
                }
            });
            okAndCancel = new JPanel();
            okAndCancel.setMaximumSize( new Dimension(200,64));
            okAndCancel.setLayout( new GridLayout(1,2));
            okAndCancel.add( apply );
            okAndCancel.add( cancel );


            slider = new JSlider( JSlider.HORIZONTAL, lower_limit, upper_limit, init_limit);
            slider.setPaintTicks(true);
            slider.setPaintLabels(true);
            slider.setMajorTickSpacing(16);
            slider.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        red_value = source.getValue();
                        green_value = red_value;
                        blue_value = red_value;
                        sliderRed.setValue(red_value);
                        sliderGreen.setValue(green_value);
                        sliderBlue.setValue(blue_value);
                    }
                }
            });
            sliderRed = new JSlider( JSlider.HORIZONTAL, lower_limit, upper_limit, init_limit);
            sliderRed.setPaintTicks(true);
            sliderRed.setPaintLabels(true);
            sliderRed.setMajorTickSpacing(16);
            sliderRed.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        red_value = source.getValue();
                    }
                }
            });
            sliderGreen = new JSlider( JSlider.HORIZONTAL, lower_limit, upper_limit, init_limit);
            sliderGreen.setPaintTicks(true);
            sliderGreen.setPaintLabels(true);
            sliderGreen.setMajorTickSpacing(16);
            sliderGreen.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        green_value = source.getValue();
                    }
                }
            });
            sliderBlue = new JSlider( JSlider.HORIZONTAL, lower_limit, upper_limit, init_limit);
            sliderBlue.setPaintTicks(true);
            sliderBlue.setPaintLabels(true);
            sliderBlue.setMajorTickSpacing(16);
            sliderBlue.addChangeListener(new ChangeListener() {
                @Override
                public void stateChanged(ChangeEvent e) {
                    JSlider source = (JSlider) e.getSource();
                    if (!source.getValueIsAdjusting()) {
                        blue_value = source.getValue();
                    }
                }
            });

            add(new JLabel("Red"));
            add(sliderRed);
            add(new JLabel("Green"));
            add(sliderGreen);
            add(new JLabel("Blue"));
            add(sliderBlue);
            add(new JLabel("RGB"));
            add(slider);

            add(okAndCancel);
            setVisible(true);
            pack();
        }
    }




}
