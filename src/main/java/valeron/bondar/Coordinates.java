package valeron.bondar;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "COORDINATES")
public class Coordinates {
    @Id
    @Column(name = "COORDINATES_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coordinatesId;

    @Column(name = "X", nullable = false)
    private Double x; //Поле не может быть null

    @Column(name = "Y", nullable = false)
    private Integer y; //Максимальное значение поля: 350, Поле не может быть null
}
