package resources;

import java.util.ArrayList;

import org.newdawn.slick.geom.Vector2f;

public class Physics {
	private static ArrayList<Platform> platforms = new ArrayList<Platform>();
	private static ArrayList<ShockWave> waves = new ArrayList<ShockWave>();
	private static ArrayList<Spike> spikes = new ArrayList<Spike>();
	public static boolean isPlayColliding(Vector2f newPosition, Vector2f size) {
		for(Platform platform : platforms){
			if(isColliding(platform,newPosition,size)){
				return true;
			}
		}
		return false;
	}

	private static boolean isColliding(Platform platform, Vector2f newPosition, Vector2f size) {
		if(isBetween(newPosition.x, platform.getPosition().x, platform.getPosition().x + platform.getSize().x) ||
				isBetween(newPosition.x + size.x, platform.getPosition().x, platform.getPosition().x + platform.getSize().x)){
			if(isBetween(newPosition.y, platform.getPosition().y, platform.getPosition().y + platform.getSize().y) || 
					isBetween(newPosition.y + size.y, platform.getPosition().y, platform.getPosition().y + platform.getSize().y)){
				return true;
			}
			if(isBetween(platform.getPosition().y, newPosition.y, newPosition.y + size.y) || 
					isBetween(platform.getPosition().y + platform.getSize().y, newPosition.y, newPosition.y + size.y)){
				return true;
			}
		}
		if(isBetween(platform.getPosition().x,newPosition.x, newPosition.x + size.x) ||
				isBetween(platform.getPosition().x + platform.getSize().x, newPosition.x, newPosition.x + size.x)){
			if(isBetween(newPosition.y, platform.getPosition().y, platform.getPosition().y + platform.getSize().y) || 
					isBetween(newPosition.y + size.y, platform.getPosition().y, platform.getPosition().y + platform.getSize().y)){
				return true;
			}
			if(isBetween(platform.getPosition().y, newPosition.y, newPosition.y + size.y) || 
					isBetween(platform.getPosition().y + platform.getSize().y, newPosition.y, newPosition.y + size.y)){
				return true;
			}
		}
		return false;
	}
	public static void setPlatforms(ArrayList<Platform> platforms){
		Physics.platforms = platforms;
	}
	public static void setWaves(ArrayList<ShockWave> wave){
		waves = wave;
	}
	public static boolean isBetween(float point,float y, float f) {
		if(point > y && point < f){
			return true;
		}
		return false;
	}
	public static boolean isCollidingMob(Vector2f newPosition, Vector2f size){
//		for(Spike spike : spikes){
//			if(isColliding(spike.getPosition(),spike.getSize(),newPosition,size)){
//				return true;
//			}
//		}
		for(Platform platform : platforms){
			if(isColliding(platform,newPosition,size)){
				return true;
			}
		}
		return false;
	}
	public static int isCollidingMobWave(Vector2f position, Vector2f size) {
		for(ShockWave wave : waves){
			if(isColliding(wave.getPosition(),wave.getSize(),position,size)){
				return wave.getDirection();
			}
		}
		return 2;
	}

	public static boolean isColliding(Vector2f position, Vector2f size, Vector2f position2, Vector2f size2) {
		if(isBetween(position2.x, position.x, position.x + size.x) ||
				isBetween(position2.x + size.x, position.x, position.x + size.x)){
			if(isBetween(position2.y, position.y, position.y + size.y) || 
					isBetween(position2.y + size.y, position.y, position.y + size.y)){
				return true;
			}
			if(isBetween(position.y, position2.y, position2.y + size2.y) || 
					isBetween(position.y + size.y, position2.y, position2.y + size2.y)){
				return true;
			}
		}
		if(isBetween(position.x,position2.x, position2.x + size2.x) ||
				isBetween(position.x + size.x, position2.x, position2.x + size2.x)){
			if(isBetween(position2.y, position.y, position.y + size.y) || 
					isBetween(position2.y + size.y, position.y, position.y + size.y)){
				return true;
			}
			if(isBetween(position.y, position2.y, position2.y + size2.y) || 
					isBetween(position.y + size.y, position2.y, position2.y + size2.y)){
				return true;
			}
		}
		return false;
	}

	public static Vector2f isCollingMobGround(Vector2f vector2f, Vector2f size) {
		for(Platform platform : platforms){
			if(isColliding(platform,vector2f,size)){
				return new Vector2f(platform.getPosition().x , platform.getPosition().x + platform.getSize().x - size.x);
			}
		}		
		return new Vector2f(0,0);
	}

	public static void setSpikes(ArrayList<Spike> spikes2) {
		spikes = spikes2;
	}

	public static boolean isCollidingSpike(Vector2f newPosition, Vector2f size) {
		for(Spike spike : spikes){
			if(isColliding(newPosition,size,spike.getPosition(),spike.getSize())){
				return true;
			}
		}
		return false;
	}
	
}
