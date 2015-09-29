package com.minglang.suiuu.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.minglang.suiuu.R;
import com.minglang.suiuu.adapter.basic.TagAdapter;
import com.minglang.suiuu.customview.FlowLayout;

import java.util.List;

public class QuestionsTagAdapter extends TagAdapter<String> {

    private final LayoutInflater layoutInflater;

    public QuestionsTagAdapter(List<String> tagList, Context context) {
        super(tagList);
        layoutInflater = LayoutInflater.from(context);
    }

    public QuestionsTagAdapter(String[] tagList, Context context) {
        super(tagList);
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public View getView(FlowLayout parent, int position, String s) {
        TextView textView = (TextView) layoutInflater.inflate(R.layout.item_question_tag, parent, false);
        textView.setText(s);
        return textView;
    }

}