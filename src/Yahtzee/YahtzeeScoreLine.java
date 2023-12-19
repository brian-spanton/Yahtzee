package Yahtzee;

class YahtzeeScoreLine extends OfAKindScoreLine
{
	YahtzeeScoreLine()
	{
		super("Yahtzee", "5 of a kind", "Score 50", 5);
	}

	@Override int get_value(Die[] dice)
	{
		return 50;
	}
}