import java.util.Scanner;

public class TrainRouteFinder {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Graph trainNetwork = new Graph();

        System.out.println("Enter the number of routes (enter 0 to stop):");
        int numRoutes = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter each route as: <start station> <end station> <fare> <time>");
        System.out.println("Example: Delhi Mumbai 800 1400");

        // Add routes from the input
        for (int i = 0; i < numRoutes; i++) {
            String input = sc.nextLine();
            String[] parts = input.split(" ");
            String start = parts[0];
            String end = parts[1];
            int fare = Integer.parseInt(parts[2]);
            int time = Integer.parseInt(parts[3]);

            trainNetwork.addStation(start);
            trainNetwork.addStation(end);

            trainNetwork.addRoute(start, end, fare, time);
        }

        trainNetwork.displayGraph();

        while (true) {
            System.out.println("\nTrain Network Menu:");
            System.out.println("1. Find Path with Least Station Hops");
            System.out.println("2. Find Path with Least Fare");
            System.out.println("3. Find Path with Least Time");
            System.out.println("4. Exit");

            int choice = sc.nextInt();
            sc.nextLine();

            if (choice == 1) {
                System.out.print("Enter source station: ");
                String source = sc.nextLine();
                System.out.print("Enter destination station: ");
                String destination = sc.nextLine();
                String result = trainNetwork.dijkstra(source, destination, 0);
                System.out.println(result);
            } else if (choice == 2) {
                System.out.print("Enter source station: ");
                String source = sc.nextLine();
                System.out.print("Enter destination station: ");
                String destination = sc.nextLine();
                String result = trainNetwork.dijkstra(source, destination, 1);
                System.out.println(result);
            } else if (choice == 3) {
                System.out.print("Enter source station: ");
                String source = sc.nextLine();
                System.out.print("Enter destination station: ");
                String destination = sc.nextLine();
                String result = trainNetwork.dijkstra(source, destination, 2);
                System.out.println(result);
            } else if (choice == 4) {
                System.out.println("Exiting program...");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        sc.close();
    }
}
