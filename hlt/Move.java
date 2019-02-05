package hlt;

import java.util.List;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Random;

public class Move {

	private final Ship ship;
	private final GameMap mapa;
	private final long rngSeed;
	private final Random random;
	private Direction dir;

	public Move(Ship ship, GameMap mapa) {
		super();
		this.ship = ship;
		this.mapa = mapa;
		this.rngSeed = System.nanoTime();
		this.random = new Random(rngSeed);

	}

	private List<Entry<Integer, Position>> area3x3() {

		List<Entry<Integer, Position>> sortArea = new LinkedList<>();

		for (Position pos : ship.position.getArea3x3()) {
			sortArea.add(new AbstractMap.SimpleEntry<Integer, Position>(mapa.at(pos).halite, pos));
		}

		Collections.sort(sortArea, new Comparator<Entry<Integer, Position>>() {

			@Override
			public int compare(Entry<Integer, Position> o1, Entry<Integer, Position> o2) {
				// TODO Auto-generated method stub
				return o2.getKey().compareTo(o1.getKey());
			}

		});

		sortArea.forEach(y -> {
			Log.log("Halite > " + y.getKey() + " | x " + y.getValue().x + " y " + y.getValue().y);
		});

		return sortArea;
	}

	public Direction SnifferHalite() {

		List<Entry<Integer, Position>> area = area3x3();
		List<Entry<Boolean, Direction>> possiveis = new LinkedList<>();

		for (int i = 0; i < area.size(); i++) {
			possiveis.addAll(checkMove(area.get(i).getValue()));
		}

		possiveis.removeIf(p -> (p.getKey() == false));

		// Position p = new Position(Direction., y);
		if (possiveis.isEmpty()) {
			return Direction.STILL;
		}
		Log.log("|" + possiveis.size());
		Log.log("|" + possiveis.get(0).getValue());
		return possiveis.get(0).getValue();

	}

	public List<Entry<Boolean, Direction>> checkMove(Position pos) {

		List<Entry<Boolean, Direction>> possibleMoves = new LinkedList<>();

		// NORTH

		if (ship.position.directionalOffset(Direction.NORTH).equals(pos)) {
			mapa.normalize(pos);

			if (!mapa.at(pos).isOccupied()) {

				mapa.at(pos).markUnsafe(ship);
				possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(true, Direction.NORTH));

			} else {

				if (mapa.at(pos).ship.owner != ship.owner) {
					mapa.at(pos).markUnsafe(ship);
					possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.NORTH));
				}

				if (mapa.at(pos).ship.owner == ship.owner) {
					mapa.at(pos).markUnsafe(ship);
					possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.NORTH));
				}

			}
		} else {
			possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.NORTH));
		}

		// SOUTH

		if (ship.position.directionalOffset(Direction.SOUTH).equals(pos)) {
			mapa.normalize(pos);
			if (!mapa.at(pos).isOccupied()) {

				mapa.at(pos).markUnsafe(ship);
				possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(true, Direction.SOUTH));

			} else {

				if (mapa.at(pos).ship.owner != ship.owner) {
					mapa.at(pos).markUnsafe(ship);
					possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.SOUTH));
				}

				if (mapa.at(pos).ship.owner == ship.owner) {
					mapa.at(pos).markUnsafe(ship);
					possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.SOUTH));
				}

			}
		} else {
			possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.SOUTH));
		}

		// EAST

		if (ship.position.directionalOffset(Direction.EAST).equals(pos)) {
			mapa.normalize(pos);
			if (!mapa.at(pos).isOccupied()) {

				mapa.at(pos).markUnsafe(ship);
				possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(true, Direction.EAST));

			} else {

				if (mapa.at(pos).ship.owner != ship.owner) {
					mapa.at(pos).markUnsafe(ship);
					possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.EAST));
				}

				if (mapa.at(pos).ship.owner == ship.owner) {
					mapa.at(pos).markUnsafe(ship);
					possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.EAST));
				}

			}
		} else {
			possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.EAST));
		}

		// WEST

		if (ship.position.directionalOffset(Direction.WEST).equals(pos)) {
			if (!mapa.at(pos).isOccupied()) {

				mapa.at(pos).markUnsafe(ship);
				possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(true, Direction.WEST));

			} else {

				if (mapa.at(pos).ship.owner != ship.owner) {
					mapa.at(pos).markUnsafe(ship);
					possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.WEST));
				}

				if (mapa.at(pos).ship.owner == ship.owner) {
					mapa.at(pos).markUnsafe(ship);
					possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.WEST));
				}

			}
		} else {
			possibleMoves.add(new AbstractMap.SimpleEntry<Boolean, Direction>(false, Direction.WEST));
		}

		return possibleMoves;
	}

	public Direction RandomDir() {

		dir = Direction.ALL_CARDINALS.get(random.nextInt(4));
		return dir;
	}

	public Direction forced(Player jogador) {
		Log.log("forced");
		ArrayList<Direction> cross = mapa.getUnsafeMoves(ship.position, jogador.shipyard.position);
		if (jogador.ships.size() >= 2) {
			for (Direction p : cross) {

				return p;

			}
		}

		return Direction.STILL;
	}

}
