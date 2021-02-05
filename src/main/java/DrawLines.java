import javafx.util.Pair;
import java.awt.*;

public final class DrawLines extends Component {

    private final static Color lineColor = new Color(44, 102, 230, 180);
    private Pair<City, City> cityPair;

    public void setCityPair(Pair<City, City> cityPair) {
        this.cityPair = cityPair;
    }

    public DrawLines() {
        setPreferredSize(new Dimension( 500, 500));
    }
    public void paint(Graphics graphics) {
        final Graphics2D g = (Graphics2D) graphics;
        g.setColor(lineColor);
        g.setStroke(new BasicStroke(2f));
        int yPadding = 30;
        int xPadding = 5;

        g.drawLine(cityPair.getKey().getX()+xPadding, cityPair.getKey().getY()+yPadding,
                   cityPair.getValue().getX()+xPadding, cityPair.getValue().getY()+yPadding);
    }
}