package thepaperpilot.shape.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import thepaperpilot.shape.Main;
import thepaperpilot.shape.Util.Constants;

public class TitleScreen implements Screen {
    private final Stage stage;

    private Option selected;
    private final Option[] options;

    Table left;
    Table right;

    public TitleScreen() {
        stage = new Stage(new StretchViewport(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT));

        left = new Table(Main.skin);
        left.setBackground(Main.skin.getDrawable("default-round"));
        left.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        left.setPosition(0, Constants.WORLD_HEIGHT / 2f, Align.center);

        right = new Table(Main.skin);
        right.setBackground(Main.skin.getDrawable("default-round"));
        right.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        right.setPosition(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT / 2f, Align.center);

        Table table = new Table(Main.skin);
        Label title = new Label("Shape Tycoon", Main.skin, "large");
        title.setFontScale(3);
        table.setPosition(Constants.WORLD_WIDTH / 2f, 2 * Constants.WORLD_HEIGHT / 3f, Align.center);
        Label sub = new Label("Shift shapes to keep the audience's attention.\nSpend money to attract larger audiences for more money!\nTry to convert the entire world.", Main.skin);
        sub.setColor(.1289f, .2617f, .3398f, 1);
        sub.setAlignment(Align.center);
        table.add(title).spaceBottom(16).row();
        table.add(sub);
        stage.addActor(table);

        Table optionsTable = new Table(Main.skin);
        optionsTable.setFillParent(true);
        optionsTable.pad(40);
        optionsTable.bottom();
        final Option continueGame = new Option("Start Game") {
            @Override
            public void run() {
                left.addAction(Actions.sequence(Actions.moveBy(.5f * Constants.WORLD_WIDTH, 0, 1, Interpolation.bounceOut)));
                right.addAction(Actions.sequence(Actions.moveBy(-.5f * Constants.WORLD_WIDTH, 0, 1, Interpolation.bounceOut), Actions.delay(.2f), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Main.changeScreen(new GameScreen());
                    }
                })));
            }
        };
        final Option exit = new Option("Exit Game") {
            @Override
            public void run() {
                stage.addAction(Actions.sequence(Actions.fadeOut(1), Actions.run(new Runnable() {
                    @Override
                    public void run() {
                        Gdx.app.exit();
                    }
                })));
            }
        };
        optionsTable.add(continueGame).center().row();
        optionsTable.add(exit).center().row();
        stage.addActor(optionsTable);

        this.options = new Option[]{continueGame, exit};
        updateSelected(continueGame);

        stage.addActor(left);
        stage.addActor(right);

        stage.addListener(new InputListener() {
            public boolean keyDown(InputEvent event, int keycode) {
                switch (keycode) {
                    case Input.Keys.E:
                    case Input.Keys.ENTER:
                    case Input.Keys.SPACE:
                        selected.run();
                        break;
                    case Input.Keys.UP:
                    case Input.Keys.W:
                    case Input.Keys.A:
                        if (selected == continueGame) {
                            updateSelected(exit);
                        } else if (selected == exit) {
                            updateSelected(continueGame);
                        }
                        break;
                    case Input.Keys.DOWN:
                    case Input.Keys.S:
                    case Input.Keys.D:
                        if (selected == continueGame) {
                            updateSelected(exit);
                        } else if (selected == exit) {
                            updateSelected(continueGame);
                        }
                        break;
                    default:
                        return false;
                }
                return true;
            }
        });
    }

    private void updateSelected(Option option) {
        selected = option;

        for (Option currOption : options) {
            if (currOption == selected) {
                currOption.setText("> " + currOption.message + " <");
                currOption.setColor(Color.WHITE);
            } else {
                currOption.setText(currOption.message);
                currOption.setColor(.1289f, .2617f, .3398f, 1);
            }
        }
    }

    private abstract class Option extends Label {
        private final String message;

        public Option(CharSequence text) {
            super(text, Main.skin, "large");
            message = (String) text;

            addListener(new InputListener() {
                public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                    run();
                    return true;
                }

                public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                    updateSelected(Option.this);
                }
            });
        }

        public abstract void run();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);

        left.addAction(Actions.sequence(Actions.moveBy(-.5f * Constants.WORLD_WIDTH, 0, 1, Interpolation.pow2)));
        right.addAction(Actions.sequence(Actions.moveBy(.5f * Constants.WORLD_WIDTH, 0, 1, Interpolation.pow2)));
    }

    @Override
    public void render(float delta) {
        stage.act();
        stage.draw();
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
