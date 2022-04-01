package valeron.bondar.model;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@Entity
public class Coordinates {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "coordinates_id")
    private int id;

    @Column(name = "x")
    @Min(-271)
    private int xCoord;

    @Column(name = "y")
    @Min(-595)
    private int yCoord;

    public Coordinates() { }

    public Coordinates(int xCoord, int yCoord) {
        this.xCoord = xCoord;
        this.yCoord = yCoord;
    }

    public int getId() {
        return id;
    }

    public int getxCoord() {
        return xCoord;
    }

    public void setxCoord(int xCoord) {
        this.xCoord = xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }

    public void setyCoord(int yCoord) {
        this.yCoord = yCoord;
    }
}
