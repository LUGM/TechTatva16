package in.techtatva.techtatva.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import android.app.FragmentManager;
import in.techtatva.techtatva.R;
import in.techtatva.techtatva.adapters.ResultAdapter;
import in.techtatva.techtatva.models.ResultModel;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Toolbar toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Results");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.result_recycler_view);
        ResultAdapter adapter = new ResultAdapter(getSupportFragmentManager(),getResultsList());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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

    public List<ResultModel> getResultsList() {
        int[] text = {R.string.event_01,R.string.event_11, R.string.event_02,R.string.event_12, R.string.event_03,R.string.event_13, R.string.event_04,R.string.event_14, R.string.event_05, R.string.event_06, R.string.event_07, R.string.event_08};

        List<ResultModel> list = new ArrayList<>();
        for (int i = 0; i < text.length; i++) {
            ResultModel result = new ResultModel();
            result.setResultName(text[i]);

            list.add(result);
        }

        return list;
    }
}
