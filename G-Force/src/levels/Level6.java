package levels;

import gameSetup.LevelSelect;

import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.MOB;
import resources.Particles;
import resources.Physics;
import resources.Platform;
import resources.Player;
import resources.Spike;
import resources.Turret;

public class Level6 extends BasicGameState {
	private final int ID;
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private ArrayList<MOB> MOBS = new ArrayList<MOB>();
	private ArrayList<Spike> spikes = new ArrayList<Spike>();
	public Level6(int ID){
		this.ID = ID;//ID = 8
	}
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		Player.clearWaves();
		LevelSelect.setLevel(ID);
		platforms = new ArrayList<Platform>();
		MOBS = new ArrayList<MOB>();
		MOB.clearArray();
		spikes.add(new Spike(0,320,50,80));

		spikes.add(new Spike(750,320,50,80));
		
		platforms.add(new Platform(150,380,500,20));//ground

		platforms.add(new Platform(50,300,150,20));
		MOBS.add(new Turret(100,250));
		
		platforms.add(new Platform(50,200,150,20));
		MOBS.add(new Turret(100,150));

		platforms.add(new Platform(50,100,150,20));
		MOBS.add(new Turret(100,50));

		platforms.add(new Platform(600,300,150,20));
		MOBS.add(new Turret(700,250));
		
		platforms.add(new Platform(600,200,150,20));
		MOBS.add(new Turret(700,150));

		platforms.add(new Platform(600,100,150,20));
		MOBS.add(new Turret(700,50));
		
		Player.load(400,150);
		Physics.setSpikes(spikes);
		Physics.setPlatforms(platforms);
		GeneralLevelMechanics.clear();
	}
	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		GeneralLevelMechanics.renderBackground(g);
		for(Platform platform : platforms){
			platform.render(g);
		}
		for(MOB mob : MOBS){
			mob.render(g);
		}
		for(Spike spike : spikes){
			spike.render(g);
		}	
		GeneralLevelMechanics.render(g);
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		GeneralLevelMechanics.update(delta,sbg,gc);
		if(MOBS.size() == 0){
			sbg.enterState(ID+1);
		}
		for(int i = 0; i < MOBS.size(); i++){
			MOBS.get(i).update(delta);
			if(MOBS.get(i).getPosition().y > 400){
				MOBS.remove(i);
				i--;
			}
		}
		for(Spike spike : spikes){
			spike.update(delta);
			if(Physics.isColliding(Player.getPosition(), Player.getSize(), spike.getPosition(), spike.getSize())){
				GeneralLevelMechanics.killPlayer(Player.getPosition().x, Player.getPosition().y);
			}
			for(int i = 0; i < MOBS.size(); i++){
				if(Physics.isColliding(MOBS.get(i).getPosition(), MOBS.get(i).getSize(), spike.getPosition(), spike.getSize())){
					GeneralLevelMechanics.addParticles(new Particles(15,MOBS.get(i).getPosition().x,MOBS.get(i).getPosition().y, Color.red));
					MOBS.remove(i);//loops through spike(s) and mobs to see if any of either are colliding then removes that mob
					i--;
				}
			}
			
		}
	}
	@Override
	public int getID() {
		return ID;
	}
}
