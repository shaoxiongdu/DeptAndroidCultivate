package cn.shaoxiongdu.photo_0815;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }


    public void svgXml(View view) {
        Intent intent = new Intent(this, SvgXmlActivity.class);
        startActivity(intent);
    }

    public void bitmap(View view) {
        Intent intent = new Intent(this, BitMapDemo.class);
        startActivity(intent);
    }

    public void glide(View view) {
        Intent intent = new Intent(this, GlideFramework.class);
        startActivity(intent);
    }
}