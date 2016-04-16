package thepaperpilot.shape;

import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import thepaperpilot.shape.Util.Constants;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public enum Shape {
    SQUARE("square", "square", 1),
    CIRCLE("circle", "circle", 1.5f);

    public String image;
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

    Shape(String imageString, String name, final float mod) {
        this.image = imageString;
        this.name = name;
        this.mod = mod;

        attLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_ATT_COST, mod));
        maxAttLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_MAX_ATT_COST, mod));
        effLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_EFF_COST, mod));
        entLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_ENT_COST, mod));
        boreLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_BORE_COST, mod));

        selectTable = new Table(Main.skin);
        selectTable.setTouchable(Touchable.enabled);
        Image image = new Image(Main.getTexture(imageString));
        selectTable.add(image).size(32).spaceRight(4);
        label = new Label(name, Main.skin);
        selectTable.add(label).width(100);
        attentionBar = new ProgressBar(0, maxAttention, 1, false, Main.skin);
        selectTable.add(attentionBar).width(80);

        upgradeTable = new Table(Main.skin);

        final DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        df.setMinimumFractionDigits(2);

        Table attUpgrade = new Table(Main.skin);
        attUpgrade.setBackground(Main.skin.getDrawable("default-round"));
        attUpgrade.add("Excitement").expandY().colspan(2).pad(4).row();
        attLevelLabel = new Label("" + attLevel, Main.skin);
        attUpgrade.add("level ").expandY();
        attUpgrade.add(attLevelLabel).expandY().row();
        final TextButton attButton = new TextButton("$" + df.format(attLevelCost), Main.skin);
        attButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Player.money.compareTo(attLevelCost) < 0) return;
                attLevel++;
                attentionSpeed *= Constants.ATTENTION_SPEED_CURVE;
                Player.money = Player.money.subtract(attLevelCost);
                attLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_ATT_COST * attLevel, mod));
                attLevelLabel.setText("" + attLevel);
                attButton.setText("$" + df.format(attLevelCost));
            }
        });
        attUpgrade.add(attButton).expandX().fill().expandY().pad(4).colspan(2);
        upgradeTable.add(attUpgrade).expandY().fill().pad(4).expandX();

        Table maxAttUpgrade = new Table(Main.skin);
        maxAttUpgrade.setBackground(Main.skin.getDrawable("default-round"));
        maxAttUpgrade.add("Satisfaction").expandY().colspan(2).pad(4).row();
        maxAttLevelLabel = new Label("" + maxAttLevel, Main.skin);
        maxAttUpgrade.add("level ").expandY();
        maxAttUpgrade.add(maxAttLevelLabel).expandY().row();
        final TextButton maxAttButton = new TextButton("$" + df.format(maxAttLevelCost), Main.skin);
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
                maxAttButton.setText("$" + df.format(maxAttLevelCost));
            }
        });
        maxAttUpgrade.add(maxAttButton).expandX().fill().expandY().pad(4).colspan(2);
        upgradeTable.add(maxAttUpgrade).expandY().fill().pad(4).expandX();

        Table efficiencyUpgrade = new Table(Main.skin);
        efficiencyUpgrade.setBackground(Main.skin.getDrawable("default-round"));
        efficiencyUpgrade.add("Efficiency").expandY().colspan(2).pad(4).row();
        effLevelLabel = new Label("" + effLevel, Main.skin);
        efficiencyUpgrade.add("level ").expandY();
        efficiencyUpgrade.add(effLevelLabel).expandY().row();
        final TextButton effButton = new TextButton("$" + df.format(effLevelCost), Main.skin);
        effButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Player.money.compareTo(effLevelCost) < 0) return;
                effLevel++;
                moneyPerPerson *= Constants.EFFICIENCY_SPEED_CURVE;
                Player.money = Player.money.subtract(effLevelCost);
                effLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_EFF_COST * effLevel, mod));
                effLevelLabel.setText("" + effLevel);
                effButton.setText("$" + df.format(effLevelCost));
            }
        });
        efficiencyUpgrade.add(effButton).expandX().fill().expandY().pad(4).colspan(2);
        upgradeTable.add(efficiencyUpgrade).expandY().fill().pad(4).expandX();

        Table entertainmentUpgrade = new Table(Main.skin);
        entertainmentUpgrade.setBackground(Main.skin.getDrawable("default-round"));
        entertainmentUpgrade.add("Entertainment").expandY().colspan(2).pad(4).row();
        entLevelLabel = new Label("" + entLevel, Main.skin);
        entertainmentUpgrade.add("level ").expandY();
        entertainmentUpgrade.add(entLevelLabel).expandY().row();
        final TextButton entButton = new TextButton("$" + df.format(entLevelCost), Main.skin);
        entButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Player.money.compareTo(entLevelCost) < 0) return;
                entLevel++;
                entertainment *= Constants.ENTERTAINMENT_SPEED_CURVE;
                Player.money = Player.money.subtract(entLevelCost);
                entLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_ENT_COST * entLevel, mod));
                entLevelLabel.setText("" + entLevel);
                entButton.setText("$" + df.format(entLevelCost));
            }
        });
        entertainmentUpgrade.add(entButton).expandX().fill().expandY().pad(4).colspan(2);
        upgradeTable.add(entertainmentUpgrade).expandY().fill().pad(4).expandX();

        Table boreUpgrade = new Table(Main.skin);
        boreUpgrade.setBackground(Main.skin.getDrawable("default-round"));
        boreUpgrade.add("Reignition").expandY().colspan(2).pad(4).row();
        boreLevelLabel = new Label("" + boreLevel, Main.skin);
        boreUpgrade.add("level ").expandY();
        boreUpgrade.add(boreLevelLabel).expandY().row();
        final TextButton boreButton = new TextButton("$" + df.format(boreLevelCost), Main.skin);
        boreButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                if (Player.money.compareTo(boreLevelCost) < 0) return;
                boreLevel++;
                unbore *= Constants.UNBORE_SPEED_CURVE;
                Player.money = Player.money.subtract(boreLevelCost);
                boreLevelCost = BigDecimal.valueOf(100 * Math.pow(Constants.BASE_BORE_COST * boreLevel, mod));
                boreLevelLabel.setText("" + boreLevel);
                boreButton.setText("$" + df.format(boreLevelCost));
            }
        });
        boreUpgrade.add(boreButton).expandX().fill().expandY().pad(4).colspan(2);
        upgradeTable.add(boreUpgrade).expandY().fill().pad(4).expandX();
    }
}
