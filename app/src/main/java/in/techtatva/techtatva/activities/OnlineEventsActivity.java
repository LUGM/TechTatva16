package in.techtatva.techtatva.activities;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;

import chipset.potato.Potato;
import in.techtatva.techtatva.R;

public class OnlineEventsActivity extends AppCompatActivity {

    private float x1, x2, y1, y2;
    private WebView EventsWebView;
    private Toolbar toolbar;
    private Context context;
    private View noConnectionLayout;
    private String ONLINE_EVENTS_URL="xxx";
    private FirebaseRemoteConfig firebaseRemoteConfig;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_events);

        context = this;
        EventsWebView = (WebView) findViewById(R.id.online_events);
        noConnectionLayout = findViewById(R.id.no_connection_layout);
        WebSettings mWebSettings = EventsWebView.getSettings();
        mWebSettings.setJavaScriptEnabled(true);
        EventsWebView.setWebChromeClient(new WebChromeClient());
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null){
            getSupportActionBar().setTitle(R.string.title_activity_online_events);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        firebaseRemoteConfig=FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .build();
        firebaseRemoteConfig.setConfigSettings(configSettings);

        noConnectionLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                ViewConfiguration vc = ViewConfiguration.get(context);
                int mSlop = vc.getScaledTouchSlop();
                final int MAX_HORIZONTAL_SWIPE = 150;

                switch (event.getAction()) {

                    case MotionEvent.ACTION_DOWN: {
                        y1 = event.getY();
                        x1 = event.getX();
                        break;
                    }

                    case MotionEvent.ACTION_UP: {
                        y2 = event.getY();
                        x2 = event.getX();

                        float deltaY = y2 - y1;
                        float deltaX = x2 - x1;

                        if (Math.abs(deltaY) > mSlop && deltaY > 0 && Math.abs(deltaX) < MAX_HORIZONTAL_SWIPE)
                            if (Potato.potate(context).Utils().isInternetConnected()) {
                                noConnectionLayout.setVisibility(View.GONE);
                                toolbar.setVisibility(View.VISIBLE);
                                loadWebView();
                            }
                            else{
                                Toast.makeText(OnlineEventsActivity.this, "Check internet connection!", Toast.LENGTH_SHORT).show();
                            }
                        break;
                    }
                }

                return true;
            }
        });

        if (Potato.potate(this).Utils().isInternetConnected()) {
            loadWebView();
        } else {
            noConnectionLayout.setVisibility(View.VISIBLE);
            toolbar.setVisibility(View.GONE);
        }
    }

    public void loadWebView(){
        firebaseRemoteConfig.fetch()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("URL", "fetched");
                            firebaseRemoteConfig.activateFetched();
                            ONLINE_EVENTS_URL = "http://" + firebaseRemoteConfig.getString("onlineevents") + "/";
                                WebViewClient mWebViewClient = new WebViewClient();
                                EventsWebView.setWebViewClient(mWebViewClient);
                                EventsWebView.loadUrl(ONLINE_EVENTS_URL);
                        } else {
                            noConnectionLayout.setVisibility(View.VISIBLE);
                            toolbar.setVisibility(View.GONE);
                        }
                    }
                });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case android.R.id.home:{
                finish();
                overridePendingTransition(R.anim.hold, R.anim.animation_fade_out);
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.hold, R.anim.animation_fade_out);
    }

}