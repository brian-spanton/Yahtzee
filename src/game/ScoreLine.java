package game;

class ScoreLine
{
	private String name;
	private String subtext;
	private String description;
	private int score = 0;

	ScoreLine(String name, String subtext, String description)
	{
		this.name = name;
		this.subtext = subtext;
		this.description = description;
	}
	
	void render(java.io.PrintStream print_stream)
	{
		print_stream.printf("%13s | %28s | %25s = %3d", this.name, this.subtext, this.description, this.score);
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