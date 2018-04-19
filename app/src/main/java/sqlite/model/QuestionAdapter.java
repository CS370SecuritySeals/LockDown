package sqlite.model;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.sp18.ssu370.baseprojectapp.R;
import sqlite.model.Passcodes;

public class QuestionAdapter extends RecyclerView.Adapter<QuestionAdapter.MyViewHolder> {

    private Context context;
    private List<Passcodes> questionList;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView answer;
        public TextView dot;
        public TextView question;

        public MyViewHolder(View view) {
            super(view);
            answer = view.findViewById(R.id.answer);
            dot = view.findViewById(R.id.dot);
            question = view.findViewById(R.id.question);
        }
    }


    public QuestionAdapter(Context context, List<Passcodes> questionList) {
        this.context = context;
        this.questionList = questionList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.security_question_row, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Passcodes passcode = questionList.get(position);

        holder.answer.setText(passcode.getAnswer());

        // Displaying dot from HTML character code
        holder.dot.setText(Html.fromHtml("&#8226;"));

        // Formatting and displaying timestamp
        holder.question.setText(passcode.getQuestion());
    }

    @Override
    public int getItemCount() {
        return questionList.size();
    }
}
