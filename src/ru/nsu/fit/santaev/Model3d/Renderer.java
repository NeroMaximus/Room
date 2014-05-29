package ru.nsu.fit.santaev.Model3d;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import ru.nsu.fit.santaev.MyPoint;

public class Renderer {

	World world = null;

	public int screenPixelHeight = 200;
	public int screenPixelWidth = 200;

	
	public int threadCount = 4;
	ArrayList<MyThread> threads = new ArrayList<MyThread>();
	
	public Renderer() {
		
	}

	public synchronized BufferedImage render() {
		BufferedImage scr = new BufferedImage(screenPixelWidth,
				screenPixelHeight, BufferedImage.TYPE_3BYTE_BGR);
		MyThread.img = scr;
		threads.clear();
		for (int i = 0; i < threadCount; i++){
			MyThread t = new MyThread(this, i);
			threads.add(t);
			t.start();
		}
		for (int i = 0; i < threadCount; i++){
			try {
				threads.get(i).join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		/*MyPoint dir = world.getCamera().getDirection();
		MyPoint center = MyPoint.addPointToPoint(world.getCamera()
				.getPosition(), world.getCamera().getDirection());

		double length = MyPoint.distanceTo(world.getCamera().getPosition(),
				center);

		MyPoint topLeft = MyPoint.addPointToPoint(center, new MyPoint(dir.y,
				dir.x * -1, length));
		MyPoint bottomRight = MyPoint.addPointToPoint(center, new MyPoint(dir.y
				* -1, dir.x, -length));
		MyPoint topRight = MyPoint.addPointToPoint(center, new MyPoint(dir.y,
				dir.x * -1, length));
		MyPoint bottomLeft = MyPoint.addPointToPoint(center, new MyPoint(dir.y
				* -1, dir.x, -length));

		for (int i = 0; i < screenPixelWidth; i++) {
			for (int j = 0; j < screenPixelHeight; j++) {
				
				double dx = ((double) i / screenPixelWidth)
						* (topLeft.x - bottomRight.x);
				double dy = ((double) i / screenPixelWidth)
						* (topLeft.y - bottomRight.y);
				double dz = ((double) j / screenPixelHeight)
						* (topLeft.z - bottomRight.z);
				
				Ray ray = new Ray(world.getCamera().getPosition(),
						MyPoint.subPointToPoint(new MyPoint(topLeft.x - dx,
								topLeft.y - dy, topLeft.z - dz), world
								.getCamera().getPosition()));
				Wall min = null;
				double dis = Double.MAX_VALUE;
				for (Wall wall: world.getObjects()){
					//if (wall.maxX < ray.start )
					MyPoint p = Util.getPointOfCollision(wall,
							ray);
					if (p == null){
						continue;
					}
					if (dis > MyPoint.distanceTo(p, world.getCamera().getPosition())){
						dis = MyPoint.distanceTo(p, world.getCamera().getPosition());
						min = wall;
						
					}
				}
				if (min == null){
					scr.setRGB(i, j, Color.BLACK.getRGB());
					continue;
				}
				MyPoint p = Util.getPointOfCollision(min,
						ray);
				//Light light = world.getLights().get(0);

				if (p == null) {
					scr.setRGB(i, j, Color.BLACK.getRGB());
					continue;
				}
				

				ArrayList<Light> lights = new ArrayList<Light>();
				for (Light light2: world.getLights()){
					if (Util.isVisible(world, new Ray(light2.position, 
							MyPoint.subPointToPoint(p, light2.position)), p)){
						lights.add(light2);
						
					}
				}
				Color result = Light.getResultColorOnPoint(p, min.getMaterial(), lights);
				scr.setRGB(i, j, result.getRGB());
				
			}
		}
		*/
		return scr;
	}
	public Color getColorOnRay(Ray ray){
		Wall min = null;
		double dis = Double.MAX_VALUE;
		for (Wall wall: world.getObjects()){
			MyPoint p = Util.getPointOfCollision(wall,
					ray);
			if (p == null){
				continue;
			}
			if (dis > MyPoint.distanceTo(p, world.getCamera().getPosition())){
				dis = MyPoint.distanceTo(p, world.getCamera().getPosition());
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
		
		Light light = world.getLights().get(0);
		//scr.setRGB(i, j, light.getResultColorOnPoint(p, min.getMaterial()).getRGB());
		Ray ray2 = new Ray(light.position,
				MyPoint.subPointToPoint(p, light.position));
		Wall min2 = null;
		double dis2 = Double.MAX_VALUE;
		
		
		
		
		for (Wall wall: world.getObjects()){
			//nif (ray.start)
			MyPoint p2 = Util.getPointOfCollision(wall,
					ray2);
			if (p2 == null){
				continue;
			}
			if (dis2 > MyPoint.distanceTo(p2, light.position)){
				dis2 = MyPoint.distanceTo(p2, light.position);
				min2 = wall;
			}
		}
		if (min2 == min){
			return light.getResultColorOnPoint(null, null, null);
		}else{
			return Color.BLACK;
		}
	}
	public World getWorld() {
		return world;
	}

	public void setWorld(World world) {
		this.world = world;
	}

	// public void ren

}
