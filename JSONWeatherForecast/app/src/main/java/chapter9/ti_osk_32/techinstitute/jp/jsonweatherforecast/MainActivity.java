package chapter9.ti_osk_32.techinstitute.jp.jsonweatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private List<String> dataList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    private Map<String, String> prefectures = new HashMap<String, String>();
    private ArrayList<String> prefectureKeys;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = (ListView)findViewById(R.id.listView);

        setupPrefectures();
        setAdapters();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListView listView = (ListView) parent;
                // クリックされたアイテムを取得します
//                String item = (String) listView.getItemAtPosition(position);
                String key = prefectureKeys.get(position);
                Toast.makeText(MainActivity.this, key, Toast.LENGTH_LONG).show();

                Intent intent = new Intent(MainActivity.this, WeatherForecastDetailActivity.class);
                intent.putExtra("prefecture", key);
                startActivity(intent);


            }
        });

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, WeatherForecastDetailActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setupPrefectures() {
        prefectures.put("tokyo", "東京");
        prefectures.put("osaka", "大阪");

        prefectureKeys = new ArrayList<String>(prefectures.keySet());
    }

    protected void setAdapters(){
        adapter = new ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                dataList);
        listView.setAdapter(adapter);

        for(Map.Entry<String, String> e : prefectures.entrySet()) {
            adapter.add(e.getValue());
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
