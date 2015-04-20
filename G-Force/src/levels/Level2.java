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
import resources.Soldier;
import resources.Spike;

public class Level2 extends BasicGameState{
	private final int ID;
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private ArrayList<MOB> MOBS = new ArrayList<MOB>();
	private ArrayList<Spike> spikes = new ArrayList<Spike>();
	public Level2(int ID){
		this.ID = ID;//ID = 4
	}
	@Override
	public void enter(GameContainer gc, StateBasedGame sbg){
		Player.clearWaves();
		platforms = new ArrayList<Platform>();
		MOBS = new ArrayList<MOB>();
		spikes = new ArrayList<Spike>();
		MOB.clearArray();
		spikes.add(new Spike(20,320,60,60));
		spikes.get(0).setRotation(90);
		MOBS.add(new Soldier(700,100,200,585));
		MOBS.add(new Soldier(700,150,200,585));
		MOBS.add(new Soldier(100,225,200,585));
		MOBS.add(new Soldier(700,325,200,585));
		platforms.add(new Platform(0,0,20,400));
		platforms.add(new Platform(800,0,20,400));
		platforms.add(new Platform(0,100,700,20));
		platforms.add(new Platform(100,200,700,20));
		platforms.add(new Platform(0,300,700,20));
		platforms.add(new Platform(1,380,800,20));

		LevelSelect.setLevel(ID);
		Player.load();
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
		GeneralLevelMechanics.render(g);
		for(Spike spike : spikes){
			spike.render(g);
		}
	}
	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		GeneralLevelMechanics.update(delta,sbg,gc);
		if(MOBS.size() == 0){
			sbg.enterState(ID+1);
		}
		for(MOB mob : MOBS){
			mob.update(delta);
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
