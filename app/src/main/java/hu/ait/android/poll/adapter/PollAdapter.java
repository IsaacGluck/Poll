package hu.ait.android.poll.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hu.ait.android.poll.R;
import hu.ait.android.poll.data.Answer;
import hu.ait.android.poll.data.Question;


public class PollAdapter extends RecyclerView.Adapter<PollAdapter.ViewHolder> {

    private static final int RB1_ID = 0;
    private static final int RB2_ID = 1;
    private static final int RB3_ID = 2;
    private static final int RB4_ID = 3;

    private Context context;
    private List<Question> pollList;
    private List<String> pollKeys;
    private String uId;
    private FirebaseUser user;
    private int lastPosition = -1;
    private DatabaseReference pollRef;

    public PollAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;

        user = FirebaseAuth.getInstance().getCurrentUser();

        pollList = new ArrayList<Question>();
        pollKeys = new ArrayList<String>();

        pollRef = FirebaseDatabase.getInstance().getReference();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.row_poll, parent, false);
        return new ViewHolder(row);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Question question = pollList.get(position);
        holder.tvAuthor.setText(question.getAuthor());
        holder.tvQuestion.setText(question.getQuestion());

        final HashMap<String, Answer> answers = question.getAnswers();
        holder.radioAnswer1.setText(answers.get("AnswerIndex0").getAnswerText());
        holder.radioAnswer1.setId(RB1_ID);
        holder.radioAnswer2.setText(answers.get("AnswerIndex1").getAnswerText());
        holder.radioAnswer2.setId(RB2_ID);
        holder.radioAnswer3.setText(answers.get("AnswerIndex2").getAnswerText());
        holder.radioAnswer3.setId(RB3_ID);
        holder.radioAnswer4.setText(answers.get("AnswerIndex3").getAnswerText());
        holder.radioAnswer4.setId(RB4_ID);

        final DatabaseReference database = this.pollRef;

        holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int checkedRadioIndex = holder.radioAnswers.getCheckedRadioButtonId();

                if (checkedRadioIndex == -1) {
                    return; // @DERS make a toast to say you have to choose an answer
                }

                String answerKey = "AnswerIndex" + checkedRadioIndex;
                answers.get(answerKey).setNumAnswers(answers.get(answerKey).getNumAnswers() + 1);
                database.child("polls").child(question.getKey()).setValue(question);

//                question.getAnsweredBy().put(userID, "");
                submitAnswer();
            }
        });

//        holder.btnDelete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                removeQuestion(holder.getAdapterPosition(), pollKeys.get(holder.getAdapterPosition()));
//            }
//        });
    }

    public void addPost(Question poll, String key) {
        pollList.add(poll);
        pollKeys.add(key);
        notifyDataSetChanged();
    }

    public void submitAnswer() {

    }

    @Override
    public int getItemCount() { return pollList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAuthor;
        public TextView tvQuestion;
        public RadioGroup radioAnswers;
        public RadioButton radioAnswer1;
        public RadioButton radioAnswer2;
        public RadioButton radioAnswer3;
        public RadioButton radioAnswer4;
        public Button btnSubmit;
        public Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            radioAnswers = itemView.findViewById(R.id.radioAnswers);
            radioAnswer1 = itemView.findViewById(R.id.radioAnswer1);
            radioAnswer2 = itemView.findViewById(R.id.radioAnswer2);
            radioAnswer3 = itemView.findViewById(R.id.radioAnswer3);
            radioAnswer4 = itemView.findViewById(R.id.radioAnswer4);
            btnSubmit = itemView.findViewById(R.id.btnSubmitAnswer);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
