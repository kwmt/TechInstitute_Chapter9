package chapter9.ti_osk_32.techinstitute.jp.jsonweatherforecast;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;


public class WeatherForecastDetailActivity extends ActionBarActivity {

    // 天気予報API URL
    String weatherUrl = "http://api.openweathermap.org/data/2.5/forecast/daily?mode=json&units=metric&cnt=2&q=Osaka-shi";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weather_forecast_detail);

        // 結果を格納する箱を用意
        StringBuilder content = new StringBuilder();

        try {
            // urlオブジェクトを作成
            URL url = new URL(weatherUrl);

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
        } catch(Exception e) {
            e.printStackTrace();
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
