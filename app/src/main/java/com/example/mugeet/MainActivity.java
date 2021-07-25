package com.example.mugeet;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

//    RecyclerView recyclerView;

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listview);
//        recyclerView = findViewById(R.id.recyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        // using Dexter module to ask storage permission from the user to read the songs from the storage


        Dexter.withContext(this)
                .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        ArrayList<File> mysongs = fetchsongs(Environment.getExternalStorageDirectory());
                        String [] items = new String[mysongs.size()];
                        for (int i=0 ; i<mysongs.size() ; i++){
                            items[i] = mysongs.get(i).getName().replace(".mp3","");
                        }

                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(MainActivity.this , android.R.layout.simple_list_item_1, items);
                        listView.setAdapter(adapter);
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(MainActivity.this , PlaySong.class);
                                String currentSong = listView.getItemAtPosition(position).toString();
                                intent.putExtra("songList",mysongs);
                                intent.putExtra("currentSong", currentSong);
                                intent.putExtra("position",position);
                                startActivity(intent);
                            }
                        });

                        //Commented recycler view adapter if to use the customized layout
//                        CustomAdapter ad = new CustomAdapter(items);
//                        recyclerView.setAdapter(ad);

                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                })
                .check();
    }
    // Adding the songs from the storage in a list and not adding files google mp3 files starting with "."
    public ArrayList<File> fetchsongs(File file){
            ArrayList arrayList = new ArrayList();
            File [] songs = file.listFiles();
            if (songs !=null){
                for (File myFile: songs){
                    if (!myFile.isHidden() && myFile.isDirectory()){
                        arrayList.addAll(fetchsongs(myFile));
                    }
                    else{
                        if (myFile.getName().endsWith(".mp3")&& !myFile.getName().startsWith(".")){
                            arrayList.add(myFile);
                        }
                    }
                }
            }
            return arrayList;
    }

}