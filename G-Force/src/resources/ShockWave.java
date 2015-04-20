package resources;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class ShockWave {
	private Vector2f position;
	private Vector2f size = new Vector2f(5, 50);
	private Vector2f speed;
	private int direction;
	private static Image ShockWaveRight = null;
	private static Image ShockWaveLeft = null;
	public ShockWave(float posX, float posY, int direction) {
		position = new Vector2f(posX, posY);
		speed = new Vector2f(direction, 0);
		this.direction = direction;
		
		if(ShockWaveRight == null){
			try {
				ShockWaveRight = new Image("res/ShockWave.png");
				ShockWaveLeft = new Image("res/ShockWave.png");
				ShockWaveLeft.rotate(180);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}

	public void render(Graphics g) {
		g.setColor(Color.cyan);
		if (direction == 1)
			g.drawImage(ShockWaveRight, position.x, position.y);
		else
			g.drawImage(ShockWaveLeft, position.x, position.y);
		
	}

	public void update(int delta) {
		position.x += speed.x;
		position.y += speed.y;
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getSize() {
		return size;
	}

	public int getDirection() {
		return direction;
	}
}
