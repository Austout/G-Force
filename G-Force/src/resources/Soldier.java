package resources;

import org.newdawn.slick.Animation;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Soldier extends MOB{
	private Vector2f position;
	private Vector2f size = new Vector2f(24, 50);
	private Vector2f xCoords;
	private float SPEED = 0.3f;
	private static final float GRAVITY = 0.5f;
	private int timeSinceLastBullet = 0;
	private boolean isWaveMovement = false;
	private static Animation animLeft = null,animRight;
	public Soldier(int posX, int posY, int Xmin, int Xmax) {
		position = new Vector2f(posX, posY);
		xCoords = new Vector2f(Xmin, Xmax);
		if(animLeft == null){
			animLeft = new Animation();
			animRight = new Animation();
			try {
				Image turret = new Image("res/Solder.png");
				animLeft.addFrame(turret.getSubImage(0, 0, 24, 50), 500);
				animLeft.addFrame(turret.getSubImage(24, 0, 24, 50), 500);
				animLeft.addFrame(turret.getSubImage(0, 0, 24, 50), 500);
				animLeft.addFrame(turret.getSubImage(48, 0, 24, 50), 500);
				animRight.addFrame(turret.getSubImage(0, 0, 24, 50).getFlippedCopy(true, false), 500);
				animRight.addFrame(turret.getSubImage(24, 0, 24, 50).getFlippedCopy(true, false),500);
				animRight.addFrame(turret.getSubImage(0, 0, 24, 50).getFlippedCopy(true, false), 500);
				animRight.addFrame(turret.getSubImage(48, 0, 24, 50).getFlippedCopy(true, false), 500);
			} catch (SlickException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void render(Graphics g) {
		if(useLeft){
			g.drawAnimation(animLeft, position.x, position.y);
		}else{
			g.drawAnimation(animRight, position.x, position.y);
		}
	}
	private boolean useLeft = false;
	public void update(int delta) {
		animLeft.update(delta);
		animRight.update(delta);
		useLeft = SPEED > 0;
		Vector2f newPosition = new Vector2f(position);
		newPosition.y += GRAVITY;
		int directionOfCollision = Physics.isCollidingMobWave(newPosition, size);
		if (directionOfCollision != 2) {// 2 means no collision
			newPosition.x += directionOfCollision;
			newPosition.y += directionOfCollision == 0 ? -1 : 0;
			isWaveMovement = true;
		} else if (isBetween(position.x, xCoords.x, xCoords.y)) {
			newPosition.x += SPEED;
			isWaveMovement = false;
		} else {
			SPEED = -SPEED;
			newPosition.x += SPEED;
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
		Vector2f possibleCoords = Physics.isCollingMobGround(new Vector2f(position.x, newPosition.y), size);
		if (possibleCoords.x == possibleCoords.y) {
			position.y = newPosition.y;
		} else if (possibleCoords.x != xCoords.x && possibleCoords.y != xCoords.y 
				&& (possibleCoords.x != 0 || possibleCoords.y != 0)) {
			xCoords = new Vector2f(possibleCoords.x, possibleCoords.y);
		}
		timeSinceLastBullet += delta;
		if (timeSinceLastBullet > 600) {
			timeSinceLastBullet = 0;
			Vector2f playerPosition = Player.getPosition();
			if (playerPosition.x < position.x && Physics.isBetween(playerPosition.x, xCoords.x,xCoords.y)
					&& Physics.isBetween(playerPosition.y, position.y - size.y*2,xCoords.y + size.y)) {
				super.addBullet(new Bullet(position.x, position.y + size.y/2, -1, 0));
			} else if (playerPosition.x > position.x && Physics.isBetween(playerPosition.x, xCoords.x,xCoords.y)
					&& Physics.isBetween(playerPosition.y, position.y - size.y*2,xCoords.y + size.y)) {
				super.addBullet(new Bullet(position.x, position.y + size.y/2, 1, 0));
			}
			// Vector2f trianglexy = new Vector2f(playerPosition.x -position.x , playerPosition.y -position.y);
			// float angle = (float) Math.atan(trianglexy.x/trianglexy.y);
			// System.out.println(Math.toDegrees(angle));
			// float zSpeed = (float) (Math.sqrt((trianglexy.x * trianglexy.x) + (trianglexy.y * trianglexy.y)) / 1000f);
			// bullets.add(new Bullet(position.x,position.y,(float)(Math.cos(angle) * zSpeed),(float)(Math.sin(angle) * zSpeed)));
		}

	}

	public Vector2f getPosition() {
		return position;
	}

	public Vector2f getSize() {
		return size;
	}

	private static boolean isBetween(float point, float y, float f) {
		if (point > y && point < f) {
			return true;
		}
		return false;
	}
}
