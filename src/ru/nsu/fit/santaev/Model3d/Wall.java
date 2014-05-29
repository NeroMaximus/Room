package ru.nsu.fit.santaev.Model3d;

import ru.nsu.fit.santaev.MyPoint;

public class Wall {
	
	public MyPoint p1 = new MyPoint(0, 0, 0);
	public MyPoint p2 = new MyPoint(0, 0, 0);
	public MyPoint p3 = new MyPoint(0, 0, 0);
	public MyPoint p4 = new MyPoint(0, 0, 0);
	
	public double maxX = 0;  
	public double maxY = 0;
	public double maxZ = 0;
	
	public double minX = 0;
	public double minY = 0;
	public double minZ = 0;
	
	public double A = 1;
	public double B = 1;
	public double C = 1;
	public double D = 1;
	
	private Material material = new Material(); 
	
	public Wall() {
	}
	
	
	public void setABCD(){
		double a = (p2.z - p1.z) * (p3.y - p1.y) - (p2.y - p1.y) * (p3.z - p1.z);
		double b = (p2.z - p1.z) * (p3.x - p1.x) - (p2.x - p1.x) * (p3.z - p1.z);
		double c = (p2.y - p1.y) * (p3.x - p1.x) - (p2.x - p1.x) * (p3.y - p1.y);
		A = a;
		B = b;
		C = c;
		D = -(a * p1.x + p1.y * b + p1.z * c);
		
		Wall wall = this;
		maxX = Math.max(wall.p3.x, Math.max(wall.p1.x, wall.p2.x));
		maxY = Math.max(wall.p3.y, Math.max(wall.p1.y, wall.p2.y));
		maxZ = Math.max(wall.p3.z, Math.max(wall.p1.z, wall.p2.z));
		
		minX = Math.min(wall.p3.x, Math.min(wall.p1.x, wall.p2.x));
		minY = Math.min(wall.p3.y, Math.min(wall.p1.y, wall.p2.y));
		minZ = Math.min(wall.p3.z, Math.min(wall.p1.z, wall.p2.z));
	}


	public Material getMaterial() {
		return material;
	}


	public void setMaterial(Material material) {
		this.material = material;
	}
	

}

