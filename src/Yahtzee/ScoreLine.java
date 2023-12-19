package Yahtzee;

class ScoreLine
{
	String name;
	String subtext;
	String description;
	int score = 0;
	
	ScoreLine(String name, String subtext, String description)
	{
		this.name = name;
		this.subtext = subtext;
		this.description = description;
	}
	
	int get_score()
	{
		return this.score;
	}
	
	void set_score(int score)
	{
		this.score = score;
	}
	
	String get_name()
	{
		return this.name;
	}
}