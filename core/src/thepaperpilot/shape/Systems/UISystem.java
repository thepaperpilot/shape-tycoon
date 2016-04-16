package thepaperpilot.shape.Systems;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import thepaperpilot.shape.Components.ActorComponent;
import thepaperpilot.shape.Main;
import thepaperpilot.shape.Shape;
import thepaperpilot.shape.Util.Constants;

public class UISystem extends EntitySystem {

    public UISystem() {
        super(10);
    }

    // suuuper temporary
    Shape selected;

    public void addedToEngine (Engine engine) {
        Table ui = new Table(Main.skin);
        ui.setFillParent(true);
        ui.top();
        Table bottom = new Table(Main.skin);
        bottom.setBackground(Main.skin.getDrawable("default-round"));
        ui.add(bottom).expandX().height(Constants.UI_HEIGHT).fill().expandY().bottom().pad(Constants.PADDING);

        Table shapes = new Table(Main.skin);
        shapes.top();
        for (final Shape shape : Shape.values()) {
            shapes.add(shape.selectTable).left().spaceBottom(4).row();
            shape.selectTable.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (selected != null) {
                        selected.label.setColor(Color.WHITE);
                    }
                    selected = shape;
                    selected.label.setColor(Color.ORANGE);
                }
            });
        }
        bottom.add(shapes).expandY().width(200).fill().expandX().left().pad(Constants.PADDING);

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
    }
}
