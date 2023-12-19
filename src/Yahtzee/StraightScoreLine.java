package Yahtzee;

class StraightScoreLine extends DiscretionaryScoreLine
{
	private int how_many;
	private int value;

	StraightScoreLine(String name, String subtext, String description, int how_many, int value)
	{
		super(name, subtext, description);
		this.how_many = how_many;
		this.value = value;
	}

	@Override boolean got_it(Die[] dice)
	{
		int[] face_counts = count_faces(dice);

		int run_length = 0;

		for (int face_count : face_counts)
		{
			if (face_count > 0)
			{
				run_length++;

				if (run_length == this.how_many)
					return true;
			}
			else
			{
				run_length = 0;
			}
		}
		
		return false;
	}

	@Override int get_value(Die[] dice)
	{
		return this.value;
	}
}