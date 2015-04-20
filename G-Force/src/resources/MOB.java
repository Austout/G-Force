package resources;

import java.util.ArrayList;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public abstract class MOB {
	private static ArrayList<Bullet> bullets = new ArrayList<Bullet>();
	public abstract void render(Graphics g);
	public abstract void update(int delta);

	public abstract Vector2f getPosition();

	public static ArrayList<Bullet> getBullets() {
		return bullets;
	}

	public abstract Vector2f getSize();

	public static void removeBullet(int i) {
		bullets.remove(i);
	}

	public static void clearArray() {
		bullets = new ArrayList<Bullet>();		
	}
	public static void addBullet(Bullet bullet){
		bullets.add(bullet);
	}
}
