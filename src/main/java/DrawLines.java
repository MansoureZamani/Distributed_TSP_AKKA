import javafx.util.Pair;
import java.awt.*;

public final class DrawLines extends Component {

    private Color lineColor;

    private final int frameSize = 1000;
    private Pair<City, City> cityPair;

    public void setCityPair(Pair<City, City> cityPair) {
        this.cityPair = cityPair;
    }
    public void setLineColor(Color lineColor) { this.lineColor = lineColor; }

    public DrawLines() {
        setPreferredSize(new Dimension( frameSize, frameSize));
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