package com.example.mynotes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import java.util.Date;
import java.util.UUID;

import static android.widget.CompoundButton.*;

public class TaskFragment extends Fragment {

    private static final String ARG_TASK_ID = "task_id";
    private static final String DIALOG_DATE = "DialogDate";
    private static final int REQUEST_DATE = 0;

    private Task mTask;
    private TextView mTitle;
    private TextView mDescription;
    private Button mDateButton;
    private CheckBox mCompletedCheckBox;

    public TaskFragment() {
    }

    public static TaskFragment newInstance(UUID taskId) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_TASK_ID, taskId);

        TaskFragment fragment = new TaskFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID taskId = (UUID)getArguments().getSerializable(ARG_TASK_ID);
        mTask = TaskLab.get(getActivity()).getTask(taskId);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_task, menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.delete_subtitle:
                TaskLab taskLab = TaskLab.get(getActivity());
                taskLab.deleteTask(mTask.getTaskId());
                Intent intent = new Intent(getActivity(), TaskListActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        mTitle = view.findViewById(R.id.task_title);
        mTitle.setText(mTask.getTitle());
        mTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setTitle(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        mDescription = view.findViewById(R.id.task_description);
        mDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mTask.setDescription(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });

        mDateButton = view.findViewById(R.id.task_date);
        updateDate();
        mDateButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mTask.getDate());
                dialog.setTargetFragment(TaskFragment.this, REQUEST_DATE);
                dialog.show(manager, DIALOG_DATE);
            }
        });

        mCompletedCheckBox = view.findViewById(R.id.task_completed);
        mCompletedCheckBox.setChecked(mTask.isCompleted());
        mCompletedCheckBox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mTask.setCompleted(isChecked);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK)
            return;

        if (requestCode == REQUEST_DATE) {
            Date date = (Date)data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mTask.setDate(date);
            updateDate();
        }
    }

    private void updateDate() {
        mDateButton.setText(mTask.getDate().toString());
    }
}
