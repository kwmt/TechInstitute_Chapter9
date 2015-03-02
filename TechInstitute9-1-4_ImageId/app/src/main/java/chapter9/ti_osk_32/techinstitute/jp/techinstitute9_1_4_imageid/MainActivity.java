package chapter9.ti_osk_32.techinstitute.jp.techinstitute9_1_4_imageid;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // activity_mainからidがimageViewのインスタンスを取得
        ImageView iv = (ImageView)findViewById(R.id.imageView);

        // ImageViewのインスタンス(実体)ivに、リソースにあるic_launcher画像をセットする
        // ImageViewクラスにsetImageResource(int resId)という画像をセットするためのメソッドが用意されているので、それを使う
        iv.setImageResource(R.drawable.ic_launcher);
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
