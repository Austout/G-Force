package resources;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Spike {
	private Vector2f position;
	private Vector2f size = new Vector2f(50, 50);
	private static Image spikeImage = null;
	private float[] positions;
	private float posToRender;
	private float rotation = 0;
	public Spike(float posX, float posY, float sizeX, float sizeY) {
		position = new Vector2f(posX, posY);
		size = new Vector2f(sizeX, sizeY);
		posToRender = sizeY - 40 + posY;
		double x = sizeX / 35f;
		double xRounded = Math.floor(x);
		if(x != xRounded){
			double xDiff = x - xRounded;
			xDiff = xDiff * 35 / 2;
			positions = new float[(int) xRounded];
			for(int i = 0; i < xRounded;i++){//no need for +1 because it will be more than number for example 5.4
				positions[i] = (float) (xDiff + i*35f);
			}
		}
		if(spikeImage == null){
			try {
				spikeImage = new Image("res/Spikes.png");
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	public void setRotation(float rotation){
		this.rotation = rotation;
	}
	public void render(Graphics g) {
		g.rotate(position.x + (size.x/2f), position.y + (size.y/2f), rotation);
		for(int i = 0; i < positions.length; i++){
			g.drawImage(spikeImage, positions[i] + position.x, posToRender);
		}
		g.rotate(position.x - size.x/2, position.y - size.y/2, -rotation);
	}

	public void update(int delta) {
		
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getSize() {
		return size;
	}
}
