package com.reber.floppybird;


import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import java.util.Random;
public class FloppyBird extends ApplicationAdapter {

	SpriteBatch batch;
	String howFinished;
	Texture background,bird,enemy,enemy2,enemy3;
	int topScore;
	float birdX = 0;
	float birdY = 0;
	float velocity = 0;
	float gravity = 0.1f;
	float enemyVelocity = 2;
	int gameState = 0;
	Random random;
	int score = 0;
	int scoredEnemy = 0;
	ShapeRenderer shapeRenderer;
	BitmapFont font,font2,font3,font4;
	Circle birdCircle;
	int numberOfEnemies = 4;
	float [] enemyX = new float[numberOfEnemies];
	float [] enemyOffSet = new float[numberOfEnemies];
	float [] enemyOffSet2 = new float[numberOfEnemies];
	float [] enemyOffSet3 = new float[numberOfEnemies];
	float distance = 0;

	Circle[] enemyCircles;
	Circle[] enemyCircles2;
	Circle[] enemyCircles3;



	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("background.png");
		bird = new Texture("bird.png");
		enemy = new Texture("rock.png");
		enemy2 = new Texture("rock.png");
		enemy3 = new Texture("rock.png");

		distance = Gdx.graphics.getWidth() / 2;
		random = new Random();


		birdX = Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2;
		birdY = Gdx.graphics.getHeight() / 3;

		shapeRenderer = new ShapeRenderer();


		birdCircle = new Circle();
		enemyCircles = new Circle[numberOfEnemies];
		enemyCircles2 = new Circle[numberOfEnemies];
		enemyCircles3 = new Circle[numberOfEnemies];


		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(4);

		font2 = new BitmapFont();
		font2.setColor(Color.WHITE);
		font2.getData().setScale(4);

		font3 = new BitmapFont();
		font3.setColor(Color.YELLOW);
		font3.getData().setScale(7);

		font4 = new BitmapFont();
		font4.setColor(Color.YELLOW);
		font4.getData().setScale(3);



		for (int i = 0; i<numberOfEnemies; i++) {


			enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			enemyX[i] = Gdx.graphics.getWidth() - enemy.getWidth() / 2 + i * distance;

			enemyCircles[i] = new Circle();
			enemyCircles2[i] = new Circle();
			enemyCircles3[i] = new Circle();
			this.topScore=topScore;
		}

	}

	@Override
	public void render () {
		batch.begin();
		batch.draw(background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
		if(gameState==0){
			font3.draw(batch,"FLOPPYBIRD",Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight() / 1.2f);
			font4.draw(batch,"Tap To Play",Gdx.graphics.getWidth()/6,Gdx.graphics.getHeight() / 1.6f);
		}
		if (gameState ==1) {

			if (enemyX[scoredEnemy] < Gdx.graphics.getWidth() / 2 - bird.getHeight() / 2) {
				if (score<120){
					score= score+10;
				}else if (score<400){
					score=score+14;
				}else if (score<400){
					score=score+22;
				}else if(score<1200){
					score=score+32;
				}else{
					score = score+52;
				}

				if (scoredEnemy < numberOfEnemies - 1) {
					scoredEnemy++;
				} else {
					scoredEnemy = 0;
				}

			}

			if (Gdx.input.justTouched()) {
				velocity = -5;

			}

			for (int i = 0; i < numberOfEnemies; i++) {

				if (enemyX[i] < enemy.getWidth()) {
					enemyX[i] = enemyX[i] + numberOfEnemies * distance;

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

				} else {
					if (score<120){
						enemyX[i] = enemyX[i] - enemyVelocity-4;

					}else if(score<400){
						enemyX[i] = enemyX[i] - enemyVelocity-5;

					}else if(score<=1200){
						enemyX[i] = enemyX[i] - enemyVelocity-6;

					}else{
						enemyX[i] = enemyX[i] - enemyVelocity-6.5f;
					}
				}

				batch.draw(enemy,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet[i],Gdx.graphics.getWidth() / 18,Gdx.graphics.getHeight() / 12);
				batch.draw(enemy2,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet2[i],Gdx.graphics.getWidth() / 18,Gdx.graphics.getHeight() / 12);
				batch.draw(enemy3,enemyX[i],Gdx.graphics.getHeight()/2 + enemyOffSet3[i],Gdx.graphics.getWidth() / 18,Gdx.graphics.getHeight() / 12);

				enemyCircles[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36,  Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 24,Gdx.graphics.getWidth() / 36);
				enemyCircles2[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36,  Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 24,Gdx.graphics.getWidth() / 36);
				enemyCircles3[i] = new Circle(enemyX[i] + Gdx.graphics.getWidth() / 36,  Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 24,Gdx.graphics.getWidth() / 36);

			}

			if (birdY > 0) {
				if(birdY>=Gdx.graphics.getHeight()-2){
					howFinished = "sky";
					gameState =2;
				}else{
					velocity = velocity + gravity;
					birdY = birdY - velocity;
				}

			} else {
				gameState = 2;
				howFinished="fall";
			}


		} else if (gameState == 0) {
			if (Gdx.input.justTouched()) {
				gameState = 1;
			}
		} else if (gameState == 2) {

			if (howFinished=="hit"){
				font2.draw(batch,"the Bird hit the rock! Tap To Play Again!",Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() / 2);

			}else if (howFinished=="sky"){
				font2.draw(batch,"the Bird went to the space! Tap To Play Again!",Gdx.graphics.getWidth()/2.1f,Gdx.graphics.getHeight() / 2);

			}else if (howFinished=="fall"){
				font2.draw(batch,"the Bird fell down! Tap To Play Again!",Gdx.graphics.getWidth()/2,Gdx.graphics.getHeight() / 2);

			}

			if (Gdx.input.justTouched()) {
				gameState = 1;

				birdY = Gdx.graphics.getHeight() / 3;

				for (int i = 0; i<numberOfEnemies; i++) {

					enemyOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					enemyOffSet3[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					enemyX[i] = Gdx.graphics.getWidth() - enemy.getWidth() / 2 + i * distance;

					enemyCircles[i] = new Circle();
					enemyCircles2[i] = new Circle();
					enemyCircles3[i] = new Circle();

				}

				velocity = 0;
				scoredEnemy = 0;
				score = 0;

			}
		}

		batch.draw(bird,birdX, birdY, Gdx.graphics.getWidth() / 18,Gdx.graphics.getHeight() / 12);

		font.draw(batch,String.valueOf(score),100,200);

		batch.end();

		birdCircle.set(birdX +Gdx.graphics.getWidth() / 36 ,birdY + Gdx.graphics.getHeight() / 24,Gdx.graphics.getWidth() / 55);

		//shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		//shapeRenderer.setColor(Color.BLACK);
		//shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

		for ( int i = 0; i < numberOfEnemies; i++) {
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet2[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);
			//shapeRenderer.circle(enemyX[i] + Gdx.graphics.getWidth() / 30,  Gdx.graphics.getHeight()/2 + enemyOffSet3[i] + Gdx.graphics.getHeight() / 20,Gdx.graphics.getWidth() / 30);

			if (Intersector.overlaps(birdCircle,enemyCircles[i]) || Intersector.overlaps(birdCircle,enemyCircles2[i]) || Intersector.overlaps(birdCircle,enemyCircles3[i])) {
				gameState = 2;
				howFinished="hit";
			}
		}

		//shapeRenderer.end();

	}

	@Override
	public void dispose () {

	}
}