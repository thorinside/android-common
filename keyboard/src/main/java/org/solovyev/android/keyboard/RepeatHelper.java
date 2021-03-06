package org.solovyev.android.keyboard;

import android.os.Handler;
import android.view.View;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * User: serso
 * Date: 11/5/12
 * Time: 6:46 PM
 */
public class RepeatHelper {

    // in ms
    private static final int MAX_REPEAT_INTERVAL = 300;
    private static final int MIN_REPEAT_INTERVAL = 100;
    private static final int STEPS = 20;

    private static final int[] REPEAT_INTERVALS = new int[STEPS];
	static {
		for (int i = 0; i < REPEAT_INTERVALS.length; i++) {
			REPEAT_INTERVALS[i] = (STEPS - i) * (MAX_REPEAT_INTERVAL - MIN_REPEAT_INTERVAL) / (STEPS - 1);

		}
	}

    @Nullable
    private View repeatView;

    private int repeatInterval = REPEAT_INTERVALS[0];

    private long lastTime = 0;

    private int repeatCounter = 0;

    private boolean repeat = false;

	@NotNull
	private final Handler uiHandler = new Handler();

	@Nullable
	private Runnable repeatRunnable;

	public synchronized void keyUp(@NotNull View v) {
		if (this.repeatView == v ) {
			clean(null);
		}
	}

	public synchronized void keyDown(@NotNull View v, @Nullable final Runnable repeatRunnable) {
		clean(v);

		if (repeatRunnable !=  null) {
			this.repeatRunnable = new Runnable() {
				@Override
				public void run() {
					repeatRunnable.run();

					if (repeatCounter < REPEAT_INTERVALS.length) {
						repeatInterval = REPEAT_INTERVALS[repeatCounter];
					}
					repeatCounter++;

					uiHandler.postDelayed(this, repeatInterval);
				}
			};

			this.uiHandler.postDelayed(this.repeatRunnable, 1);
		}
	}

	private void clean(@Nullable View v) {
		this.repeatView = v;
		this.repeatCounter = 0;

		if (this.repeatRunnable != null) {
			this.uiHandler.removeCallbacks(this.repeatRunnable);
			this.repeatRunnable = null;
		}
	}
}
