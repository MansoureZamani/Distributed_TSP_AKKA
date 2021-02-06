import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CalculationActor extends AbstractLoggingActor {


    DrawLines drawLines;
    Graphics graphics;
    JFrame frame;
    ActorRef master;

    public CalculationActor(ActorRef master, Graphics graphics, DrawLines drawLines,
                            JFrame frame) {
        this.drawLines = drawLines;
        this.frame = frame;
        this.graphics =graphics;
        this.master = master;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match((Class<Pair<List<City>,ActorRef>>)(Object) Pair.class, r -> {
                    System.out.println(this.getSelf() + " : "+ r.getKey().size());
                    SimulatedAnnealing sa = new SimulatedAnnealing();
                    Tour tour = sa.calculationOptimalDistance(r.getKey());
                    drawLines.setLineColor( new Color(44, 102, 230, 180));
                    for (int i = 0; i < tour.tourSize()-1 ; i++) {
                        drawLines.setCityPair(new Pair<>(tour.getCity(i), tour.getCity(i+1)));
                        drawLines.paint(graphics);
                        drawLines.repaint();
                    }
//                    Pair<City, City> first_last = new Pair(tour.getCity(0),tour.getCity(
//                            tour.tourSize()-1));
                    master.tell(sa.getFirst_last(), this.getSelf());
                }).build();
    }
    public static Props props(ActorRef master,Graphics graphics, DrawLines drawLines,
                              JFrame frame) {
        return Props.create(CalculationActor.class, master, graphics, drawLines, frame);
    }

}