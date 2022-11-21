package game;

class BonusScoreLine extends ScoreLine
{
	BonusScoreLine()
	{
		super("Bonus", "If total score is 63 or over", "Score 35");
	}

	void update_score(UpperSectionScoreLine[] upper_section)
	{
        int total = 0;

        for (int upper_section_index = 0; upper_section_index < 6; upper_section_index++)
            total += upper_section[upper_section_index].get_score();

        if (total < 63)
            this.set_score(0);
        else
            this.set_score(35);
	}
}
