package model;

public class StationEdge {
    public int from;
    public int to;
    public int distance;
    public String IMAGEPATH;

    public StationEdge(int from, int to, int distance,String IMAGEPATH) {
        this.from = from;
        this.to = to;
        this.distance = distance;
        this.IMAGEPATH = IMAGEPATH;
    }
    public String getIMAGEPATH() {
        return IMAGEPATH;
    }
}
