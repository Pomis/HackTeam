package kekify.io.hackteam;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.SurfaceView;


public class CircleSurface extends SurfaceView {

    private Path clipPath;

    public CircleSurface(Context context) {
        super(context);
        init();
    }

    public CircleSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        clipPath = new Path();
        clipPath.addCircle(300, 300, 300, Path.Direction.CW);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        canvas.clipPath(clipPath);
        super.dispatchDraw(canvas);
    }
}