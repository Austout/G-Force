package resources;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Platform {
	private Vector2f position;
	private Vector2f size, xCoords; 
	private float SPEED; 
	private static Image stone, stoneEnd;
	private Vector2f[] xPositions;
	private Vector2f[] xPositionsEnds = {new Vector2f(-100,0),new Vector2f(-100,0)};
	public Platform(int posX, int posY, int width, int height){
		position = new Vector2f(posX, posY);
		size = new Vector2f(width,height);
		SPEED = 0;
		xCoords = new Vector2f(0,0);
		if(width > height){
			setPositionsWidth(width);
		}else{
			setPositionsHeight(height);
		}
		try {
			if(stone == null){
				stone = new Image("res/Stone.png");
				stoneEnd = new Image("res/stoneEnd.png");
			}
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
	private void setPositionsWidth(int width){
		double x = width / 20f;
		double xRounded = Math.floor(x);
		if(x == xRounded){
			xPositions = new Vector2f[(int) xRounded];
			for(int i = 0; i < xRounded;i++){//no need for +1 because it will be more than number for example 5.4
				xPositions[i] = new Vector2f(i*20f, 0);
			}
		}else if(x == xRounded + 0.5f){
			xPositions = new Vector2f[(int) xRounded];
			xPositionsEnds[0] = new Vector2f(0,0);
			xPositionsEnds[1] = new Vector2f( width-5f,0);
			for(int i = 0; i < xRounded;i++){//no need for +1 because it will be more than number for example 5.4
				xPositions[i] = new Vector2f(i*20f + 5f, 0);
			}
		}
	}
	private void setPositionsHeight(int height){
		double x = height / 20f;
		double xRounded = Math.floor(x);
		if(x == xRounded){
			xPositions = new Vector2f[(int) xRounded];
			for(int i = 0; i < xRounded;i++){//no need for +1 because it will be more than number for example 5.4
				xPositions[i] = new Vector2f(0, i*20f);
			}
		}
	}
	public Platform(int posX, int posY, int width, int height, float SPEED, int xMin,int xMax){
		position = new Vector2f(posX, posY);
		size = new Vector2f(width,height);
		xCoords = new Vector2f(xMin,xMax);
		this.SPEED = SPEED;
		if(width > height){
			setPositionsWidth(width);
		}else{
			setPositionsHeight(height);
		}	}
	public void render(Graphics g){
		//g.setColor(Color.white);
		for(int i = 0; i < xPositionsEnds.length; i++){
			if(xPositionsEnds[i].x != -100){
				g.drawImage(stoneEnd, xPositionsEnds[i].x + position.x, position.y);
			}
		}	
		for(int i = 0; i < xPositions.length; i++){
			g.drawImage(stone, xPositions[i].x + position.x, xPositions[i].y + position.y);
		}		
		//g.fillRect(position.x, position.y, size.x, size.y);
	}
	public void update(int delta){
		position.x += SPEED;
		if(position.x < xCoords.x || position.x > xCoords.y){
			SPEED = -SPEED;
		}
	}
	public Vector2f getPosition() {
		return position;
	}
	public Vector2f getSize() {
		return size;
	}
}
