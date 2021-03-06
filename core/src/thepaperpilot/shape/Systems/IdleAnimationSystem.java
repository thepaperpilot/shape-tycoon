package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Components.AudienceActorComponent;
import thepaperpilot.shape.Components.IdleAnimationComponent;
import thepaperpilot.shape.Main;
import thepaperpilot.shape.Util.Mappers;

public class IdleAnimationSystem extends IteratingSystem {
    public IdleAnimationSystem() {
        super(Family.one(ActorComponent.class, AudienceActorComponent.class).all(IdleAnimationComponent.class).get(), 5);
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        IdleAnimationComponent ic = Mappers.idleAnimation.get(entity);
        Actor actor;
        if (Mappers.actor.has(entity))
            actor = Mappers.actor.get(entity).actor;
        else if (Mappers.audienceActor.has(entity))
            actor = Mappers.audienceActor.get(entity).actor;
        else return;

        if (ic.update) {
            updateAnimation(entity);
        }

        if (ic.animating) {
            ic.time += deltaTime;
            if (ic.animation.isAnimationFinished(ic.time)) {
                ic.animating = false;
                ic.time = 0;
            }
            ((Image) actor).setDrawable(new TextureRegionDrawable(ic.animation.getKeyFrame(ic.time)));
        } else if (MathUtils.randomBoolean(ic.chance)) {
            ic.animating = true;
        }
    }

    public static void updateAnimation(Entity entity) {
        IdleAnimationComponent ic = Mappers.idleAnimation.get(entity);
        Actor actor;
        if (Mappers.actor.has(entity))
            actor = Mappers.actor.get(entity).actor;
        else if (Mappers.audienceActor.has(entity))
            actor = Mappers.audienceActor.get(entity).actor;
        else return;

        TextureRegion texture = new TextureRegion(Main.getTexture(ic.file));
        //texture.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        int size = texture.getRegionHeight();
        TextureRegion[] frames = texture.split(size, size)[0];
        ic.animation = new Animation(.1f, frames);

        Image image = (Image) actor;
        image.setDrawable(new TextureRegionDrawable(frames[0]));
        ic.update = false;
    }
}
