package levels;

import gameSetup.LevelSelect;

import java.util.ArrayList;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.MOB;
import resources.Physics;
import resources.Platform;
import resources.Player;
import resources.Spike;
import resources.Turret;

public class Level7 extends BasicGameState {
	private final int ID;
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private ArrayList<MOB> MOBS = new ArrayList<MOB>();
	private ArrayList<Spike> spikes = new ArrayList<Spike>();
	public Level7(int ID){
		this.ID = ID;//ID = 9
	}
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		Player.clearWaves();
		LevelSelect.setLevel(ID);
		platforms = new ArrayList<Platform>();
		MOBS = new ArrayList<MOB>();
		MOB.clearArray();
		
		platforms.add(new Platform(350,190,100,20));
		platforms.add(new Platform(350,190,100,20,0.4f,100,700));
		
		platforms.add(new Platform(350,290,100,20));
		platforms.add(new Platform(650,290,100,20,0.4f,100,700));

		platforms.add(new Platform(650,100,150,20));
		MOBS.add(new Turret(750,50));
		platforms.add(new Platform(650,380,150,20));
		MOBS.add(new Turret(750,330));
		
		platforms.add(new Platform(0,100,150,20));
		MOBS.add(new Turret(50,50));
		platforms.add(new Platform(0,380,150,20));
		MOBS.add(new Turret(50,330));


		
		Player.load(400,100);
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
		for(Platform platform : platforms){
			platform.update(delta);
		}
		Physics.setPlatforms(platforms);
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
			for(int i = 0; i < MOBS.size(); i++){
				if(Physics.isColliding(MOBS.get(i).getPosition(), MOBS.get(i).getSize(), spike.getPosition(), spike.getSize())){
					MOBS.remove(i);//loops through spike(s) and mobs to see if any of either are colliding then removes that mob
					i--;
				}
			}
			
		}
		GeneralLevelMechanics.update(delta,sbg,gc);
	}
	@Override
	public int getID() {
		return ID;
	}
}
