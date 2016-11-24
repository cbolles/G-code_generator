package com.bolles.AutoCNC.gCodeWriter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class CNCDraw 
{
	private double bitSize;
	private int currentNum;
	private PrintWriter gCode;
	
	public CNCDraw(double bitSize, int currentNum, String fileName)
	{
		this.bitSize = bitSize;
		this.currentNum = currentNum;
		try 
		{
			this.gCode = new PrintWriter(fileName);
		} 
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		}
	}
	
	public void enterDefaults(int toolBit, int spindleSpeed)
	{
		gCode.println(getNum() + " T" + toolBit + " S" + spindleSpeed + " M3");
	}
	
	public void setCurrentNum(int currentNum)
	{
		this.currentNum = currentNum;
	}
	
	public void setBitSize(double bitSize)
	{
		this.bitSize = bitSize;
	}
	
	public double getCurrentNum()
	{
		return currentNum;
	}
	
	public String getNum()
	{
		return "N" + ++currentNum;
	}
	
	public void safeSpace()
	{
		gCode.println(getNum() + " G00 " + "Z" + 0.1);
	}
	
	public void cutDown(double depth)
	{
		gCode.println(getNum() + " G01 " + "Z" + depth);
	}
	
	public void goTo(double x, double y)
	{
		gCode.println(getNum() + " G00 " + "X" + x + " Y" + y);
	}
	
	public void drawLine(double x, double y, double length, double depth, char direction)
	{
		double endX = x;
		double endY = y;
		goTo(x,y);//Moves tool to starting position
		cutDown(depth);
		switch(direction) //Changes end point depending on direction
		{
			case 'R' : endX = endX + length; break;
			case 'L' : endX = endX - length; break;
			case 'U' : endY = endY + length; break;
			case 'D' : endY = endY - length; break;
			default : break;
		}
		gCode.println(getNum() + " G01 " + " X" + endX + " Y" + endY); //Cutting section
	}
	
	public void drawRectangle(double x, double y, double height, double width, double depth)
	{
		goTo(x,y);
		cutDown(depth);
		drawLine(x, y, width, depth, 'R');
		drawLine(x+width, y, height, depth, 'D');
		drawLine(x+width, y-height, width, depth, 'L');
		drawLine(x, y-height, height, depth, 'U');
	}
	
	public void drawRectangleFill(double x, double y, double height, double width, double depth)
	{
		for(double i = 0; i <= width; i += (bitSize-0.01))
		{
			drawRectangle(x+i, y-i, height-i, width-i, depth);
		}
	}
	
	public void closeCode()
	{
		gCode.close();
	}
}
