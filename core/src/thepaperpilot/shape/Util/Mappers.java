package thepaperpilot.shape.Util;

import com.badlogic.ashley.core.ComponentMapper;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Components.AudienceComponent;
import thepaperpilot.shape.Components.IdleAnimationComponent;
import thepaperpilot.shape.Components.ParticleEffectComponent;

import java.nio.DoubleBuffer;

public class Mappers {
    public static final ComponentMapper<ActorComponent> actor = ComponentMapper.getFor(ActorComponent.class);
    public static final ComponentMapper<AudienceComponent> audience = ComponentMapper.getFor(AudienceComponent.class);
    public static final ComponentMapper<IdleAnimationComponent> idleAnimation = ComponentMapper.getFor(IdleAnimationComponent.class);
    public static final ComponentMapper<ParticleEffectComponent> particleEffect = ComponentMapper.getFor(ParticleEffectComponent.class);
}
