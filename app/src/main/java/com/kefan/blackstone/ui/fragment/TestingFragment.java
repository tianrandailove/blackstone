package com.kefan.blackstone.ui.fragment;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.kefan.blackstone.R;
import com.kefan.blackstone.ui.activity.MainActivity;
import com.kefan.blackstone.ui.activity.RankActivity;
import com.kefan.blackstone.widget.HeaderBar;
import com.kefan.blackstone.widget.PrecentCricleView;
import com.orhanobut.logger.Logger;

import butterknife.BindView;


/**
 * @note:
 * @author: 柯帆
 * @date: 2018/6/8 上午12:42
 */
public class TestingFragment extends BaseFragment implements View.OnClickListener {

    @BindView(R.id.iv_icon_test_fragment)
    ImageView icon;

    @BindView(R.id.precent_view_correct_rate_testing_fragment)
    PrecentCricleView correctRate;

    @BindView(R.id.precent_view_this_score_testing_fragment)
    PrecentCricleView thisScore;

    @BindView(R.id.precent_view_last_score_testing_fragment)
    PrecentCricleView lastScore;

    @BindView(R.id.tv_start_testing_fragment)
    TextView start;

    @BindView(R.id.tv_rank_testing_fragment)
    TextView rank;


    private ExecuteTestFragment executeTestFragment;

    private HeaderBar headerBar;

    @Override
    public int setLayout() {
        return R.layout.fragment_testing;
    }

    @Override
    public void initView() {


        Glide.with(this).load(R.drawable.testing_bg).into(icon);
        headerBar=((MainActivity) getActivity()).headerBar;

        headerBar.getRightPart().setVisibility(View.GONE);
        headerBar.getCenterTextView().setText("小测试");

    }

    @Override
    public void initEvent() {

        start.setOnClickListener(this);
        rank.setOnClickListener(this);

    }

    @Override
    public void onDestroyView() {
        headerBar.getRightPart().setVisibility(View.VISIBLE);
        headerBar.getCenterTextView().setText("");
        super.onDestroyView();

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {
            case R.id.tv_start_testing_fragment:

                if (executeTestFragment == null) {
                    executeTestFragment=new ExecuteTestFragment();
                }

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fl_content_main_activity,executeTestFragment).commit();

                break;
            case R.id.tv_rank_testing_fragment:

                startActivity(new Intent(getContext(), RankActivity.class));

                break;
        }

    }
}