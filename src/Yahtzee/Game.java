package Yahtzee;

public class Game
{
	private int current_player_index;
	java.util.ArrayList<Player> players;
	Player current_player;
	
	Game(int player_count)
	{
		if (player_count < 1)
			player_count = 1;
	
		this.players = new java.util.ArrayList<Player>();
	
		for (int player_index = 0; player_index < player_count; player_index++)
			this.players.add(new Player("Player " + (player_index + 1)));
	
		this.current_player_index = 0;
		this.current_player = this.players.get(0);
	}
	
	boolean is_over()
	{
		return !this.current_player.has_turns();
	}
	
	void rename_player(String name)
	{
		this.current_player.rename(name);
	}
	
	boolean roll()
	{
		return this.current_player.roll();
	}
	
	Player take(String score_line_name)
	{
		boolean success = this.current_player.take(score_line_name);
		if (!success)
			return null;
	
		Player previous_player = this.current_player;
		this.current_player_index = (this.current_player_index + 1) % this.players.size();
		this.current_player = this.players.get(this.current_player_index);
	
		return previous_player;
	}
}