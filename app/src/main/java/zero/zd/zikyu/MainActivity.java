package zero.zd.zikyu;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                startActivity(AboutActivity.getStartIntent(MainActivity.this));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onClickAnswer(View view) {
        startActivity(QnaLoadActivity.getStartIntent(MainActivity.this));
    }

    public void onClickBuilder(View view) {
        Toast.makeText(this, "QnA Builder Feature is on Progress, expect it on first release", Toast.LENGTH_SHORT).show();
//        startActivity(QnaBuilderActivity.getStartIntent(MainActivity.this));
    }

}
