package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.Align;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Components.AudienceActorComponent;
import thepaperpilot.shape.Components.AudienceComponent;
import thepaperpilot.shape.Components.IdleAnimationComponent;
import thepaperpilot.shape.Player;
import thepaperpilot.shape.Util.Constants;
import thepaperpilot.shape.Util.Mappers;

import java.math.BigDecimal;
import java.util.ArrayList;

public class AudienceSystem extends EntitySystem {

    public Entity player;
    int people = 0;
    public ArrayList<Entity> audience = new ArrayList<Entity>();

    public AudienceSystem() {
        super(1);
    }

    public void addedToEngine (Engine engine) {
        player = new Entity();
        ActorComponent ac = new ActorComponent();
        IdleAnimationComponent ic = new IdleAnimationComponent();
        ac.actor = new Image();
        ac.actor.setPosition(Constants.WORLD_WIDTH / 2f - 32, Constants.UI_HEIGHT + 64);
        ac.actor.setOrigin(Align.center);
        ac.actor.setSize(64, 64);
        ac.actor.addAction(Actions.forever(Actions.delay(3.6f, Actions.sequence(Actions.moveBy(0, 40, 1, Interpolation.pow2), Actions.moveBy(0, -40, 1, Interpolation.pow2)))));
        ac.actor.addAction(Actions.forever(Actions.delay(2f, Actions.rotateBy(360, 1))));
        ac.actor.setOrigin(Align.center);
        ic.file = "square";
        ic.chance = .02f;
        player.add(ac);
        player.add(ic);
        engine.addEntity(player);
    }

    public void update (float deltaTime) {
        if (Player.audience.compareTo(BigDecimal.valueOf(people)) > 0) {
            Entity entity = new Entity();
            AudienceActorComponent ac = new AudienceActorComponent();
            IdleAnimationComponent ic = new IdleAnimationComponent();
            AudienceComponent auc = new AudienceComponent();
            ac.actor = new Image();
            float angle = MathUtils.random(0, 180);
            float x = Constants.WORLD_WIDTH / 2f - 32;
            float y = Constants.UI_HEIGHT + 64;
            ac.actor.setPosition(x + MathUtils.cosDeg(angle) * (200 + 16 * audience.size()), y + MathUtils.sinDeg(angle) * (200 + 16 * audience.size()));
            ac.actor.setSize(64, 64);
            ac.actor.addAction(Actions.moveTo(x + MathUtils.cosDeg(angle) * MathUtils.random(100, 100 + 8 * audience.size()), y + MathUtils.sinDeg(angle) * MathUtils.random(100, 100 + 8 * audience.size()), 1, Interpolation.pow2));
            ic.file = "line";
            ic.chance = 1;
            entity.add(ac);
            entity.add(ic);
            entity.add(auc);
            people += auc.people;
            audience.add(entity);
            getEngine().addEntity(entity);
            while (audience.size() > Constants.MAX_AUDIENCE) {
                getEngine().removeEntity(audience.get(0));
                audience.remove(0);
            }
        } else if (Player.audience.compareTo(BigDecimal.valueOf(people - 1)) < 0) {
            for (final Entity entity : audience) {
                AudienceActorComponent ac = Mappers.audienceActor.get(entity);
                AudienceComponent auc = Mappers.audience.get(entity);

                if (ac.actor.hasActions()) continue;

                people -= auc.people;

                ac.actor.addAction(Actions.sequence(Actions.moveBy(0, 200 + 16 * audience.size(), 1, Interpolation.pow2), Actions.run(new Runnable() {
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
