package resources;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Turret extends MOB{
	private Vector2f position;
	private Vector2f size = new Vector2f(12, 50);
	private float SPEED = 0.3f;
	private static final float GRAVITY = 0.5f;
	private int timeSinceLastBullet = 0;
	private boolean isWaveMovement = false;
	private static Animation animLeft = null,animRight;
	public Turret(int posX, int posY) {
		position = new Vector2f(posX, posY);
		if(animLeft == null){
			animLeft = new Animation();
			animRight = new Animation();
			try {
				Image turret = new Image("res/Turret.png");
				animLeft.addFrame(turret.getSubImage(0, 0, 50, 50), 250);
				animLeft.addFrame(turret.getSubImage(50, 0, 50, 50), 250);
				animLeft.addFrame(turret.getSubImage(100, 0, 50, 50), 250);
				animLeft.addFrame(turret.getSubImage(150, 0, 50, 50), 250);
				animRight.addFrame(turret.getSubImage(0, 0, 50, 50).getFlippedCopy(true, false), 250);
				animRight.addFrame(turret.getSubImage(50, 0, 50, 50).getFlippedCopy(true, false), 250);
				animRight.addFrame(turret.getSubImage(100, 0, 50, 50).getFlippedCopy(true, false), 250);
				animRight.addFrame(turret.getSubImage(150, 0, 50, 50).getFlippedCopy(true, false), 250);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	private boolean useLeft;
	public void render(Graphics g) {
		g.setColor(Color.lightGray);
		if(useLeft)
			g.drawAnimation(animLeft, position.x, position.y);
		else
			g.drawAnimation(animRight, position.x, position.y);

	}
	public void update(int delta) {
		animLeft.update(delta);
		animRight.update(delta);
		Vector2f playerPosition = Player.getPosition();
		useLeft = playerPosition.x < position.x;// end of anim stuff
		Vector2f newPosition = new Vector2f(position);
		newPosition.y += GRAVITY;
		int directionOfCollision = Physics.isCollidingMobWave(newPosition, size);
		if (directionOfCollision != 2) {// 2 means no collision
			newPosition.x += directionOfCollision;
			newPosition.y += directionOfCollision == 0 ? -1 : 0;
			isWaveMovement = true;
		} else{
			isWaveMovement = false;
		}
		if (!Physics.isCollidingMob(new Vector2f(newPosition.x, position.y), size)) {
				if(Physics.isCollidingSpike(new Vector2f(newPosition.x, position.y), size) && isWaveMovement){
					position.x = newPosition.x;
				}else if(!Physics.isCollidingSpike(new Vector2f(newPosition.x, position.y), size)){
					position.x = newPosition.x;
				}else{
					SPEED = -SPEED;
				}
		}else{
			SPEED = -SPEED;
		}
		if (!Physics.isCollidingMob(new Vector2f(position.x, newPosition.y), size)) {
			position.y = newPosition.y;
		}
		timeSinceLastBullet += delta;
		if (timeSinceLastBullet > 1000) {
			timeSinceLastBullet = 0;
			if (playerPosition.x < position.x) {
				super.addBullet(new Bullet(position.x, position.y + size.y/2, -1, 0));
			} else if (playerPosition.x > position.x) {
				super.addBullet(new Bullet(position.x, position.y + size.y/2, 1, 0));
			}
		}

	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getSize() {
		return size;
	}

}
