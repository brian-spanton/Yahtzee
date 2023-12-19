package Yahtzee;

class Player implements java.lang.Comparable<Player>
{
	Die[] dice = new Die[5];
	int remaining_rolls = 3;
	String name;
	ScoreSheet score_sheet = new ScoreSheet();
	boolean can_take = false;
	int remaining_turns = 13;
	
	Player(String name)
	{
		this.name = name;
	
		for(int dice_index = 0; dice_index < this.dice.length; dice_index++)
			this.dice[dice_index] = new Die();
	}
	
	@Override public int compareTo(Player player)
	{
		return this.get_total() - player.get_total();
	}
	
	void rename(String customn)
	{
		this.name = customn;
	}
	
	void keep(java.util.ArrayList<Integer> dice_indexes)
	{
		for (int die_index : dice_indexes)
			this.dice[die_index].keep();
	}
	
	boolean take(String name)
	{
		if (!this.can_take)
			return false;
	
		boolean taken = this.score_sheet.take(name, this.dice);
		if (!taken)
			return false;
	
		this.can_take = false;
		this.remaining_turns--;
	
		for (Die die : this.dice)
			die.reset();
	
		if (this.remaining_turns == 0)
			this.remaining_rolls = 0;
		else
			this.remaining_rolls = 3;
	
		return true;
	}
	
	boolean roll()
	{
		if (this.remaining_rolls == 0)
			return false;
	
		for (Die die : this.dice)
			die.roll();
		
		this.remaining_rolls--;
		this.can_take = true;
	
		if (this.remaining_rolls == 0)
		{
			for(Die die : this.dice)
				die.unkeep();
		}
	
		return true;
	}
	
	int get_total()
	{
		return this.score_sheet.get_total();
	}
	
	boolean has_turns()
	{
		return this.remaining_turns > 0;
	}
}