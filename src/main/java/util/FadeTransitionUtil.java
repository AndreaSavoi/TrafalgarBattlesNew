package util;

import javafx.animation.FadeTransition;
import javafx.animation.PauseTransition;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class FadeTransitionUtil {

    private FadeTransitionUtil() { throw new IllegalStateException("Utility class"); }

    public static FadeTransition getFadeTransition (ImageView img) {
        javafx.animation.FadeTransition fadeIn = new javafx.animation.FadeTransition(Duration.seconds(0.7), img);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        fadeIn.setOnFinished(event1 -> {
            PauseTransition delay = new PauseTransition(Duration.seconds(5));
            delay.setOnFinished(event2 -> {
                javafx.animation.FadeTransition fadeOut = new javafx.animation.FadeTransition(Duration.seconds(1), img);
                fadeOut.setFromValue(1.0);
                fadeOut.setToValue(0.0);
                fadeOut.setOnFinished(event3 -> {
                    img.setVisible(false);
                    img.setOpacity(1.0);
                });
                fadeOut.play();
            });
            delay.play();
        });
        return fadeIn;
    }
}
