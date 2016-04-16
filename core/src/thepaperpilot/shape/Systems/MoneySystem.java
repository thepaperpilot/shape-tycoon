package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.EntitySystem;
import thepaperpilot.shape.Player;
import thepaperpilot.shape.Rank;
import thepaperpilot.shape.Util.Constants;

import java.math.BigDecimal;

public class MoneySystem extends EntitySystem {

    public MoneySystem() {
        super(2);
    }

    public void update (float deltaTime) {
        if (Player.selected == null) return;

        Player.money = Player.money.add(Player.audience.multiply(BigDecimal.valueOf(Player.selected.moneyPerPerson).multiply(BigDecimal.valueOf(deltaTime))));
        if (Player.rank == null) Player.rank = Rank.BASE_RANK;
        else {
            if (Player.audience.compareTo(Player.rank.next.rank) >= 0) {
                Player.rankUp = true;
                Player.rank = Player.rank.next;
            }
        }

        // temporary
        Player.audience = Player.audience.add(BigDecimal.valueOf(deltaTime * Player.selected.entertainment * (Player.selected.attention - Constants.BASE_ATTENTION / 2f)));
        if (Player.audience.compareTo(BigDecimal.ZERO) < 0) {
            Player.audience = BigDecimal.ZERO;
        }
    }
}
