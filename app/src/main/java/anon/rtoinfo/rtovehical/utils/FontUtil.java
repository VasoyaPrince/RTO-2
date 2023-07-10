package anon.rtoinfo.rtovehical.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontUtil {
    private static Typeface font;
    private static String typeFaceName;

    public enum FontType {
        SOURCE_SANS_PRO_REGULAR {
            public String toString() {
                return "fonts/Signika-Regular.ttf";
            }
        },
        SOURCE_SANS_PRO_BOLD {
            public String toString() {
                return "fonts/Signika-SemiBold.ttf";
            }
        }
    }

    public static Typeface getTypeface(Context context, String str) {
        try {
            if (str.equals(FontType.SOURCE_SANS_PRO_REGULAR.toString())) {
                String str2 = typeFaceName;
                if (str2 == null || !str2.equals(str)) {
                    font = Typeface.createFromAsset(context.getAssets(), str);
                    typeFaceName = str;
                }
                return font;
            } else if (!str.equals(FontType.SOURCE_SANS_PRO_BOLD.toString())) {
                return null;
            } else {
                String str3 = typeFaceName;
                if (str3 == null || !str3.equals(str)) {
                    font = Typeface.createFromAsset(context.getAssets(), str);
                    typeFaceName = str;
                }
                return font;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Typeface.DEFAULT;
        }
    }

    public static Typeface getTypeface(Context context, FontType fontType) {
        return getTypeface(context, fontType.toString());
    }
}
