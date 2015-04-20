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
import resources.Soldier;

public class Level1 extends BasicGameState {
	private final int ID;
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	private ArrayList<MOB> MOBS = new ArrayList<MOB>();
	public Level1(int ID) {
		this.ID = ID;// ID = 3
	}

	@Override
	public void enter(GameContainer gc, StateBasedGame sbg) {
		platforms = new ArrayList<Platform>();
		MOBS = new ArrayList<MOB>();
		MOB.clearArray();
		Player.clearWaves();
		MOBS.add(new Soldier(210, 320, 175, 585));
		MOBS.add(new Soldier(210, 100, 175, 585));
		platforms.add(new Platform(-20, 0, 20, 300));
		platforms.add(new Platform(800, 0, 20, 300));
		platforms.add(new Platform(600, 300, 200, 20));
		platforms.add(new Platform(0, 300, 200, 20));
		platforms.add(new Platform(0, 100, 100, 20));
		platforms.add(new Platform(700, 100, 100, 20));
		platforms.add(new Platform(200, 380, 400, 20));
		platforms.add(new Platform(200, 200, 400, 20));
		LevelSelect.setLevel(ID);
		Player.load();
		Physics.setPlatforms(platforms);
		GeneralLevelMechanics.clear();
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {

	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		GeneralLevelMechanics.renderBackground(g);
		for (Platform platform : platforms) {
			platform.render(g);
		}
		for (MOB mob : MOBS) {
			mob.render(g);
		}
		GeneralLevelMechanics.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		GeneralLevelMechanics.update(delta,sbg,gc);
		if (MOBS.size() == 0) {
			sbg.enterState(ID + 1);
		}
		for (int i = 0; i < MOBS.size(); i++) {
			MOBS.get(i).update(delta);
			if (MOBS.get(i).getPosition().x > 800 || MOBS.get(i).getPosition().x < 0 || MOBS.get(i).getPosition().y > 400 || MOBS.get(i).getPosition().y < 0) {
				MOBS.remove(i);
				i--;
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}
}
