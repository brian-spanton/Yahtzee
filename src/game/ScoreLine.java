package game;

public class ScoreLine {

	boolean empty = true;
	int score = 0;
	
	public int calculate(Die[] dice)
	{
		int total = 0;
		
		for(int i = 0; i<5; i++)
		{
			total = total + dice[i].get_face();
		}
		
		return total;
	}
	
}


