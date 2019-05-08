package usama.utech.firebasepractice.AllPostsWork;

import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import usama.utech.firebasepractice.Fragments.ListAllPostsDriver;
import usama.utech.firebasepractice.Fragments.ListAllPostsRider;
import usama.utech.firebasepractice.R;

public class ListAllPosts extends AppCompatActivity implements
        ListAllPostsRider.OnFragmentInteractionListener,
        ListAllPostsDriver.OnFragmentInteractionListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_all_posts);


        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), FragmentPagerItems.with(this)
                .add("Driver Posts", ListAllPostsDriver.class)
                .add("Riders Posts", ListAllPostsRider.class)
                .create());

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);

        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpagertab);
        viewPagerTab.setViewPager(viewPager);
        viewPagerTab.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Toast.makeText(ListAllPosts.this, "Changed to "+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
