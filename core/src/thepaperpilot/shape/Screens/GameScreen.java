package thepaperpilot.shape.Screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Listeners.ActorListener;
import thepaperpilot.shape.Systems.StageSystem;
import thepaperpilot.shape.Systems.UISystem;
import thepaperpilot.shape.Util.Constants;

public class GameScreen implements Screen {
    private Engine engine;
    private Stage stage;

    public GameScreen() {
        engine = new Engine();
        stage = new Stage(new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));

        // Listeners
        engine.addEntityListener(Family.all(ActorComponent.class).get(), 10, new ActorListener(stage));

        // Systems
        engine.addSystem(new StageSystem(stage));
        engine.addSystem(new UISystem());
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}
