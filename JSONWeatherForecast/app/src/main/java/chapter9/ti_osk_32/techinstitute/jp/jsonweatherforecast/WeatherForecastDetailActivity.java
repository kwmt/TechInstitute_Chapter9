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

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;



public class WeatherForecastDetailActivity extends ActionBarActivity {

    // 天気予報API URL
    String weatherUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=2&q=";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast_detail);

        // インテントに入っているcityを取り出す。
        String city = getIntent().getStringExtra("city");

        // 天気予報詳細画面に都市名を表示する。
        TextView cityTextView = (TextView) findViewById(R.id.city);
        cityTextView.setText(city);

        // バックグラウンドでネットワークにアクセスして、
        // バックグラウンドでの処理が完了すると、UIスレッドでビューにセットする。
        ForecastLoadAsyncTask task = new ForecastLoadAsyncTask();
        task.execute(weatherUrl + city);
    }

    /**
     * 天気予報情報をロードするクラス
     */
    public class ForecastLoadAsyncTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            // バックグラウンド(サブスレッド)で処理する必要のある処理を書く。
            // ここではインターネットに接続する処理を書く。

            // 結果を格納する箱を用意
            StringBuilder content = new StringBuilder();

            try {
                // urlオブジェクトを作成
                URL url = new URL(params[0]);

                // インターネットに接続
                URLConnection urlConnection = url.openConnection();

                // データを取得
                InputStream inputStream = urlConnection.getInputStream();

                // データを扱いやすい形に変換する。
                InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    content.append(line);
                }
                bufferedReader.close();
                Log.d("結果の確認", content.toString());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return content.toString();
        }

        @Override
        protected void onPostExecute(String content) {
            super.onPostExecute(content);

            // バックグラウンドでの実行が終わると呼ばれる。（UIスレッドで実行）
            // UIを操作する処理を書く。

            try {
                // 明日の天気予報
                // JSONをパースして、最高気温、最低気温、予報画像URLが入ったインスタンスを作成
                Forecast forecastTomorrow = new Forecast(content, 0);

                // 最高気温を表示するTextView
                TextView tempMaxTextView = (TextView)findViewById(R.id.tempMax);
                // 最低気温を表示するTextView
                TextView tempMinTextView = (TextView)findViewById(R.id.tempMin);
                // 天気予報を表示するImageView
                ImageView weatherIconImageView = (ImageView)findViewById(R.id.weatherIcon);

                // 取得した最高気温、最低気温をViewにセットする。
                tempMaxTextView.setText(forecastTomorrow.getTempMax());
                tempMinTextView.setText(forecastTomorrow.getTempMin());

                // 画像をダウンロードして、ImageVieｗにセットする
                DownloadImageAsyncTask task = new DownloadImageAsyncTask(weatherIconImageView);
                task.execute(forecastTomorrow.getWeatherIconUrl());


                // 明後日の天気予報
                Forecast forecastDayAfterTomorrow = new Forecast(content, 1);

                // 最高気温を表示するTextView
                TextView tempMaxTextView2 = (TextView)findViewById(R.id.tempMax2);
                // 最低気温を表示するTextView
                TextView tempMinTextView2 = (TextView)findViewById(R.id.tempMin2);
                // 天気予報を表示するImageView
                ImageView weatherIconImageView2 = (ImageView)findViewById(R.id.weatherIcon2);

                // 取得した最高気温、最低気温をViewにセットする。
                tempMaxTextView2.setText(forecastDayAfterTomorrow.getTempMax());
                tempMinTextView2.setText(forecastDayAfterTomorrow.getTempMin());

                // 画像をダウンロードして、ImageVieｗにセットする
                DownloadImageAsyncTask task2 = new DownloadImageAsyncTask(weatherIconImageView2);
                task2.execute(forecastDayAfterTomorrow.getWeatherIconUrl());


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    /**
     * 天気予報画像をダウンロードするクラス
     */
    public class DownloadImageAsyncTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView imageView;
        public DownloadImageAsyncTask(ImageView imageView) {
            this.imageView = imageView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            // バックグラウンド(サブスレッド)で処理する必要のある処理を書く。
            // ここではインターネットに接続する処理を書く。

            // 結果を格納する箱を用意
            Bitmap bitmap = null;

            try {
                // urlオブジェクトを作成
                URL url = new URL(params[0]);

                // urlConnectionを作成
                URLConnection urlConnection = url.openConnection();

                // 画像データを取得
                InputStream inputStream = urlConnection.getInputStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch(Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap image) {
            super.onPostExecute(image);

            // バックグラウンドでの実行が終わると呼ばれる。（UIスレッドで実行）
            // UIを操作する処理を書く。

            // 画像データをimageViewにセットする。
            imageView.setImageBitmap(image);

        }
    }


    /***********************************************************
     * ここから下はメニュー項目関連(今回の講義では使用しない)
     **********************************************************/

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
