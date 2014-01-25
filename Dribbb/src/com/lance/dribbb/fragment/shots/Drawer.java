package com.lance.dribbb.fragment.shots;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract.Contacts.Data;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.lance.dribbb.R;
import com.lance.dribbb.activites.UActivity;
import com.lance.dribbb.adapter.DrawerAdapter;
import com.lance.dribbb.network.DribbbleAPI;
import com.lance.dribbb.network.PlayerData;
import com.lance.dribbb.views.ConnectDialog;

public class Drawer extends Fragment {
  
  private ConnectDialog c;
  private DrawerAdapter adapter;
  private PlayerData data;
  private Activity mActivity;
  private SharedPreferences userInfo;
  
  public Drawer(Activity a) {
    mActivity = a;
    userInfo = a.getSharedPreferences("user_info", Context.MODE_PRIVATE);
  }
  
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    View rootView = inflater.inflate(R.layout.fragment_drawer, container, false);
    ListView listView = (ListView)rootView.findViewById(R.id.drawer_list);
    data = new PlayerData(getActivity());
    
    adapter = new DrawerAdapter(mActivity, data.getDrawerList());
    listView.setAdapter(adapter);
    
    listView.setOnItemClickListener(new OnItemClickListener() {

      @Override
      public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
          long arg3) {
        Intent intent = new Intent(getActivity(), UActivity.class);
        Bundle bundle = new Bundle();
        if(arg2 == 0) {
          initDialog(getActivity());
        } else if (arg2 == 1) {
          bundle.putString("url", DribbbleAPI.getUserFollowingUrl(userInfo.getString("username", "")));
        } else if (arg2 == 2) {
          bundle.putString("url", DribbbleAPI.getuserLikesUel(userInfo.getString("username", "")));
        }
        intent.putExtras(bundle);
        getActivity().startActivity(intent);
        getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
      }
    });;
    return rootView;
  }
  
  public void initDialog(final Activity a) {
    View connectDialog = a.getLayoutInflater().inflate(R.layout.dialog_connect, null);
    final EditText editText = (EditText)connectDialog.findViewById(R.id.user_account);
    
    if(userInfo.getString("username", "") != ""){
      editText.setText(userInfo.getString("username", ""));
    }
    
    new AlertDialog.Builder(a)
    .setView(connectDialog)
    .setPositiveButton("Ok",
        new android.content.DialogInterface.OnClickListener() {

          @Override
          public void onClick(DialogInterface dialog, int which) {
            if(editText.getText().toString() != "") {
              userInfo.edit().putString("username", editText.getText().toString()).commit();
              data.getPlayerInfo("http://api.dribbble.com/players/" + userInfo.getString("username", ""), adapter);
            }
          }
        }).setNegativeButton("Cancel", null).show();
  }

}
