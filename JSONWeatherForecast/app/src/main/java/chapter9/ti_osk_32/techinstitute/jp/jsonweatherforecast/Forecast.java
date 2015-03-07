package chapter9.ti_osk_32.techinstitute.jp.jsonweatherforecast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 天気予報情報クラス
 */
public class Forecast {

    /** 最高気温 */
    private String tempMax;
    /** 最低気温 */
    private String tempMin;
    /** 天気予報画像url */
    private String weatherIconUrl;

    /**
     * コンストラクタ
     * @param jsonString JSON形式の文字列
     */
    public Forecast(String jsonString) throws JSONException {
        parse(jsonString);
    }

    /**
     * JSONをパース（解析）する。
     * @throws org.json.JSONException
     */
    private void parse(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        // 配列を取り出す。
        JSONArray list = jsonObject.getJSONArray("list");
        // 明日の天気予報情報がはいったオブジェクトを取り出す。
        JSONObject tomorrow = list.getJSONObject(0);
        // 最高気温を取り出す。
        Double max = tomorrow.getJSONObject("temp").getDouble("max");
        // 最低気温を取り出す。
        Double min = tomorrow.getJSONObject("temp").getDouble("min");
        // 予報画像(の一部)を取り出す。
        String weatherIcon = tomorrow.getJSONArray("weather").getJSONObject(0).getString("icon");

        // 最高気温をメンバ変数にセットする。
        tempMax = String.valueOf(max) + "℃";
        // 最低気温をメンバ変数にセットする。
        tempMin = String.valueOf(min) + "℃";
        // 予報画像のURLをメンバ変数にセットする。
        weatherIconUrl = "http://openweathermap.org/img/w/" + weatherIcon + ".png";
    }

    /**
     * 最高気温を取得する。
     * @return
     */
    public String getTempMax() {
        return tempMax;
    }

    /**
     * 最低気温を取得する。
     * @return
     */
    public String getTempMin() {
        return tempMin;
    }

    /**
     * 天気予報画像URLを取得する。
     * @return
     */
    public String getWeatherIconUrl() {
        return weatherIconUrl;
    }
}
