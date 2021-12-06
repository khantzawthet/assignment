package origin;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class BrandAndBound {

	private static final String FILE_NAME = "data.txt";

	private static double[][] distances;
	private static final int NODES;
	private static List<City> cities;

	private static List<Route> BaBRoutePerms = new ArrayList<Route>();
	private static double BaBcheapestCost = Double.MAX_VALUE;
	private static Route BaBcheapestRoute;

	// initialize distances
	static {
		try {
			distances = Files.lines(Paths.get(FILE_NAME))
					.map(item -> Pattern.compile("\t").splitAsStream(item).mapToDouble(Double::parseDouble).toArray())
					.toArray(double[][]::new);
		} catch (IOException e) {
			e.printStackTrace();
		}
		NODES = distances[0].length;
	}

	public static void main(String[] args) {

		long time3 = 0;
		// Used to determine number of times the three algorithms should run
		int start;

		// Only individual algorithms should be run during profiling
		for (start = 0; start < NODES; start++) {
			long time = System.currentTimeMillis();

			time = System.currentTimeMillis();

			// Run branch and bound
			branchAndBound(start);
			System.out.println("\tTime:" + (System.currentTimeMillis() - time) + "ms");
			time3 += System.currentTimeMillis() - time;
		}

		// Output average time for functions
		System.out.println("\tBB:" + time3 / start + "ms");
		// Output rough memory usage (profiler is more accurate)
		System.out.println(
				"KB: " + (double) (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) / 1024);
	}

	private static void branchAndBound(int start) {
		System.out.println("branchAndBound:");
		// Setup city list
		resetLists(start);

		// Remove stoke from permutations as always start and end
		List<Integer> cityNums = new ArrayList<Integer>();
		for (int i = 0; i < NODES; i++) {
			cityNums.add(i);
		}

		// Calculate
		permute(new Route(), cityNums, start);
		// Output the number of complete permutations generated NOTE: This is also the
		// number of times the optimal route improved
		System.out.println("\tComplete Permutations: " + BaBRoutePerms.size());
		System.out.println("\t" + BaBcheapestRoute.toString() + "\n\tCost: " + getRouteCost(BaBcheapestRoute));
	}

	private static void permute(Route r, List<Integer> notVisited, int start) {
		if (!notVisited.isEmpty()) {

			for (int i = 0; i < notVisited.size(); i++) {
				
				// Pointer to first city in list
				int temp = notVisited.remove(0);

				Route newRoute = new Route();
				// Lazy copy
				for (City c1 : r.getRoute()) {
					newRoute.getRoute().add(c1);
				}

				// Add the first city from notVisited to the route
				newRoute.getRoute().add(cities.get(temp));

				// If a complete route has not yet been created keep permuting
				if (BaBRoutePerms.isEmpty()) {
					// Recursive call
					permute(newRoute, notVisited, start);
				} else if (getRouteCost(newRoute) < BaBcheapestCost) {
					// Current route cost is less than the best so far so keep permuting
					permute(newRoute, notVisited, start);
				}
				// Add first city back into notVisited list
				notVisited.add(temp);
			}
		} else {
			// Add stoke to start and end of route
			appendStartCity(r, start);
			BaBRoutePerms.add(r);

				System.out.println(BaBRoutePerms.size());
			
			// If shorter than best so far, update best cost
			if (getRouteCost(r) < BaBcheapestCost) {
				BaBcheapestRoute = r;
				BaBcheapestCost = getRouteCost(r);
			}
		}
	}

	private static void resetLists(int start) {
		BaBRoutePerms = new ArrayList<Route>();

		cities = new ArrayList<City>();
		for (int i = 0; i < distances[0].length; i++) {
			cities.add(new City("City: " + (i + 1), i, i == start ? true : false));
		}
	}

	private static void appendStartCity(Route r, int start) {
		r.getRoute().add(0, cities.get(start));
		r.getRoute().add(cities.get(start));
	}

	private static Double getRouteCost(Route r) {
		double tempCost = 0;
		// Add route costs
		for (int i = 0; i < r.getRoute().size() - 1; i++) {
			tempCost += distances[r.getRoute().get(i).getID()][r.getRoute().get(i + 1).getID()];
		}
		return tempCost;
	}
}
