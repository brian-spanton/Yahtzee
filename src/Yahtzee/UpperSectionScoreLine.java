package Yahtzee;

class UpperSectionScoreLine extends DiscretionaryScoreLine
{
	private int face;
	
	UpperSectionScoreLine(String name, int face)
	{
		super(name, name + " = " + face, "Count and add only " + name);
		this.face = face;
	}
	
	@Override boolean got_it(Die[] dice)
	{
		return true;
	}
	
	@Override int get_value(Die[] dice)
	{
		int total = 0;
		
		for(Die die : dice)
		{
			if (die.get_face() == this.face)
				total = total + die.get_face();
		}
		
		return total;
	}
}