package cn.codeyourlife.fragmentbestpractice;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NewsContentFragment extends Fragment {
    private View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        // 绑定布局
        this.view = inflater.inflate(R.layout.news_content_frag, container, false);
        return this.view;
    }

    public void refresh(String newsTitle, String newsContent) {
        View visibilityLayout = this.view.findViewById(R.id.visibility_layout);
        visibilityLayout.setVisibility(View.VISIBLE);

        TextView newsTitleText = (TextView) this.view.findViewById(R.id.news_title);
        newsTitleText.setText(newsTitle);

        TextView newsContentText = (TextView) this.view.findViewById(R.id.news_content);
        newsContentText.setText(newsContent);
    }
}
