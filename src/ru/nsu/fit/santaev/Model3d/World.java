package ru.nsu.fit.santaev.Model3d;

import java.util.ArrayList;

import ru.nsu.fit.santaev.MyPoint;

public class World {
	
	private ArrayList<Wall> objects = new ArrayList<Wall>();
	private ArrayList<Light> lights = new ArrayList<Light>();
	
	private Camera camera = new Camera(new MyPoint(0, 0, 0), new MyPoint(1, 1, 0));
	
	public World() {
		
	}


	public ArrayList<Wall> getObjects() {
		return objects;
	}


	public void setObjects(ArrayList<Wall> objects) {
		this.objects = objects;
	}


	public Camera getCamera() {
		return camera;
	}


	public void setCamera(Camera camera) {
		this.camera = camera;
	}


	public ArrayList<Light> getLights() {
		return lights;
	}


	public void setLights(ArrayList<Light> lights) {
		this.lights = lights;
	}



}

