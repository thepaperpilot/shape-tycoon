package thepaperpilot.shape;

import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public enum Shape {
    SQUARE("square", "square"),
    CIRCLE("circle", "circle");

    public String image;
    public String name;
    public Table table;
    public Label label;

    Shape(String imageString, String name) {
        this.image = imageString;
        this.name = name;

        table = new Table(Main.skin);
        table.setTouchable(Touchable.enabled);
        Image image = new Image(Main.getTexture(imageString));
        table.add(image).size(32).spaceRight(4);
        label = new Label(name, Main.skin);
        table.add(label);
    }
}
