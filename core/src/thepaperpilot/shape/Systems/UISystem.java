package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Components.IdleAnimationComponent;
import thepaperpilot.shape.Main;
import thepaperpilot.shape.Player;
import thepaperpilot.shape.Shape;
import thepaperpilot.shape.Util.Constants;
import thepaperpilot.shape.Util.Mappers;

import java.text.DecimalFormat;

public class UISystem extends EntitySystem {

    public UISystem() {
        super(10);
    }

    private Label money;
    private Label rank;
    private Container rankContainer;
    private Label audience;

    private Table upgradeTable;

    public void addedToEngine (Engine engine) {
        Player.reset();

        Table ui = new Table(Main.skin);
        ui.setFillParent(true);
        ui.bottom();

        Table HUD = new Table(Main.skin);
        money = new Label("", Main.skin);
        rank = new Label("", Main.skin, "large");
        rankContainer = new Container(rank);
        rankContainer.setTransform(true);
        audience = new Label("", Main.skin);
        HUD.bottom().add(money).expandX().uniform();
        HUD.add(rankContainer).expandX().fill().uniform();
        HUD.add(audience).expandX().uniform();
        ui.add(HUD).expandX().fill().expandY().row();

        Table bottom = new Table(Main.skin);
        bottom.setBackground(Main.skin.getDrawable("default-round"));
        Table shapes = new Table(Main.skin);
        shapes.left().top();
        for (final Shape shape : Shape.values()) {
            shapes.add(shape.selectTable).left().spaceBottom(4).row();
            shape.selectTable.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Player.selected != null) {
                        Player.selected.label.setColor(Color.WHITE);
                    }
                    shape.label.setColor(Color.ORANGE);
                    Player.selected = shape;
                    upgradeTable.clearChildren();
                    upgradeTable.add(Player.selected.upgradeTable).expand().fill();

                    AudienceSystem as = getEngine().getSystem(AudienceSystem.class);
                    if (as != null && as.player != null && Mappers.idleAnimation.has(as.player)) {
                        ActorComponent ac = Mappers.actor.get(as.player);
                        final IdleAnimationComponent ic = Mappers.idleAnimation.get(as.player);

                        ac.actor.addAction(Actions.sequence(Actions.moveBy(200, 0, Constants.ANIM_SPEED, Interpolation.pow2), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                ic.file = Player.selected.image;
                                ic.update = true;
                            }
                        }), Actions.moveBy(-200, 0, Constants.ANIM_SPEED, Interpolation.pow2)));
                    }
                }
            });
            if (Player.selected == shape) Player.selected.label.setColor(Color.ORANGE);
        }
        bottom.add(shapes).expandY().fill().pad(Constants.PADDING);
        upgradeTable = new Table(Main.skin);
        upgradeTable.add(Player.selected.upgradeTable).expand().fill();
        bottom.add(upgradeTable).expand().fill();
        ui.add(bottom).expandX().height(Constants.UI_HEIGHT).fill().pad(Constants.PADDING);

        Entity entity = new Entity();
        ActorComponent ac = new ActorComponent();
        ac.actor = ui;
        entity.add(ac);
        engine.addEntity(entity);
    }

    public void update (float deltaTime) {
        for (Shape shape : Shape.values()) {
            shape.attentionBar.setValue(shape.attention);
        }

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);
        money.setText("$" + df.format(Player.money));
        rank.setText(Player.rank.string);
        if (Player.rankUp) {
            rankContainer.setOrigin(Align.center);
            rankContainer.clearActions();
            rankContainer.addAction(Actions.sequence(Actions.scaleTo(2, 2, Constants.ANIM_SPEED, Interpolation.pow2), Actions.scaleTo(1, 1, Constants.ANIM_SPEED, Interpolation.pow2)));
            Player.rankUp = false;
        }
        df.setMaximumFractionDigits(0);
        df.setMinimumFractionDigits(0);
        audience.setText(df.format(Player.audience));
    }
}
