package hu.ait.android.poll;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.ButterKnife;
import hu.ait.android.poll.adapter.PollAdapter;
import hu.ait.android.poll.data.Answer;
import hu.ait.android.poll.data.Question;

public class PollActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private PollAdapter adapter;
    private DrawerLayout drawer;

    public static final String ANSWER_INDEX_0 = "AnswerIndex0";
    public static final String ANSWER_INDEX_1 = "AnswerIndex1";
    public static final String ANSWER_INDEX_2 = "AnswerIndex2";
    public static final String ANSWER_INDEX_3 = "AnswerIndex3";
    public static final String AUTHOR = "AUTHOR";
    public static final String QUESTON = "QUESTION";
    public static final String ANSWER_INDEX = "ANSWER_INDEX";
    public static final String HOLDER_POSITION = "HOLDER_POSITION";
    public static final String ANSWER1 = "ANSWER1";
    public static final String ANSWER2 = "ANSWER2";
    public static final String ANSWER3 = "ANSWER3";
    public static final String ANSWER4 = "ANSWER4";
    public static final String ANSWER1_COUNT = "ANSWER1_COUNT";
    public static final String ANSWER2_COUNT = "ANSWER2_COUNT";
    public static final String ANSWER3_COUNT = "ANSWER3_COUNT";
    public static final String ANSWER4_COUNT = "ANSWER4_COUNT";
    public static final String POLLS = "polls";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_poll);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PollActivity.this, CreatePollActivity.class));
            }
        });

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        TextView tvUserEmail = navigationView.getHeaderView(0).findViewById(R.id.tvUserEmail);

        tvUserEmail.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        ButterKnife.bind(this);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPosts);
        adapter = new PollAdapter(this, FirebaseAuth.getInstance().getCurrentUser().getUid());

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        initPollListener();
    }


    private void initPollListener() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("polls");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Question question = dataSnapshot.getValue(Question.class);
                adapter.addPost(question, dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
//                adapter.removePostByKey(dataSnapshot.getKey());
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

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
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.nav_logout:
                FirebaseAuth.getInstance().signOut();
                finish();
            case R.id.nav_about:
                showSnackBarMessage(getString(R.string.txt_about));
        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_CANCELED) {
            return;
        }
        if (requestCode == PollAdapter.MY_REQUEST_CODE) {
            if (!data.hasExtra(PollActivity.ANSWER_INDEX)) return;
            int questionPos = data.getIntExtra(PollActivity.HOLDER_POSITION, -1);
            if (questionPos != -1) {
                Question q = adapter.getPollList().get(questionPos);
                String answerKey = data.getStringExtra(PollActivity.ANSWER_INDEX);
                final HashMap<String, Answer> answers = q.getAnswers();
                answers.get(answerKey).setNumAnswers(answers.get(answerKey).getNumAnswers() + 1);

                if (q.getAnsweredBy() == null) {
                    HashMap<String, String> answeredBy = new HashMap<>();
                    q.setAnsweredBy(answeredBy);
                }
                q.getAnsweredBy().put(adapter.user.getUid(), "");

                adapter.pollRef.child("polls").child(q.getKey()).setValue(q);
                adapter.notifyItemChanged(questionPos);

            }
        }
    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(drawer,
                message,
                Snackbar.LENGTH_LONG
        ).setAction(R.string.Hide, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //...
            }
        }).show();
    }
}
