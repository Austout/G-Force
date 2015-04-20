package resources;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Bullet {
	private Vector2f position;
	private Vector2f size = new Vector2f(7, 7);
	private Vector2f speed;
	private static Animation animRight,animLeft;
	private static boolean initiazlized = false;

	public Bullet(float posX, float posY, float speedX, float speedY) {
		position = new Vector2f(posX, posY);
		speed = new Vector2f(speedX, speedY);
		if(!initiazlized){
			try {
				Image image = new Image("res/Bullet.png");
				animRight = new Animation();
				animLeft = new Animation();
				animRight.addFrame(image.getSubImage(0, 0, 10, 10), 3);
				animRight.addFrame(image.getSubImage(10, 0, 10, 10), 3);
				animLeft.addFrame(rotate(image.getSubImage(0, 0, 10, 10)), 3);
				animLeft.addFrame(rotate(image.getSubImage(10, 0, 10, 10)), 3);
				} catch (SlickException e) {
				e.printStackTrace();
			}
			initiazlized = true;
			animRight.start();
			animLeft.start();
		}
	}

	private Image rotate(Image subImage) {
		subImage.rotate(180);
		return subImage;
	}

	public void render(Graphics g) {
		if(speed.x > 0){
			g.drawAnimation(animRight, position.x, position.y);
		}else{
			g.drawAnimation(animLeft, position.x, position.y);

		}
	}

	public void update(int delta) {
		position.x += speed.x;
		position.y += speed.y;
		animRight.update(delta);
		animLeft.update(delta);
	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getSize() {
		return size;
	}
}
