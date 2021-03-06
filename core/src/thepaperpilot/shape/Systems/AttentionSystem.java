package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import thepaperpilot.shape.Player;
import thepaperpilot.shape.Shape;
import thepaperpilot.shape.Util.Constants;

import java.math.BigDecimal;

public class AttentionSystem extends EntitySystem {

    public AttentionSystem() {
        super(5);
    }

    public void addedToEngine (Engine engine) {
        for (Shape shape : Shape.values()) {
            shape.attention = shape.maxAttention = Constants.BASE_ATTENTION;
            shape.moneyPerPerson = Constants.BASE_MONEY_PER_PERSON;
            shape.entertainment = Constants.BASE_ENTERTAINMENT;
            shape.unbore = Constants.BASE_UNBORE;
            shape.people = BigDecimal.ZERO;
        }
    }

    public void update (float deltaTime) {
        if (Player.selected == null) return;
        for (Shape shape : Shape.values()) {
            if (Player.selected == shape) shape.attention -= deltaTime / shape.attentionSpeed;
            else shape.attention += deltaTime * shape.unbore;
            if (shape.attention < 0) shape.attention = 0;
            if (shape.attention > shape.maxAttention) shape.attention = shape.maxAttention;
        }
    }
}
