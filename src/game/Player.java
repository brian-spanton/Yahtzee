package game;

public class Player {
	
	Die[] dice = new Die[5];
	int remaining_rolls = 0;
	String name;
	
	
	
	public Player(String nameinput)
	{
		this.name = nameinput;
		for(int i = 0; i<5; i++)
		{
			this.dice[i]=new Die();
		}
	}
	
	public void rename(String customn)
	{
		this.name = customn;
	}
	
	public void keep(int iDie)
	{
		this.dice[iDie].keep();
	}
	public void roll()
	
	{
		if (remaining_rolls == 0)
		{
			for(int i = 0; i<5; i++)
			{
				this.dice[i].unkeep();
			}
			
			this.remaining_rolls = 3;
		}
		
		for(int dice_index = 0; dice_index < 5; dice_index++)
		{
			dice[dice_index].roll();
		}
		
		this.remaining_rolls--;
		
		print_dice();
		
	}
	public void print_dice()
	{
		for(int dice_index = 0; dice_index < 5; dice_index++)
		{
			Die die = dice[dice_index];
			int die_number = dice_index+1;
			System.out.println("die " + die_number + ": " + die);
		}
		
		System.out.println("");
		System.out.println(this.name + " has " + remaining_rolls + " rolls remaining this turn.");
	}
}
