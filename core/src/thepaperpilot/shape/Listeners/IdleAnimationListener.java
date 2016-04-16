package thepaperpilot.shape.Listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import thepaperpilot.shape.Systems.IdleAnimationSystem;

public class IdleAnimationListener implements EntityListener {
    @Override
    public void entityAdded(Entity entity) {
        IdleAnimationSystem.updateAnimation(entity);
    }

    @Override
    public void entityRemoved(Entity entity) {

    }
}
