package resources;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Button {
	private Vector2f position;
	private Vector2f size;
	private Color color1,color2;
	private boolean isHovering = false;
	private final int ID;
	private String text;
	private int xOffSet;
	public Button(int posX, int posY, int Width, int Height, Color color1, Color color2, int ID, String text, int xOffSet){
		position = new Vector2f(posX,posY);
		size = new Vector2f(Width, Height);
		this.color1 = color1;
		this.color2 = color2;
		this.ID = ID;
		this.text = text;
		this.xOffSet = xOffSet;
	}
	public void render(Graphics g){
		if(isHovering)
			g.setColor(color1);
		else 
			g.setColor(color2);
		g.fillRect(position.x, position.y, size.x, size.y);
		g.setColor(Color.black);
		g.drawString(text, position.x + xOffSet, position.y);
		g.setColor(Color.white);
	}
	public int checkMousePosition(int x , int y){
		if(IsBetween(x,position.x, position.x + size.x) && IsBetween(y,position.y, position.y + size.y)){
			isHovering = true;
			return ID; //to be use in conjunction with Mouse.isDown
		}
		isHovering = false;
		return -1;
	}
	private boolean IsBetween(float point,float y, float f) {
		if(point > y && point < f){
			return true;
		}
		return false;
	}
}
