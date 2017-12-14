package hu.ait.android.poll;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
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
        boolean isEmpty = false;
        if(TextUtils.isEmpty(etQuestion.getText().toString())) {
            etQuestion.setError(getString(R.string.FieldCannotBeEmpty));
            isEmpty = true;
        }
        if(TextUtils.isEmpty(etAnswer1.getText().toString())) {
            etAnswer1.setError(getString(R.string.FieldCannotBeEmpty));
            isEmpty = true;
        }
        if(TextUtils.isEmpty(etAnswer2.getText().toString())) {
            etAnswer2.setError(getString(R.string.FieldCannotBeEmpty));
            isEmpty = true;
        }
        if(TextUtils.isEmpty(etAnswer3.getText().toString())) {
            etAnswer3.setError(getString(R.string.FieldCannotBeEmpty));
            isEmpty = true;
        }
        if(TextUtils.isEmpty(etAnswer4.getText().toString())) {
            etAnswer4.setError(getString(R.string.FieldCannotBeEmpty));
            isEmpty = true;
        }
        if (isEmpty) return;


        HashMap<String, Answer> answers = new HashMap<>();
        answers.put(PollActivity.ANSWER_INDEX_0, new Answer(etAnswer1.getText().toString()));
        answers.put(PollActivity.ANSWER_INDEX_1, new Answer(etAnswer2.getText().toString()));
        answers.put(PollActivity.ANSWER_INDEX_2, new Answer(etAnswer3.getText().toString()));
        answers.put(PollActivity.ANSWER_INDEX_3, new Answer(etAnswer4.getText().toString()));

        HashMap<String, String> answeredBy = new HashMap<>();

        String key = FirebaseDatabase.getInstance().getReference().child(PollActivity.POLLS).push().getKey();
        Log.d("AUTHOR", FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        Question newQuestion = new Question(
                FirebaseAuth.getInstance().getCurrentUser().getUid(),
                key,
                FirebaseAuth.getInstance().getCurrentUser().getDisplayName(),
                etQuestion.getText().toString(),
                answers,
                answeredBy);

        FirebaseDatabase.getInstance().getReference().child(PollActivity.POLLS).child(key)
                .setValue(newQuestion).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Toast.makeText(CreatePollActivity.this, R.string.PostCreated, Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }
}
