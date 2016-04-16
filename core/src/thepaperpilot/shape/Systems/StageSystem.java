package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.scenes.scene2d.Stage;

public class StageSystem extends EntitySystem {

    private Stage stage;

    public StageSystem(Stage stage) {
        this.stage = stage;
    }

    public void update (float deltaTime) {
        stage.act(deltaTime);
        stage.draw();
    }
}
