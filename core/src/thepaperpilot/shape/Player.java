package thepaperpilot.shape;

import java.math.BigDecimal;

public class Player {

    public static Shape selected;
    public static BigDecimal money;
    public static BigDecimal audience;
    public static Rank rank;
    public static boolean rankUp;

    public static void reset() {
        selected = Shape.SQUARE;
        money = BigDecimal.ZERO;
        audience = BigDecimal.ZERO;
        rank = Rank.BASE_RANK;
    }
}
