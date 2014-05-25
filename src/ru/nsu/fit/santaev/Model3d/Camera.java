package ru.nsu.fit.santaev.Model3d;

import ru.nsu.fit.santaev.MyPoint;

public class Camera {
	
	private MyPoint direction = new MyPoint(0, 0, 0);
	private MyPoint position = new MyPoint(0, 0, 0);
	
	
	public Camera(MyPoint pos, MyPoint direct){
		setPosition(pos);
		setDirection(direct);
	}

	public MyPoint getPosition() {
		return position;
	}

	public void setPosition(MyPoint position) {
		this.position = position;
	}

	public MyPoint getDirection() {
		return direction;
	}

	public void setDirection(MyPoint direct) {
		this.direction = direct;
	}
}
