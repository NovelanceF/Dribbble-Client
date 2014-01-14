package com.lance.dribbb.activites.content;

import com.lance.dribbb.R;
import com.lance.dribbb.R.layout;
import com.lance.dribbb.R.menu;
import com.lance.dribbb.network.DribbbleAPI;
import com.lance.dribbb.network.ShotsData;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;

public class ContentActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_content);
    TextView textView = (TextView)findViewById(R.id.ttt);
    ShotsData getData = new ShotsData(this);
    getData.getShots(DribbbleAPI.SHOTS_POPULAR, textView);
  }

}
