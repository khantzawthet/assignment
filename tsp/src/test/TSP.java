package test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class TSP {

	private static final String FILE_NAME = "data.txt";

	private static int [][] distances;
	private static final int NODES;
	private static List<City> cities;

	// initialize distances
	static {
		try {
			distances = Files.lines(Paths.get(FILE_NAME))
					.map(item -> Pattern.compile("\t")
							.splitAsStream(item)
							.mapToInt(Integer::parseInt)
							.toArray())
					.toArray(int [][]::new);
		} catch (IOException e) {
			e.printStackTrace();
		}
		NODES = distances[0].length;
	}

	public static void main(String[] args) {

		long timeNNB = 0;
		int startCity;
		
		for (startCity = 0; startCity < NODES; startCity++) {

			long time = System.currentTimeMillis();
			nearestNeighbour(startCity);
			System.out.println("\tTime:" + (System.currentTimeMillis() - time) + "ms");
			
			timeNNB += System.currentTimeMillis() - time;
			time = System.currentTimeMillis();
		}

		System.out.println("Avg Time NN:" + timeNNB / NODES + "ms");
		System.out.println("KB: " + (double) (Runtime.getRuntime().totalMemory() 
				- Runtime.getRuntime().freeMemory()) / 1024);
	}

	private static void nearestNeighbour(int start) {

		resetLists(start);
		int routeCost = 0;
		Route nearestRoute = new Route(cities.get(start));

		while (nearestRoute.getRoute().size() != cities.size()) {

			City neighbourCity = null;
			int neighbourDistance = Integer.MAX_VALUE;

			for (int i = 0; i < NODES; i++) {

				if (getDistance(nearestRoute, i) < neighbourDistance
						&& getDistance(nearestRoute, i) != 0
						&& cities.get(i).isVisited() == false) {

					// Update closest neighbour
					neighbourCity = cities.get(i);
					neighbourDistance = getDistance(nearestRoute, i);
				}
			}
			
			if (neighbourCity != null) {
				nearestRoute.getRoute().add(neighbourCity);
				nearestRoute.setCurrentCity(neighbourCity);
				neighbourCity.setVisited(true);
				routeCost += neighbourDistance;
			}
		}

		// Add cost to return toCurrent StartCity
		routeCost += getDistance(nearestRoute, nearestRoute.getCurrentCity().getId());
		// Add stoke to route end
		nearestRoute.getRoute().add(cities.get(start));
		System.out.println("\t" + nearestRoute.toString() + "\n\tCost: " + routeCost);
	}

	private static int getDistance(Route nearestRoute, int i) {
		return distances[nearestRoute.getCurrentCity().getId()][i];
	}

	private static void resetLists(int start) {
		cities = new ArrayList<City>();
		for (int i = 0; i < NODES; i++) {
			cities.add(new City("City: " + (i + 1), i, i == start ? true : false));
		}
	}
}
