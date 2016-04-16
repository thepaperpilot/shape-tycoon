package thepaperpilot.shape.Util;

import com.badlogic.ashley.core.ComponentMapper;
import thepaperpilot.shape.Components.ActorComponent;

public class Mappers {
    public static final ComponentMapper<ActorComponent> actor = ComponentMapper.getFor(ActorComponent.class);
}
