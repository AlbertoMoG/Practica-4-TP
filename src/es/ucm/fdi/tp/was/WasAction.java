package es.ucm.fdi.tp.was;

import es.ucm.fdi.tp.base.model.GameAction;
import es.ucm.fdi.tp.ttt.TttState;

public class WasAction implements GameAction<WasState, WasAction> 
{
	private static final long serialVersionUID = -8491198872908329925L;
	
	//private int animal;//lobo 0 u ov
	private int player;
	private Posicion origen;
	private Posicion destino;
	//private int col;
	
	public WasAction(int player, Posicion origen, Posicion destino)
	{
		this.player = player;
		this.origen = origen;
		this.destino = destino;
	}

	@Override
	public int getPlayerNumber() 
	{
		return player;
	}

	@Override
	public WasState applyTo(WasState state) {
		if (player != state.getTurn()) 
		{
            throw new IllegalArgumentException("Not the turn of this player");
        }		
		
		 int[][] board = state.getBoard();
	        board[destino.getRow()][destino.getCol()] = player;

	        // update state
	        TttState next;
	        /*if (TttState.isWinner(board, state.getTurn())) {
	            next = new WasState(state, board, true, state.getTurn());
	        } else if (TttState.isDraw(board)) {
	            next = new WasState(state, board, true, -1);
	        } else {
	            next = new WasState(state, board, false, -1);
	        }*/
	        //return next;
	        return null;

	}	

	 public int getRow() {
	        return destino.getRow();
	    }

	    public int getCol() {
	        return destino.getCol();
	    }

	    public String toString() {
	        return "place " + player + "("+origen.getCol()+", "+ origen.getRow() +") at (" + destino.getRow() + ", " + destino.getCol() + ")";
	    }
	
}