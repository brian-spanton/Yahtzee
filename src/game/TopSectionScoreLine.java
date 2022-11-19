package game;

public class TopSectionScoreLine extends ScoreLine
{
	int toplinenum;

	public int calculate(Die[] dice)
	{
		int total = 0;
		
		for(int i = 0; i<5; i++)
		{
			total = total + dice[i].get_face();
		}
		
		return total;
	}w


}
