import java.util.*;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Graph {
    private Map<String, List<Route>> graph = new HashMap<>();
    private static final String LOG_FILE = "logs/userlog.txt"; // Log file

    // Adds a station to the graph
    public void addStation(String station) {
        graph.putIfAbsent(station, new ArrayList<>());
    }

    // Adds a route between two stations (bi-directional) and logs it
    public void addRoute(String start, String end, int fare, int time) {
        graph.get(start).add(new Route(end, fare, time));
        graph.get(end).add(new Route(start, fare, time));

        // Log the route to the file
        Logger.log("Added Route: " + start + " -> " + end + " | Fare: " + fare + " | Time: " + time);
    }

    // Dijkstra's algorithm to find the best path
    public String dijkstra(String start, String destination, int metric) {
        PriorityQueue<StationInfo> pq = new PriorityQueue<>(Comparator.comparingInt(s -> s.cost));
        Map<String, StationInfo> visited = new HashMap<>();
        Map<String, String> prevStation = new HashMap<>();
        pq.add(new StationInfo(start, 0, 0));

        while (!pq.isEmpty()) {
            StationInfo current = pq.poll();

            if (current.station.equals(destination)) {
                String result = reconstructPath(prevStation, start, destination, current.hops, current.cost);
                Logger.log("Found Path: " + result);
                return result;
            }

            if (visited.containsKey(current.station) && visited.get(current.station).cost <= current.cost) {
                continue;
            }

            visited.put(current.station, current);

            for (Route route : graph.get(current.station)) {
                int newCost = current.cost;
                int newHops = current.hops + 1;

                if (metric == 0) {
                    newCost = current.cost + 1;
                } else if (metric == 1) {
                    newCost = current.cost + route.fare;
                } else if (metric == 2) {
                    newCost = current.cost + route.time;
                }

                StationInfo nextStation = new StationInfo(route.destination, newCost, newHops);
                if (!visited.containsKey(route.destination) || visited.get(route.destination).cost > newCost) {
                    prevStation.put(route.destination, current.station);
                    pq.add(nextStation);
                }
            }
        }

        Logger.log("No path found from " + start + " to " + destination);
        return "No path found";
    }

    // Helper function to reconstruct the path
    private String reconstructPath(Map<String, String> prevStation, String start, String destination, int hops, int cost) {
        List<String> path = new ArrayList<>();
        String current = destination;
        while (current != null) {
            path.add(current);
            current = prevStation.get(current);
        }
        Collections.reverse(path);
        return "Path: " + String.join(" -> ", path) + ", Hops: " + hops + ", Total cost: " + cost;
    }

    // Display the graph
    public void displayGraph() {
        System.out.println("\nTrain Network Graph (Stations and Routes):");
        for (String station : graph.keySet()) {
            System.out.print(station + " -> ");
            List<Route> routes = graph.get(station);
            for (Route route : routes) {
                System.out.print(route.destination + " (Fare: " + route.fare + ", Time: " + route.time + ")  ");
            }
            System.out.println();
        }
    }
}
