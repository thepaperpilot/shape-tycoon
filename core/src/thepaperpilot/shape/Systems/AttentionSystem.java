package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.EntitySystem;
import thepaperpilot.shape.Shape;
import thepaperpilot.shape.Util.Constants;

public class AttentionSystem extends EntitySystem {

    public AttentionSystem() {
        super(5);
    }

    public void addedToEngine (Engine engine) {
        for (Shape shape : Shape.values()) {
            shape.attention = shape.maxAttention = Constants.BASE_ATTENTION;
        }
    }

    public void update (float deltaTime) {
        UISystem ui = getEngine().getSystem(UISystem.class);

        if (ui == null) return;
        if (ui.selected == null) return;
        for (Shape shape : Shape.values()) {
            if (ui.selected == shape) shape.attention -= deltaTime;
            else shape.attention += deltaTime * Constants.UNBORE_MOD;
            if (shape.attention < 0) shape.attention = 0;
            if (shape.attention > shape.maxAttention) shape.attention = shape.maxAttention;
        }
    }
}
