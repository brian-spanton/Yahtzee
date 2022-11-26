package Yahtzee;

class ScoreSheet
{
	private java.util.ArrayList<ScoreLine> all_lines = new java.util.ArrayList<ScoreLine>();
	java.util.ArrayList<DiscretionaryScoreLine> discretionary_line_list = new java.util.ArrayList<DiscretionaryScoreLine>();
	private java.util.HashMap<String, DiscretionaryScoreLine> discretionary_line_map = new java.util.HashMap<String, DiscretionaryScoreLine>();
	private BonusScoreLine bonus_score_line = new BonusScoreLine();
	private YahtzeeBonusScoreLine yahtzee_bonus_score_line = new YahtzeeBonusScoreLine();
	private ScoreLine total_score_line = new ScoreLine("Grand Total", "", "");

	ScoreSheet()
	{
		add_score_line(new UpperSectionScoreLine("Aces", 1));
		add_score_line(new UpperSectionScoreLine("Twos", 2));
		add_score_line(new UpperSectionScoreLine("Threes", 3));
		add_score_line(new UpperSectionScoreLine("Fours", 4));
		add_score_line(new UpperSectionScoreLine("Fives", 5));
		add_score_line(new UpperSectionScoreLine("Sixes", 6));
		add_score_line(this.bonus_score_line);
		add_score_line(new OfAKindScoreLine("3 of a kind", "", "Add total of all dice", 3));
		add_score_line(new OfAKindScoreLine("4 of a kind", "", "Add total of all dice", 4));
		add_score_line(new FullHouseScoreLine());
		add_score_line(new StraightScoreLine("SM Straight", "Sequence of 4", "Score 30", 4, 30));
		add_score_line(new StraightScoreLine("LG Straight", "Sequence of 5", "Score 40", 5, 40));
		add_score_line(new YahtzeeScoreLine());
		add_score_line(new ChanceScoreLine());
		add_score_line(this.yahtzee_bonus_score_line);
		add_score_line(this.total_score_line);
	}

	private void add_score_line(ScoreLine line)
	{
		this.all_lines.add(line);

		if (line instanceof DiscretionaryScoreLine == false)
			return;

		DiscretionaryScoreLine discretionary_score_line = (DiscretionaryScoreLine)line;
		this.discretionary_line_list.add(discretionary_score_line);
		this.discretionary_line_map.put(line.get_name().toLowerCase(), discretionary_score_line);
	}

	int get_total()
	{
		return this.total_score_line.get_score();
	}

	void render_available_to_take(java.io.PrintStream print_stream, Die[] dice)
	{
		for (DiscretionaryScoreLine line : this.discretionary_line_list)
		{
			if (!line.is_available())
				continue;

			line.render_score(print_stream, dice);
			print_stream.println();
		}
	}

	private DiscretionaryScoreLine get_available_line(String name)
	{
		String lower_name = name.toLowerCase();

		DiscretionaryScoreLine score_line = this.discretionary_line_map.get(lower_name);
		if (score_line == null)
			return null;

		if (!score_line.is_available())
			return null;

		return score_line;
	}

	boolean is_available(String name)
	{
		DiscretionaryScoreLine line = get_available_line(name);
		return line != null;
	}

	boolean take(String name, Die[] dice)
	{
		DiscretionaryScoreLine take_line = get_available_line(name);
		if (take_line == null)
			return false;

		take_line.take(dice);

		if (take_line instanceof UpperSectionScoreLine)
			this.bonus_score_line.update_score(this.all_lines);
		else if (take_line instanceof YahtzeeScoreLine && take_line.got_it(dice))
			this.yahtzee_bonus_score_line.set_available(true);

		this.total_score_line.set_score(0);

		int total = 0;
		for (ScoreLine line : this.all_lines)
			total += line.get_score();

		this.total_score_line.set_score(total);
	
		return true;
	}

	void render(java.io.PrintStream print_stream)
	{
		for (ScoreLine line : this.all_lines)
		{
			line.render(print_stream);
			print_stream.println();
		}
	}
}
