
import acm.graphics.*;     // GOval, GRect, etc.
import acm.program.*;      // GraphicsProgram
import acm.util.*;         // RandomGenerator
import java.applet.*;      // AudioClip
import java.awt.*;         // Color
import java.awt.event.*;   // MouseEvent
//Written by Jakymoon with help of ready2use libraries 
public class Breakout extends GraphicsProgram implements BreakoutConstants {
	RandomGenerator rg = new RandomGenerator();
	GRect paddle = new GRect(PADDLE_WIDTH,PADDLE_HEIGHT);
	GOval ball = new GOval(BALL_SIZE,BALL_SIZE);
	int life = 3;
	int speed = 0;
	int vx = 0;
	int vy = 0;

	private void brickBuild(int x,int y,Color colorB){
		GRect brick = new GRect(BRICK_WIDTH,BRICK_HEIGHT);
		brick.setColor(colorB);
		brick.setFilled(true);
		add(brick,x,y);
	}

	public void mouseMoved(MouseEvent e){
		paddle.setLocation(e.getX(),APPLICATION_HEIGHT-PADDLE_Y_OFFSET);
	}
	public void mousePressed(MouseEvent e){
		vx=4;
		vy=4;
	}

	public void run() {
		//Colors
		Color arr[]={Color.RED,Color.ORANGE,Color.YELLOW,Color.GREEN,Color.CYAN};

		//Background
		setBackground(Color.BLACK);

		//Bricks
		for(int i=0;i<NBRICK_ROWS;i++)
			for(int j=0;j<NBRICKS_PER_ROW;j++)
				brickBuild(j*42,i*17,arr[i/2]);

		//Paddle
		paddle.setColor(Color.LIGHT_GRAY);
		paddle.setFilled(true);
		add(paddle,getWidth()/2-PADDLE_WIDTH/2,APPLICATION_HEIGHT-PADDLE_Y_OFFSET);

		//Ball
		ball.setColor(Color.white);
		ball.setFilled(true);
		add(ball,getWidth()/2-BALL_SIZE/2,APPLICATION_HEIGHT-PADDLE_Y_OFFSET-20);

		setBackground(Color.BLACK);
		//MOVE & Collision
		addMouseListeners();
		while(true){
			ball.move(vx,vy);
			pause(DELAY);			
			//top
			if(ball.getY()<0)
				vy*=-1;
			//left
			if(ball.getX()<0)
				vx*=-1;
			//right
			if(ball.getX()+BALL_SIZE>APPLICATION_WIDTH)
				vx*=-1;
			//bottom&life
			if(ball.getY()+BALL_SIZE>APPLICATION_HEIGHT) {
				life--;
				if(life>0) {
					vx = -0;
					vy = -0;
					ball.setLocation(getWidth()/2-BALL_SIZE/2,APPLICATION_HEIGHT-PADDLE_Y_OFFSET-20);
				}
				//Game Over
				else {
					System.exit(0);
				}
				
			}	
			GObject obje = getElementAt((ball.getX()+BALL_SIZE/2),(ball.getY()));
			//Checking 4 corners
			if(obje == null)
				obje = getElementAt((ball.getX()+BALL_SIZE),(ball.getY()+BALL_SIZE/2));
			if(obje == null)
				obje = getElementAt((ball.getX()),(ball.getY()+BALL_SIZE/2));
			if(obje == null)
				obje = getElementAt((ball.getX()+BALL_SIZE/2),(ball.getY()+BALL_SIZE));
			//Remove
			if(obje != paddle && obje != ball && obje != null){
				remove(obje);
				vy*=-1;
			}
			if(obje == paddle)
				vy*=-1;
		}		

	}

}