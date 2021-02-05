import java.util.ArrayList;
import java.util.List;

/*
 * TourManager.java
 * Holds and keeps track of the cities of a tour
 */
public class TourManager {

    // Holds our cities
    private static ArrayList<City> destinationCities = new ArrayList<City>();

    /**
     * Adds a destination city
     *
     * @param cities
     */
    public static void addCity(List<City> cities) {
        destinationCities.addAll(cities);
    }

    /**
     * returns a city given its index
     *
     * @param index
     * @return city the city at index
     */
    public static City getCity(int index) {
        return (City) destinationCities.get(index);
    }

    /**
     * Returns the number of destination cities
     *
     * @return size the number of destination cities
     */
    public static int numberOfCities() {
        return destinationCities.size();
    }
}
