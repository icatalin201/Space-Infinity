package space.infinity.app.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import space.infinity.app.R;
import space.infinity.app.view.fragment.IntroFragment1;
import space.infinity.app.view.fragment.IntroFragment2;
import space.infinity.app.view.fragment.IntroFragment3;
import space.infinity.app.view.fragment.IntroFragment4;
import space.infinity.app.viewmodel.adapters.SectionsPagerAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class IntroActivity extends AppCompatActivity {

    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mSectionsPagerAdapter.addFragment(new IntroFragment1());
        mSectionsPagerAdapter.addFragment(new IntroFragment2());
        mSectionsPagerAdapter.addFragment(new IntroFragment3());
        mSectionsPagerAdapter.addFragment(new IntroFragment4());
        mViewPager = findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        final ImageView indicator0 = findViewById(R.id.intro_indicator_0);
        final ImageView indicator1 = findViewById(R.id.intro_indicator_1);
        final ImageView indicator2 = findViewById(R.id.intro_indicator_2);
        final ImageView indicator3 = findViewById(R.id.intro_indicator_3);
        final Button skip = findViewById(R.id.intro_btn_skip);
        final Button finish = findViewById(R.id.intro_btn_finish);
        final ImageButton next = findViewById(R.id.intro_btn_next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
            }
        });
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            }
        });
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(IntroActivity.this, MainActivity.class));
                finish();
            }
        });
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        indicator0.setBackgroundResource(R.drawable.indicator_selected);
                        indicator1.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator2.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator3.setBackgroundResource(R.drawable.indicator_unselected);
                        break;
                    case 1:
                        indicator0.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator1.setBackgroundResource(R.drawable.indicator_selected);
                        indicator2.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator3.setBackgroundResource(R.drawable.indicator_unselected);
                        break;
                    case 2:
                        indicator0.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator1.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator2.setBackgroundResource(R.drawable.indicator_selected);
                        indicator3.setBackgroundResource(R.drawable.indicator_unselected);
                        break;
                    case 3:
                        indicator0.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator1.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator2.setBackgroundResource(R.drawable.indicator_unselected);
                        indicator3.setBackgroundResource(R.drawable.indicator_selected);
                        break;
                }
                if (position == 3) {
                    next.setVisibility(View.GONE);
                    finish.setVisibility(View.VISIBLE);
                    skip.setVisibility(View.GONE);
                } else {
                    next.setVisibility(View.VISIBLE);
                    finish.setVisibility(View.GONE);
                    skip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
