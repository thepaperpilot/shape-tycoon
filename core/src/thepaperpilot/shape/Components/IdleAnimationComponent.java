package thepaperpilot.shape.Components;

import com.badlogic.ashley.core.Component;
import com.badlogic.gdx.graphics.g2d.Animation;
import thepaperpilot.shape.Util.Constants;

public class IdleAnimationComponent implements Component {
    public String file;
    public Animation animation;
    public boolean animating;
    public float time;
    public float chance = Constants.IDLE_CHANCE;
    public boolean update;
}
