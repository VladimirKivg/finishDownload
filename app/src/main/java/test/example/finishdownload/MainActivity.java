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

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

import test.example.finishdownload.regions.ReadXml;
import test.example.finishdownload.regions.Region;


public class MainActivity extends AppCompatActivity {
   private ArrayList<Region> europe;
   private static Region region;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("Download Map");
        ListView listView=findViewById(R.id.listView);

        XmlPullParser xmlPullParser=getResources().getXml(R.xml.regions);

        ReadXml readXml=new ReadXml(xmlPullParser);
        for (Region region:readXml.getRegions()){
            if (region.getName().equals("europe")){
                this.europe=region.getUnderRegions();
            }
        }

        ArrayAdapter<Region> arrayAdapter=new ArrayAdapter<Region>(this, android.R.layout.simple_dropdown_item_1line,europe);

        listView.setAdapter(arrayAdapter);

        Intent intent = new Intent(MainActivity.this, Country.class);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                if (europe.get(position).getUnderRegions().size()!=0){
                    region = europe.get(position);
                    Log.d("Revision","Name = "+europe.get(position).getName()+" Глубина = "+europe.get(position).getDeeply());
                startActivity(intent);}

                else if (europe.get(position).isMap()&&europe.get(position).isThisMapIs()){
                    europe.get(position).setThisMapIs(false);

                    DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

                    Uri uri = Uri.parse(europe.get(position).getDownloadName().trim());
                    DownloadManager.Request request=new DownloadManager.Request(uri);
                    request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    Long reference = downloadManager.enqueue(request);

                }else if (!europe.get(position).isMap()){
                    Toast.makeText(MainActivity.this, " this map isn't", Toast.LENGTH_SHORT).show();
                }else if (!europe.get(position).isThisMapIs()){
                    Toast.makeText(MainActivity.this, "in your mobil is this map ", Toast.LENGTH_SHORT).show();}


            }



        });

    }

    public static Region getRegion() {
        return region;
    }
}