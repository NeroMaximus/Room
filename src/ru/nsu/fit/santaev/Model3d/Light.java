package ru.nsu.fit.santaev.Model3d;

import java.awt.Color;
import java.util.ArrayList;

import ru.nsu.fit.santaev.MyPoint;

public class Light {
	
	public MyPoint position = new MyPoint(0, 0, 0);
	public double brightness = 1;
	private double coefficienT_diffusion = 1;
	private static double ambian = 0.1;
	public Color color = Color.RED;
	
	public Light(MyPoint pos) {
		position = pos;
	}
	
	public double getBrightnessOnPoint(MyPoint p){
		double distance = MyPoint.distanceTo(position, p);
		return 1 / Math.pow(distance, 2);
	}
	public static double getBrightnessOnPoint(MyPoint p, Light l){
		double distance = MyPoint.distanceTo(l.position, p);
		return 1 / Math.pow(distance, 2);
	}
	public static Color getResultColorOnPoint(MyPoint p, Material material, ArrayList<Light> lights){
		//double result_red = ( color.getRed() + material.getColor().getRed() ) * brightness * getBrightnessOnPoint(p) * material.getkDiffusion() + material.getColor().getRed() * ambian;
		//double result_green = ( color.getGreen()  + material.getColor().getGreen() ) * brightness * getBrightnessOnPoint(p)  * material.getkDiffusion() + material.getColor().getGreen() * ambian;
		//double result_blue = ( color.getBlue() + material.getColor().getBlue() ) * brightness * getBrightnessOnPoint(p) * material.getkDiffusion() + material.getColor().getBlue() * ambian;
		double result_red = 0;
		double result_green = 0;
		double result_blue = 0;
		
		double k = 0;
		double sumR = 0; 
		double sumG = 0;
		double sumB = 0;
		for (int i = 0; i < lights.size(); i++){
			k += lights.get(i).brightness * getBrightnessOnPoint(p, lights.get(i));
			sumR += lights.get(i).brightness * getBrightnessOnPoint(p, lights.get(i))
					* lights.get(i).color.getRed(); 
			sumG += lights.get(i).brightness * getBrightnessOnPoint(p, lights.get(i))
					* lights.get(i).color.getGreen();
			sumB += lights.get(i).brightness * getBrightnessOnPoint(p, lights.get(i))
					* lights.get(i).color.getBlue();
		}
		
		result_red = k * material.getColor().getRed() + sumR + material.getColor().getRed() * ambian;
		result_green = k * material.getColor().getGreen() + sumG + material.getColor().getGreen() * ambian;
		result_blue = k * material.getColor().getBlue() + sumB + material.getColor().getBlue() * ambian;
		if (result_red > 255){
			result_red = 255;
		}
		if (result_green > 255){
			result_green = 255;
		}
		if (result_blue > 255){
			result_blue = 255;
		}
		return new Color( (int)result_red, (int)result_green, (int)result_blue);
	}
	public static Color getSumOfColors(Color c1, Color c2, double k, double k1){
		double r = c1.getRed() * k + c2.getRed() * k1;
		double g = c1.getGreen() * k + c2.getGreen() * k1;
		double b = c1.getBlue() * k + c2.getBlue() * k1;
		if (r > 255){
			r = 255;
		}
		if (g > 255){
			g = 255;
		}
		if (b > 255){
			b = 255;
		}
		return new Color((int)r, (int)g, (int)b);
	}
}
