package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Components.IdleAnimationComponent;
import thepaperpilot.shape.Util.Constants;

public class AudienceSystem extends EntitySystem {

    public Entity player;

    public AudienceSystem() {
        super(1);
    }

    public void addedToEngine (Engine engine) {
        player = new Entity();
        ActorComponent ac = new ActorComponent();
        IdleAnimationComponent ic = new IdleAnimationComponent();
        ac.actor = new Image();
        ac.actor.setPosition(Constants.WORLD_WIDTH - 100, Constants.WORLD_HEIGHT - (Constants.WORLD_HEIGHT - Constants.UI_HEIGHT) / 2f);
        ac.actor.setSize(64, 64);
        //((Image) ac.actor).setScaling(Scaling.);
        ic.file = "square";
        player.add(ac);
        player.add(ic);
        engine.addEntity(player);
    }
}
