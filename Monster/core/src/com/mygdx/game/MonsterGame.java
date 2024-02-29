package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.ScreenUtils;

import java.util.Random;

public class MonsterGame extends ApplicationAdapter {

	ShapeRenderer sr;
	Monster monster;
	ActualMonster actualMonster;
	Block block[][] = new Block[15][15];
	Block gold[][] = new Block[15][15];
	Random rand1 = new Random();
	Random rand2 = new Random();
	float monstermovementCooldown = 1.2f;
	float timesincelastmonsterMove = 0;
	boolean alive=true;
	public SpriteBatch batch;
	public BitmapFont font;
	int coins=0;
	int randomcoin;
    int level=1;


	@Override
	public void create() {
		sr = new ShapeRenderer();
		batch = new SpriteBatch();
		font = new BitmapFont();
		int x = 0;
		int y = Gdx.graphics.getHeight()-Gdx.graphics.getHeight()/15;
		int w = Gdx.graphics.getWidth()/15;
		int h = Gdx.graphics.getHeight()/15;





		monster = new Monster(0, 0, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/15, 5, 5, Color.ROYAL);
		actualMonster = new ActualMonster(Gdx.graphics.getWidth()/2-Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2-Gdx.graphics.getHeight()/30, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/15, 5, 5, Color.FOREST);




		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				block[i][j] = new Block(x, y, w, h, Color.SLATE);
				x= x + Gdx.graphics.getWidth()/15;

			}
			y = y - Gdx.graphics.getHeight()/15;
			x=0;
		}
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				randomcoin = MathUtils.random(1,20);
				if (randomcoin==10){
					gold[i][j] = new Block(x, y+Gdx.graphics.getHeight()/15, w, h, Color.GOLD);
					coins++;
				}
				else{
					gold[i][j] = new Block(x, y+Gdx.graphics.getHeight()/15, w, h, Color.CLEAR);
				}
				x= x + Gdx.graphics.getWidth()/15;
			}
			y = y + Gdx.graphics.getHeight()/15;
			x=0;
		}
	}
	public void blockdraw() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				block[i][j].draw(sr);
			}
		}
	}

	public void golddraw() {
		for (int i = 0; i < 15; i++) {
			for (int j = 0; j < 15; j++) {
				gold[i][j].draw(sr);
			}
		}
	}


	public void coinPickup(){
		int j = monster.getX()/53;
		int i = monster.getY()/53;
		if(gold[i][j].getCol()==Color.GOLD){
			gold[i][j] = new Block(monster.getX(), monster.getY(), 53, 53, Color.CLEAR);
			coins=coins-1;
		}
	}

	public void newLevel(){
		int x = 0;
		int y = 0-Gdx.graphics.getHeight()/15;
		int w = Gdx.graphics.getWidth()/15;
		int h = Gdx.graphics.getHeight()/15;
		if (coins==0){
            level++;
            monstermovementCooldown = monstermovementCooldown - 0.1f;
			monster = new Monster(0, 0, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/15, 5, 5, Color.ROYAL);
			actualMonster = new ActualMonster(Gdx.graphics.getWidth()/2-Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2-Gdx.graphics.getHeight()/30, Gdx.graphics.getWidth()/15, Gdx.graphics.getHeight()/15, 5, 5, Color.FOREST);
			for (int i = 0; i < 15; i++) {
				for (int j = 0; j < 15; j++) {
					randomcoin = MathUtils.random(1,20);
					if (randomcoin==10){
						gold[i][j] = new Block(x, y+Gdx.graphics.getHeight()/15, w, h, Color.GOLD);
						coins++;
					}
					else{
						gold[i][j] = new Block(x, y+Gdx.graphics.getHeight()/15, w, h, Color.CLEAR);
					}

					x= x + Gdx.graphics.getWidth()/15;

				}
				y = y + Gdx.graphics.getHeight()/15;
				x=0;
			}
		}
	}

	public void Death(){
		if (monster.getX()==actualMonster.getX()&monster.getY()== actualMonster.getY()){
			alive=false;
		}
	}




		@Override
		public void render () {
			ScreenUtils.clear(0, 0, 0, 1);
			float deltaTime = Gdx.graphics.getDeltaTime();
			sr.begin(ShapeRenderer.ShapeType.Filled);
			if(alive) {
				Death();
				monsterMovement(deltaTime);
				playerMovement();
				newLevel();
				golddraw();
				monster.draw(sr);
				actualMonster.draw(sr);
				coinPickup();
			}
			else{
				ScreenUtils.clear(0,0,0,1);
				batch.begin();
				font.draw(batch,"You died",Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2);
                font.draw(batch,"You reached Level "+level,Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight()/2-100);
				batch.end();
			}
            sr.end();
            Gdx.gl.glLineWidth(4);
            sr.begin(ShapeRenderer.ShapeType.Line);
            blockdraw();
            sr.end();



		}
		public void monsterMovement(float delta){
			timesincelastmonsterMove+=delta;
			if(timesincelastmonsterMove>=monstermovementCooldown) {
				if(monster.getY()>actualMonster.getY()&&(actualMonster.getY()+Gdx.graphics.getHeight()/15!=Gdx.graphics.getHeight())){
					actualMonster.setY(actualMonster.getY()+Gdx.graphics.getHeight()/15);
				}
				if(monster.getY()<actualMonster.getY()&&(actualMonster.getY()-Gdx.graphics.getHeight()/15!=0-actualMonster.getH())){
					actualMonster.setY(actualMonster.getY()-Gdx.graphics.getHeight()/15);
				}
				if(monster.getX()>actualMonster.getX()&&(actualMonster.getX()+Gdx.graphics.getWidth()/15!=Gdx.graphics.getWidth())){
					actualMonster.setX(actualMonster.getX()+Gdx.graphics.getWidth()/15);
				}
				if(monster.getX()<actualMonster.getX()&&(actualMonster.getX()-Gdx.graphics.getWidth()/15!=0-actualMonster.getW())){
					actualMonster.setX(actualMonster.getX()-Gdx.graphics.getWidth()/15);
				}
				if(monster.getY()>actualMonster.getY()&&(actualMonster.getY()+Gdx.graphics.getHeight()/15!=Gdx.graphics.getHeight())){
					actualMonster.setY(actualMonster.getY()+Gdx.graphics.getHeight()/15);
				}
				if(monster.getY()<actualMonster.getY()&&(actualMonster.getY()-Gdx.graphics.getHeight()/15!=0-actualMonster.getH())){
					actualMonster.setY(actualMonster.getY()-Gdx.graphics.getHeight()/15);
				}
				if(monster.getX()>actualMonster.getX()&&(actualMonster.getX()+Gdx.graphics.getWidth()/15!=Gdx.graphics.getWidth())){
					actualMonster.setX(actualMonster.getX()+Gdx.graphics.getWidth()/15);
				}
				if(monster.getX()<actualMonster.getX()&&(actualMonster.getX()-Gdx.graphics.getWidth()/15!=0-actualMonster.getW())){
					actualMonster.setX(actualMonster.getX()-Gdx.graphics.getWidth()/15);
				}
				timesincelastmonsterMove=0;
			}
		}

		public void playerMovement() {
				if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
					monster.setX((monster.getX() + Gdx.graphics.getWidth() / 15));
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
					monster.setX((monster.getX() - Gdx.graphics.getWidth() / 15));
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
					monster.setY((monster.getY() + Gdx.graphics.getHeight() / 15));
				}
				if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
					monster.setY((monster.getY() - Gdx.graphics.getHeight() / 15));
				}
				if (monster.getX() + monster.getW() > Gdx.graphics.getWidth()) {
					monster.setX(0);
				}
				if (monster.getX() < 0) {
					monster.setX(Gdx.graphics.getWidth() - monster.getW());
				}
				if (monster.getY() + monster.getH() > Gdx.graphics.getHeight()) {
					monster.setY(0);
				}
				if (monster.getY() < 0) {
					monster.setY(Gdx.graphics.getHeight() - monster.getH());
				}
			}





		@Override
		public void dispose () {

		}
	}
