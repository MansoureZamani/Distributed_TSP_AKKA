import akka.actor.AbstractLoggingActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import javafx.util.Pair;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class MasterActor extends AbstractLoggingActor {

    private final DrawLines drawLines;
    private final JFrame frame;
    private final Graphics graphics;
    List<Pair<City,City>> cities = new ArrayList<>();
    int sectionNumbers;


    public MasterActor(Graphics graphics, DrawLines drawLines, JFrame frame, int sectionNumbers) {
        this.drawLines = drawLines;
        this.frame = frame;
        this.graphics =graphics;
        this.sectionNumbers = sectionNumbers;
    }
    @Override
    public Receive createReceive() {
        return receiveBuilder()
                .match((Class<Pair<City, City>>)(Object) Pair.class, r -> {
                    System.out.println("we are here: first is " +r.getKey().getCityName()+ " and " +
                                               "last is " + r.getValue().getCityName());
                    cities.add(r);
                    if(cities.size() == sectionNumbers)
                    {
                        drawLines.setLineColor( new Color(102, 231, 11, 180));
                        for (int i = 0; i < cities.size()-1; i++) {
                            //draw line
                            System.out.println(cities.get(i).getValue().getCityName()+" -> "+
                                                       cities.get(i+1).getKey().getCityName());
                            drawLines.setCityPair(new Pair<>(cities.get(i).getValue(),
                                                             cities.get(i+1).getKey()));
                            drawLines.paint(graphics);
                            drawLines.repaint();
                        }
                        System.out.println(cities.get(cities.size()-1).getValue().getCityName()+
                                                   " -> "+ cities.get(0).getKey().getCityName());
                        drawLines.setCityPair(new Pair<>(cities.get(cities.size()-1).getValue(),
                                                         cities.get(0).getKey()));
                        drawLines.paint(graphics);
                        drawLines.repaint();

                    }
              }).build();
    }

    public static Props props(Graphics graphics, DrawLines drawLines,
                              JFrame frame, int sectionNumbers) {
        return Props.create(MasterActor.class, graphics, drawLines, frame,sectionNumbers);
    }
}
