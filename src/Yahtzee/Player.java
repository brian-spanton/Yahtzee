package Yahtzee;

class Player implements java.lang.Comparable<Player>
{
	private Die[] dice = new Die[5];
	private int remaining_rolls = 3;
	private String name;
	ScoreSheet score_sheet = new ScoreSheet();
	private boolean can_take = false;
	private int remaining_turns = 13;
	
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

	void render_turn(java.io.PrintStream print_stream)
	{
		render_header(print_stream, "TURN!");

		if (this.can_take)
		{
			render_header(print_stream, "DICE");
			for (int dice_index = 0; dice_index < this.dice.length; dice_index++)
			{
				Die die = this.dice[dice_index];
				int die_number = dice_index + 1;
				print_stream.print("Die ");
				print_stream.print(die_number);
				print_stream.print(": ");
				die.render(print_stream);
				print_stream.println();
			}

			print_stream.println();
			render_header(print_stream, "OPTIONS");
			this.score_sheet.render_available_to_take(print_stream, this.dice);
			print_stream.println();
		}

		render_header(print_stream, "ROLLS REMAINING");
		print_stream.println(this.remaining_rolls);
	}

	void render_total(java.io.PrintStream print_stream)
	{
		print_stream.printf("%16s: %2d", this.name, this.get_total());
	}

	boolean has_turns()
	{
		return this.remaining_turns > 0;
	}

	void render_header(java.io.PrintStream print_stream, String header)
	{
		print_stream.print("*** ");
		print_stream.print(this.name);
		print_stream.print("'s ");
		print_stream.print(header);
		print_stream.println(" ***");
	}

	void render_score_sheet(java.io.PrintStream print_stream)
	{
		this.render_header(print_stream, "SCORE SHEET");
		this.score_sheet.render(print_stream);

		print_stream.println();
		render_header(print_stream, "TURNS REMAINING");
		print_stream.println(this.remaining_turns);
	}
}
