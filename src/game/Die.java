package game;

public class Die {
	int face;
	boolean keep = false;
	
	public Die()
	{
		roll();
	}
	
	public void roll() 
	{
		if(this.keep == false)
		{
			this.face = (int)(Math.random() * 6f) + 1;
		}
	}
	
	public int get_face()
	{
		return this.face;
	}
	
	public void keep()
	{
		this.keep = true;
	}
	
	public void unkeep()
	{
		this.keep = false;
	}
	
	@Override
	public String toString()
	{
		String text = "";
		
		text = text + this.face;
		
		if (this.keep == true)
		{
			text = text + " (kept)";
		}
		
		return text;
	}
}
