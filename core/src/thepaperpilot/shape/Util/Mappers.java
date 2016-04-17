package thepaperpilot.shape.Util;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import thepaperpilot.shape.Components.*;

import java.nio.DoubleBuffer;

public class Mappers {
    public static final ComponentMapper<ActorComponent> actor = ComponentMapper.getFor(ActorComponent.class);
    public static final ComponentMapper<AudienceComponent> audience = ComponentMapper.getFor(AudienceComponent.class);
    public static final ComponentMapper<IdleAnimationComponent> idleAnimation = ComponentMapper.getFor(IdleAnimationComponent.class);
    public static final ComponentMapper<ParticleEffectComponent> particleEffect = ComponentMapper.getFor(ParticleEffectComponent.class);
    public static final ComponentMapper<AudienceActorComponent> audienceActor = ComponentMapper.getFor(AudienceActorComponent.class);
}
