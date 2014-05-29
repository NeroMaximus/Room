package ru.nsu.fit.santaev.Model3d;

import ru.nsu.fit.santaev.MyPoint;

import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Created by nero on 5/29/14.
 */
public class Parser {
    Scanner scanner = null;
    ArrayList<Wall> arrayList = null;

    public Parser(String fileName, ArrayList<Wall> arrayList){
        try {
            scanner = new Scanner(new File("walls.txt"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        this.arrayList = arrayList;
    }


    public void run(){

        while(scanner.hasNextInt())
        {
            int p1x = scanner.nextInt();
            int p1y = scanner.nextInt();
            int p1z = scanner.nextInt();
            int p2x = scanner.nextInt();
            int p2y = scanner.nextInt();
            int p2z = scanner.nextInt();
            int p3x = scanner.nextInt();
            int p3y = scanner.nextInt();
            int p3z = scanner.nextInt();
            int p4x = scanner.nextInt();
            int p4y = scanner.nextInt();
            int p4z = scanner.nextInt();

            int r = scanner.nextInt();
            int g = scanner.nextInt();
            int b = scanner.nextInt();

            int kD = scanner.nextInt();
            int kR = scanner.nextInt();


            Wall wall = new Wall();
            wall.p1 = new MyPoint(p1x, p1y, p1z);
            wall.p2 = new MyPoint(p2x, p2y, p2z);
            wall.p3 = new MyPoint(p3x, p3y, p3z);
            wall.p4 = new MyPoint(p4x, p4y, p4z);
            wall.setABCD();
            Material material = new Material();
            material.setColor(new Color(r,g,b));
            material.setkDiffusion(kD/100.0);
            material.setkReflection(kR/100.0);
            wall.setMaterial(material);
            arrayList.add(wall);
        }
    }
}
