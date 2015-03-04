package chapter9.ti_osk_32.techinstitute.jp.jsonweatherforecast;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class WeatherForecastDetailActivity extends ActionBarActivity {

    // 天気予報API URL
    String weatherUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?q=Osaka&mode=json&units=metric&cnt=2";

    // 天気予報API 画像ダウンロード用 URL
    String imageUrl = "http://openweathermap.org/img/w/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast_detail);

        // 非同期でネットワークにアクセスする。
        AsyncHttpRequest task = new AsyncHttpRequest();
        task.execute(weatherUrl);
    }


    public class AsyncHttpRequest extends AsyncTask<String, Void, String> {

        public AsyncHttpRequest() {

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


        // このメソッドは非同期処理が終わった後に呼び出される。
        @Override
        protected void onPostExecute(String result) {
            try {

                JSONObject jsonObject = new JSONObject(result);
                JSONArray list = jsonObject.getJSONArray("list");
                JSONObject tomorrow = list.getJSONObject(0);
                int tempMax = tomorrow.getJSONObject("temp").getInt("max");
                int tempMin = tomorrow.getJSONObject("temp").getInt("min");
                String weatherIcon = tomorrow.getJSONArray("weather").getJSONObject(0).getString("icon");

                TextView tempMaxTextView = (TextView)findViewById(R.id.tempMax);
                TextView tempMinTextView = (TextView)findViewById(R.id.tempMin);

                tempMaxTextView.setText(String.valueOf(tempMax));
                tempMinTextView.setText(String.valueOf(tempMin));

                // 画像をダウンロードする。
                DownloadImageTask task = new DownloadImageTask();
                task.execute(imageUrl + weatherIcon + ".png");


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }

    /**
     * 画像ダウンロード用のAsyncTask
     */
    public class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {


        public DownloadImageTask() {
        }

        @Override
        protected Bitmap doInBackground(String... urls) {

            InputStream inputStream = null;
            Bitmap bitmap = null;
            StringBuilder content = new StringBuilder();

            try {
                // urlオブジェクトを作成
                URL url = new URL(urls[0]);

                // urlConnectionを作成
                URLConnection urlConnection = url.openConnection();

                // データを取得
                inputStream = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch(Exception e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return bitmap;
        }


        // このメソッドは非同期処理が終わった後に呼び出さる。
        @Override
        protected void onPostExecute(Bitmap result) {
            ImageView imageView = (ImageView)findViewById(R.id.weatherIcon);
            imageView.setImageBitmap(result);

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
