package game;
import java.io.*;
public class Game
{
	Player[] players;
	int currentplayer;
	int playercount;
	
	public static void main(String[] args) throws Exception
	{
		Game game = new Game();
		game.main();
	}
	
	public void main() throws Exception
	{
		
	
		setplayercount(1);
		System.out.println("In this program you will be playing yahtzee.");
		print_commands();
		System.out.println("");
	
		InputStreamReader r=new InputStreamReader(System.in);
		BufferedReader br=new BufferedReader(r);
		
		while(true) 
		{
			System.out.println("Please enter a command:");
			
			String input = br.readLine();
			String[] words = input.split(" ");
			
			if (input.equalsIgnoreCase("i"))
			{
				System.out.println("The object of the game is to win the most points by the end. There will be 13 categories to win points for. You will add these up for your total at the end.");
				System.out.println("");
			}
			
			
			else if (words[0].equalsIgnoreCase("k"))
			{
				for(int iWord = 1; iWord<words.length; iWord++)
				{
					int iDie = Integer.parseInt(words[iWord]);
					this.players[this.currentplayer].keep(iDie-1);
				}
				
				this.players[this.currentplayer].print_dice();
			}
						
			else if (input.equalsIgnoreCase("r"))
			{
				this.players[this.currentplayer].roll();
			}
			
			else if (input.equalsIgnoreCase("rr"))
			{
				this.players[this.currentplayer].print_dice();
			}
			
			else if (input.equalsIgnoreCase("help"))
			{
				print_commands();
			}
			
			else if (words[0].equalsIgnoreCase("pc"))
			{
				setplayercount(Integer.parseInt(words[1]));
			}
			else if (words[0].equalsIgnoreCase("pn"))
			{
				this.players[this.currentplayer].rename(words[1]);
			}
					
			else	
			{
				System.out.println(input + " is an invalid command.");
				System.out.println("");
				print_commands();
			}
			
			System.out.println("");
		}
	}
	
	public void setplayercount(int playercount)
	{
		this.playercount = playercount;
		this.players = new Player[this.playercount];
		
		for(int iPlayer = 0; iPlayer<this.playercount; iPlayer++)
		{
			this.players[iPlayer]=new Player("Player " + (iPlayer + 1));
		}
		
		this.currentplayer = 0;

	}
	
	public void print_commands()
	{
		System.out.println("Here are the possible commands:");
		System.out.println("");
		System.out.println("i = instructions on how to play");
		System.out.println("r = roll the dice");
		System.out.println("rr = show the dice and remaining rolls");
		System.out.println("k = keep the dice specified by numbers after the k");	
		System.out.println("help = print this list of commands");	
		System.out.println("pc = set the player count to the specified number after the pc");
		System.out.println("pn = set a player's name");
	}
	
	
}
