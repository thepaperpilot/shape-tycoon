package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Components.AudienceComponent;
import thepaperpilot.shape.Components.IdleAnimationComponent;
import thepaperpilot.shape.Player;
import thepaperpilot.shape.Util.Constants;
import thepaperpilot.shape.Util.Mappers;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;

public class AudienceSystem extends EntitySystem {

    public Entity player;
    int people = 0;
    ArrayList<Entity> audience = new ArrayList<Entity>();

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

    public void update (float deltaTime) {
        if (Player.audience.round(MathContext.DECIMAL32).compareTo(BigDecimal.valueOf(people)) > 0) {
            Entity entity = new Entity();
            entity.add(new ActorComponent());
            ActorComponent ac = new ActorComponent();
            IdleAnimationComponent ic = new IdleAnimationComponent();
            AudienceComponent auc = new AudienceComponent();
            ac.actor = new Image();
            ac.actor.setPosition(-50, MathUtils.random(Constants.UI_HEIGHT + 50, Constants.WORLD_HEIGHT - 50));
            ac.actor.setSize(64, 64);
            ac.actor.addAction(Actions.moveBy(MathUtils.random(100, Constants.WORLD_WIDTH - 200), 0, 1, Interpolation.pow2));
            ic.file = "line";
            entity.add(ac);
            entity.add(ic);
            entity.add(auc);
            people += auc.people;
            audience.add(entity);
            getEngine().addEntity(entity);
        } else if (Player.audience.round(MathContext.DECIMAL32).compareTo(BigDecimal.valueOf(people - 1)) < 0) {
            for (final Entity entity : audience) {
                ActorComponent ac = Mappers.actor.get(entity);
                AudienceComponent auc = Mappers.audience.get(entity);

                if (ac.actor.hasActions()) continue;

                people -= auc.people;

                ac.actor.addAction(Actions.sequence(Actions.moveBy(-Constants.WORLD_WIDTH, 0, 1, Interpolation.pow2), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        audience.remove(entity);
                        getEngine().removeEntity(entity);
                    }
                })));
                break;
            }
        }
    }
}
