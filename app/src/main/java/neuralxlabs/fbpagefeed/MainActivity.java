package neuralxlabs.fbpagefeed;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.AccessToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import nl.psdcompany.duonavigationdrawer.views.DuoDrawerLayout;
import nl.psdcompany.duonavigationdrawer.widgets.DuoDrawerToggle;

public class MainActivity extends AppCompatActivity {
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       /* DuoDrawerLayout drawerLayout = (DuoDrawerLayout) findViewById(R.id.drawer);
        Toolbar toolbar = findViewById(R.id.toolbar);
        DuoDrawerToggle drawerToggle = new DuoDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);

        drawerLayout.setDrawerListener(drawerToggle);
        drawerToggle.syncState();
*/
        listView = (ListView) findViewById(R.id.list);
        final ArrayList<String> msgList = new ArrayList<>();

        FacebookSdk.sdkInitialize(getApplicationContext());
        AccessToken accessToken = new AccessToken(getString(R.string.access_token), getString(R.string.app_id), getString(R.string.user_id), null, null, null, null, null);

        GraphRequest request = GraphRequest.newGraphPathRequest(
                accessToken,
                "/sandeepdwaba",
                new GraphRequest.Callback() {
                    @Override
                    public void onCompleted(GraphResponse response) {
                        // Insert your code here
                        try {
                            //Log.v("Output", response.getJSONObject().getJSONObject("posts").getJSONArray("data").getString(0));
                            //String msg = response.getJSONObject().getJSONObject("posts").getJSONArray("data").get(0).toString();

                            JSONObject posts = response.getJSONObject().getJSONObject("posts");
                            JSONArray data = posts.getJSONArray("data");
                            for(int i=0; i < data.length(); i++) {
                                JSONObject message = data.getJSONObject(i);
                                String msg = message.get("message").toString();
                                msgList.add(msg);
                            }
                            final ArrayAdapter<String> adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, android.R.id.text1, msgList);
                            listView.setAdapter(adapter);
                                //textView.setText(msg);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                });

        Bundle parameters = new Bundle();
        parameters.putString("fields", "posts{message,picture}");
        request.setParameters(parameters);
        request.executeAsync();




    }
}
