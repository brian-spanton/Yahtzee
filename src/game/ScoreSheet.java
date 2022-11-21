package game;

class ScoreSheet
{
	private java.util.HashMap<String, DiscretionaryScoreLine> map = new java.util.HashMap<String, DiscretionaryScoreLine>();
	private UpperSectionScoreLine[] upper_section = new UpperSectionScoreLine[6];
	private BonusScoreLine bonus_score_line = new BonusScoreLine();
	private DiscretionaryScoreLine[] lower_section = new DiscretionaryScoreLine[8];
	private YahtzeeBonusScoreLine yahtzee_bonus_score_line = new YahtzeeBonusScoreLine();

	ScoreSheet()
	{
		this.upper_section[0] = new UpperSectionScoreLine("Aces", 1);
		this.upper_section[1] = new UpperSectionScoreLine("Twos", 2);
		this.upper_section[2] = new UpperSectionScoreLine("Threes", 3);
		this.upper_section[3] = new UpperSectionScoreLine("Fours", 4);
		this.upper_section[4] = new UpperSectionScoreLine("Fives", 5);
		this.upper_section[5] = new UpperSectionScoreLine("Sixes", 6);

		for (int index = 0; index < this.upper_section.length; index++)
		{
			this.map.put(this.upper_section[index].get_name(), this.upper_section[index]);
		}

		this.lower_section[0] = new OfAKindScoreLine("3 of a kind", null, "Add total of all dice", 3);
		this.lower_section[1] = new OfAKindScoreLine("4 of a kind", null, "Add total of all dice", 4);
		this.lower_section[2] = new FullHouseScoreLine();
		this.lower_section[3] = new StraightScoreLine("SM Straight", "Sequence of 4", "Score 30", 4, 30);
		this.lower_section[4] = new StraightScoreLine("LG Straight", "Sequence of 5", "Score 40", 5, 40);
		this.lower_section[5] = new YahtzeeScoreLine();
		this.lower_section[6] = new ChanceScoreLine();
		this.lower_section[7] = this.yahtzee_bonus_score_line;

		for (int index = 0; index < this.lower_section.length; index++)
		{
			this.map.put(this.lower_section[index].get_name(), this.lower_section[index]);
		}
	}

	void render_available_to_take(java.io.PrintStream print_stream, Die[] dice)
	{
		for (int line_index = 0; line_index < this.upper_section.length; line_index++)
		{
			if (this.upper_section[line_index].is_available())
			{
				this.upper_section[line_index].render_score(print_stream, dice);
				print_stream.println();
			}
		}

		for (int line_index = 0; line_index < this.lower_section.length; line_index++)
		{
			if (this.lower_section[line_index].is_available())
			{
				this.lower_section[line_index].render_score(print_stream, dice);
				print_stream.println();
			}
		}
	}

	boolean is_available(String name)
	{
		DiscretionaryScoreLine score_line = this.map.get(name);

		if (score_line == null)
			return false;

		return score_line.is_available();
	}

	void take(String name, Die[] dice)
	{
		if (!is_available(name))
			return;
			
		DiscretionaryScoreLine score_line = this.map.get(name);

		if (score_line == null)
			return;

		score_line.take(dice);

		if (score_line instanceof UpperSectionScoreLine)
			this.bonus_score_line.update_score(this.upper_section);
		else if (score_line instanceof YahtzeeScoreLine && score_line.got_it(dice))
			this.yahtzee_bonus_score_line.set_available(true);
	}

	void render(java.io.PrintStream print_stream)
	{
		int total = 0;

		for (int line_index = 0; line_index < this.upper_section.length; line_index++)
		{
			total += this.upper_section[line_index].get_score();
			this.upper_section[line_index].render(print_stream);
			print_stream.println();
		}

		total += this.bonus_score_line.get_score();
		this.bonus_score_line.render(print_stream);
		print_stream.println();

		for (int line_index = 0; line_index < this.lower_section.length; line_index++)
		{
			total += this.lower_section[line_index].get_score();
			this.lower_section[line_index].render(print_stream);
			print_stream.println();
		}

		ScoreLine total_score_line = new ScoreLine("", "", "Grand Total");
		total_score_line.set_score(total);
		total_score_line.render(print_stream);
		print_stream.println();
}
}
