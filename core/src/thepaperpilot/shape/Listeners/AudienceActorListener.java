package thepaperpilot.shape.Listeners;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntityListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import thepaperpilot.shape.Components.AudienceActorComponent;
import thepaperpilot.shape.Util.Mappers;

public class AudienceActorListener implements EntityListener {

    private Stage stage;

    public AudienceActorListener(Stage stage) {
        this.stage = stage;
    }

    @Override
    public void entityAdded(Entity entity) {
        AudienceActorComponent ac = Mappers.audienceActor.get(entity);

        stage.addActor(ac.actor);

        ac.actor.toBack();
    }

    @Override
    public void entityRemoved(Entity entity) {
        AudienceActorComponent ac = Mappers.audienceActor.get(entity);

        ac.actor.remove();
    }
}
