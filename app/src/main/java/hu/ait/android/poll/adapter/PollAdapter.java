package hu.ait.android.poll.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
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
import java.util.List;

import hu.ait.android.poll.AnswerQuestionActivity;
import hu.ait.android.poll.PollActivity;
import hu.ait.android.poll.R;
import hu.ait.android.poll.data.Question;


public class PollAdapter extends RecyclerView.Adapter<PollAdapter.ViewHolder> {

    private static final int RB1_ID = 0;
    private static final int RB2_ID = 1;
    private static final int RB3_ID = 2;
    private static final int RB4_ID = 3;
    public static final int MY_REQUEST_CODE = 1234;

    private Context context;
    private List<Question> pollList;
    private List<String> pollKeys;
    private String uId;
    public FirebaseUser user;
    private int lastPosition = -1;
    public DatabaseReference pollRef;

    public PollAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;

        user = FirebaseAuth.getInstance().getCurrentUser();

        pollList = new ArrayList<Question>();
        pollKeys = new ArrayList<String>();

        pollRef = FirebaseDatabase.getInstance().getReference();
    }

    public List<Question> getPollList() {
        return this.pollList;
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
        holder.answer1 = question.getAnswers().get(PollActivity.ANSWER_INDEX_0).getAnswerText();
        holder.answer2 = question.getAnswers().get(PollActivity.ANSWER_INDEX_1).getAnswerText();
        holder.answer3 = question.getAnswers().get(PollActivity.ANSWER_INDEX_2).getAnswerText();
        holder.answer4 = question.getAnswers().get(PollActivity.ANSWER_INDEX_3).getAnswerText();
        holder.author = question.getAuthor();
        holder.question = question.getQuestion();
        holder.intent = null;
        holder.position = position;

        final DatabaseReference database = this.pollRef;
        final FirebaseUser userKey = this.user;

        holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, AnswerQuestionActivity.class);
                intent.putExtra(PollActivity.ANSWER_INDEX_0, holder.answer1);
                intent.putExtra(PollActivity.ANSWER_INDEX_1, holder.answer2);
                intent.putExtra(PollActivity.ANSWER_INDEX_2, holder.answer3);
                intent.putExtra(PollActivity.ANSWER_INDEX_3, holder.answer4);
                intent.putExtra(PollActivity.AUTHOR, holder.author);
                intent.putExtra(PollActivity.QUESTON, holder.question);
                intent.putExtra(PollActivity.HOLDER_POSITION, holder.position);

                holder.intent = intent;
                ((Activity)context).startActivityForResult(intent, MY_REQUEST_CODE);
            }
        });


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
//        public RadioGroup radioAnswers;
//        public RadioButton radioAnswer1;
//        public RadioButton radioAnswer2;
//        public RadioButton radioAnswer3;
//        public RadioButton radioAnswer4;
        public String answer1;
        public String answer2;
        public String answer3;
        public String answer4;
        public String author;
        public String question;
        Intent intent;
        public int position;
        public Button btnSubmit;
        public Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            btnSubmit = itemView.findViewById(R.id.btnSubmitAnswer);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
