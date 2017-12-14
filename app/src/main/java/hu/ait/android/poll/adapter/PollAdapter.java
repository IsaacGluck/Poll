package hu.ait.android.poll.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import hu.ait.android.poll.AnswerQuestionActivity;
import hu.ait.android.poll.PollActivity;
import hu.ait.android.poll.QuestionResultsActivity;
import hu.ait.android.poll.R;
import hu.ait.android.poll.data.Answer;
import hu.ait.android.poll.data.Question;


public class PollAdapter extends RecyclerView.Adapter<PollAdapter.ViewHolder> {
    public static final int MY_REQUEST_CODE = 1234;

    private Context context;
    private List<Question> pollList;
    private List<String> pollKeys;
    private String uId;
    public FirebaseUser user;
    public DatabaseReference pollRef;

    public PollAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;

        pollList = new ArrayList<>();
        pollKeys = new ArrayList<>();

        user = FirebaseAuth.getInstance().getCurrentUser();
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
    public void onBindViewHolder(final ViewHolder holder, final int position) {
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

        boolean hasAnswered = false;
        if (this.pollList.get(position).getAnsweredBy() != null) {
            if (this.pollList.get(position).getAnsweredBy().containsKey(this.uId)) {
                hasAnswered = true;
            }
        }

        if (hasAnswered) {
            holder.btnSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, QuestionResultsActivity.class);
                    final HashMap<String, Answer> answers = pollList.get(position).getAnswers();
                    intent.putExtra(PollActivity.ANSWER1, answers.get(PollActivity.ANSWER_INDEX_0).getAnswerText());
                    intent.putExtra(PollActivity.ANSWER1_COUNT, answers.get(PollActivity.ANSWER_INDEX_0).getNumAnswers());

                    intent.putExtra(PollActivity.ANSWER2, answers.get(PollActivity.ANSWER_INDEX_1).getAnswerText());
                    intent.putExtra(PollActivity.ANSWER2_COUNT, answers.get(PollActivity.ANSWER_INDEX_1).getNumAnswers());

                    intent.putExtra(PollActivity.ANSWER3, answers.get(PollActivity.ANSWER_INDEX_2).getAnswerText());
                    intent.putExtra(PollActivity.ANSWER3_COUNT, answers.get(PollActivity.ANSWER_INDEX_2).getNumAnswers());

                    intent.putExtra(PollActivity.ANSWER4, answers.get(PollActivity.ANSWER_INDEX_3).getAnswerText());
                    intent.putExtra(PollActivity.ANSWER4_COUNT, answers.get(PollActivity.ANSWER_INDEX_3).getNumAnswers());

                    intent.putExtra(PollActivity.QUESTON, holder.question);
                    context.startActivity(intent);
                }
            });
            holder.btnSubmit.setText(R.string.view_results);
        } else {
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




    }

    public void addPost(Question poll, String key) {
        pollList.add(poll);
        pollKeys.add(key);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() { return pollList.size(); }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAuthor;
        public TextView tvQuestion;

        public String answer1;
        public String answer2;
        public String answer3;
        public String answer4;
        public String author;
        public String question;
        public Button btnSubmit;
        public Button btnDelete;
        public Intent intent;
        public int position;

        public ViewHolder(View itemView) {
            super(itemView);

            tvAuthor = itemView.findViewById(R.id.tvAuthor);
            tvQuestion = itemView.findViewById(R.id.tvQuestion);
            btnSubmit = itemView.findViewById(R.id.btnSubmitAnswer);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
