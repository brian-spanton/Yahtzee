package Yahtzee;

public class Game
{
	private java.util.ArrayList<Player> players;
	private int current_player_index;
	private Player current_player;
    private boolean auto_mode = false;
	
	boolean command(java.io.PrintStream print_stream, String[] words) throws Exception
	{
		if (words.length == 0 || words[0].length() == 0)
		{
			if (!this.auto_mode)
				return false;

			auto_command(print_stream);
		}
		else if (words[0].equalsIgnoreCase("i"))
		{
			print_stream.println("The object of the game is to score the most points in 13 turns. Each turn you take one score after 1-3 rolls. Roll 1 is all 5 dice, any subsequent roll is 1-5 of those.");
		}
		else if (words[0].equalsIgnoreCase("help"))
		{
			return false;
		}
		else if (words[0].equalsIgnoreCase("pc") && words.length == 2)
		{
			int count = Integer.parseInt(words[1]);
			set_player_count(count);
		}
		else if (words[0].equalsIgnoreCase("pn") && words.length == 2)
		{
			rename_player(words[1]);
		}
		else if (words[0].equalsIgnoreCase("a"))
		{
			toggle_auto_mode(print_stream);
		}
		else if (words[0].equalsIgnoreCase("k"))
		{
			java.util.ArrayList<Integer> keep_indexes = new java.util.ArrayList<Integer>();

			for (int word_index = 1; word_index < words.length; word_index++)
			{
				int die_number;

				try
				{
					die_number = Integer.parseInt(words[word_index]);
				}
				catch(Exception e)
				{
					return false;
				}

				int index = die_number - 1;
				keep_indexes.add(index);
			}

			keep(print_stream, keep_indexes);
		}			
		else if (words[0].equalsIgnoreCase("r"))
		{
			roll(print_stream);
		}
		else if (words[0].equalsIgnoreCase("rr"))
		{
			render_turn(print_stream);
		}
		else if (words[0].equalsIgnoreCase("t"))
		{
			if (words.length < 2)
				return false;

			StringBuilder name = new StringBuilder();
			name.append(words[1]);

			for (int word_index = 2; word_index < words.length; word_index++)
			{
				name.append(" ");
				name.append(words[word_index]);
			}

			take(print_stream, name.toString());
		}
		else if (words[0].equalsIgnoreCase("s"))
		{
			Integer index = null;
		
			if (words.length > 1)
			{
				try
				{
					index = Integer.parseInt(words[1]) - 1;
				}
				catch (Exception e)
				{
					return false;
				}
			}

			score(print_stream, index);
		}
		else	
		{
			return false;
		}

		return true;
	}

	void auto_command(java.io.PrintStream print_stream)
	{
		if (!this.current_player.has_turns())
			return;

		if (!this.current_player.can_take())
		{
			roll(print_stream);
			return;
		}

		take(print_stream, null);
	}
	
	void set_player_count(int player_count)
	{
		if (player_count < 1)
			return;

		this.players = new java.util.ArrayList<Player>();

		for (int player_index = 0; player_index < player_count; player_index++)
			this.players.add(new Player("Player " + (player_index + 1)));

		this.current_player_index = 0;
		this.current_player = this.players.get(0);
	}

	void rename_player(String name)
	{
		this.current_player.rename(name);
	}

	void toggle_auto_mode(java.io.PrintStream print_stream)
	{
		this.auto_mode = !this.auto_mode;
		render_auto_mode(print_stream);
	}

	void render_auto_mode(java.io.PrintStream print_stream)
	{
		print_stream.print("auto mode = ");
		print_stream.print(this.auto_mode);
		print_stream.println();
	}

	void render_results(java.io.PrintStream print_stream)
	{
		java.util.Collections.sort(this.players);
		java.util.Collections.reverse(this.players);

		print_stream.println("*** GAME OVER! ***");

		int place = 1;
		int index = 1;
		int last_score = 0;

		for (Player player : this.players)
		{
			if (player.get_total() != last_score)
			{
				place = index;
				last_score = player.get_total();
			}

			print_stream.printf("%2d. ", place);
			player.render_total(print_stream);
			print_stream.println();

			index ++;
		}
	}

	void keep(java.io.PrintStream print_stream, java.util.ArrayList<Integer> keep_indexes)
	{
		if (!this.current_player.has_turns())
		{
			render_results(print_stream);
			return;
		}

		for (int index : keep_indexes)
			current_player.keep(index);
			
		current_player.render_turn(print_stream);
	}

	void roll(java.io.PrintStream print_stream)
	{
		if (!this.current_player.has_turns())
		{
			render_results(print_stream);
			return;
		}

		this.current_player.roll();
		this.current_player.render_turn(print_stream);
	}

	void render_turn(java.io.PrintStream print_stream)
	{
		if (!this.current_player.has_turns())
		{
			render_results(print_stream);
			return;
		}

		this.current_player.render_turn(print_stream);
	}

	void take(java.io.PrintStream print_stream, String score_line_name)
	{
		if (!this.current_player.has_turns())
		{
			render_results(print_stream);
			return;
		}

		boolean taken = this.current_player.take(score_line_name);
		if (taken)
		{
			this.current_player.render_score_sheet(print_stream);

			this.current_player_index = (this.current_player_index + 1) % this.players.size();
			this.current_player = this.players.get(this.current_player_index);

			print_stream.println();

			if (!this.current_player.has_turns())
			{
				render_results(print_stream);
				return;
			}
			
			this.current_player.render_turn(print_stream);
		}
	}

	void score(java.io.PrintStream print_stream, Integer index)
	{
		if (index == null)
		{
			if (!this.current_player.has_turns())
			{
				render_results(print_stream);
				return;
			}

			this.current_player.render_score_sheet(print_stream);
		}
		else
		{
			this.players.get(index).render_score_sheet(print_stream);
		}
	}
}
