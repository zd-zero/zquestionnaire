package zero.zd.zikyu;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import zero.zd.zikyu.model.QnaSubject;

public class QnaLoadActivity extends AppCompatActivity {

    private static final String TAG = QnaLoadActivity.class.getSimpleName();

    private ArrayList<QnaSubject> mSubjectList;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, QnaLoadActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_NEW_TASK);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_qna);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setTitle(R.string.title_load);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        populateSubjectList();

        ListView list = (ListView) findViewById(R.id.list);
        ArrayAdapter adapter =
                new SubjectArrayAdapter(this, R.layout.list_subject, mSubjectList);
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                QnaAnswerState.getInstance().setQnaSubject(mSubjectList.get(position));
                startActivity(QnaAnswerActivity.getStartIntent(QnaLoadActivity.this, false));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void populateSubjectList() {
        mSubjectList = new ArrayList<>();
        mSubjectList.add(QnaHelper.getBasicQnA());
        mSubjectList.add(QnaHelper.getBasicQnaSmall());
        mSubjectList.add(QnaHelper.getBasicQnaMultiple());
    }

    private static class ViewHolder {
        private TextView subjectName;
        private TextView qnaCount;
    }

    private class SubjectArrayAdapter extends ArrayAdapter {
        private Context mContext;
        private int mResource;
        private ArrayList<QnaSubject> mSubjectList;

        SubjectArrayAdapter(
                Context context, int resource, ArrayList<QnaSubject> subjectList) {
            super(context, resource, subjectList);
            mContext = context;
            mResource = resource;
            mSubjectList = subjectList;
        }

        @Override
        public int getCount() {
            return mSubjectList.size();
        }

        @Nullable
        @Override
        public QnaSubject getItem(int position) {
            return mSubjectList.get(position);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            ViewHolder viewHolder;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater)
                        mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mResource, parent, false);

                viewHolder = new ViewHolder();
                viewHolder.subjectName = (TextView) convertView.findViewById(R.id.text_subject_name);
                viewHolder.qnaCount = (TextView) convertView.findViewById(R.id.text_qna_count);

                convertView.setTag(viewHolder);
            } else viewHolder = (ViewHolder) convertView.getTag();

            QnaSubject qnASubject = mSubjectList.get(position);

            viewHolder.subjectName.setText(qnASubject.getSubjectName());
            viewHolder.qnaCount.setText(getResources()
                    .getString(R.string.list_qna_count, qnASubject.getQnaList().size()));

            return convertView;
        }
    }
}
