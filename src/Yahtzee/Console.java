package Yahtzee;

public class Console
{
	private Game game;
	private java.io.PrintStream out = System.out;
    private boolean auto_mode = false;	

	public static void main(String[] args)
	{
		Console console = new Console();
		console.run();
	}

	void run()
	{
		this.game = new Game(1);

		this.out.println();
		this.out.println("In this program you will be playing yahtzee.");
		render_commands();

		java.io.InputStreamReader console_reader = new java.io.InputStreamReader(System.in);
		java.io.BufferedReader in = new java.io.BufferedReader(console_reader);
			
		while(true) 
		{
            this.out.println();
            this.out.println("Please enter a command:");
			
			String command;
			
			try
			{
				command = in.readLine();
			}
			catch (Exception e)
			{
				return;
			}
	
			this.out.println();

			String[] words = command.split(" ");

            CommandResult result = command(words);
			if (result == CommandResult.exit)
				break;

            if (result == CommandResult.invalid)
            {
                this.out.println(words[0] + " is an invalid command.");
                this.out.println();
                render_commands();
            }
		}
	}

	enum CommandResult
	{
		success,
		exit,
		invalid,
	}
	
	CommandResult command(String[] words)
	{
		if (words.length == 0 || words[0].length() == 0)
		{
			if (!this.auto_mode)
				return CommandResult.invalid;

			auto_command();
		}
		else if (words[0].equalsIgnoreCase("q"))
		{
			return CommandResult.exit;
		}
		else if (words[0].equalsIgnoreCase("i"))
		{
			render_instructions();
		}
		else if (words[0].equalsIgnoreCase("help"))
		{
			render_commands();
		}
		else if (words[0].equalsIgnoreCase("pc"))
		{
			if (words.length != 2)
				return CommandResult.invalid;

			int count;

			try { count = Integer.parseInt(words[1]); }
			catch(Exception e) { return CommandResult.invalid; }

			set_player_count(count);
		}
		else if (words[0].equalsIgnoreCase("pn"))
		{
			if (words.length != 2)
				return CommandResult.invalid;

			rename_player(words[1]);
		}
		else if (words[0].equalsIgnoreCase("a"))
		{
			toggle_auto_mode();
		}
        else if (words[0].equalsIgnoreCase("g"))
        {
            if (!this.auto_mode)
                return CommandResult.invalid;
            
            auto_finish();
        }
		else if (words[0].equalsIgnoreCase("k"))
		{
			java.util.ArrayList<Integer> keep_indexes = new java.util.ArrayList<Integer>();

			for (int word_index = 1; word_index < words.length; word_index++)
			{
				int die_number;

				try { die_number = Integer.parseInt(words[word_index]); }
				catch(Exception e) { return CommandResult.invalid; }

				int index = die_number - 1;
				keep_indexes.add(index);
			}

			keep(keep_indexes);
		}			
		else if (words[0].equalsIgnoreCase("r"))
		{
			roll();
		}
		else if (words[0].equalsIgnoreCase("rr"))
		{
			render_turn();
		}
		else if (words[0].equalsIgnoreCase("t"))
		{
			if (words.length < 2)
				return CommandResult.invalid;

			StringBuilder name = new StringBuilder();
			name.append(words[1]);

			for (int word_index = 2; word_index < words.length; word_index++)
			{
				name.append(" ");
				name.append(words[word_index]);
			}

			take(name.toString());
		}
		else if (words[0].equalsIgnoreCase("s"))
		{		
			if (words.length > 2)
				return CommandResult.invalid;

			if (words.length == 2)
			{
				int index;
				try { index = Integer.parseInt(words[1]); }
				catch (Exception e) { return CommandResult.invalid; }

				score(index);
			}
			else
			{
				score();
			}
		}
		else	
		{
			return CommandResult.invalid;
		}

		return CommandResult.success;
	}
	
	void render_commands()
	{
		this.out.println("Here are the possible commands:");
		this.out.println();
		this.out.println("help = show this list of commands");
		this.out.println("q = quit");
		this.out.println("i = show instructions on how to play");
		this.out.println("pc = reset the game with the specified number of players");
		this.out.println("pn = set the current player's name");
		this.out.println("s = show the current player's score sheet");
		this.out.println("rr = show the current player's dice and remaining rolls");
		this.out.println("r = roll the dice");
		this.out.println("k = toggle keeping for the dice specified");	
		this.out.println("t = take the current roll for the current player's specified score line");
        this.out.println("a = toggle auto mode");
        this.out.println("<enter> = (auto mode) take or roll");
        this.out.println("g = (auto mode) finish game");
	}

	void set_player_count(int count)
	{
		this.game = new Game(count);
	}

	void rename_player(String name)
	{
		this.game.rename_player(name);
	}

	void render_instructions()
	{
		this.out.println("The object of the game is to score the most points in 13 turns. Each turn you take one score after 1-3 rolls. Roll 1 is all 5 dice, any subsequent roll is 1-5 of those.");
	}

	boolean auto_command()
	{
		if (this.game.is_over())
		{
			render_results();
			return false;
		}

		for (DiscretionaryScoreLine line : this.game.current_player.score_sheet.discretionary_line_list)
		{
			if (!line.is_available())
				continue;

			boolean success = take(line.get_name());
			if (!success)
				break;

			return true;
		}

		roll();
        return true;
	}

    void auto_finish()
    {
        while (!this.game.is_over())
    		auto_command();                   
    }

	void toggle_auto_mode()
	{
		this.auto_mode = !this.auto_mode;
		render_auto_mode();
	}

	void render_auto_mode()
	{
		this.out.print("auto mode = ");
		this.out.print(this.auto_mode);
		this.out.println();
	}

	void render_results()
	{
		this.out.println("*** GAME OVER! ***");

		int place = 1;
		int index = 1;
		int last_score = 0;

		java.util.Collections.sort(this.game.players);
		java.util.Collections.reverse(this.game.players);

		for (Player player : this.game.players)
		{
			if (player.get_total() != last_score)
			{
				place = index;
				last_score = player.get_total();
			}

			this.out.printf("%2d. ", place);
			player.render_total(this.out);
			this.out.println();

			index ++;
		}
	}

	void keep(java.util.ArrayList<Integer> dice_indexes)
	{
		if (this.game.is_over())
		{
			render_results();
			return;
		}

		this.game.current_player.keep(dice_indexes);
		this.game.current_player.render_turn(this.out);
	}

	void roll()
	{
		if (this.game.is_over())
		{
			render_results();
			return;
		}

		this.game.roll();
		this.game.current_player.render_turn(this.out);
	}

	void render_turn()
	{
		if (this.game.is_over())
		{
			render_results();
			return;
		}

		this.game.current_player.render_turn(this.out);
	}

	boolean take(String score_line_name)
	{
		if (this.game.is_over())
		{
			render_results();
			return false;
		}

		Player previous_player = this.game.take(score_line_name);
		if (previous_player == null)
			return false;

		previous_player.render_score_sheet(this.out);
		this.out.println();

		if (this.game.is_over())
			render_results();
		else
			this.game.current_player.render_turn(this.out);
			
		return true;
	}

	void score()
	{
		if (this.game.is_over())
		{
			render_results();
			return;
		}

		this.game.current_player.render_score_sheet(this.out);
	}

	void score(int number)
	{
		this.game.players.get(number - 1).render_score_sheet(this.out);
	}
}
