package com.example.favoritestore;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.ShowableListMenu;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView favoriteListView;
    private FloatingActionButton addButton;
    private List<StoreData> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        favoriteListView = (ListView) findViewById(R.id.favoriteListView);
        addButton = (FloatingActionButton) findViewById(R.id.addBtn);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), AddFavoriteActivity.class));
            }
        });
        setOnClickListView();

    }

    @Override
    protected void onResume() {
        super.onResume();
        createList();
    }

    private void createList() {
        SharedPreferences sp = this.getSharedPreferences("data", Context.MODE_PRIVATE);
        list = new ArrayList<StoreData>();
        if (!sp.getString("list", "").equals("")) {
            Gson gson = new Gson();
            JsonArray jsonArray = gson.fromJson(sp.getString("list", ""), JsonArray.class);
            Type listType = new TypeToken<ArrayList<StoreData>>() {

            }.getType();
            list = gson.fromJson(jsonArray, listType);
            if (list.size() > 0) {
                ArrayList<String> nameList = new ArrayList<String>();
                for (int i = 0; i < list.size(); i++) {
                    nameList.add(list.get(i).getName());
                }
                setDataToList(nameList);
            } else {

            }

        } else {

        }
    }

   /* private void createTempData() {
        List<StoreData> storeList = new ArrayList<StoreData>();
        storeList.add(new StoreData("ร้านเล่า", 12.56567, 12.56432));
        storeList.add(new StoreData("ม. สุรา", 12.56567, 12.56432));
        storeList.add(new StoreData("บางเวลา", 12.56567, 12.56432));
        //setDataToList(storeList);
    }*/

    private void setDataToList(List<String> list) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        favoriteListView.setAdapter(adapter);
    }

    private void setOnClickListView() {
        favoriteListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("Main", "cluck position: " + position);
                Intent intent = new Intent(MainActivity.this, ShowMapActivity.class);
                intent.putExtra("latitude", list.get(position).getLatitude());
                intent.putExtra("longitude", list.get(position).getLongitude());
                startActivity(intent);
            }
        });
    }
}
