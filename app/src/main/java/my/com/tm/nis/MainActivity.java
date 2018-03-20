package my.com.tm.nis;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;


public class MainActivity extends AppCompatActivity {

    Calendar calander;

    private ListView listView;
    EditText editext;
    String Date;

    View myView;
    private String JSON_STRING;
    private TextView cabinet,remark,date,aging,time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                TextView ttime = (TextView) findViewById(R.id.time);
                                long date = System.currentTimeMillis();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyy-M-dd \n hh:mm:ss");
                                String dateString = sdf.format(date);
                                ttime.setText(dateString);

                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        t.start();

        listView = (ListView) findViewById(R.id.list);

        aging = (TextView) findViewById(R.id.empat);
        cabinet = (TextView) findViewById(R.id.satu);
        remark = (TextView) findViewById(R.id.dua);
        date = (TextView) findViewById(R.id.tiga);
        time = (TextView) findViewById(R.id.time);

        getJSON();

    }

    private void showEmployee(){
        JSONObject jsonObject = null;
        ArrayList<HashMap<String,String>> list = new ArrayList  <HashMap<String, String>>();
        try {
            jsonObject = new JSONObject(JSON_STRING);
            JSONArray result = jsonObject.getJSONArray(Config.TAG_JSON_NIS);

            for(int i = 0; i<result.length(); i++){
                JSONObject jo = result.getJSONObject(i);
                String a = jo.getString(Config.TAG_NEW);
                String b = jo.getString(Config.TAG_REMARK);
                String c = jo.getString("datenotifynis");

                HashMap<String,String> employees = new HashMap<>();
                employees.put(Config.TAG_NEW,a);
                employees.put(Config.TAG_REMARK,b);
                employees.put("datenotifynis",c);

//                int x=Integer.parseInt(time.getText().toString());
//                int y=Integer.parseInt(date.getText().toString());
//                int z=x-y;
//                String zstr = String.valueOf(z);
//                aging.setText(zstr);

                list.add(employees);

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListAdapter adapter = new SimpleAdapter(
                getApplicationContext(), list, R.layout.listnis,
                new String[]{Config.TAG_NEW,Config.TAG_REMARK,"datenotifynis"
                },

                new int[]{R.id.satu, R.id.dua,R.id.tiga
                });

        listView.setAdapter(adapter);

    }


    private void getJSON(){
        class GetJSON extends AsyncTask<Void,Void,String> {

            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
//                loading = ProgressDialog.show(getApplicationContext(),"Loading Data","Wait...",false,false);
                //    loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
//                loading.dismiss();
                //   loading.setIndeterminateDrawable(getResources().getDrawable(R.drawable.dashb));
                JSON_STRING = s;
                //  showData();
                showEmployee();
            }

            @Override
            protected String doInBackground(Void... params) {
                RequestHandler3 rh = new RequestHandler3();
                String s = rh.sendGetRequest(Config.URL_GET_NIS);
                return s;
            }
        }
        GetJSON gj = new GetJSON();
        gj.execute();
    }
}

