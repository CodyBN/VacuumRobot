import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Environment extends JPanel {

	
	/**
	 * we create the Environment panel that will 
	 * draw the building and controls the Graphic
	 * locations of dirt and the agent vacuum.
	 */
	private static final long serialVersionUID = 1L;
	final int originX = 50;  
	int vOriginX= 53; //origin points of agent 
	int tempX;
	int vOriginY= 313; 
	int floor = 314;//bottom of the environment 
	int leftWall = 51;//left side of the environment 
	int rightWall = 327; 
	final int cols = 8;
	final int rows = 8;
	final int originY = 37; //top of environment  
	final int cellSide =39; //size of a cell wall
	AgentCell agent = new AgentCell();
	private BufferedImage img = null;
	private BufferedImage img2 = null;

/**
 * Cody Noack
 */
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		

		//drawing the grid for environment.
		Graphics2D g2 = (Graphics2D)g;
		
		drawGrid(g2);
		try
		{
			/**
			 * 
			 * You may need to edit the file locations for the images
			 * 
			 * 
			 */
			//loads the images for us to use for the agent and dirt piles
			img = ImageIO.read(new File("E:\\Users\\Spindle2\\eclipse-workspace\\VacuumV2\\images\\vacuumimg.png"));
			img2 = ImageIO.read(new File("E:\\Users\\Spindle2\\eclipse-workspace\\VacuumV2\\images\\dirt.png"));

		}
		catch(IOException exc)
		{
			System.out.print("oops");
		}
		
		populateDirt(g2);
		
		g2.setColor(Color.WHITE);
		g2.fillOval(vOriginX, vOriginY, 32, 32); // fillOval(x point, y point, size, size)
		g2.drawImage(img, vOriginX, vOriginY, cellSide, cellSide, this);
		
				


		
	}
	
	/**
	 * Calculates the movements of our agent 
	 * so that we can move the graphics around 
	 * the environment.  
	 * 
	 * 
	 * 
	 * Cody Noack
	 */
	public void moveRight()
	{
		
		this.vOriginX = vOriginX + cellSide;
		if(agent.bumped(1,0))
		{
			//undo calculation, if we are at a wall.
			this.vOriginX = vOriginX - cellSide;
			moveLeft();
		}
		else
		{
			repaint();

		}
		
		
	}
	
	public void moveLeft()
	{
		this.vOriginX = vOriginX - cellSide;
		if(agent.bumped(-1,0))
		{
			//undo calculation, if we are at a wall.
			this.vOriginX = vOriginX + cellSide;
			moveRight();
		}
		else
		{

			repaint(); 
		}
	}
	/**
	 * Zachary Upshaw
	 */
	public void moveUp()
	{
		this.vOriginY = vOriginY - cellSide;
		if(agent.bumped(0,1))
		{
			//undo calculation, if we are at a wall.
			this.vOriginY = vOriginY + cellSide;
			moveDown();
		}
		else
		{

			repaint(); 
		} 
	}
	public void moveDown()
	{
		this.vOriginY = vOriginY + cellSide;
		if(agent.bumped(0,-1))
		{
			//undo calculation, if we are at a wall.
			this.vOriginY = vOriginY - cellSide;
			moveUp();
		}
		else
		{

			repaint(); 
		}  
	}
	


	
	//draws the grid
	/**
	 * Cody Noack
	 * @param g2
	 */
	private void drawGrid(Graphics2D g2)
	{
		g2.setColor(Color.BLACK);
		for(int i = 0; i<= rows; i++) {
			g2.drawLine(originX, originY + i * cellSide , originX + cols * cellSide, originY +i * cellSide);
			
			
		}
		for(int i = 0; i<= cols; i++) {
			g2.drawLine(originX + i * cellSide, originY, originX + i * cellSide, originY + rows * cellSide);
		}
	}
	
	/**
	 * populates the dirt randomly on 20 rooms in the environment.
	 * @param g2
	 * Cody Noack
	 */
	private void populateDirt(Graphics2D g2)
	{
		g2.setColor(Color.RED);
			
		for(int i = 0; i < agent.loc.length; i++)
		{
			for(int j =0; j < agent.loc[i].length; j++)
			{
				if(agent.loc[i][j].dirty == true)
				{	
					g2.drawImage(img2, leftWall + i * cellSide, floor - j * cellSide, cellSide, cellSide, this);
				}
			}
		}
	}
	
	/**
	 * checks if the entire building is cleaned.
	 * @return
	 */
	/**
	 * Zachary Upshaw
	 * @return
	 */
	public boolean buildingCleaned()
	{
		int count2 = 0;
		for(int i = 0; i < agent.loc.length; i++)
		{
			for(int j =0; j < agent.loc[i].length; j++)
			{
				if(agent.loc[i][j].dirty == true)
				{
					count2++;
				}
			}
		}
		if(count2 > 0)
		{
			return false;
		}
		else
		{
			return true;
		}
		
	}
}
