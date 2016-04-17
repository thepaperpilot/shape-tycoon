package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class AudienceRenderSystem extends EntitySystem {

    private Stage stage;

    public AudienceRenderSystem(Stage stage) {
        super(19);
        this.stage = stage;
    }

    public void update (float deltaTime) {
        OrthographicCamera camera = ((OrthographicCamera) stage.getCamera());
        float targetZoom = 1 + MathUtils.log(2, stage.getActors().size) / 10f;
        if (Math.abs(camera.zoom - targetZoom) < deltaTime) {
            camera.zoom = targetZoom;
        } else {
            if (camera.zoom > targetZoom) camera.zoom -= deltaTime;
            else camera.zoom += deltaTime;
        }

        stage.act(deltaTime);
        stage.draw();
    }
}
