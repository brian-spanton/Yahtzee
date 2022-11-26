package Yahtzee;

import java.io.*;

public class App
{
	public static void main(String[] args) throws Exception
	{
		Game game = new Game();
		game.set_player_count(1);

		System.out.println();
		System.out.println("In this program you will be playing yahtzee.");
		render_commands(System.out);

		InputStreamReader console_reader = new InputStreamReader(System.in);
		BufferedReader buffered_console_reader = new BufferedReader(console_reader);
		
		while(true) 
		{
            System.out.println();
            System.out.println("Please enter a command:");
			
			String input = buffered_console_reader.readLine();
			String[] words = input.split(" ");
	
			System.out.println();

            boolean success = game.command(System.out, words);
            if (!success)
            {
                if (words[0].equalsIgnoreCase("q"))
				    break;

                System.out.println(words[0] + " is an invalid command.");
                System.out.println();
                render_commands(System.out);
            }
		}
	}
	
	static void render_commands(java.io.PrintStream print_stream)
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
