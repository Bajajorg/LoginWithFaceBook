package com.loginfbk;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Simpli5d on 15/05/16.
 */
public class ShowProfileInformation extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ArrayList<String> stringArrayList;
    private RecyclerAdapter adapter;
    private String email;
    private String gender;
    private String name;
    private String id;
    private String birthday;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_info);

        Intent intent = getIntent();
        HashMap<String, String> hashMap = (HashMap<String, String>) intent.getSerializableExtra("hashMap");
        email = hashMap.get("email");
        gender = hashMap.get("gender");
        name = hashMap.get("name");
        id = hashMap.get("id");
        birthday = hashMap.get("birthday");
        imgUrl = hashMap.get("imgurl");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(getString(R.string.expand));

       /* recyclerView = (RecyclerView) findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        setData(); //adding data to array list
        adapter = new RecyclerAdapter(this, stringArrayList);
        recyclerView.setAdapter(adapter);*/
        initViews();
    }

    private void initViews(){
        TextView textViewName = (TextView) findViewById(R.id.name);
        textViewName.setText(name);
        TextView textViewGender = (TextView) findViewById(R.id.gender);
        textViewGender.setText(gender);
        TextView textViewEmail = (TextView) findViewById(R.id.email);
        textViewEmail.setText(email);
        TextView textViewBirthday = (TextView) findViewById(R.id.birthday);
        textViewBirthday.setText(birthday);
        ImageView imageViewProfile = (ImageView) findViewById(R.id.header);

        ImageLoader imageLoader = ImageLoader.getInstance();
        DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
                .cacheOnDisc(true).resetViewBeforeLoading(true)
                .showImageForEmptyUri(R.mipmap.ic_launcher)
                .showImageOnFail(R.mipmap.ic_launcher)
                .showImageOnLoading(R.mipmap.ic_launcher).build();

        imageLoader.displayImage(imgUrl, imageViewProfile, options);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
    private void setData() {
        stringArrayList = new ArrayList<>();

        for (int i = 0; i < 100; i++) {
            stringArrayList.add("Item " + (i + 1));
        }
    }


}
