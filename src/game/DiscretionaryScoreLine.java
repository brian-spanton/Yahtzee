package game;

abstract class DiscretionaryScoreLine extends ScoreLine
{
	private boolean available = true;

    DiscretionaryScoreLine(String name, String subtext, String description)
	{
        super(name, subtext, description);
	}

	abstract boolean got_it(Die[] dice);
    abstract int get_value(Die[] dice);

    void set_available(boolean available)
    {
        this.available = available;
    }

	int get_score(Die[] dice)
	{
        if (!got_it(dice))
            return 0;

        return this.get_value(dice);
	}

    int[] count_faces(Die[] dice)
    {
        int[] face_count = new int[6];
		
		for (int face_count_index = 0; face_count_index < dice.length; face_count_index++)
		{
            int face = dice[face_count_index].get_face();

            if (face > 0)
    			face_count[face - 1] += 1;
		}

        return face_count;
    }

	int sum_all(Die[] dice)
	{
		int total = 0;
		
		for(int i = 0; i < dice.length; i++)
		{
			total = total + dice[i].get_face();
		}

		return total;
	}

    boolean is_available()
    {
        return this.available;
    }

	void take(Die[] dice)
	{
        this.set_score(this.get_score(dice));
        this.available = false;
	}
	
	void render_score(java.io.PrintStream print_stream, Die[] dice)
	{
		int score = this.get_score(dice);
		print_stream.print(this.get_name());
        print_stream.print(" = ");
        print_stream.print(score);
	}
}


