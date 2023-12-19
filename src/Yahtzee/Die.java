package Yahtzee;

class Die
{
	int face = 0;
	boolean keeping = false;
	
	Die()
	{
	}
	
	void reset()
	{
		this.face = 0;
		unkeep();
	}
	
	void roll() 
	{
		if(this.keeping == false)
		{
			this.face = (int)(Math.random() * 6f) + 1;
		}
	}
	
	int get_face()
	{
		return this.face;
	}
	
	void keep()
	{
		this.keeping = !this.keeping;
	}
	
	void unkeep()
	{
		this.keeping = false;
	}
}