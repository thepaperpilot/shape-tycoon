package thepaperpilot.shape.Screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Components.IdleAnimationComponent;
import thepaperpilot.shape.Listeners.ActorListener;
import thepaperpilot.shape.Listeners.IdleAnimationListener;
import thepaperpilot.shape.Systems.*;
import thepaperpilot.shape.Util.Constants;

public class GameScreen implements Screen {
    private Engine engine;
    private Stage stage;

    public GameScreen() {
        engine = new Engine();
        stage = new Stage(new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));

        // Listeners
        engine.addEntityListener(Family.all(ActorComponent.class).get(), 10, new ActorListener(stage));
        engine.addEntityListener(Family.all(ActorComponent.class, IdleAnimationComponent.class).get(), 5, new IdleAnimationListener());

        // Systems
        engine.addSystem(new StageSystem(stage)); //priority 20
        engine.addSystem(new UISystem()); // priority 10
        engine.addSystem(new AttentionSystem()); //priority 5
        engine.addSystem(new MoneySystem()); //priority 2
        engine.addSystem(new AudienceSystem()); //priority 1
        engine.addSystem(new IdleAnimationSystem()); //priority 5

        // debug
        stage.addListener(new InputListener() {
            boolean debug = false;
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.D:
                        // draw debug (stage)
                        stage.setDebugAll(debug = !debug);
                        break;
                }
                return false;
            }
        });
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
