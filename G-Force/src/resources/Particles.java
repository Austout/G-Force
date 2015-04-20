package resources;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.geom.Vector2f;

public class Particles {
	private ArrayList<Particle> particles = new ArrayList<Particle>();
	public Particles(int amount, float xPos,float yPos,Color color){
		for(int i =0; i < amount; i++){
			particles.add(new Particle(xPos,yPos,(float)(Math.random()/15f),(float)(Math.random()/15f), color));
		}
	}
	public void addParticle(Particle particle){
		particles.add(particle);
	}
	public boolean end = false;
	public void update(int delta){
		if(particles.get(0).duration < 0){
			end = true;
		}
		for(int i = 0; i < particles.size(); i++){
			particles.get(i).update(delta);
		}
	}
	public void render(Graphics g){
		for(Particle particle : particles){
			particle.render(g);
		}
	}
	public class Particle{
		public Vector2f position,speed,size = new Vector2f(3,3);
		public Color color;
		public int duration = 500;
		Particle(float xPos, float yPos, float xSpeed, float ySpeed, Color color){
			position = new Vector2f(xPos,yPos);
			speed = new Vector2f(xSpeed, ySpeed);
			this.color = color;
		}
		public void render(Graphics g){
			g.setColor(color);
			g.fillRect(position.x, position.y, size.x, size.y);
		}
		public void update(int delta){
			duration -= delta;
			Vector2f newPosition = new Vector2f(position);
			newPosition.x += speed.x;
			newPosition.y += speed.y;
			if(!Physics.isPlayColliding(new Vector2f(newPosition.x, position.y), size)){
				position.x = newPosition.x;
			}
			if(!Physics.isPlayColliding(new Vector2f(position.x, newPosition.y), size)){
				position.y = newPosition.y;
			}
		}
	}
}
