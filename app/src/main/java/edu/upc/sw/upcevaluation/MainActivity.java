package edu.upc.sw.upcevaluation;

import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.loopj.android.http.*;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.entity.mime.Header;
import edu.upc.sw.upcevaluation.dummy.DummyContent;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentManager frm = getFragmentManager();
        frm.beginTransaction().replace(R.id.container, new ItemFragment()).addToBackStack(null).commit();
        AsyncHttpClient client = new AsyncHttpClient();
        //RequestParams rp = new RequestParams();
        //rp.put("params1", value1);
        //rp.put("params2", value2);

        DummyContent.ITEMS = new ArrayList<DummyContent.DummyItem>();
        client.get("http://192.168.43.128:81/generaJSON.php", null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                //super.onSuccess(statusCode, headers, response);

                Log.i("JSONREQUEST", response + "");

                try {

                    JSONArray pro = response.getJSONArray("Profesor");

                    //Log.i("ITEMS", rows.length() + "");
                    if (pro != null && pro.length() > 0) {
                        JSONObject item;
                        DummyContent.DummyItem obj = null;
                        SQLiteDatabase db = DBHandler.getDB(getApplicationContext());

                        for (int i = 0; i < pro.length(); i++) {
                            item = pro.getJSONObject(i);
                            Log.i("ITEMS", item.getString("idProfesor"));
                            //Log.i("ITEMs", item.getString("idCurso"));
                            String query = "insert into Profesor values(" + item.getString("idProfesor") + ",'" + item.getString("nombre") + "');";

                            JSONArray cur = new JSONArray(pro.getJSONObject(i).getString("Cursos"));
                            obj = new DummyContent.DummyItem(item.getString("idProfesor"), item.getString("nombre"), null);
                            DummyContent.addItem(obj);
                            DummyContent.ITEMS.add(obj);
                            db.execSQL(query);

                            String prof = item.getString("idProfesor");

                            for(int j = 0; j < cur.length(); j++){
                                item = cur.getJSONObject(j);
                                String query2 = "insert into Cursos values(" + item.getString("idCurso") + "," + prof + ","+ item.getString("grado") + ",'" +
                                        item.getString("descripcion") + "','" + item.getString("hrIni") + "','" + item.getString("hrFin") +"');";

                                JSONArray alum = new JSONArray(cur.getJSONObject(j).getString("Alumnos"));

                                obj = new DummyContent.DummyItem(item.getString("idProfesor"), item.getString("nombre"), null);
                                DummyContent.addItem(obj);
                                DummyContent.ITEMS.add(obj);
                                db.execSQL(query2);

                                for(int k = 0; k < alum.length(); k++){
                                    item = alum.getJSONObject(k);
                                    String query3 = "insert into Alumnos values(" + item.getString("matricula") + ",'" + item.getString("nombre") + "');";
                                    obj = new DummyContent.DummyItem(item.getString("idProfesor"), item.getString("nombre"), null);
                                    DummyContent.addItem(obj);
                                    DummyContent.ITEMS.add(obj);
                                    db.execSQL(query3);
                                }
                            }



                            Log.i("Query: ", query);

                        }

                        String query2 = "select * from Profesor";
                        Cursor curs = db.rawQuery(query2, null);
                        curs.moveToFirst();
                        while (curs.moveToNext()) {
                            Log.i("item: ", curs.getInt(1) + "");
                        }
                        curs.close();
                        db.close();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
