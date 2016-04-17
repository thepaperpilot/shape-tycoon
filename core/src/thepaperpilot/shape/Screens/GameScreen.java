package thepaperpilot.shape.Screens;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Family;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Components.AudienceActorComponent;
import thepaperpilot.shape.Components.IdleAnimationComponent;
import thepaperpilot.shape.Listeners.ActorListener;
import thepaperpilot.shape.Listeners.AudienceActorListener;
import thepaperpilot.shape.Listeners.IdleAnimationListener;
import thepaperpilot.shape.Main;
import thepaperpilot.shape.Systems.*;
import thepaperpilot.shape.Util.Constants;

public class GameScreen implements Screen {
    public Engine engine;
    private Stage ui;
    private Stage stage;

    Table left;
    Table right;

    public GameScreen() {
        engine = new Engine();
        ui = new Stage(new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));
        stage = new Stage(new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));

        // Listeners
        engine.addEntityListener(Family.all(ActorComponent.class).get(), 10, new ActorListener(ui));
        engine.addEntityListener(Family.one(ActorComponent.class, AudienceActorComponent.class).all(IdleAnimationComponent.class).get(), 5, new IdleAnimationListener());
        engine.addEntityListener(Family.all(AudienceActorComponent.class).get(), 10, new AudienceActorListener(stage));

        // Systems
        engine.addSystem(new StageSystem(ui)); //priority 20
        engine.addSystem(new UISystem()); // priority 10
        engine.addSystem(new AttentionSystem()); //priority 5
        engine.addSystem(new MoneySystem()); //priority 2
        engine.addSystem(new AudienceSystem()); //priority 1
        engine.addSystem(new IdleAnimationSystem()); //priority 5
        engine.addSystem(new ParticleEffectSystem(ui.getBatch())); //priority 25
        engine.addSystem(new AudienceRenderSystem(stage)); //priority 19

        // debug
        ui.addListener(new InputListener() {
            boolean debug = false;
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.D:
                        // draw debug (ui)
                        ui.setDebugAll(debug = !debug);
                        break;
                }
                return false;
            }
        });

        left = new Table(Main.skin);
        left.setBackground(Main.skin.getDrawable("default-round"));
        left.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        left.setPosition(0, Constants.WORLD_HEIGHT / 2f, Align.center);

        right = new Table(Main.skin);
        right.setBackground(Main.skin.getDrawable("default-round"));
        right.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        right.setPosition(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT / 2f, Align.center);

        ui.addActor(left);
        ui.addActor(right);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(ui);

        left.addAction(Actions.sequence(Actions.moveBy(-.5f * Constants.WORLD_WIDTH, 0, 1, Interpolation.pow2)));
        right.addAction(Actions.sequence(Actions.moveBy(.5f * Constants.WORLD_WIDTH, 0, 1, Interpolation.pow2)));
    }

    @Override
    public void render(float delta) {
        engine.update(delta);
    }

    @Override
    public void resize(int width, int height) {
        ui.getViewport().update(width, height);
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
        ui.dispose();
        stage.dispose();
    }
}
