package com.example.zadanie;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class TaskFragment extends Fragment {
    private final Calendar calendar = Calendar.getInstance();
    private Task task;
    private Spinner categorySpinner;
    private EditText nameField;
    private CheckBox doneCheckBox;
    private EditText dateField;
    public static final String ARG_TASK_ID = "task_id";

    Locale locale = new Locale("pl", "PL");
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy", locale);
    public TaskFragment() {}

    public static TaskFragment newInstance(UUID taskId) {

        Bundle bundle = new Bundle();
        bundle.putSerializable(ARG_TASK_ID, taskId);
        TaskFragment taskFragment = new TaskFragment();
        taskFragment.setArguments(bundle);
        return taskFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UUID taskId = (UUID)getArguments().getSerializable(ARG_TASK_ID);
        task = TaskStorage.getInstance().getTask(taskId);

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, false);

        nameField = view.findViewById(R.id.task_name);
        dateField = view.findViewById(R.id.task_date);
        doneCheckBox = view.findViewById(R.id.task_done);
        categorySpinner = view.findViewById(R.id.task_category);


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                getContext(),
                R.array.category_spinner_items,
                android.R.layout.simple_spinner_item
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        categorySpinner.setAdapter(adapter);
        categorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                task.setCategory(position == 0 ? Category.HOME : Category.STUDIES);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                categorySpinner.setSelection(0);
                task.setCategory(Category.HOME);
            }

        });


        DatePickerDialog.OnDateSetListener date = (view12, year, month, day) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, month);
            calendar.set(Calendar.DAY_OF_MONTH, day);
            dateField.setText(calendar.getTime().toString());
            task.setDate(calendar.getTime());
        };
        dateField.setOnClickListener(view1 -> {
            new DatePickerDialog(
                    getContext(),
                    date,
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
            ).show();
        });
        nameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                task.setName(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
            nameField.setText(task.getName());
            dateField.setText(dateFormat.format(task.getDate()));
            doneCheckBox.setChecked(task.isDone());
            doneCheckBox.setOnCheckedChangeListener((buttonView, isChecked) -> task.setDone(isChecked));
            return view;
        }



    }



