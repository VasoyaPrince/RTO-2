package anon.rtoinfo.rtovehical.widget;

import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.mikhaellopez.circularfillableloaders.CircularFillableLoaders;
import anon.rtoinfo.rtovehical.R;

public class CustomLoaderScreen extends FrameLayout {
    /* access modifiers changed from: private */
    public Callback callback;
    CircularFillableLoaders circularFillableLoaders;
    /* access modifiers changed from: private */
    public boolean loadingStarted = false;
    Context mContext;
    Handler mHandler = new Handler();
    private int progress = 10;
    Runnable progressTask;
    Runnable repeatTask;
    Runnable startDataFetchingTask = new Runnable() {
        public void run() {
            boolean unused = CustomLoaderScreen.this.loadingStarted = true;
            if (CustomLoaderScreen.this.callback != null) {
                CustomLoaderScreen.this.callback.start();
            }
        }
    };

    public interface Callback {
        void start();
    }

    public CustomLoaderScreen(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.mContext = context;
        this.circularFillableLoaders = (CircularFillableLoaders) View.inflate(context, R.layout.view_custom_loader, this).findViewById(R.id.cfl_progress);
        this.progressTask = new Runnable() {
            public final void run() {
                CustomLoaderScreen.this.lambda$new$0$CustomLoaderScreen();
            }
        };
        this.repeatTask = new Runnable() {
            public final void run() {
                CustomLoaderScreen.this.lambda$new$1$CustomLoaderScreen();
            }
        };
    }

    public /* synthetic */ void lambda$new$0$CustomLoaderScreen() {
        int i = this.progress + 10;
        this.progress = i;
        this.circularFillableLoaders.setProgress(i);
        this.repeatTask.run();
    }

    public /* synthetic */ void lambda$new$1$CustomLoaderScreen() {
        if (this.progress != 90) {
            this.mHandler.postDelayed(this.progressTask, 1000);
        }
    }

    public void lambda$setVisibility$2$CustomLoaderScreen(int i) {
        if (i == 0) {
            this.mHandler.postDelayed(this.startDataFetchingTask, 2000);
            this.progressTask.run();
            super.setVisibility(i);
        } else if (i == 8) {
            this.circularFillableLoaders.setProgress(100);
            this.mHandler.removeCallbacksAndMessages((Object) null);
            restartProgress();
            this.mHandler.postDelayed(new Runnable() {

                public final void run() {
                    CustomLoaderScreen.this.lambda$setVisibility$2$CustomLoaderScreen(i);
                }
            }, 1000);
        }
    }

    private void restartProgress() {
        this.mHandler.removeCallbacks(this.startDataFetchingTask);
        this.mHandler.removeCallbacksAndMessages((Object) null);
        this.progress = 0;
        this.loadingStarted = false;
    }

    public void finishLoading() {
        this.mHandler.removeCallbacks(this.startDataFetchingTask);
        this.circularFillableLoaders.setProgress(100);
        this.mHandler.removeCallbacksAndMessages((Object) null);
        restartProgress();
        this.mHandler.postDelayed(new Runnable() {
            public final void run() {
                CustomLoaderScreen.this.lambda$finishLoading$3$CustomLoaderScreen();
            }
        }, 1000);
    }

    public /* synthetic */ void lambda$finishLoading$3$CustomLoaderScreen() {
        super.setVisibility(8);
        this.circularFillableLoaders.setProgress(0);
    }

    public boolean isLoadingStarted() {
        return this.loadingStarted;
    }

    public void setCallback(Callback callback2) {
        this.callback = callback2;
    }
}
