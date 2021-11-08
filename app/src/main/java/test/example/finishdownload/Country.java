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


public class Country extends AppCompatActivity {
    private ArrayList<Region> country;
    private static Region region;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country);
        setTitle(MainActivity.getRegion().getNormalName());
        ListView listView=findViewById(R.id.listView2);
        country=MainActivity.getRegion().getUnderRegions();

        ArrayAdapter<Region> adapter=new ArrayAdapter<Region>(this, android.R.layout.simple_dropdown_item_1line,country);

        listView.setAdapter(adapter);

        Intent intent=new Intent(Country.this,CountryRegions.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                if (country.get(position).getUnderRegions().size()!=0){
                    region=country.get(position);
                    Log.d("Revision","Name = "+country.get(position).getName()+" Глубина = "+country.get(position).getDeeply());

                    startActivity(intent);}
                else if (country.get(position).isMap()&&country.get(position).isThisMapIs()){
                    country.get(position).setThisMapIs(false);

                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                    Uri uri = Uri.parse(country.get(position).getDownloadName().trim());
                    DownloadManager.Request request=new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long reference = downloadManager.enqueue(request);

                }else if (!country.get(position).isMap()){
                    Toast.makeText(Country.this, " this map isn't", Toast.LENGTH_SHORT).show();
                }else if (!country.get(position).isThisMapIs()){
                    Toast.makeText(Country.this, "in your mobil is this map ", Toast.LENGTH_SHORT).show();}
            }
        });
    }

    public static Region getRegion() {
        return region;
    }
}