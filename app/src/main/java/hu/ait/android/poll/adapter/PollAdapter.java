package hu.ait.android.poll.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import hu.ait.android.poll.R;
import hu.ait.android.poll.data.Question;


public class PollAdapter extends RecyclerView.Adapter<PollAdapter.ViewHolder> {

    private Context context;
    private List<Question> pollList;
    private List<String> pollKeys;
    private String uId;
    private int lastPosition = -1;
    private DatabaseReference pollRef;

    public PollAdapter(Context context, String uId) {
        this.context = context;
        this.uId = uId;

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
    public void onBindViewHolder(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvAuthor;
        public TextView tvQuestion;
        public TextView tvBody;
        public Button btnSubmit;
        public Button btnDelete;

        public ViewHolder(View itemView) {
            super(itemView);


        }
    }
}
