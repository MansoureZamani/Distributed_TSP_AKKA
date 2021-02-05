import javafx.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Management {

    private int cityNumbers;
    private int sectionNumbers;

    public Management(int cityNumbers, int sectionNumbers) {
        this.cityNumbers = cityNumbers;
        this.sectionNumbers = sectionNumbers;
    }

    public int getCityNumbers() {
        return cityNumbers;
    }

    public int getSectionNumbers() {
        return sectionNumbers;
    }

    public List<City> initialRandomCities(){
        List<City> cities = new ArrayList<>();
        for (int i = 0; i < cityNumbers; i++) {
            cities.add(new City("City_"+i));
        }
        return cities;
    }

    public Map<String,List<City>> partitionCities(List<City> cities){
        Map<String,List<City>> Zoning = new HashMap<>();
        Pair<Integer, Integer> bestX_Y = findBestDivisors();
        int xPartitions = 500/bestX_Y.getKey();
        int yPartitions = 500/bestX_Y.getValue();
        for (int i = 1; i <= bestX_Y.getKey(); i++) {
            for (int j = 1; j <= bestX_Y.getValue(); j++) {
                Zoning.put("Area_"+i+"_"+j,new ArrayList<City>());
            }
        }
        for (City city: cities) {
            outer: for (int i = 1; i <= bestX_Y.getKey(); i++) {
                for (int j = 1; j <= bestX_Y.getValue(); j++) {
                    if (city.getX() < i * xPartitions && city.getY() < j * yPartitions) {
                        Zoning.get("Area_" + i + "_" + j).add(city);
                        break outer;
                    }
                }
            }
        }
        return Zoning;
    }

    public Pair<Integer, Integer> findBestDivisors(){
        List<Integer> allDivisor = new ArrayList();
        for (int i=1;i<=sectionNumbers;i++) {
            if (sectionNumbers % i == 0) {
                allDivisor.add(i);
            }
        }
        int middle = allDivisor.size()/2;
        int bestX = allDivisor.get(middle);
        int bestY = sectionNumbers/bestX;
        return new Pair(bestX,bestY);
    }
}
