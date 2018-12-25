package recyclerview.demo.com.recylerviewdemo;

import android.content.Context;
import android.util.DisplayMetrics;



public class DisplayTool {
    private int wDip; // 屏幕宽度的dip
    private int hDip; // 屏幕长度的dip
    private int wScreen; // 获取屏幕的px
    private int hScreen; // 获取屏幕的px
    public Context con;

    public int getwDip() {
        return wDip;
    }

    public int gethDip() {
        return hDip;
    }

    public int getwScreen() {
        return wScreen;
    }

    public int gethScreen() {
        return hScreen;
    }

    public DisplayTool(Context context) {
        con = context;
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        wScreen = dm.widthPixels; // 屏幕宽度的px
        hScreen = dm.heightPixels; // 屏幕宽度的px
        wDip = px2dip(wScreen); // 屏幕宽度的dip
        hDip = px2dip(hScreen); // 屏幕长度的dip
    }

    public int dip2px(double dipValue) {

        float scale = con.getResources().getDisplayMetrics().density;

        return (int) (dipValue * scale + 0.5f);
    }

    public int px2dip(double pxValue) {

        float scale = con.getResources().getDisplayMetrics().density;

        return (int) (pxValue / scale + 0.5f);

    }



    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public int px2sp(float pxValue) {
        final float fontScale = con.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     *
     * @param spValue （DisplayMetrics类中属性scaledDensity）
     * @return
     */
    public int sp2px(float spValue) {
        final float fontScale = con.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}
