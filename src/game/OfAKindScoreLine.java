package game;

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
        int[] face_count = count_faces(dice);

        for (int face_count_index = 0; face_count_index < face_count.length; face_count_index++)
        {
            if (face_count[face_count_index] >= this.how_many)
                return true;
        }
		
		return false;
    }

	@Override int get_value(Die[] dice)
	{
        return sum_all(dice);
	}
}
