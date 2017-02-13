package kr.co.easterbunny.wonderple.event;

/**
 * Created by scona on 2017-02-10.
 */

public class ClickNextEvent {

    private final boolean isClicked;

    public ClickNextEvent(boolean isClicked) {
        this.isClicked = isClicked;
    }

    public boolean isClicked() {
        return isClicked;
    }
}
