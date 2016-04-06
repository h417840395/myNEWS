package com.example.blade.myweatherbeta2.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.blade.myweatherbeta2.DB.MyWeatherIOHelper;
import com.example.blade.myweatherbeta2.R;
import com.example.blade.myweatherbeta2.SearchActivity;
import com.example.blade.myweatherbeta2.Util.CityCodeHelper;
import com.example.blade.myweatherbeta2.Util.HttpCallBackListener;
import com.example.blade.myweatherbeta2.Util.HttpHelper;

public class MainActivity extends Activity {
    public static final int REQUESET_CODE1 = 1;
    private String cityNum;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tiem, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_city:
                Intent intent = new Intent(MainActivity.this, SearchActivity.class);
                startActivityForResult(intent, REQUESET_CODE1);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUESET_CODE1:
                if (resultCode == RESULT_OK) {
                    cityNum = data.getStringExtra("cityNum");
//                    Log.d("11111111",cityNum);

                }
                break;
            default:
        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);
        CityCodeHelper.putCityCodeIn(this);
        HttpHelper.sendRequest(cityNum, new HttpCallBackListener() {
            @Override
            public void onFinish(String response) {

                MyWeatherIOHelper helper = MyWeatherIOHelper.getMyWeatherIOHelper(MainActivity.this);


            }

            @Override
            public void onError(Exception e) {

            }
        });


    }

}
