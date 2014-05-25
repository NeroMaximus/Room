package ru.nsu.fit.santaev.Model3d;

import ru.nsu.fit.santaev.MyPoint;

public class Util {
	
	public static MyPoint getPointOfCollision(Wall wall, Ray ray){
		MyPoint collisionPoint = new MyPoint(0, 0, 0);
		double t = -(wall.A * ray.start.x + wall.B * ray.start.y + wall.C * ray.start.z + wall.D) 
				/ (wall.A * ray.dir.x + wall.B * ray.dir.y + wall.C * ray.dir.z);
		if (t <= 0){
			return null;
		}
		collisionPoint.x = ray.start.x + ray.dir.x * t;
		collisionPoint.y = ray.start.y + ray.dir.y * t;
	 	collisionPoint.z = ray.start.z + ray.dir.z * t;
	 	
	 	if (Math.abs(collisionPoint.y - Math.round(collisionPoint.y)) < 0.001){
	 		collisionPoint.y = Math.round(collisionPoint.y);
	 	}
		if (Math.abs(collisionPoint.x - Math.round(collisionPoint.x)) < 0.001){
	 		collisionPoint.x = Math.round(collisionPoint.x);
	 	}
		if (Math.abs(collisionPoint.z - Math.round(collisionPoint.z)) < 0.001){
	 		collisionPoint.z = Math.round(collisionPoint.z);
	 	}
	 	if (isPointInWall(wall, collisionPoint)){
	 		return collisionPoint;
	 	}else{
	 		return null;
	 	}
	 	
		//return collisionPoint;
	}
	// Плоскости и прямой
	public static MyPoint getPointOfCollision2(Wall wall, Ray ray){
		MyPoint collisionPoint = new MyPoint(0, 0, 0);
		double t = -(wall.A * ray.start.x + wall.B * ray.start.y + wall.C * ray.start.z + wall.D) 
				/ (wall.A * ray.dir.x + wall.B * ray.dir.y + wall.C * ray.dir.z);
		collisionPoint.x = ray.start.x + ray.dir.x * t;
		collisionPoint.y = ray.start.y + ray.dir.y * t;
	 	collisionPoint.z = ray.start.z + ray.dir.z * t;
	 	
	 	if (Math.abs(collisionPoint.y - Math.round(collisionPoint.y)) < 0.001){
	 		collisionPoint.y = Math.round(collisionPoint.y);
	 	}
		if (Math.abs(collisionPoint.x - Math.round(collisionPoint.x)) < 0.001){
	 		collisionPoint.x = Math.round(collisionPoint.x);
	 	}
		if (Math.abs(collisionPoint.z - Math.round(collisionPoint.z)) < 0.001){
	 		collisionPoint.z = Math.round(collisionPoint.z);
	 	}
		return collisionPoint;
	 	
		//return collisionPoint;
	}
	public static boolean isPointInWall(Wall wall, MyPoint p){
		double maxX = Math.max(wall.p3.x, Math.max(wall.p1.x, wall.p2.x));
		double maxY = Math.max(wall.p3.y, Math.max(wall.p1.y, wall.p2.y));
		double maxZ = Math.max(wall.p3.z, Math.max(wall.p1.z, wall.p2.z));
		
		double minX = Math.min(wall.p3.x, Math.min(wall.p1.x, wall.p2.x));
		double minY = Math.min(wall.p3.y, Math.min(wall.p1.y, wall.p2.y));
		double minZ = Math.min(wall.p3.z, Math.min(wall.p1.z, wall.p2.z));
		
		
		if (minX <= p.x && maxX >= p.x){
			if (minY <= p.y && maxY >= p.y){
				if (minZ <= p.z && maxZ >= p.z){
					return true;
				}
			}
		}	
		return false;
	}
	public static boolean isVisible(World world, Ray ray, MyPoint p){
		//Wall min2 = null;
		double original = MyPoint.distanceTo(p, ray.start);
		double dis = Double.MAX_VALUE;
		for (Wall wall: world.getObjects()){
			MyPoint p2 = Util.getPointOfCollision(wall,
					ray);
			if (p2 == null){
				continue;
			}
			if (dis > MyPoint.distanceTo(p2, ray.start)){
				dis = MyPoint.distanceTo(p2, ray.start);
				//min2 = wall;
			}
		}
		if (original <= dis){
			return true;
		}else{
			return false;
		}
		
	}

}
