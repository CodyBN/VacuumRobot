
import java.util.Random;



class Location {
	int x;
	int y;
	boolean dirty = false;
}
class currentLocation
{
	int x;
	int y;

}
public class AgentCell {

	private Random rand = new Random();
	currentLocation current = new currentLocation();
	
	Location[][] loc = new Location[8][8];
	


	/**
	 * creates the map in a 2D array
	 * and places dirt randomly
	 * Cody Noack
	 */
	public void populate()
	{
		int dirt = 0;
		for(int i = 0; i < loc.length; i++)
		{
			for( int j = 0; j < loc[i].length; j++)
			{
			loc[i][j] = new Location();
			loc[i][j].x =i;
			loc[i][j].y =j;
			if(determineAction()==2 || determineAction() == 3)
			{
				if(dirt<20)
				{
					loc[i][j].dirty =true;
					dirt++;
					
				}
				
			}
			
			}
			
		}
		

		this.current.x = 0;
		this.current.y = 0;

	}
	

	/**
	 * 
	 * Agent determines an action, if there is dirt
	 * we need to clean up the dirt,
	 * if there is no dirt the Agent decides to move
	 * if the Agent moves and the calculations move outside
	 * of the environment boundaries the Agent goes another
	 * direction.
	 * @return
	 */
	public int determineAction()
	{
		if(seesDirt())
		{
			suckDirt();
			
			return 4;
		}
		else
		{
			int rand1 = rand.nextInt(4);
			return rand1;
		}

		
	}

	/**
	 * returns Agents current location in the environment
	 * @return
	 */
	public currentLocation getLocation()
	{
		
		return current;
	}
/**
 * Agent attempts to move in a direction
 * and determines if there is a wall or not. 
 * @param i
 * @param j
 * @return
 * 
 * Cody Noack
 */
	public boolean bumped(int i, int j)
	{
		try
		{
			
		current.x = current.x + i;
		current.y = current.y + j;
		seesDirt(); //making use of a method that's already created for my try catch, this is simply being used to check for an out of bounds exception in the array.
		
		return false;
		}
		catch(Exception e)
		{
			current.x = current.x + (i*-1);
			current.y = current.y + (j*-1);
			
			return true;
		}
		
	
	}
	
	
	/**
	 * determines if there is dirt
	 *  in the current location
	 *  Zachary Upshaw
	 * @return
	 */
	public boolean seesDirt()
	{
		if(loc[current.x][current.y].dirty)
		{
			return true;
		}
		else 
		{
		
			return false;
		}
	}
	/**
	 * Cleans the dirt from a room
	 * Cody Noack
	 */
	public void suckDirt() 
	{
		loc[current.x][current.y].dirty = false;
	}
	
	/**
	 * Zachary Upshaw
	 * @return
	 */
	public boolean isLobby()
	{
		if(current.x == 0 && current.y == 0)
		{
			return true;
			
		}
		else 
		{
			return false;
		}
	}
	
}

