import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

public class SimulatedAnnealing {
    private Pair<City, City> first_last ;

    public Pair<City, City> getFirst_last() {
        return first_last;
    }

    public Tour calculationOptimalDistance(List<City> cities) {
        //Set initial temp
        double temp = 100000;

        //Cooling rate
        double coolingRate = 0.000003;

        //create random intial solution
        Tour currentSolution = new Tour((ArrayList<City>) cities);
        currentSolution.generateIndividual();

        System.out.println("Total distance of initial solution: " + currentSolution.getTotalDistance());

        // We would like to keep track if the best solution
        // Assume best solution is the current solution
        Tour best = new Tour(currentSolution.getTour());

        // Loop until system has cooled
        while (temp > 1) {
            // Create new neighbour tour
            Tour newSolution = new Tour(currentSolution.getTour());

            // Get random positions in the tour
            int tourPos1 = Utility.randomInt(0 , newSolution.tourSize());
            int tourPos2 = Utility.randomInt(0 , newSolution.tourSize());

            //to make sure that tourPos1 and tourPos2 are different
            while(tourPos1 == tourPos2) {tourPos2 = Utility.randomInt(0 , newSolution.tourSize());}

            // Get the cities at selected positions in the tour
            City citySwap1 = newSolution.getCity(tourPos1);
            City citySwap2 = newSolution.getCity(tourPos2);

            // Swap them
            newSolution.setCity(tourPos2, citySwap1);
            newSolution.setCity(tourPos1, citySwap2);

            // Get energy of solutions
            int currentDistance   = currentSolution.getTotalDistance();
            int neighbourDistance = newSolution.getTotalDistance();

            // Decide if we should accept the neighbour
            double rand = Utility.randomDouble();
            if (Utility.acceptanceProbability(currentDistance, neighbourDistance, temp) > rand) {
                currentSolution = new Tour(newSolution.getTour());
            }

            // Keep track of the best solution found
            if (currentSolution.getTotalDistance() < best.getTotalDistance()) {
                best = new Tour(currentSolution.getTour());
            }

            // Cool system
            temp *= 1 - coolingRate;
        }

//        System.out.println("Final solution distance: " + best.getTotalDistance());
//        System.out.println("Tour: " + best);
        first_last = new Pair(best.getCity(0),best.getCity(
                best.tourSize()-1));
        System.out.println("best Tour: " + best);
        return best;
    }
}