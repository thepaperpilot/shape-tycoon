package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Components.IdleAnimationComponent;
import thepaperpilot.shape.Components.ParticleEffectComponent;
import thepaperpilot.shape.Main;
import thepaperpilot.shape.Player;
import thepaperpilot.shape.Shape;
import thepaperpilot.shape.Util.Constants;
import thepaperpilot.shape.Util.Mappers;

import java.text.DecimalFormat;

public class UISystem extends EntitySystem {

    private Table HUD;

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

        HUD = new Table(Main.skin);
        money = new Label("", Main.skin);
        rank = new Label("", Main.skin, "large");
        rankContainer = new Container(rank);
        rankContainer.setTransform(true);
        audience = new Label("", Main.skin);
        HUD.bottom().add("bank").expandX().uniform();
        HUD.add("rank").expandX().uniform();
        HUD.add("audience").expandX().uniform().row();
        HUD.add(money).expandX().uniform();
        HUD.add(rankContainer).expandX().fill().uniform();
        HUD.add(audience).expandX().uniform();
        ui.add(HUD).expandX().fill().expandY().row();

        Table bottom = new Table(Main.skin);
        bottom.setBackground(Main.skin.getDrawable("default-round"));
        Table shapes = new Table(Main.skin);
        shapes.left().top();
        for (final Shape shape : Shape.values()) {
            shape.engine = engine;
            shapes.add(shape.selectTable).left().spaceBottom(4).expandY().row();
            shape.selectTable.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (Player.selected != null) {
                        Player.selected.selectTable.setBackground(Main.skin.getDrawable("default-round"));
                    }
                    shape.selectTable.setBackground(Main.skin.getDrawable("default-round-down"));
                    Player.selected = shape;
                    Main.changeBGM(shape.bgm, shape.id);
                    upgradeTable.clearChildren();
                    upgradeTable.add(Player.selected.upgradeTable).expand().fill();

                    AudienceSystem as = getEngine().getSystem(AudienceSystem.class);
                    if (as != null && as.player != null && Mappers.idleAnimation.has(as.player)) {
                        Actor actor;
                        if (Mappers.actor.has(as.player))
                            actor = Mappers.actor.get(as.player).actor;
                        else if (Mappers.audienceActor.has(as.player))
                            actor = Mappers.audienceActor.get(as.player).actor;
                        else return;
                        final IdleAnimationComponent ic = Mappers.idleAnimation.get(as.player);

                        actor.addAction(Actions.sequence(Actions.moveBy(0, -200, Constants.ANIM_SPEED, Interpolation.pow2), Actions.run(new Runnable() {
                            @Override
                            public void run() {
                                ic.file = Player.selected.image;
                                ic.update = true;
                            }
                        }), Actions.moveBy(0, 200, Constants.ANIM_SPEED, Interpolation.pow2)));
                    }
                }
            });
            if (Player.selected == shape) {
                shape.selectTable.setBackground(Main.skin.getDrawable("default-round-down"));
                Main.changeBGM(shape.bgm, shape.id);
            }
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
            HUD.toFront();
            rankContainer.setOrigin(Align.center);
            rankContainer.clearActions();
            rankContainer.addAction(Actions.sequence(Actions.scaleTo(4, 4, Constants.ANIM_SPEED, Interpolation.pow2), Actions.scaleTo(1, 1, Constants.ANIM_SPEED, Interpolation.pow2)));
            ParticleEffect effect = new ParticleEffect();
            effect.load(Gdx.files.internal("rank.p"), Gdx.files.internal(""));
            Vector2 pos = rankContainer.localToStageCoordinates(new Vector2(rankContainer.getOriginX(), rankContainer.getOriginY()));
            effect.setPosition(pos.x - 100, pos.y);
            effect.start();
            Entity entity = new Entity();
            ParticleEffectComponent pc = new ParticleEffectComponent();
            pc.effect = effect;
            entity.add(pc);
            getEngine().addEntity(entity);
            Player.rankUp = false;
        }
        df.setMaximumFractionDigits(0);
        df.setMinimumFractionDigits(0);
        audience.setText(df.format(Player.audience));
    }
}
