package gameSetup;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import resources.Button;

@SuppressWarnings("deprecation")
public class MainMenu extends BasicGameState{
	private final int ID;
	MainMenu(int ID){
		this.ID = ID;
	}
	TrueTypeFont uFont;
	private ArrayList<Button> buttons = new ArrayList<Button>();
	@Override
	public void init(GameContainer arg0, StateBasedGame arg1) throws SlickException {
		Font awtFont = new Font("Times New Roman", Font.BOLD, 50);
	    uFont = new TrueTypeFont(awtFont, false);
		//buttons.add(new Button(275, 295, 250,30,Color.cyan,Color.white, 2, "Options",95));		

	}
	@Override 
	public void enter(GameContainer gc, StateBasedGame sbg){
		buttons = new ArrayList<Button>();
		buttons.add(new Button(275, 250, 250,30,Color.cyan,Color.white, 1, "LevelSelect",75));
		buttons.add(new Button(275, 200, 250,30,Color.cyan,Color.white, LevelSelect.getHighestLevel(), "Play",105));
		
	}
	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		for(Button button : buttons){
			button.render(g);
		}
		g.setColor(Color.cyan);
		g.setFont(uFont);
		g.drawString("G-Force", 315, 60);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		Input input = gc.getInput();
		for(Button button : buttons){
			int stateID = button.checkMousePosition(input.getMouseX(), input.getMouseY());
			if(stateID != -1 && input.isMouseButtonDown(0)){
				sbg.enterState(stateID);
			}
		}
	}

	@Override
	public int getID() {
		return ID;
	}

	

}
