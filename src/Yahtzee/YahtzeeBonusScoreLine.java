package Yahtzee;

class YahtzeeBonusScoreLine extends OfAKindScoreLine
{
	int checks = 0;
	
	YahtzeeBonusScoreLine()
	{
	    super("Yahtzee Bonus", "Check For each bonus", "Score 100 per check", 5);
	    this.set_available(false);
	}
	
	void take(Die[] dice)
	{
		if (this.is_available() && got_it(dice))
		{
	        this.checks++;
			this.set_score(this.checks * get_value(dice));
		}
	}
	
	@Override int get_value(Die[] dice)
	{
	    return 100;
	}
}