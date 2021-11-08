package test.example.finishdownload;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

import test.example.finishdownload.regions.Region;


public class CountryRegions extends AppCompatActivity {

    private ArrayList<Region> countryRegions;
    private static Region smallRegion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_region);
        setTitle(Country.getRegion().getNormalName());
        countryRegions = Country.getRegion().getUnderRegions();
        ListView listView = findViewById(R.id.listView3);
        ArrayAdapter<Region> adapter = new ArrayAdapter<Region>(this, android.R.layout.simple_dropdown_item_1line, countryRegions);
        listView.setAdapter(adapter);
        Intent intent=new Intent(CountryRegions.this,SmallRegions.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

              if (countryRegions.get(position).getUnderRegions().size()!=0){
                  smallRegion= countryRegions.get(position);
                  Log.d("Revision","Name = "+countryRegions.get(position).getName()+" Глубина = "+countryRegions.get(position).getDeeply());

                  startActivity(intent);

              }
              else if (countryRegions.get(position).isMap()&&countryRegions.get(position).isThisMapIs()){
                  countryRegions.get(position).setThisMapIs(false);

                  DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                  Uri uri = Uri.parse(countryRegions.get(position).getDownloadName().trim());
                  DownloadManager.Request request=new DownloadManager.Request(uri);
                  request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                  Long reference = downloadManager.enqueue(request);

              }else if (!countryRegions.get(position).isMap()){
                  Toast.makeText(CountryRegions.this, " this map isn't", Toast.LENGTH_SHORT).show();
              }else if (!countryRegions.get(position).isThisMapIs()){
                  Toast.makeText(CountryRegions.this, "in your mobil is this map ", Toast.LENGTH_SHORT).show();}
            }
        });

    }

    public static Region getSmallRegion() {
        return smallRegion;
    }
}