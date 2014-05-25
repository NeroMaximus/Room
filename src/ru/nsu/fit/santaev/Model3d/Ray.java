package ru.nsu.fit.santaev.Model3d;

import ru.nsu.fit.santaev.MyPoint;

public class Ray {
	
	public MyPoint start = new MyPoint(0, 0, 0);
	public MyPoint dir = new MyPoint(1, 1, 1);
	 
	public Ray(MyPoint p1, MyPoint p2) {
		this.start = p1;
		this.dir = p2;
	}

}
