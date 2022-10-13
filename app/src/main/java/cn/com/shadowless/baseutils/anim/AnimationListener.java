package cn.com.shadowless.baseutils.anim;

import android.view.View;


/**
 * The type Animation listener.
 *
 * @author sHadowLess
 */
public class AnimationListener {

    /**
     * Instantiates a new Animation listener.
     */
    private AnimationListener() {
    }

    /**
     * The interface Start.
     */
    public interface Start {
        /**
         * On start.
         */
        void onStart();
    }

    /**
     * The interface Stop.
     */
    public interface Stop {
        /**
         * On stop.
         */
        void onStop();
    }

    /**
     * The interface Update.
     *
     * @param <V> the type parameter
     */
    public interface Update<V extends View> {
        /**
         * Update.
         *
         * @param view  the view
         * @param value the value
         */
        void update(V view, float value);
    }

}
