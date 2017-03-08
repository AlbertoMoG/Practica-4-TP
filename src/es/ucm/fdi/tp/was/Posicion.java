package es.ucm.fdi.tp.was;

public class Posicion 
{
	private int row;
	private int col;
	
	public Posicion()
	{}
	
	
	public Posicion(int row, int col)
	{
		this.row  =row;
		this.col = col;
	}
	
	public int getCol()
	{
		return this.col;
	}
	public int getRow()
	{
		return this.row;
	}
	public void setPosicion(int row, int col)
	{
		this.row = row;
		this.col = col;
	}
}