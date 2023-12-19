package Yahtzee;

class FullHouseScoreLine extends DiscretionaryScoreLine
{
	FullHouseScoreLine()
	{
		super("Full House", "", "Score 25");
	}

	@Override boolean got_it(Die[] dice)
	{
		int[] face_count = count_faces(dice);
		boolean pair = false;
		boolean set = false;

		for (int face_count_index = 0; face_count_index < face_count.length; face_count_index++)
		{
			if (face_count[face_count_index] == 2)
				pair = true;
			else if (face_count[face_count_index] == 3)
				set = true;
			else if (face_count[face_count_index] == 5)
				return true;
		}
		
		return pair && set;
	}

	@Override int get_value(Die[] dice)
	{
		return 25;
	}
}