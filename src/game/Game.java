package game;
import java.io.*;

public class Game
{
	private java.util.ArrayList<Player> players;
	private int current_player_index;
	private Player current_player;
	
	public static void main(String[] args) throws Exception
	{
		Game game = new Game();
		game.main();
	}
	
	void main() throws Exception
	{
		set_player_count(1);

		System.out.println();
		System.out.println("In this program you will be playing yahtzee.");
		render_commands(System.out);
		System.out.println();
	
		InputStreamReader console_reader = new InputStreamReader(System.in);
		BufferedReader buffered_console_reader = new BufferedReader(console_reader);
		
		while(true) 
		{
			System.out.println("Please enter a command:");
			
			String input = buffered_console_reader.readLine();
			String[] words = input.split(" ");

			System.out.println();
			
			if (input.equalsIgnoreCase("i"))
			{
				System.out.println("The object of the game is to score the most points in 13 turns. Each turn you take one score after 1-3 rolls. Roll 1 is all 5 dice, any subsequent roll is 1-5 of those.");
				System.out.println();
			}
			else if (input.equalsIgnoreCase("help"))
			{
				render_commands(System.out);
			}
			else if (words[0].equalsIgnoreCase("pc") && words.length == 2)
			{
				set_player_count(Integer.parseInt(words[1]));
			}
			else if (words[0].equalsIgnoreCase("pn") && words.length == 2)
			{
				this.current_player.rename(words[1]);
			}
			else if (words[0].equalsIgnoreCase("q"))
			{
				break;
			}
			else if (words[0].equalsIgnoreCase("k"))
			{
				if (this.current_player.has_turns())
				{
					for(int word_index = 1; word_index < words.length; word_index++)
					{
						try
						{
							int die_number = Integer.parseInt(words[word_index]);
							current_player.keep(die_number - 1);
						}
						catch(Exception e)
						{
						}
					}
					
					current_player.render_turn(System.out);
				}
				else
				{
					render_results(System.out);
				}
			}			
			else if (input.equalsIgnoreCase("r"))
			{
				if (this.current_player.has_turns())
				{
					this.current_player.roll();
					this.current_player.render_turn(System.out);
				}
				else
				{
					render_results(System.out);
				}
			}
			else if (input.equalsIgnoreCase("rr"))
			{
				if (this.current_player.has_turns())
				{
					this.current_player.render_turn(System.out);
				}
				else
				{
					render_results(System.out);
				}
			}
			else if (words[0].equalsIgnoreCase("t") && words.length >= 2)
			{
				if (this.current_player.has_turns())
				{
					StringBuilder name = new StringBuilder();
					name.append(words[1]);

					for (int word_index = 2; word_index < words.length; word_index++)
					{
						name.append(" ");
						name.append(words[word_index]);
					}

					boolean taken = this.current_player.take(name.toString());
					if (taken)
					{
						this.current_player.render_score_sheet(System.out);

						this.current_player_index = (this.current_player_index + 1) % this.players.size();
						this.current_player = this.players.get(this.current_player_index);

						System.out.println();

						if (this.current_player.has_turns())
						{
							this.current_player.render_turn(System.out);
						}
						else
						{
							render_results(System.out);
						}
					}
				}
				else
				{
					render_results(System.out);
				}
			}
			else if (words[0].equalsIgnoreCase("s"))
			{
				if (this.current_player.has_turns())
				{
					this.current_player.render_score_sheet(System.out);
				}
				else
				{
					render_results(System.out);
				}
			}
			else	
			{
				System.out.println(input + " is an invalid command.");
				System.out.println();
				render_commands(System.out);
			}
			
			System.out.println();
		}
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

	void render_results(java.io.PrintStream print_stream)
	{
		java.util.Collections.sort(this.players);
		java.util.Collections.reverse(this.players);

		System.out.println("*** GAME OVER! ***");

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

			System.out.printf("%2d. ", place);
			player.render_total(System.out);
			System.out.println();

			index ++;
		}
}
	
	void render_commands(java.io.PrintStream print_stream)
	{
		print_stream.println("Here are the possible commands:");
		print_stream.println();
		print_stream.println("help = print this list of commands");
		print_stream.println("q = quit");
		print_stream.println("i = instructions on how to play");
		print_stream.println("pc = set the player count to the specified number after the pc");
		print_stream.println("pn = set a player's name");
		print_stream.println("s = display the current player's score sheet");
		print_stream.println("rr = show the dice and remaining rolls");
		print_stream.println("r = roll the dice");
		print_stream.println("k = keep the dice specified by numbers after the k");	
		print_stream.println("t = take the current roll for the given score line");
	}
}
