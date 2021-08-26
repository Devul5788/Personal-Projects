package projects.nahar.devul.timeline.utils;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import projects.nahar.devul.timeline.R;
import projects.nahar.devul.timeline.model.Orientation;

public class MainActivity extends AppCompatActivity {

    public final static String EXTRA_ORIENTATION = "EXTRA_ORIENTATION";
    public final static String EXTRA_WITH_LINE_PADDING = "EXTRA_WITH_LINE_PADDING";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = new Intent(this, TimeLineActivity.class);
        /*intent.putExtra(EXTRA_ORIENTATION, Orientation.VERTICAL);
        intent.putExtra(EXTRA_WITH_LINE_PADDING, false);*/
        startActivity(intent);
    }
}
