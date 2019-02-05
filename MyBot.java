
// This Java API uses camelCase instead of the snake_case as documented in the API docs.
//     Otherwise the names of methods are consistent.

import hlt.*;

import java.util.ArrayList;
import java.util.Collections;

public class MyBot {

	public static void main(final String[] args) {

		Game game = new Game();
		// At this point "game" variable is populated with initial map data.
		// This is a good place to do computationally expensive start-up pre-processing.
		// As soon as you call "ready" function below, the 2 second per turn timer will
		// start.
		game.ready("HBLP v10");

		Log.log("Successfully created bot! My Player ID is " + game.myId);

		for (;;) {
			game.updateFrame();
			final Player Jogador = game.me;
			final GameMap Mapa = game.gameMap;
			final Functions f = new Functions(game);

			final ArrayList<Command> comandos = new ArrayList<>();

			for (final Ship ship : Jogador.ships.values()) {
				Log.log("Ship controlado é "+ship.id.id);
				f.Move(ship);
				Log.log("===\n");
			}

			f.SpawnBot();
			comandos.addAll(f.getComandos());

			game.endTurn(comandos);
		}
	}
}
