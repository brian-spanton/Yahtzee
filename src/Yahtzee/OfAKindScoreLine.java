package Yahtzee;

class OfAKindScoreLine extends DiscretionaryScoreLine
{
	private int how_many;

	OfAKindScoreLine(String name, String subtext, String description, int how_many)
	{
		super(name, subtext, description);
		this.how_many = how_many;
	}

	@Override boolean got_it(Die[] dice)
	{
		int[] face_counts = count_faces(dice);

		for (int face_count : face_counts)
		{
			if (face_count >= this.how_many)
				return true;
		}
		
		return false;
	}

	@Override int get_value(Die[] dice)
	{
		return sum_all(dice);
	}
}