package chapter9.ti_osk_32.techinstitute.jp.jsonweatherforecast;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class WeatherForecastDetailActivity extends ActionBarActivity {

    // 天気予報API URL
    String weatherUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Osaka&mode=json&units=metric&cnt=2";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast_detail);

        // 非同期でネットワークにアクセスする。
        AsyncHttpRequest task = new AsyncHttpRequest(this);
        task.execute(weatherUrl);
    }


    public class AsyncHttpRequest extends AsyncTask<String, Void, String> {

        private Activity activity;

        public AsyncHttpRequest(Activity activity) {

            // 呼び出し元のアクティビティ
            this.activity = activity;
        }

        @Override
        protected String doInBackground(String... urls) {

            StringBuilder content = new StringBuilder();

            try {
                // urlオブジェクトを作成
                URL url = new URL(urls[0]);

                // urlConnectionを作成
                URLConnection urlConnection = url.openConnection();

                // データを取得
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                String line;

                // データを一行ずつ読み込んで、contentに追記する。
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }

                bufferedReader.close();

            } catch(Exception e) {
                e.printStackTrace();
            }

            // 取得結果を確認
            Log.d("TAG", content.toString());
            return content.toString();

        }


        // このメソッドは非同期処理が終わった後に呼び出さる。
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray list = jsonObject.getJSONArray("list");
                JSONObject tomorrow = list.getJSONObject(0);
                int tempMax = tomorrow.getJSONObject("temp").getInt("max");
                int tempMin = tomorrow.getJSONObject("temp").getInt("min");

                TextView tempMaxTextView = (TextView)activity.findViewById(R.id.tempMax);
                TextView tempMinTextView = (TextView)activity.findViewById(R.id.tempMin);

                tempMaxTextView.setText(String.valueOf(tempMax));
                tempMinTextView.setText(String.valueOf(tempMin));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_weather_forecast_detail, menu);
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
