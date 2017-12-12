package hu.ait.android.poll;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import hu.ait.android.poll.data.Answer;
import hu.ait.android.poll.data.Question;

public class CreatePollActivity extends AppCompatActivity {

    @BindView(R.id.etQuestion)
    EditText etQuestion;
    @BindView(R.id.etAnswer1)
    EditText etAnswer1;
    @BindView(R.id.etAnswer2)
    EditText etAnswer2;
    @BindView(R.id.etAnswer3)
    EditText etAnswer3;
    @BindView(R.id.etAnswer4)
    EditText etAnswer4;

    @BindView(R.id.btnAsk)
    Button btnAsk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_poll);

        ButterKnife.bind(this);
    }

    @OnClick(R.id.btnAsk)
    public void askQuestion() {
        String key = FirebaseDatabase.getInstance().getReference().child("polls").push().getKey();
        HashMap<String, Answer> answers = new HashMap<String, Answer>();

        answers.put("AnswerIndex0", new Answer(etAnswer1.getText().toString()));
        answers.put("AnswerIndex1", new Answer(etAnswer2.getText().toString()));
        answers.put("AnswerIndex2", new Answer(etAnswer3.getText().toString()));
        answers.put("AnswerIndex3", new Answer(etAnswer4.getText().toString()));

        HashMap<String, String> answeredBy = new HashMap<String, String>();

        Question newQuestion = new Question(
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                etQuestion.getText().toString(),
                answers,
                answeredBy);


        FirebaseDatabase.getInstance().getReference().child("polls").child(key)
                .setValue(newQuestion).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CreatePollActivity.this, "Post created", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
