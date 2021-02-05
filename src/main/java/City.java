public class City {
    private int x;
    private int y;
    private String cityName;

    public City(String cityName, int x, int y){
        this.cityName = cityName;
        this.x = x;
        this.y = y;
    }


    public City(String cityName) {
        this.x = (int) (Math.random() * 500);
        this.y = (int) (Math.random() * 500);
        this.cityName = cityName;
    }

    public double distanceToCity(City city) {
        int x = Math.abs(getX() - city.getX());
        int y = Math.abs(getY() - city.getY());
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
}
