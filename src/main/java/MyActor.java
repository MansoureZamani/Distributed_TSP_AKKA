import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MyActor extends AbstractLoggingActor {

    Tour tour;
    DrawLines drawLines;
    GraphView graphView;
    JFrame frame;
    ActorSystem system;

    public MyActor(ActorSystem system, GraphView graphView,DrawLines drawLines, JFrame frame) {
        this.drawLines = drawLines;
        this.frame = frame;
        this.graphView =graphView;
        this.system = system;
    }

    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match(City.class, r -> {
                    // write city Lines
                    System.out.println("we are here:" + r.getCityName());
                    List<City> cities = new ArrayList<>();
                    cities.add(r);
                    cities.add(tour.getTour().get(0));
//                    graphView.drawLines(graphView.getGraphics2D(), cities);
                    System.out.println("connected: " + r.getCityName() + " to "+ tour.getTour().get(0).getCityName());
                })
                .match((Class<Pair<List<City>,ActorRef>>)(Object) Pair.class, r -> {
                    System.out.println(this.getSelf() + ": "+ r.getKey().size());
                    SimulatedAnnealing sa = new SimulatedAnnealing();
                    this.tour = sa.calculationOptimalDistance(r.getKey());
                    tour.toString();
//                    Graphics2D g = graphView.getGraphics2D();
//                    drawLines.setCities(tour.getTour());
//                    drawLines.paintComponent(g);
//                    graphView.drawLines(graphView.getGraphics2D(), tour.getTour());
//                    frame.pack();
//                    frame.setLocationRelativeTo(null);
//                    frame.setVisible(true);
//                    this.getSelf().
//
                    if(r.getValue() != null)
                        r.getValue().tell(tour.getTour().get(tour.tourSize()-1), this.getSelf());
                }).build();
    }
    public static Props props(ActorSystem system, GraphView graphView, DrawLines drawLines,
                              JFrame frame) {
        return Props.create(MyActor.class, system, graphView, drawLines, frame);
    }

}