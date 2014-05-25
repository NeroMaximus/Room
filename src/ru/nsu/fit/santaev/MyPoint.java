package ru.nsu.fit.santaev;

import java.awt.Color;
import java.awt.Point;

public class MyPoint{
	
	private static final long serialVersionUID = 1L;
	
	public double x = 0;
	public double y = 0;
	public double z = 0;
	
	public MyPoint(double x, double y, double z){
		this.x = x;
		this.y = y;
		this.z = z;
	}
	public double distanceTo(MyPoint p1){
		return distanceTo(this, p1);
	}

	public static double distanceTo(MyPoint p1, MyPoint p2){
		return Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) 
				+ (p1.y - p2.y) * (p1.y - p2.y) + (p1.z - p2.z) * (p1.z - p2.z));
	}

	public static MyPoint addPointToPoint(MyPoint p1, MyPoint p2){
		return new MyPoint(p1.x + p2.x, p1.y + p2.y, p1.z + p2.z);
	}
	public static MyPoint subPointToPoint(MyPoint p1, MyPoint p2){
		return new MyPoint(p1.x - p2.x, p1.y - p2.y, p1.z - p2.z);
	}
	
	
}
