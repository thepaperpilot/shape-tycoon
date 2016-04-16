package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.graphics.g2d.Batch;
import thepaperpilot.shape.Components.ParticleEffectComponent;
import thepaperpilot.shape.Util.Mappers;

public class ParticleEffectSystem extends IteratingSystem {

    private Batch batch;

    public ParticleEffectSystem(Batch batch) {
        super(Family.all(ParticleEffectComponent.class).get(), 25);
        this.batch = batch;
    }

    public void update (float deltaTime) {
        batch.begin();
        super.update(deltaTime);
        batch.end();
    }

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        ParticleEffectComponent pc = Mappers.particleEffect.get(entity);

        pc.effect.draw(batch, deltaTime);

        if (pc.effect.isComplete()) getEngine().removeEntity(entity);
    }
}
