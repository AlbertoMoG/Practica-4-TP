package es.ucm.fdi.tp.was;

import java.util.ArrayList;
import java.util.List;

import es.ucm.fdi.tp.base.model.GameState;
import es.ucm.fdi.tp.ttt.TttAction;
import es.ucm.fdi.tp.ttt.TttState;


public class WasState extends GameState<WasState, WasAction>{

	private static final long serialVersionUID = 2641387354190472377L;
	
	private final int turn;
	private final boolean finished;
	private final int[][]board;
	private final int winner;
	
	private final int dim;
	
	final static int EMPTY = -1;
    final static int BLACK = -2; 
    final static int SHEEP = 1; 
    final static int WOLF = 0; 
    
	public WasState(int dim) 
	{
		 super(2);
	        if (dim < 3 || dim > 8) {
	            throw new IllegalArgumentException("Expected dim to be 3 or 4");
	        }
	        int aux=0;
	        this.dim = dim;
	        board = new int[dim][];
	        for (int i=0; i<dim; i++) {
	            board[i] = new int[dim];
	            for (int j = 0; j < dim; j++) 
	            	{
	            	aux=(int) Math.pow(-1, j);
	            	if(aux==1 ){
	            		if(j % 2 == 0 && (i + 1) % 2 == 0)
	            			board[i][j] = BLACK;
	            		else
	            			board[i][j] = EMPTY;
	            	}
	            	else if(aux==-1){
	            		if(j % 2 == 1 && (i + 1) % 2 == 1)
	            			board[i][j] = BLACK;
	            		else
	            			board[i][j] = EMPTY;
	            	}
	            	else
	            		board[i][j] = EMPTY;
	            	}
	        }
	        
	        
	        //Posición inicial ovejas
	        board[0][1] = SHEEP;
	        board[0][3] = SHEEP;
	        board[0][5] = SHEEP;
	        board[0][7] = SHEEP;
	        
	        //Posición inicial del Lobo
	        board[7][0] = WOLF;
	        
	        
	     System.out.println(toString());

	        this.turn = 0;
	        this.winner = -1;
	        this.finished = false;
	}

	public WasState(WasState prev, int[][] board, boolean finished, int winner) {
    	super(2);
    	this.dim = prev.dim;
        this.board = board;
        this.turn = (prev.turn + 1) % 2;
        this.finished = finished;
        this.winner = winner;
    } 
	
	public boolean isValid(TttAction action) {
        if (isFinished()) {
            return false;
        }
        return at(action.getRow(), action.getCol()) == EMPTY;
    }

    public List<WasAction> validActions(int playerNumber) {
        ArrayList<WasAction> valid = new ArrayList<>();
        if (finished) {
            return valid;
        }
        //0 OVEJA 1 LOBO
        Posicion origen =new Posicion();
        Posicion destino= new Posicion();
      //  int x=0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < dim-1; j++) {//XDXD
                if (playerNumber==1 && at(i, j) == WOLF) {
                //	System.out.println("LOOOOBO"+ x);
                	for(int a = 0; a<4; a++) {
                		//valid.add(getMovWolf(playerNumber, i, j).get(a));
                	}
                //	x++;                	
               }
                else if(playerNumber==0 && at(i, j) == SHEEP){
                	//valid.add(getMovSheep(playerNumber, i, j));
                	for(int y = 0; y<2; y++) //Falta recorrer correctamnete una lista
                	{
                		origen.setPosicion(i, j);
                		//destino.setPosicion(i, j);
                		valid.add(getMovSheep(playerNumber, origen).get(y));
                	}
                }
            }
        }
    
        return valid;
    }
    
   /* private ArrayList<WasAction> getMovWolf(int playerNumber, int f, int c) {
    	ArrayList<WasAction> movsWolf = new ArrayList<>();
    	int[] fil={-1, -1, 1, 1};
    	int[] col={-1, 1, 1, 1};
    	
    	for(int i=0; i<4; i++){
    		int nf=f + fil[i];
    		int nc=c + col[i];
    		if(this.board[nf][nc]==BLACK && this.correcto(nf, nc)){
    			System.out.println("fila "+nf+" col "+nc);
    			//posLobo= new WasAction(playerNumber, nf, nc);
    			movsWolf.add(new WasAction(playerNumber, nf, nc));
    		}
    		
    	}
    	return movsWolf;
    	
    }*/

	public ArrayList<WasAction> getMovSheep(int playerNumber, Posicion origen )
	{
		ArrayList<WasAction> movsSheep = new ArrayList<>();
		
    	int[] fil={1, 1};
    	int[] col={-1, 1};
    	
    	for(int i=0; i<2; i++){
    		int nf=origen.getRow() + fil[i];
    		int nc=origen.getCol() + col[i];
    		if(this.board[nf][nc]==BLACK)
    		{
				if(this.correcto(nf, nc))
				{
					//System.out.println("fila "+nf+" col "+nc);
					//posOveja= new WasAction(playerNumber, nf, nc);
					Posicion destino = new Posicion(nf, nc);
					Posicion ori = new Posicion(origen.getCol(), origen.getRow());
					movsSheep.add(new WasAction(playerNumber, ori, destino));
				}
    		}
    	}
    	return movsSheep;
    }

    private boolean correcto(int nf, int nc) {
		if((nf<0) || (nf>=this.dim) || (nc<0) || (nc>=this.dim)){
			return false;
		}
		else return true;
	}

	public static boolean isDraw(int[][] board) {
        boolean empty = false;
        for (int i=0; ! empty && i<board.length; i++) {
            for (int j=0; ! empty && j<board.length; j++) {
                if (board[i][j] == EMPTY) {
                    empty = true;
                }
            }
        }
        return ! empty;
    }

    private static boolean isWinner(int[][] board, int playerNumber, 
    		int x0, int y0, int dx, int dy) {
        boolean won = true;
        for (int i=0, x=x0, y=y0; won && i<board.length; i++, x+=dx, y+=dy) {
            if (board[y][x] != playerNumber) won = false;
        }
        return won;
    }


    public static boolean isWinner(int[][] board, int playerNumber) {
        boolean won = false;
        for (int i=0; !won && i<board.length; i++) {
            if (isWinner(board, playerNumber, 0, i, 1, 0)) won = true;
            if (isWinner(board, playerNumber, i, 0, 0, 1)) won = true;
        }
        return won ||
                isWinner(board, playerNumber, 0, 0, 1, 1) ||
                isWinner(board, playerNumber, 2, 0, -1, 1);
    }    

    public int at(int row, int col) {
        return board[row][col];
    }

    public int getTurn() {
        return turn;
    }

    public boolean isFinished() {
        return finished;
    }

    public int getWinner() {
        return winner;
    }

    /**
     * @return a copy of the board
     */
    public int[][] getBoard() {
        int[][] copy = new int[board.length][];
        for (int i=0; i<board.length; i++) copy[i] = board[i].clone();
        return copy;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int x=0; x<board.length; x++) sb.append("   "+x);
        sb.append("\n");
        for (int i=0; i<board.length; i++) {
            sb.append(i+"|");
            for (int j=0; j<board.length; j++) {
                sb.append(board[i][j] == EMPTY ? "   |" : board[i][j] == BLACK ? " * |" : board[i][j] == 0 ? " W |" : " S |");
                
            }
            sb.append("\n");
            
        }
        return sb.toString();
    }
}
