package net.scientificdev.opencvheartbeat;

import java.io.IOException;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.widget.ShareActionProvider;
import androidx.core.view.MenuItemCompat;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import org.opencv.android.OpenCVLoader;
import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

public class MainActivity extends AppCompatActivity {

    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        OpenCVLoader.initDebug();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Przygotowanie menu i dodanie elementów do paska aplikacji
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        shareActionProvider =
                (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
        setShareActionIntent("Share something with me ...");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.opcja1:
                Intent intent = new Intent(this, OrderActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }



        public void displayCanny(View v) {
        Mat img = null;

        try {
            img = Utils.loadResource(getApplicationContext(), R.drawable.nature1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Imgproc.cvtColor(img, img, Imgproc.COLOR_RGB2BGRA);

        Mat img_result = img.clone();
        Imgproc.Canny(img, img_result, 80, 90);
        Bitmap img_bitmap = Bitmap.createBitmap(img_result.cols(), img_result.rows(),Bitmap.Config.ARGB_8888);
        Utils.matToBitmap(img_result, img_bitmap);
        ImageView imageView = findViewById(R.id.img);
        imageView.setImageBitmap(img_bitmap);
    }

    private void setShareActionIntent(String text) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("text/plain");
        intent.putExtra(Intent.EXTRA_TEXT, text);
        shareActionProvider.setShareIntent(intent);
    }


}
