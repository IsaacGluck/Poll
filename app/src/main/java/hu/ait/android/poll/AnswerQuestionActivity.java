package hu.ait.android.poll;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

public class AnswerQuestionActivity extends AppCompatActivity {
    public TextView tvAuthor;
    public TextView tvQuestion;
    public RadioGroup radioAnswers;
    public RadioButton radioAnswer1;
    public RadioButton radioAnswer2;
    public RadioButton radioAnswer3;
    public RadioButton radioAnswer4;

    public LinearLayout layout;
    public String answer1;
    public String answer2;
    public String answer3;
    public String answer4;

    public Button btnSubmit;
    public Button btnDelete;

    private static final int RB1_ID = 0;
    private static final int RB2_ID = 1;
    private static final int RB3_ID = 2;
    private static final int RB4_ID = 3;

    private static final String ANSWER_INDEX = "AnswerIndex";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_answer_question);

        tvAuthor = findViewById(R.id.tvAuthor);
        tvQuestion = findViewById(R.id.tvQuestion);
        radioAnswers = findViewById(R.id.radioAnswers);
        radioAnswer1 = findViewById(R.id.radioAnswer1);
        radioAnswer2 = findViewById(R.id.radioAnswer2);
        radioAnswer3 = findViewById(R.id.radioAnswer3);
        radioAnswer4 = findViewById(R.id.radioAnswer4);
        btnSubmit = findViewById(R.id.btnSubmitAnswer);
        layout = findViewById(R.id.card_view);

        radioAnswer1.setText(getIntent().getStringExtra(PollActivity.ANSWER_INDEX_0));
        radioAnswer1.setId(RB1_ID);
        radioAnswer2.setText(getIntent().getStringExtra(PollActivity.ANSWER_INDEX_1));
        radioAnswer2.setId(RB2_ID);
        radioAnswer3.setText(getIntent().getStringExtra(PollActivity.ANSWER_INDEX_2));
        radioAnswer3.setId(RB3_ID);
        radioAnswer4.setText(getIntent().getStringExtra(PollActivity.ANSWER_INDEX_3));
        radioAnswer4.setId(RB4_ID);
        tvAuthor.setText(getIntent().getStringExtra(PollActivity.AUTHOR));
        tvQuestion.setText(getIntent().getStringExtra(PollActivity.QUESTION));

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedRadioIndex = radioAnswers.getCheckedRadioButtonId();

                if (checkedRadioIndex == -1) {
                    Snackbar.make(layout,
                            R.string.PleaseSelectAnAnswer,
                            Snackbar.LENGTH_LONG
                    ).setAction(R.string.Hide, new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            //...
                        }
                    }).show();
                    return;
                }

                String answerKey = ANSWER_INDEX + checkedRadioIndex;

                Intent intentResult = new Intent();
                intentResult.putExtra(PollActivity.HOLDER_POSITION, getIntent().getIntExtra(PollActivity.HOLDER_POSITION, -1));
                intentResult.putExtra(PollActivity.ANSWER_INDEX, answerKey);
                setResult(RESULT_OK, intentResult);
                finish();
            }
        });

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }
}
