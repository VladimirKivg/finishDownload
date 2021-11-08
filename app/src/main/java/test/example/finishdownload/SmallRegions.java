package test.example.finishdownload;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import test.example.finishdownload.regions.Region;


public class SmallRegions extends AppCompatActivity {

    private ArrayList<Region> countryRegions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_small_regions);
        ListView listView=findViewById(R.id.listView4);
        countryRegions=CountryRegions.getSmallRegion().getUnderRegions();
        setTitle(CountryRegions.getSmallRegion().getNormalName());
        ArrayAdapter<Region> adapter= new ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line,countryRegions);
        listView.setAdapter(adapter);



        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (countryRegions.get(position).isMap()&&countryRegions.get(position).isThisMapIs()){
                    countryRegions.get(position).setThisMapIs(false);

                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                    Uri uri = Uri.parse(countryRegions.get(position).getDownloadName().trim());
                    DownloadManager.Request request=new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long reference = downloadManager.enqueue(request);

                }else if (!countryRegions.get(position).isMap()){
                    Toast.makeText(SmallRegions.this, " this map isn't", Toast.LENGTH_SHORT).show();
                }else if (!countryRegions.get(position).isThisMapIs()){
                    Toast.makeText(SmallRegions.this, "in your mobil is this map ", Toast.LENGTH_SHORT).show();}


            }



        });



    }
}