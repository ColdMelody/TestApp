package com.demos.testapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.demos.testapp.R;
import com.demos.testapp.fragment.FragActFirstFragment;
import com.demos.testapp.fragment.FragActSecondFragment;

/**
 * Created by peng on 2016/8/30.
 */
public class FragActivity extends AppCompatActivity implements FragActFirstFragment.OnFragmentInteractionListener {
    FragmentManager manager;
    FragmentTransaction transaction;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frag);
        manager=getSupportFragmentManager();
        transaction=manager.beginTransaction();
        Fragment frag1=new FragActFirstFragment();
        transaction.add(R.id.frag_container,frag1);
        transaction.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        FragmentTransaction tran=manager.beginTransaction();
        Fragment frag2=new FragActSecondFragment();
        tran.replace(R.id.frag_container,frag2);
        tran.addToBackStack(null);
        tran.commit();
    }
}
