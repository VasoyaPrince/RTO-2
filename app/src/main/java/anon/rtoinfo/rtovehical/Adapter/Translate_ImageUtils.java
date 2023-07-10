package anon.rtoinfo.rtovehical.Adapter;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Build;

import anon.rtoinfo.rtovehical.Activities.MainActivity;

import java.io.FileOutputStream;

public class Translate_ImageUtils {
    public MainActivity mActivity;

    public Translate_ImageUtils(MainActivity mainActivity) {
        this.mActivity = mainActivity;
    }

    public static void resampleImageAndSaveToNewLocation(String str, String str2) throws Exception {
        resampleImage(str, 800).compress(Bitmap.CompressFormat.PNG, 100, new FileOutputStream(str2));
    }

    @SuppressLint({"UseValueOf"})
    public static Bitmap resampleImage(String str, int i) throws Exception {
        int exifRotation;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        BitmapFactory.Options options2 = new BitmapFactory.Options();
        options2.inSampleSize = getClosestResampleSize(options.outWidth, options.outHeight, i);
        Bitmap decodeFile = BitmapFactory.decodeFile(str, options2);
        Matrix matrix = new Matrix();
        if (decodeFile.getWidth() > i || decodeFile.getHeight() > i) {
            BitmapFactory.Options resampling = getResampling(decodeFile.getWidth(), decodeFile.getHeight(), i);
            matrix.postScale(((float) resampling.outWidth) / ((float) decodeFile.getWidth()), ((float) resampling.outHeight) / ((float) decodeFile.getHeight()));
        }
        if (new Integer(Build.VERSION.SDK).intValue() > 4 && (exifRotation = Translate_ExifUtils.getExifRotation(str)) != 0) {
            matrix.postRotate((float) exifRotation);
        }
        return Bitmap.createBitmap(decodeFile, 0, 0, decodeFile.getWidth(), decodeFile.getHeight(), matrix, true);
    }

    private static BitmapFactory.Options getResampling(int i, int i2, int i3) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        float f = i > i2 ? ((float) i3) / ((float) i) : i2 > i ? ((float) i3) / ((float) i2) : ((float) i3) / ((float) i);
        options.outWidth = (int) ((((float) i) * f) + 0.5f);
        options.outHeight = (int) ((((float) i2) * f) + 0.5f);
        return options;
    }

    private static int getClosestResampleSize(int i, int i2, int i3) {
        int max = Math.max(i, i2);
        int i4 = 1;
        while (true) {
            if (i4 >= Integer.MAX_VALUE) {
                break;
            } else if (i4 * i3 > max) {
                i4--;
                break;
            } else {
                i4++;
            }
        }
        if (i4 > 0) {
            return i4;
        }
        return 1;
    }

    public static BitmapFactory.Options getBitmapDims(String str) throws Exception {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(str, options);
        return options;
    }
}
