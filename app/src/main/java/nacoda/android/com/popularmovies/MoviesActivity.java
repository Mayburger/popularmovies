package nacoda.android.com.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import nacoda.android.com.popularmovies.favoritesdata.FavoritesContract;
import nacoda.android.com.popularmovies.fragments.Popular;
import nacoda.android.com.popularmovies.fragments.Top;

public class MoviesActivity extends AppCompatActivity {

    private TabLayout tabs;
    private ViewPager vPager;
    ImageView ivIntentFavorites;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ivIntentFavorites = (ImageView)findViewById(R.id.ivIntentFavorites);
        setSupportActionBar(toolbar);

        tabs = (TabLayout)findViewById(R.id.tabs);
        vPager = (ViewPager)findViewById(R.id.vPager);
        SetupViewPager(vPager);
        tabs.setupWithViewPager(vPager);

        ivIntentFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MoviesActivity.this, FavoritesActivity.class);
                startActivity(intent);
            }
        });

    }

    //        Setup View Pager
    private void SetupViewPager(ViewPager viewPager){
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new Top(),"Top");
        adapter.addFragment(new Popular(),"Popular");
        viewPager.setAdapter(adapter);
    }
    //Adapter View Pager
    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> nameList =new ArrayList<>();




        public ViewPagerAdapter(FragmentManager fm){
            super(fm);
        }
        //Get Data Fragment

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
        //Method Menambahkan Fragment
        public void addFragment(Fragment fragment, String title){
            fragmentList.add(fragment);
            nameList.add(title);
        }

        //Setting nama tabs

        @Override
        public CharSequence getPageTitle(int position) {
//            return super.getPageTitle(position);
            return nameList.get(position);
        }
    }
}
