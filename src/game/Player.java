package game;

class Player
{
	private Die[] dice = new Die[5];
	private int remaining_rolls = 3;
	private String name;
	private ScoreSheet sheet = new ScoreSheet();
	private boolean can_take = false;
	private int remaining_turns = 13;
	
	Player(String name)
	{
		this.name = name;

		for(int dice_index = 0; dice_index < this.dice.length; dice_index++)
		{
			this.dice[dice_index] = new Die();
		}
	}
	
	void rename(String customn)
	{
		this.name = customn;
	}
	
	void keep(int die_index)
	{
		this.dice[die_index].keep();
	}

	void take(String name)
	{
		if (!this.can_take)
			return;

		if (!this.sheet.is_available(name))
			return;

		this.sheet.take(name, this.dice);			
		this.can_take = false;
		this.remaining_turns--;

		for (int dice_index = 0; dice_index < this.dice.length; dice_index++)
			this.dice[dice_index].reset();

		if (this.remaining_turns > 0)
			this.remaining_rolls = 3;
	}

	void roll()
	{
		if (this.remaining_rolls == 0)
			return;

		for (int dice_index = 0; dice_index < this.dice.length; dice_index++)
			this.dice[dice_index].roll();
		
		this.remaining_rolls--;
		this.can_take = true;

		if (this.remaining_rolls == 0)
		{
			for(int dice_index = 0; dice_index < this.dice.length; dice_index++)
				this.dice[dice_index].unkeep();
		}
	}

	void render_turn(java.io.PrintStream print_stream)
	{
		if (this.can_take)
		{
			render_header(print_stream, "DICE");
			for(int dice_index = 0; dice_index < this.dice.length; dice_index++)
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
			this.sheet.render_available_to_take(print_stream, this.dice);
			print_stream.println();
		}

		render_header(print_stream, "ROLLS REMAINING");
		print_stream.println(this.remaining_rolls);

		print_stream.println();
		render_header(print_stream, "TURNS REMAINING");
		print_stream.println(this.remaining_turns);
	}

	boolean has_rolls()
	{
		return this.remaining_rolls > 0;
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
		this.sheet.render(print_stream);
	}
}
