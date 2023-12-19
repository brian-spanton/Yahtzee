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
			{
				this.out.println("Goodbye!");
				break;
			}
	
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
			render_turn_or_result();
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
	
	void set_player_count(int count)
	{
		this.game = new Game(count);
	}
	
	void rename_player(String name)
	{
		this.game.rename_player(name);
	}
	
	void auto_command()
	{
		if (this.game.is_over())
		{
			render_results();
			return;
		}
	
		for (DiscretionaryScoreLine line : this.game.current_player.score_sheet.discretionary_line_list)
		{
			if (!line.is_available())
				continue;
	
			boolean success = take(line.get_name());
			if (!success)
				break;
	
			return;
		}
	
		roll();
	}
	
	void toggle_auto_mode()
	{
		this.auto_mode = !this.auto_mode;
		render_auto_mode();
	}
	
	void keep(java.util.ArrayList<Integer> dice_indexes)
	{
		if (this.game.is_over())
		{
			render_results();
			return;
		}
	
		this.game.current_player.keep(dice_indexes);
		render_player_turn(this.game.current_player);
	}
	
	void roll()
	{
		if (this.game.is_over())
		{
			render_results();
			return;
		}
	
		this.game.roll();
		render_player_turn(this.game.current_player);
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
	
		render_player_score_sheet(previous_player);
		this.out.println();
	
		if (this.game.is_over())
			render_results();
		else
			render_player_turn(this.game.current_player);
			
		return true;
	}
	
	void score()
	{
		if (this.game.is_over())
		{
			render_results();
			return;
		}
	
		render_player_score_sheet(this.game.current_player);
	}
	
	void score(int number)
	{
		render_player_score_sheet(this.game.players.get(number - 1));
	}
	
	void render_commands()
	{
		this.out.println("Here are the possible commands:");
		this.out.println();
		this.out.println("help = print this list of commands");
		this.out.println("q = quit");
		this.out.println("i = instructions on how to play");
		this.out.println("pc = set the player count to the specified number after the pc");
		this.out.println("pn = set a player's name");
		this.out.println("s = display the current player's score sheet");
		this.out.println("rr = show the dice and remaining rolls");
		this.out.println("r = roll the dice");
		this.out.println("k = keep the dice specified by numbers after the k");	
		this.out.println("t = take the current roll for the given score line");
	}
	
	void render_instructions()
	{
		this.out.println("The object of the game is to score the most points in 13 turns. Each turn you take one score after 1-3 rolls. Roll 1 is all 5 dice, any subsequent roll is 1-5 of those.");
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
			render_player_total(player);
			this.out.println();
	
			index ++;
		}
	}
	
	void render_turn_or_result()
	{
		if (this.game.is_over())
		{
			render_results();
			return;
		}
	
		render_player_turn(this.game.current_player);
	}
	
	void render_player_total(Player player)
	{
		this.out.printf("%16s: %2d", player.name, player.get_total());
	}
	
	void render_player_header(Player player, String header)
	{
		this.out.print("*** ");
		this.out.print(player.name);
		this.out.print("'s ");
		this.out.print(header);
		this.out.println(" ***");
	}
	
	void render_player_score_sheet(Player player)
	{
		render_player_header(player, "SCORE SHEET");
		render_score_sheet(player.score_sheet);
	
		this.out.println();
		render_player_header(player, "TURNS REMAINING");
		this.out.println(player.remaining_turns);
	}
	
	void render_player_turn(Player player)
	{
		render_player_header(player, "TURN!");
	
		if (player.can_take)
		{
			render_player_header(player, "DICE");
			for (int dice_index = 0; dice_index < player.dice.length; dice_index++)
			{
				Die die = player.dice[dice_index];
				int die_number = dice_index + 1;
				this.out.print("Die ");
				this.out.print(die_number);
				this.out.print(": ");
				render_die(die);
				this.out.println();
			}
	
			this.out.println();
			render_player_header(player, "OPTIONS");
			render_available_to_take(player.score_sheet, player.dice);
			this.out.println();
		}
	
		render_player_header(player, "ROLLS REMAINING");
		this.out.println(player.remaining_rolls);
	}
	
	void render_die(Die die)
	{
		this.out.print(die.face);
		
		if (die.keeping == true)
		{
			this.out.print(" (keeping)");
		}
	}
	
	void render_discretionary_score_line(DiscretionaryScoreLine line, Die[] dice)
	{
		int score = line.get_score(dice);
		this.out.print(line.get_name());
		this.out.print(" = ");
		this.out.print(score);
	}	
	
	void render_score_line(ScoreLine line)
	{
		this.out.printf("%13s | %28s | %25s = %3d", line.name, line.subtext, line.description, line.score);
	}
	
	void render_available_to_take(ScoreSheet sheet, Die[] dice)
	{
		for (DiscretionaryScoreLine line : sheet.discretionary_line_list)
		{
			if (!line.is_available())
				continue;
	
			render_discretionary_score_line(line, dice);
			this.out.println();
		}
	}
	
	void render_score_sheet(ScoreSheet sheet)
	{
		for (ScoreLine line : sheet.all_lines)
		{
			render_score_line(line);
			this.out.println();
		}
	}
}