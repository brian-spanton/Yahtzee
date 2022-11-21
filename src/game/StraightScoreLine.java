package game;

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
        int[] face_count = count_faces(dice);

        int run_length = 0;

        for (int face_count_index = 0; face_count_index < face_count.length; face_count_index++)
        {
            if (face_count[face_count_index] > 0)
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
