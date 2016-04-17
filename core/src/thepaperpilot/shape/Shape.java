package thepaperpilot.shape;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Components.ParticleEffectComponent;
import thepaperpilot.shape.Systems.AudienceSystem;
import thepaperpilot.shape.Util.Constants;
import thepaperpilot.shape.Util.Mappers;

import java.math.BigDecimal;

public enum Shape {
    SQUARE("square", "square", "square", 1.2f),
    CIRCLE("circle", "circle", "circle", 1.4f),
    TRIANGLE("triangle", "triangle", "triangle", 1.6f);

    public Engine engine;

    public String image;
    public long id;
    public Sound bgm;
    public String name;
    public float mod;

    public int attLevel = 1;
    public int maxAttLevel = 1;
    public int effLevel = 1;
    public int entLevel = 1;
    public int boreLevel = 1;

    public BigDecimal attLevelCost;
    public BigDecimal maxAttLevelCost;
    public BigDecimal effLevelCost;
    public BigDecimal entLevelCost;
    public BigDecimal boreLevelCost;

    public float attention;
    public float attentionSpeed = Constants.BASE_ATTENTION_SPEED;
    public float maxAttention = Constants.BASE_ATTENTION;
    public float moneyPerPerson = Constants.BASE_MONEY_PER_PERSON;
    public float entertainment = Constants.BASE_ENTERTAINMENT;
    public float unbore = Constants.BASE_UNBORE;
    public BigDecimal people = BigDecimal.ZERO;

    public Label attLevelLabel;
    public Label maxAttLevelLabel;
    public Label effLevelLabel;
    public Label entLevelLabel;
    public Label boreLevelLabel;

    public Table selectTable;
    public Label label;
    public ProgressBar attentionBar;
    public Table upgradeTable;

    Shape(String imageString, String bgmString, String name, final float mod) {
        this.image = imageString;
        this.bgm = Main.getBGM(bgmString);
        this.id = this.bgm.loop(0);
        this.name = name;
        this.mod = mod;

        attLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_ATT_COST, mod));
        maxAttLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_MAX_ATT_COST, mod));
        effLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_EFF_COST, mod));
        entLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_ENT_COST, mod));
        boreLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_BORE_COST, mod));

        selectTable = new Table(Main.skin);
        selectTable.setTouchable(Touchable.enabled);
        selectTable.setBackground(Main.skin.getDrawable("default-round"));
        Image image = new Image(new TextureRegion(Main.getTexture(imageString), 640, 640));
        selectTable.add(image).size(32).spaceRight(4).pad(4);
        label = new Label(name, Main.skin);
        selectTable.add(label).width(100);
        attentionBar = new ProgressBar(0, maxAttention, 1, false, Main.skin);
        selectTable.add(attentionBar).width(80).pad(4);

        upgradeTable = new Table(Main.skin);

        Table attUpgrade = new Table(Main.skin);
        attUpgrade.setBackground(Main.skin.getDrawable("default-round"));
        attUpgrade.add("Excitement").expandY().colspan(2).pad(4).row();
        attLevelLabel = new Label("" + attLevel, Main.skin);
        attUpgrade.add("level ").expandY();
        attUpgrade.add(attLevelLabel).expandY().row();
        final TextButton attButton = new TextButton("$" + attLevelCost.toBigInteger(), Main.skin);
        attButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Player.money.compareTo(attLevelCost) < 0) return;
                attLevel++;
                attentionSpeed *= Constants.ATTENTION_SPEED_CURVE;
                Player.money = Player.money.subtract(attLevelCost);
                attLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_ATT_COST * attLevel, mod));
                attLevelLabel.setText("" + attLevel);
                attButton.setText("$" + attLevelCost.toBigInteger());
                upgradeEffect(engine, attButton.localToStageCoordinates(new Vector2(attButton.getOriginX(), attButton.getOriginY())).x);
            }
        });
        attUpgrade.add(attButton).expandX().fill().expandY().pad(4).colspan(2);
        upgradeTable.add(attUpgrade).expandY().fill().pad(4).expandX().uniform();

        Table maxAttUpgrade = new Table(Main.skin);
        maxAttUpgrade.setBackground(Main.skin.getDrawable("default-round"));
        maxAttUpgrade.add("Satisfaction").expandY().colspan(2).pad(4).row();
        maxAttLevelLabel = new Label("" + maxAttLevel, Main.skin);
        maxAttUpgrade.add("level ").expandY();
        maxAttUpgrade.add(maxAttLevelLabel).expandY().row();
        final TextButton maxAttButton = new TextButton("$" + maxAttLevelCost.toBigInteger(), Main.skin);
        maxAttButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Player.money.compareTo(maxAttLevelCost) < 0) return;
                maxAttLevel++;
                maxAttention *= Constants.MAX_ATTENTION_SPEED_CURVE;
                Player.money = Player.money.subtract(maxAttLevelCost);
                attentionBar.setRange(0, maxAttention);
                maxAttLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_MAX_ATT_COST * maxAttLevel, mod));
                maxAttLevelLabel.setText("" + maxAttLevel);
                maxAttButton.setText("$" + maxAttLevelCost.toBigInteger());
                upgradeEffect(engine, maxAttButton.localToStageCoordinates(new Vector2(maxAttButton.getOriginX(), maxAttButton.getOriginY())).x);
            }
        });
        maxAttUpgrade.add(maxAttButton).expandX().fill().expandY().pad(4).colspan(2);
        upgradeTable.add(maxAttUpgrade).expandY().fill().pad(4).expandX().uniform();

        Table efficiencyUpgrade = new Table(Main.skin);
        efficiencyUpgrade.setBackground(Main.skin.getDrawable("default-round"));
        efficiencyUpgrade.add("Efficiency").expandY().colspan(2).pad(4).row();
        effLevelLabel = new Label("" + effLevel, Main.skin);
        efficiencyUpgrade.add("level ").expandY();
        efficiencyUpgrade.add(effLevelLabel).expandY().row();
        final TextButton effButton = new TextButton("$" + effLevelCost.toBigInteger(), Main.skin);
        effButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Player.money.compareTo(effLevelCost) < 0) return;
                effLevel++;
                moneyPerPerson *= Constants.EFFICIENCY_SPEED_CURVE;
                Player.money = Player.money.subtract(effLevelCost);
                effLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_EFF_COST * effLevel, mod));
                effLevelLabel.setText("" + effLevel);
                effButton.setText("$" + effLevelCost.toBigInteger());
                upgradeEffect(engine, effButton.localToStageCoordinates(new Vector2(effButton.getOriginX(), effButton.getOriginY())).x);
            }
        });
        efficiencyUpgrade.add(effButton).expandX().fill().expandY().pad(4).colspan(2);
        upgradeTable.add(efficiencyUpgrade).expandY().fill().pad(4).expandX().uniform();

        Table entertainmentUpgrade = new Table(Main.skin);
        entertainmentUpgrade.setBackground(Main.skin.getDrawable("default-round"));
        entertainmentUpgrade.add("Entertainment").expandY().colspan(2).pad(4).row();
        entLevelLabel = new Label("" + entLevel, Main.skin);
        entertainmentUpgrade.add("level ").expandY();
        entertainmentUpgrade.add(entLevelLabel).expandY().row();
        final TextButton entButton = new TextButton("$" + entLevelCost.toBigInteger(), Main.skin);
        entButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Player.money.compareTo(entLevelCost) < 0) return;
                entLevel++;
                entertainment *= Constants.ENTERTAINMENT_SPEED_CURVE;
                Player.money = Player.money.subtract(entLevelCost);
                entLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_ENT_COST * entLevel, mod));
                entLevelLabel.setText("" + entLevel);
                entButton.setText("$" + entLevelCost.toBigInteger());
                upgradeEffect(engine, entButton.localToStageCoordinates(new Vector2(entButton.getOriginX(), entButton.getOriginY())).x);
            }
        });
        entertainmentUpgrade.add(entButton).expandX().fill().expandY().pad(4).colspan(2);
        upgradeTable.add(entertainmentUpgrade).expandY().fill().pad(4).expandX().uniform();

        Table boreUpgrade = new Table(Main.skin);
        boreUpgrade.setBackground(Main.skin.getDrawable("default-round"));
        boreUpgrade.add("Reignition").expandY().colspan(2).pad(4).row();
        boreLevelLabel = new Label("" + boreLevel, Main.skin);
        boreUpgrade.add("level ").expandY();
        boreUpgrade.add(boreLevelLabel).expandY().row();
        final TextButton boreButton = new TextButton("$" + boreLevelCost.toBigInteger(), Main.skin);
        boreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Player.money.compareTo(boreLevelCost) < 0) return;
                boreLevel++;
                unbore *= Constants.UNBORE_SPEED_CURVE;
                Player.money = Player.money.subtract(boreLevelCost);
                boreLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_BORE_COST * boreLevel, mod));
                boreLevelLabel.setText("" + boreLevel);
                boreButton.setText("$" + boreLevelCost.toBigInteger());
                upgradeEffect(engine, boreButton.localToStageCoordinates(new Vector2(boreButton.getOriginX(), boreButton.getOriginY())).x);
            }
        });
        boreUpgrade.add(boreButton).expandX().fill().expandY().pad(4).colspan(2);
        upgradeTable.add(boreUpgrade).expandY().fill().pad(4).expandX().uniform();
    }

    public void upgradeEffect(final Engine engine, float x) {
        ParticleEffect effect = new ParticleEffect();
        effect.load(Gdx.files.internal("upgrade.p"), Gdx.files.internal(""));
        effect.setPosition(Constants.WORLD_WIDTH / 2f, Constants.WORLD_HEIGHT - (Constants.WORLD_HEIGHT - Constants.UI_HEIGHT) / 2f);
        effect.start();
        Entity entity = new Entity();
        ParticleEffectComponent pc = new ParticleEffectComponent();
        pc.effect = effect;
        entity.add(pc);
        engine.addEntity(entity);

        for (Entity person : engine.getSystem(AudienceSystem.class).audience) {
            Actor actor;
            if (Mappers.actor.has(person))
                actor = Mappers.actor.get(person).actor;
            else if (Mappers.audienceActor.has(person))
                actor = Mappers.audienceActor.get(person).actor;
            else return;

            actor.addAction(Actions.sequence(Actions.delay(MathUtils.random()), Actions.repeat(MathUtils.random(5), Actions.sequence(Actions.moveBy(0, 40, .5f, Interpolation.pow2), Actions.moveBy(0, -40, .5f, Interpolation.pow2)))));
        }

        final Entity message = new Entity();
        ActorComponent ac = new ActorComponent();
        ac.actor = new Label("level up", Main.skin);
        ac.actor.setPosition(x + 50, Constants.UI_HEIGHT - 50);
        ac.actor.addAction(Actions.sequence(Actions.parallel(Actions.fadeOut(1), Actions.moveBy(0, 200, 1, Interpolation.pow2)), Actions.run(new Runnable() {
            @Override
            public void run() {
                engine.removeEntity(message);
            }
        })));
        message.add(ac);
        engine.addEntity(message);
    }
}
