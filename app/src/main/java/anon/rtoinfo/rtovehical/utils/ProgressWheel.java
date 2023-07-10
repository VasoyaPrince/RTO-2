package anon.rtoinfo.rtovehical.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Shader;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import androidx.core.view.ViewCompat;

import anon.rtoinfo.rtovehical.R;

public class ProgressWheel extends View {
    private int barColor = -1442840576;
    private int barLength = 60;
    private Paint barPaint = new Paint();
    private int barWidth = 20;
    private RectF circleBounds = new RectF();
    private int circleColor = 0;
    private RectF circleInnerContour = new RectF();
    private RectF circleOuterContour = new RectF();
    private Paint circlePaint = new Paint();
    private int circleRadius = 80;
    private int contourColor = -1442840576;
    private Paint contourPaint = new Paint();
    private float contourSize = 0.0f;
    public int delayMillis = 0;
    private int fullRadius = 100;
    boolean isSpinning = false;
    private int layout_height = 0;
    private int layout_width = 0;
    private int paddingBottom = 5;
    private int paddingLeft = 5;
    private int paddingRight = 5;
    private int paddingTop = 5;
    int progress = 0;
    private RectF rectBounds = new RectF();
    private int rimColor = -1428300323;
    private Paint rimPaint = new Paint();
    private int rimWidth = 20;
    public Handler spinHandler = new Handler() {
        public void handleMessage(Message message) {
            invalidate();
            if (isSpinning) {
                progress += spinSpeed;
                if (progress > 360) {
                    progress = 0;
                }
                spinHandler.sendEmptyMessageDelayed(0, (long) delayMillis);
            }
        }
    };
    public int spinSpeed = 2;
    private String[] splitText = new String[0];
    private String text = "";
    private int textColor = ViewCompat.MEASURED_STATE_MASK;
    private Paint textPaint = new Paint();
    private int textSize = 20;

    public ProgressWheel(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        parseAttributes(context.obtainStyledAttributes(attributeSet, R.styleable.ProgressWheel));
    }

    public void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int measuredWidth = getMeasuredWidth();
        int measuredHeight = getMeasuredHeight();
        int paddingLeft2 = (measuredWidth - getPaddingLeft()) - getPaddingRight();
        int paddingTop2 = (measuredHeight - getPaddingTop()) - getPaddingBottom();
        if (paddingLeft2 > paddingTop2) {
            paddingLeft2 = paddingTop2;
        }
        setMeasuredDimension(getPaddingLeft() + paddingLeft2 + getPaddingRight(), paddingLeft2 + getPaddingTop() + getPaddingBottom());
    }

    public void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        this.layout_width = i;
        this.layout_height = i2;
        setupBounds();
        setupPaints();
        invalidate();
    }

    private void setupPaints() {
        this.barPaint.setColor(this.barColor);
        this.barPaint.setAntiAlias(true);
        this.barPaint.setStyle(Paint.Style.STROKE);
        this.barPaint.setStrokeWidth((float) this.barWidth);
        this.rimPaint.setColor(this.rimColor);
        this.rimPaint.setAntiAlias(true);
        this.rimPaint.setStyle(Paint.Style.STROKE);
        this.rimPaint.setStrokeWidth((float) this.rimWidth);
        this.circlePaint.setColor(this.circleColor);
        this.circlePaint.setAntiAlias(true);
        this.circlePaint.setStyle(Paint.Style.FILL);
        this.textPaint.setColor(this.textColor);
        this.textPaint.setStyle(Paint.Style.FILL);
        this.textPaint.setAntiAlias(true);
        this.textPaint.setTextSize((float) this.textSize);
        this.contourPaint.setColor(this.contourColor);
        this.contourPaint.setAntiAlias(true);
        this.contourPaint.setStyle(Paint.Style.STROKE);
        this.contourPaint.setStrokeWidth(this.contourSize);
    }

    private void setupBounds() {
        int min = Math.min(this.layout_width, this.layout_height);
        int i = this.layout_width - min;
        int i2 = (this.layout_height - min) / 2;
        this.paddingTop = getPaddingTop() + i2;
        this.paddingBottom = getPaddingBottom() + i2;
        int i3 = i / 2;
        this.paddingLeft = getPaddingLeft() + i3;
        this.paddingRight = getPaddingRight() + i3;
        int width = getWidth();
        int height = getHeight();
        this.rectBounds = new RectF((float) this.paddingLeft, (float) this.paddingTop, (float) (width - this.paddingRight), (float) (height - this.paddingBottom));
        int i4 = this.paddingLeft;
        int i5 = this.barWidth;
        this.circleBounds = new RectF((float) (i4 + i5), (float) (this.paddingTop + i5), (float) ((width - this.paddingRight) - i5), (float) ((height - this.paddingBottom) - i5));
        this.circleInnerContour = new RectF(this.circleBounds.left + (((float) this.rimWidth) / 2.0f) + (this.contourSize / 2.0f), this.circleBounds.top + (((float) this.rimWidth) / 2.0f) + (this.contourSize / 2.0f), (this.circleBounds.right - (((float) this.rimWidth) / 2.0f)) - (this.contourSize / 2.0f), (this.circleBounds.bottom - (((float) this.rimWidth) / 2.0f)) - (this.contourSize / 2.0f));
        this.circleOuterContour = new RectF((this.circleBounds.left - (((float) this.rimWidth) / 2.0f)) - (this.contourSize / 2.0f), (this.circleBounds.top - (((float) this.rimWidth) / 2.0f)) - (this.contourSize / 2.0f), this.circleBounds.right + (((float) this.rimWidth) / 2.0f) + (this.contourSize / 2.0f), this.circleBounds.bottom + (((float) this.rimWidth) / 2.0f) + (this.contourSize / 2.0f));
        int i6 = width - this.paddingRight;
        int i7 = this.barWidth;
        this.fullRadius = (i6 - i7) / 2;
        this.circleRadius = (this.fullRadius - i7) + 1;
    }

    private void parseAttributes(TypedArray typedArray) {
        this.barWidth = (int) typedArray.getDimension(R.styleable.ProgressWheel_barWidth, (float) this.barWidth);
        this.rimWidth = (int) typedArray.getDimension(R.styleable.ProgressWheel_rimWidth, (float) this.rimWidth);
        this.spinSpeed = (int) typedArray.getDimension(R.styleable.ProgressWheel_spinSpeed, (float) this.spinSpeed);
        this.delayMillis = typedArray.getInteger(R.styleable.ProgressWheel_delayMillis, this.delayMillis);
        if (this.delayMillis < 0) {
            this.delayMillis = 0;
        }
        this.barColor = typedArray.getColor(R.styleable.ProgressWheel_barColor, this.barColor);
        this.barLength = (int) typedArray.getDimension(R.styleable.ProgressWheel_barLength, (float) this.barLength);
        this.textSize = (int) typedArray.getDimension(R.styleable.ProgressWheel_textSize, (float) this.textSize);
        this.textColor = typedArray.getColor(R.styleable.ProgressWheel_textColor, this.textColor);
        if (typedArray.hasValue(R.styleable.ProgressWheel_text)) {
            setText(typedArray.getString(R.styleable.ProgressWheel_text));
        }
        this.rimColor = typedArray.getColor(R.styleable.ProgressWheel_rimColor, this.rimColor);
        this.circleColor = typedArray.getColor(R.styleable.ProgressWheel_circleColor, this.circleColor);
        this.contourColor = typedArray.getColor(R.styleable.ProgressWheel_contourColor, this.contourColor);
        this.contourSize = typedArray.getDimension(R.styleable.ProgressWheel_contourSize, this.contourSize);
        typedArray.recycle();
    }

    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawArc(this.circleBounds, 360.0f, 360.0f, false, this.circlePaint);
        canvas.drawArc(this.circleBounds, 360.0f, 360.0f, false, this.rimPaint);
        canvas.drawArc(this.circleOuterContour, 360.0f, 360.0f, false, this.contourPaint);
        canvas.drawArc(this.circleInnerContour, 360.0f, 360.0f, false, this.contourPaint);
        if (this.isSpinning) {
            canvas.drawArc(this.circleBounds, (float) (this.progress - 90), (float) this.barLength, false, this.barPaint);
        } else {
            canvas.drawArc(this.circleBounds, -90.0f, (float) this.progress, false, this.barPaint);
        }
        float descent = ((this.textPaint.descent() - this.textPaint.ascent()) / 2.0f) - this.textPaint.descent();
        String[] strArr = this.splitText;
        for (String str : strArr) {
            canvas.drawText(str, ((float) (getWidth() / 2)) - (this.textPaint.measureText(str) / 2.0f), ((float) (getHeight() / 2)) + descent, this.textPaint);
        }
    }

    public boolean isSpinning() {
        return this.isSpinning;
    }

    public void resetCount() {
        this.progress = 0;
        setText("0%");
        invalidate();
    }

    public void stopSpinning() {
        this.isSpinning = false;
        this.progress = 0;
        this.spinHandler.removeMessages(0);
    }

    public void spin() {
        this.isSpinning = true;
        this.spinHandler.sendEmptyMessage(0);
    }

    public void incrementProgress() {
        this.isSpinning = false;
        this.progress++;
        if (this.progress > 360) {
            this.progress = 0;
        }
        this.spinHandler.sendEmptyMessage(0);
    }

    public void setProgress(int i) {
        this.isSpinning = false;
        this.progress = i;
        this.spinHandler.sendEmptyMessage(0);
    }

    public void setText(String str) {
        this.text = str;
        this.splitText = this.text.split("\n");
    }

    public int getCircleRadius() {
        return this.circleRadius;
    }

    public void setCircleRadius(int i) {
        this.circleRadius = i;
    }

    public int getBarLength() {
        return this.barLength;
    }

    public void setBarLength(int i) {
        this.barLength = i;
    }

    public int getBarWidth() {
        return this.barWidth;
    }

    public void setBarWidth(int i) {
        this.barWidth = i;
    }

    public int getTextSize() {
        return this.textSize;
    }

    public void setTextSize(int i) {
        this.textSize = i;
    }

    public int getPaddingTop() {
        return this.paddingTop;
    }

    public void setPaddingTop(int i) {
        this.paddingTop = i;
    }

    public int getPaddingBottom() {
        return this.paddingBottom;
    }

    public void setPaddingBottom(int i) {
        this.paddingBottom = i;
    }

    public int getPaddingLeft() {
        return this.paddingLeft;
    }

    public void setPaddingLeft(int i) {
        this.paddingLeft = i;
    }

    public int getPaddingRight() {
        return this.paddingRight;
    }

    public void setPaddingRight(int i) {
        this.paddingRight = i;
    }

    public int getBarColor() {
        return this.barColor;
    }

    public void setBarColor(int i) {
        this.barColor = i;
    }

    public int getCircleColor() {
        return this.circleColor;
    }

    public void setCircleColor(int i) {
        this.circleColor = i;
    }

    public int getRimColor() {
        return this.rimColor;
    }

    public void setRimColor(int i) {
        this.rimColor = i;
    }

    public Shader getRimShader() {
        return this.rimPaint.getShader();
    }

    public void setRimShader(Shader shader) {
        this.rimPaint.setShader(shader);
    }

    public int getTextColor() {
        return this.textColor;
    }

    public void setTextColor(int i) {
        this.textColor = i;
    }

    public int getSpinSpeed() {
        return this.spinSpeed;
    }

    public void setSpinSpeed(int i) {
        this.spinSpeed = i;
    }

    public int getRimWidth() {
        return this.rimWidth;
    }

    public void setRimWidth(int i) {
        this.rimWidth = i;
    }

    public int getDelayMillis() {
        return this.delayMillis;
    }

    public void setDelayMillis(int i) {
        this.delayMillis = i;
    }
}