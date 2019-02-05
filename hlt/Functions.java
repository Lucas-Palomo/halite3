package hlt;

import java.util.ArrayList;

public class Functions {

	private final Player Jogador;
	private final Game game;
	private final GameMap mapa;
	public ArrayList<Command> comandos = new ArrayList<>();

	public Functions(Game game) {
		super();
		this.game = game;
		this.Jogador = game.me;
		this.mapa = game.gameMap;
	}

	public void SpawnBot() {
		if (game.turnNumber <= 200 && Jogador.halite >= Constants.SHIP_COST
				&& !mapa.at(Jogador.shipyard).isOccupied()) {
			Log.log("> Spawn Bot");
			comandos.add(Jogador.shipyard.spawn());
		}
	}

	public void Move(Ship ship) {

		Move move = new Move(ship, mapa);
		if (ship.halite >= 600 && !ship.isFull()) {
			if (ship.position.equals(Jogador.shipyard.position)) {
				Log.log("|" + Jogador.halite);
				comandos.add(ship.stayStill());
			} else {
				Direction x = mapa.naiveNavigate(ship, Jogador.shipyard.position);
				comandos.add(ship.move(x));
			}
		} else {
			if (mapa.at(ship.position).halite >= 50) {
				comandos.add(ship.stayStill());
			} else {
				comandos.add(ship.move(move.SnifferHalite()));
			}
		}
	}

	public void CleanComandos() {
		comandos.clear();
	}

	public ArrayList<Command> getComandos() {
		return comandos;
	}

}
