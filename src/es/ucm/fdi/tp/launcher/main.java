package es.ucm.fdi.tp.launcher;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import es.ucm.fdi.tp.base.console.*;
import es.ucm.fdi.tp.base.model.*;
import es.ucm.fdi.tp.base.player.*;
import es.ucm.fdi.tp.ttt.TttState;



public class main {

    public main() {
        // TODO Auto-generated constructor stub
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        
        
        List<GamePlayer> players = new ArrayList<GamePlayer>();
        if(args.length==3){
        	String juego = args[0];
            String j1=args[1];
            String j2=args[2];
            GameState<?, ?> game=null;
            game=createInitialState(juego);
            
            players.add(createPlayer(juego, j1, "Player 1"));
            players.add(createPlayer(juego, j2, "Player 2"));
            
            playGame(game, players);
            
        }
        
    }

    public static GameState<?,?> createInitialState(String gameName){
        GameState<?, ?> game=null;
        if(gameName.equals("ttt")){
            System.out.println("Jugando al 3 en raya\n");
            game= new TttState(3); 
        }
        else throw new GameError("Juego incorrecto");
        return game;
        
    }
    
    public static GamePlayer createPlayer(String gameName, String playerType, String playerName) throws GameError{
        GamePlayer jugador=null;
        switch(playerType){
        case "random":
            jugador=new RandomPlayer(playerName);
            break;
        case "console":
            Scanner s = new Scanner(System.in);
            jugador=new ConsolePlayer(playerName, s);
            break;
        case "smart":
            jugador=new SmartPlayer(playerName, 5);
            break;
        }
        return jugador;
        
    }

    public static <S extends GameState<S, A>, A extends GameAction<S, A>> int playGame(GameState<S, A> initialState, List<GamePlayer> players) {
        int playerCount = 0;
        for (GamePlayer p : players) {
            p.join(playerCount++); // welcome each player, and assign
                                    // playerNumber
        }
        @SuppressWarnings("unchecked")
        S currentState = (S) initialState;

        while (!currentState.isFinished()) {
            // request move
            A action = players.get(currentState.getTurn()).requestAction(currentState);
            // apply move
            currentState = action.applyTo(currentState);
            System.out.println("After action:\n" + currentState);

            if (currentState.isFinished()) {
                // game over
                String endText = "The game ended: ";
                int winner = currentState.getWinner();
                if (winner == -1) {
                    endText += "draw!";
                } else {
                    endText += "player " + (winner + 1) + " (" + players.get(winner).getName() + ") won!";
                }
                System.out.println(endText);
            }
        }
        return currentState.getWinner();
    }
}



