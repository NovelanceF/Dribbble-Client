package com.lance.dribbb.activites;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.lance.dribbb.R;
import com.lance.dribbb.animation.ZoomOutPageTransformer;
import com.lance.dribbb.fragment.shots.Drawer;
import com.lance.dribbb.fragment.shots.ShotsFragment;

public class ContentActivity extends FragmentActivity implements OnPageChangeListener, ActionBar.TabListener {
  
  private ViewPager contentPager;
  private ActionBarDrawerToggle mDrawerToggle;
  private DrawerLayout mDrawerLayout;
  private mPagerAdapter adapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    requestWindowFeature(Window.FEATURE_ACTION_BAR_OVERLAY);
    setContentView(R.layout.activity_content);
    
    setPager();
    setActionBarStyle();
    
    mDrawerLayout = (DrawerLayout)findViewById(R.id.content_drawer);
    mDrawerLayout.setDrawerShadow(R.drawable.drawer_shadow, GravityCompat.START);
    mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, 
        R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

      public void onDrawerClosed(View view) {
        
      }

      public void onDrawerOpened(View drawerView) {
        
      }
    };
    
    mDrawerLayout.setDrawerListener(mDrawerToggle);
    
    getSupportFragmentManager().beginTransaction()
        .replace(R.id.left_drawer, new Drawer())
        .commit();
    
  }
  
  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.content, menu);
      return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
      int id = item.getItemId();
      if (id == R.id.action_settings) {
          return true;
      }

      if (mDrawerToggle.onOptionsItemSelected(item)) {
          return true;
      }
      return super.onOptionsItemSelected(item);
  }
  
  @Override
  protected void onPostCreate(Bundle savedInstanceState) {
      super.onPostCreate(savedInstanceState);
      mDrawerToggle.syncState();
  }
  
  private void setPager() {
    contentPager = (ViewPager)findViewById(R.id.content_pager);
    contentPager.setOnPageChangeListener(this);
    adapter = new mPagerAdapter(getSupportFragmentManager());
    contentPager.setAdapter(adapter);
    contentPager.setOffscreenPageLimit(2);
    contentPager.setPageTransformer(true, new ZoomOutPageTransformer());
  }
  
  private class mPagerAdapter extends FragmentStatePagerAdapter {

    public mPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int arg0) {
      return new ShotsFragment(arg0, ContentActivity.this);
    }

    @Override
    public int getCount() {
      return 3;
    }
    
    @Override
    public CharSequence getPageTitle(int position) {
      if(position == 0) {
        return "Debut";
      } else if (position == 1) {
        return "Popular";
      } else {
        return "Everyone";
      }
    }
    
  }

  @Override
  public void onPageScrollStateChanged(int arg0) {
    
  }

  @Override
  public void onPageScrolled(int arg0, float arg1, int arg2) {
    
  }

  @Override
  public void onPageSelected(int position) {
    getActionBar().setSelectedNavigationItem(position);
  }
  
  private void setActionBarStyle() {
    this.getActionBar().setTitle("dribbble");    
    getActionBar().setBackgroundDrawable(this.getBaseContext().getResources().getDrawable(R.drawable.actionbar_back));
    getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
    int titleId = Resources.getSystem().getIdentifier("action_bar_title", "id", "android");
    TextView textView = (TextView) findViewById(titleId);
    textView.setTypeface(Typeface.createFromAsset(getAssets(), "font/Wendy.ttf"));
    textView.setTextColor(0xFFdfdfdf);
    textView.setTextSize(32); 
    getActionBar().setDisplayHomeAsUpEnabled(true);
    getActionBar().setHomeButtonEnabled(true);
    
    for (int i = 0; i < adapter.getCount(); i++) {
      getActionBar().addTab( getActionBar().newTab().setText(adapter.getPageTitle(i)).setTabListener(this));
    }
  }

  @Override
  public void onTabReselected(Tab tab, FragmentTransaction ft) {
    
  }

  @Override
  public void onTabSelected(Tab tab, FragmentTransaction ft) {
    contentPager.setCurrentItem(tab.getPosition());
  }

  @Override
  public void onTabUnselected(Tab tab, FragmentTransaction ft) {
    
  }

}
