package ru.nsu.fit.santaev.Model3d;

import java.awt.Color;

public class Material {
	
	
	private Color color = Color.GRAY;
	
	private double kDiffusion = 1; 
	private double kReflection = 0;
	

	public void normalize(){
		double r = kDiffusion + kReflection;
		kDiffusion = kDiffusion / r;
		kReflection = kReflection / r;
	}

	public double getkDiffusion() {
		return kDiffusion;
	}

	public void setkDiffusion(double kDiffusion) {
		this.kDiffusion = kDiffusion;
		normalize();
	}

	public double getkReflection() {
		return kReflection;
	}

	public void setkReflection(double kReflection) {
		this.kReflection = kReflection;
		normalize();
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}

