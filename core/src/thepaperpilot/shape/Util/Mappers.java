package thepaperpilot.shape.Util;

import com.badlogic.ashley.core.ComponentMapper;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Components.IdleAnimationComponent;

import java.nio.DoubleBuffer;

public class Mappers {
    public static final ComponentMapper<ActorComponent> actor = ComponentMapper.getFor(ActorComponent.class);
    public static final ComponentMapper<IdleAnimationComponent> idleAnimation = ComponentMapper.getFor(IdleAnimationComponent.class);
}
