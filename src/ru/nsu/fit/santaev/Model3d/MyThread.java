package ru.nsu.fit.santaev.Model3d;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ru.nsu.fit.santaev.MyPoint;

public class MyThread extends Thread {

	private Renderer renderer = null;
	private int num = 0;
	// private static
	public static BufferedImage img = null;

	public MyThread(Renderer renderer, int num) {
		this.renderer = renderer;
		this.num = num;
	}

	@Override
	public void run() {

		// BufferedImage scr = new BufferedImage(renderer.screenPixelWidth,
		// renderer.screenPixelHeight, BufferedImage.TYPE_3BYTE_BGR);
		MyPoint dir = renderer.world.getCamera().getDirection();
		MyPoint center = MyPoint.addPointToPoint(renderer.world.getCamera()
				.getPosition(), renderer.world.getCamera().getDirection());

		double length = MyPoint.distanceTo(renderer.world.getCamera()
				.getPosition(), center);

		MyPoint topLeft = MyPoint.addPointToPoint(center, new MyPoint(dir.y,
				dir.x * -1, length));
		MyPoint bottomRight = MyPoint.addPointToPoint(center, new MyPoint(dir.y
				* -1, dir.x, -length));

		for (int i = 0; i < renderer.screenPixelWidth; i++) {
			for (int j = num * renderer.screenPixelHeight
					/ renderer.threadCount; j < (num + 1)
					* renderer.screenPixelHeight / renderer.threadCount; j++) {

				double dx = ((double) i / renderer.screenPixelWidth)
						* (topLeft.x - bottomRight.x);
				double dy = ((double) i / renderer.screenPixelWidth)
						* (topLeft.y - bottomRight.y);
				double dz = ((double) j / renderer.screenPixelHeight)
						* (topLeft.z - bottomRight.z);

				Ray ray = new Ray(renderer.world.getCamera().getPosition(),
						MyPoint.subPointToPoint(new MyPoint(topLeft.x - dx,
								topLeft.y - dy, topLeft.z - dz), renderer.world
								.getCamera().getPosition()));
				Wall min = null;
				double dis = Double.MAX_VALUE;
				boolean flag = false;
				int c = 0;
				int c1 = 0;
				for (Wall wall : renderer.world.getObjects()) {
					c1++;
					if (ray.dir.x >= 0 && (ray.start.x >= wall.maxX)){
						if (ray.dir.y >= 0 && (ray.start.y >= wall.maxY)){
							flag = true;
						}
						if (ray.dir.y <= 0 && (ray.start.y <= wall.minY)){
							flag = true;
						}
					}
					if (ray.dir.x <= 0 && (ray.start.x <= wall.minX)){
						if (ray.dir.y >= 0 && (ray.start.y >= wall.maxY)){
							flag = true;
						}
						if (ray.dir.y <= 0 && (ray.start.y <= wall.minY)){
							flag = true;
						}
					}
					if (flag){
						c++;
						flag = false;
						continue;
					}
					MyPoint p = Util.getPointOfCollision(wall, ray);
					if (p == null) {
						continue;
					}
					if (dis > MyPoint.distanceTo(p, renderer.world.getCamera()
							.getPosition())) {
						dis = MyPoint.distanceTo(p, renderer.world.getCamera()
								.getPosition());
						min = wall;
					}
				}
				//System.out.println("--- " + c1 + " " + c);
				if (min == null) {
					setRGB(i, j, Color.BLACK.getRGB());
					continue;
				}
				MyPoint p = Util.getPointOfCollision(min, ray);

				if (p == null) {
					setRGB(i, j, Color.BLACK.getRGB());
					continue;
				}
				if (min.getMaterial().getkReflection() != 0 &&  p != null){
					setRGB(i, j, Color.BLACK.getRGB());
					MyPoint normal = new MyPoint(min.A, min.B, min.C);
					Ray normalRay = new Ray(renderer.world.getCamera().getPosition(), normal);
					MyPoint a2 = Util.getPointOfCollision2(min, normalRay);
					MyPoint a2p2 = new MyPoint(2 * (p.x - a2.x), 2 * (p.y - a2.y), 2 * (p.z - a2.z));
					MyPoint a3 = MyPoint.addPointToPoint(renderer.world.getCamera().getPosition(), a2p2);
					Ray rayy = new Ray(p, MyPoint.subPointToPoint(a3, p));
					Color r = getColorOnRay(rayy);
					
					ArrayList<Light> lights = new ArrayList<Light>();
					synchronized (renderer.world.getLights()){
                    for (Light light2 : renderer.world.getLights()) {
						if (Util.isVisible(renderer.world, new Ray(light2.position,
								MyPoint.subPointToPoint(p, light2.position)), p)) {
							lights.add(light2);

						}
					}}
					Color result = Light.getResultColorOnPoint(p,
							min.getMaterial(), lights);
					
					Color g = result;//light.getResultColorOnPoint(p, min.getMaterial());
					result = Light.getSumOfColors(r, g, min.getMaterial().getkReflection(), min.getMaterial().getkDiffusion());
					setRGB(i, j, result.getRGB());
					continue;
				}


				ArrayList<Light> lights = new ArrayList<Light>();
				synchronized (renderer.world.getLights()){
                for (Light light2 : renderer.world.getLights()) {
					if (Util.isVisible(renderer.world, new Ray(light2.position,
							MyPoint.subPointToPoint(p, light2.position)), p)) {
						lights.add(light2);

					}
				}
                }
				Color result = Light.getResultColorOnPoint(p,
						min.getMaterial(), lights);
				// scr.setRGB(i, j, result.getRGB());
				setRGB(i, j, result.getRGB());

				
			}

		}
		// super.run();
	}
	public Color getColorOnRay(Ray ray){
		Wall min = null;
		double dis = Double.MAX_VALUE;
		for (Wall wall: renderer.world.getObjects()){
			MyPoint p = Util.getPointOfCollision(wall,
					ray);
			if (p == null){
				continue;
			}
			if (dis > MyPoint.distanceTo(p, renderer.world.getCamera().getPosition())){
				dis = MyPoint.distanceTo(p, renderer.world.getCamera().getPosition());
				min = wall;
				
			}
		}
		if (min == null){
			return Color.BLACK;
		}
		MyPoint p = Util.getPointOfCollision(min,
				ray);
		if (p == null) {
			return Color.BLACK;
		}
		
		ArrayList<Light> lights = new ArrayList<Light>();
		for ( int k = 0 ; k < renderer.world.getLights().size(); k++){
            Light light2 = renderer.world.getLights().get(k);
			if (Util.isVisible(renderer.world, new Ray(light2.position,
					MyPoint.subPointToPoint(p, light2.position)), p)) {
				lights.add(light2);

			}
		}
		Color result = Light.getResultColorOnPoint(p,
				min.getMaterial(), lights);
		// scr.setRGB(i, j, result.getRGB());
		return result;
	}
	public static void setRGB(int x, int y, int rgb) {
		synchronized (img) {
			img.setRGB(x, y, rgb);
		}
	}
}
