package chapter9.ti_osk_32.techinstitute.jp.jsonweatherforecast;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 大阪ボタンのインスタンスを作成する。
        Button osakaButton = (Button)findViewById(R.id.osakaButton);
        // 大阪ボタンにOnClickイベントを設定する。
        osakaButton.setOnClickListener(new View.OnClickListener() {
            // 大阪ボタンが押されたときの処理を書く。
            @Override
            public void onClick(View v) {
                // 天気予報詳細画面に遷移する。
                Intent intent = new Intent(MainActivity.this, WeatherForecastDetailActivity.class);
                startActivity(intent);
            }
        });

        // 東京ボタンのインスタンスを作成する。
        Button tokyoButton = (Button)findViewById(R.id.tokyoButton);
        // 東京ボタンにOnClickイベントを設定する。
        tokyoButton.setOnClickListener(new View.OnClickListener() {
            // 東京ボタンが押されたときの処理を書く。
            @Override
            public void onClick(View v) {
                // 天気予報詳細画面に遷移する。
                Intent intent = new Intent(MainActivity.this, WeatherForecastDetailActivity.class);
                startActivity(intent);
            }
        });

    }

    /***********************************************************
     * ここから下はメニュー項目関連(今回の講義では使用しない)
     **********************************************************/
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
