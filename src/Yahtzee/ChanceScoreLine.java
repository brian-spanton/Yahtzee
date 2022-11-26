package Yahtzee;

class ChanceScoreLine extends DiscretionaryScoreLine
{
    ChanceScoreLine()
    {
        super("Chance", "", "Add total of all 5 dice");
    }

	@Override boolean got_it(Die[] dice)
    {
		return true;
    }

	@Override int get_value(Die[] dice)
	{
        return sum_all(dice);
	}
}
