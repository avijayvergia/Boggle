package com.example.aksha.tester;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.robinhood.ticker.TickerUtils;
import com.robinhood.ticker.TickerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    FastDictionary dictionary;
    private ArrayList<String> wordsTyped = new ArrayList<>();
    char[] a = new char[16];
    int score = 0, check = 0;
    char[][] b = new char[4][4];
    Random ran = new Random();
    TickerView myScore;
    EditText word;
    TextView n, n1, n2, n3, n4, n5, n6, n7, n8, n9, n10, n11, n12, n13, n14, n15;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        wasteOfTime();
        resetGame();
    }


    public void boggle(View view) {
        String p = word.getText().toString().toUpperCase();
        if (wordsTyped.contains(p)) {
            Toast.makeText(this, "Already Given!!", Toast.LENGTH_SHORT).show();
            return;
        }
        wordsTyped.add(p);
        if (p.length() < 3) {
            Toast.makeText(this, "Min word size 3!!", Toast.LENGTH_SHORT).show();
            return;
        }
        check = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (check != 0) {
                    word.setText(null);
                    return;
                } else
                    dfs(i, j, 0, p);

            }
        }
        if (check == 0)
            Toast.makeText(this, "Not Possible!!", Toast.LENGTH_SHORT).show();
        word.setText(null);
    }


    private void dfs(int i, int j, int k, String p) {
        if (!inBoard(i, j)) return;
        if (visited[i][j]) return;
        visited[i][j] = true;
        if (k == (p.length())) {
            check = 1;
            checkWord(p.toLowerCase());
            visited[i][j] = false;
            return;
        }

        if (b[i][j] == p.charAt(k) && k < p.length()) {
            k++;
            for (int d = 0; d < 8; d++) {
                if (check == 0)
                    dfs(i + dx[d], j + dy[d], k, p);
            }
        }
        visited[i][j] = false;
    }

    private void checkWord(String p) {
        if (dictionary.isWord(p)) {
            if (p.length() == 3)
                score = score + 1;
            else if (p.length() == 4)
                score = score + 1;
            else if (p.length() == 5)
                score = score + 2;
            else if (p.length() == 6)
                score = score + 3;
            else if (p.length() == 7)
                score = score + 4;
            else
                score = score + 5;

            myScore.setText(String.valueOf(score));
            Toast.makeText(this, "Valid word", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Not a valid word", Toast.LENGTH_SHORT).show();
        }
    }

    static boolean inBoard(int i, int j) {
        return i >= 0 && i < 4 && j >= 0 && j < 4;
    }

    static boolean[][] visited = new boolean[4][4];
    static int[] dx = new int[]{-1, -1, -1, 0, 0, 1, 1, 1};
    static int[] dy = new int[]{-1, 0, 1, -1, 1, -1, 0, 1};

    private void resetGame() {
        Toast.makeText(this, "New game Start!!", Toast.LENGTH_SHORT).show();
        for (int i = 0; i < 16; i++) {
            int k = 65 + ran.nextInt(26);
            a[i] = (char) k;

        }
        n.setText(String.valueOf(a[0]));
        n1.setText(String.valueOf(a[1]));
        n2.setText(String.valueOf(a[2]));
        n3.setText(String.valueOf(a[3]));
        n4.setText(String.valueOf(a[4]));
        n5.setText(String.valueOf(a[5]));
        n6.setText(String.valueOf(a[6]));
        n7.setText(String.valueOf(a[7]));
        n8.setText(String.valueOf(a[8]));
        n9.setText(String.valueOf(a[9]));
        n10.setText(String.valueOf(a[10]));
        n11.setText(String.valueOf(a[11]));
        n12.setText(String.valueOf(a[12]));
        n13.setText(String.valueOf(a[13]));
        n14.setText(String.valueOf(a[14]));
        n15.setText(String.valueOf(a[15]));
        word.setText(null);
        wordsTyped.clear();
        score = 0;
        word.setEnabled(true);

        b[0][0] = a[0];
        b[0][1] = a[1];
        b[0][2] = a[2];
        b[0][3] = a[3];
        b[1][0] = a[4];
        b[1][1] = a[5];
        b[1][2] = a[6];
        b[1][3] = a[7];
        b[2][0] = a[8];
        b[2][1] = a[9];
        b[2][2] = a[10];
        b[2][3] = a[11];
        b[3][0] = a[12];
        b[3][1] = a[13];
        b[3][2] = a[14];
        b[3][3] = a[15];

    }


    private void wasteOfTime() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        AssetManager assetManager = getAssets();
        try {
            InputStream inputStream = assetManager.open("words.txt");
            dictionary = new FastDictionary(inputStream);
        } catch (IOException e) {
            Toast.makeText(this, "Could not load dictionary", Toast.LENGTH_LONG).show();
        }
        initialize();
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

    public void reset(View view) {
        resetGame();
    }

    private void initialize() {
        n = (TextView) findViewById(R.id.textView2);
        n1 = (TextView) findViewById(R.id.textView3);
        n2 = (TextView) findViewById(R.id.textView4);
        n3 = (TextView) findViewById(R.id.textView5);
        n4 = (TextView) findViewById(R.id.textView6);
        n5 = (TextView) findViewById(R.id.textView7);
        n6 = (TextView) findViewById(R.id.textView8);
        n7 = (TextView) findViewById(R.id.textView9);
        n8 = (TextView) findViewById(R.id.textView10);
        n9 = (TextView) findViewById(R.id.textView11);
        n10 = (TextView) findViewById(R.id.textView12);
        n11 = (TextView) findViewById(R.id.textView13);
        n12 = (TextView) findViewById(R.id.textView14);
        n13 = (TextView) findViewById(R.id.textView15);
        n14 = (TextView) findViewById(R.id.textView16);
        n15 = (TextView) findViewById(R.id.textView17);
        myScore = (TickerView) findViewById(R.id.myScore);
        word = (EditText) findViewById(R.id.word);
        myScore.setCharacterList(TickerUtils.getDefaultNumberList());
    }


}
