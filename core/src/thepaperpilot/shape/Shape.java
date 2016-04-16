package thepaperpilot.shape;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import thepaperpilot.shape.Util.Constants;

public enum Shape {
    SQUARE("square", "square"),
    CIRCLE("circle", "circle");

    public String image;
    public String name;

    public float attention;
    public float maxAttention = Constants.BASE_ATTENTION;
    public float moneyPerPerson = Constants.BASE_MONEY_PER_PERSON;
    public float entertainment = Constants.BASE_ENTERTAINMENT;

    public Table selectTable;
    public Label label;
    public ProgressBar attentionBar;

    Shape(String imageString, String name) {
        this.image = imageString;
        this.name = name;

        selectTable = new Table(Main.skin);
        selectTable.setTouchable(Touchable.enabled);
        Image image = new Image(Main.getTexture(imageString));
        selectTable.add(image).size(32).spaceRight(4);
        label = new Label(name, Main.skin);
        selectTable.add(label).width(100);
        attentionBar = new ProgressBar(0, maxAttention, 1, false, Main.skin);
        selectTable.add(attentionBar).width(80);
    }
}
