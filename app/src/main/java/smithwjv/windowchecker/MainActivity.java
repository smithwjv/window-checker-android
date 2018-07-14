package smithwjv.windowchecker;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.URL;

import smithwjv.windowchecker.utilities.NetworkUtils;

public class MainActivity extends AppCompatActivity {

    private Button checkWindowButton;
    private TextView piResponseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkWindowButton = (Button) findViewById(R.id.btn_check_window);
        piResponseTextView = (TextView) findViewById(R.id.tv_pi_response);
    }

    public void checkWindow(View view) {
        checkWindowButton.setEnabled(false);
        new PiQueryTask().execute(NetworkUtils.getUrl(this));
    }

    public class PiQueryTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... urls) {
            URL piUrl = urls[0];
            String piResponse = null;
            try {
                piResponse = NetworkUtils.getResponseFromHttpUrl(piUrl);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return piResponse;
        }

        @Override
        protected void onPostExecute(String s) {
            if (s!= null && !s.equals("")) {
                piResponseTextView.setText(s);
            }
            checkWindowButton.setEnabled(true);
        }
    }
}
