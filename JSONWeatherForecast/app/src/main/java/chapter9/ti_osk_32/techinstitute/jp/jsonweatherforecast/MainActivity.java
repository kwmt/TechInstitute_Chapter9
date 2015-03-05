package chapter9.ti_osk_32.techinstitute.jp.jsonweatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class MainActivity extends ActionBarActivity {


    private ListView listView;
    private List<String> dataList = new ArrayList<String>();
    private ArrayAdapter<String> adapter;

    // キーを格納した順に順番を保持
    public static Map<String, String> prefectures = new LinkedHashMap<String, String>();
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
                // クリックされたリスト位置(position)から都道府県のkey("Osaka-shi"など)を取得する。
                String key = prefectureKeys.get(position);

                Intent intent = new Intent(MainActivity.this, WeatherForecastDetailActivity.class);
                // 都道府県のkeyをインテントに詰め込む
                intent.putExtra("prefecture", key);
                // 天気予報詳細アクティビティを表示する。
                startActivity(intent);
            }
        });

    }

    /**
     * 都道府県リストの作成して準備しておく。
     */
    private void setupPrefectures() {
        // openweathermap APIのCityリスト
        // http://openweathermap.org/help/city_list.txt

        prefectures.put("Sapporo-shi", "札幌市");
        prefectures.put("Aomori-shi", "青森市");
        prefectures.put("Sendai-shi", "仙台市");
        prefectures.put("Tokyo", "東京");
        prefectures.put("Osaka-shi", "大阪市");
        prefectures.put("Kobe-shi", "神戸市");
        prefectures.put("Okayama-shi", "岡山市");
        prefectures.put("Yamaguchi-shi", "山口市");
        prefectures.put("Matsuyama-shi", "松山市");
        prefectures.put("Kochi-shi", "高知市");
        prefectures.put("Fukuoka-shi", "福岡市");
        prefectures.put("Kagoshima-shi", "鹿児島市");

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
