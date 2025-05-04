package util;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Labeled;
import javafx.scene.paint.Color;
import javafx.util.Duration;

public class HoverEffectUtil {

    private HoverEffectUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static void applyHoverEffect(Labeled... nodes) {
        for (Labeled node : nodes) {
            Color fromColor = Color.BLACK;
            Color toColor = Color.WHITE;

            node.setTextFill(fromColor);

            Timeline hoverIn = new Timeline(
                    new KeyFrame(Duration.millis(200), new KeyValue(node.textFillProperty(), toColor))
            );

            Timeline hoverOut = new Timeline(
                    new KeyFrame(Duration.millis(200), new KeyValue(node.textFillProperty(), fromColor))
            );

            node.setOnMouseEntered(e -> hoverIn.playFromStart());
            node.setOnMouseExited(e -> hoverOut.playFromStart());        }


    }
}
