package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.EntitySystem;
import thepaperpilot.shape.Player;
import thepaperpilot.shape.Rank;
import thepaperpilot.shape.Shape;
import thepaperpilot.shape.Util.Constants;

import java.math.BigDecimal;

public class MoneySystem extends EntitySystem {

    public MoneySystem() {
        super(2);
    }

    public void update (float deltaTime) {
        if (Player.selected == null) return;
        BigDecimal money = BigDecimal.ZERO;
        for (Shape shape : Shape.values()) {
            money = money.add(shape.people.multiply(BigDecimal.valueOf(deltaTime)).multiply(BigDecimal.valueOf(shape.moneyPerPerson)));
        }
        Player.money = Player.money.add(money);
        if (Player.rank == null) Player.rank = Rank.BASE_RANK;
        else {
            if (Player.rank.next != null && Player.audience.compareTo(Player.rank.next.rank) >= 0) {
                Player.rankUp = true;
                Player.rank = Player.rank.next;
            }
        }

        BigDecimal add = BigDecimal.valueOf(deltaTime * Player.selected.entertainment * (Player.selected.attention - Constants.BASE_ATTENTION / 2f));
        Player.selected.people = Player.selected.people.add(add);
        Player.audience = Player.audience.add(add);
        if (Player.selected.people.compareTo(BigDecimal.ZERO) < 0) {
            Player.selected.people = BigDecimal.ZERO;
        }
        if (Player.audience.compareTo(BigDecimal.ZERO) < 0) {
            Player.audience = BigDecimal.ZERO;
        }
    }
}
