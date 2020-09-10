package cn.codeyourlife.uicustomview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class TitleLayout extends LinearLayout {
    public TitleLayout(Context context, AttributeSet attributeSet){
        super(context, attributeSet);
        LayoutInflater.from(context).inflate(R.layout.title, this);
    }
}
