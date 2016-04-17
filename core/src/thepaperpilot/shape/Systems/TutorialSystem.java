package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Main;

public class TutorialSystem extends EntitySystem {

    private final Table left;
    private final Table right;
    private Table tutorial;

    Entity previousEntity;

    public TutorialSystem(Table left, Table right) {
        super(1);
        this.left = left;
        this.right = right;
    }

    public void addedToEngine (final Engine engine) {
        super.addedToEngine(engine);

        String[] strings = new String[]{
                "Welcome to Shape Tycoon!\nYou are a shapeshifter trying to make it big in the entertainment industry",
                "These are the shapes you can shift into\nYou'll need to balance how long you spend in each shape in order to keep your audience's attention",
                "These are visual indicators for each shape's attention\nIf they get too low then audience members will start to leave",
                "This is your HUD. Money and audience members are straight forward\nYour rank is based on your audience size, and compares you to various sizes of groups of people",
                "Here is your audience. As you grow more popular more and more people will joing your audience here.",
                "These are your upgrades, improving various features of your performance\nHowever, each shape has its own set of upgrades"
        };

        Table previous = tutorial = getTable(strings[0]);
        tutorial.setBackground(Main.skin.getDrawable("default-round-trans"));
        previousEntity = new Entity();
        ActorComponent ac = new ActorComponent();
        ac.actor = tutorial;
        previousEntity.add(ac);
        engine.addEntity(previousEntity);

        for (int i = 1; i < strings.length; i++) {
            final Table table = getTable(strings[i]);
            table.setBackground(new TextureRegionDrawable(new TextureRegion(Main.getTexture("tutorial_" + i))));

            previous.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    Entity tutorial = new Entity();
                    ActorComponent ac = new ActorComponent();
                    ac.actor = TutorialSystem.this.tutorial = table;
                    tutorial.add(ac);
                    engine.addEntity(tutorial);
                    engine.removeEntity(previousEntity);
                    previousEntity = tutorial;
                }
            });

            previous = table;
        }

        previous.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                engine.removeEntity(previousEntity);
                engine.removeSystem(TutorialSystem.this);
                engine.getSystem(AttentionSystem.class).setProcessing(true);
                engine.getSystem(AudienceSystem.class).setProcessing(true);
                engine.getSystem(MoneySystem.class).setProcessing(true);
            }
        });
    }

    public Table getTable(String message) {
        Table table = new Table(Main.skin);
        table.setFillParent(true);
        table.center();
        table.setTouchable(Touchable.enabled);

        Label label = new Label(message, Main.skin);
        label.setAlignment(Align.center);
        table.add(label).spaceBottom(8).row();
        Label continueLabel = new Label("Click to continue", Main.skin);
        continueLabel.setColor(.1289f, .2617f, .3398f, 1);
        continueLabel.getColor().a = 0;
        continueLabel.addAction(Actions.forever(Actions.sequence(Actions.fadeIn(1, Interpolation.pow2), Actions.fadeOut(1, Interpolation.pow2))));
        table.add(continueLabel);
        return table;
    }

    public void update (float deltaTime) {
        tutorial.toFront();
        left.toFront();
        right.toFront();
    }
}
