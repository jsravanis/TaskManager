package edu.uncc.taskmanager.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import edu.uncc.taskmanager.databinding.FragmentAddTaskBinding;
import edu.uncc.taskmanager.models.Task;

public class AddTaskFragment extends Fragment {

    FragmentAddTaskBinding binding;
    AddTaskListener mListener;
    private Task task;
    private ArrayList<String> priorities = new ArrayList<>(Arrays.asList("Low", "Very Low", "Medium", "High", "Very High"));

    public void setSelectedCategory(String selectedCategory) {
        if(task == null)
            task = new Task();
        task.setCategory(selectedCategory);
    }


    public interface AddTaskListener {
        void goToSelectCategory();
        void addNewTaskToTasks(Task task);
    }

    public AddTaskFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentAddTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(task != null){
            if(task.getCategory() != null)
                binding.textViewCategory.setText(task.getCategory());
            if(task.getPriorityStr() != null) {
                int priority = priorities.indexOf(task.getPriorityStr());
                switch (priority){
                    case 0:
                        binding.radioButtonVeryLow.setChecked(true);
                        break;
                    case 1:
                        binding.radioButtonLow.setChecked(true);
                        break;
                    case 2:
                        binding.radioButtonMedium.setChecked(true);
                        break;
                    case 3:
                        binding.radioButtonHigh.setChecked(true);
                        break;
                    case 4:
                        binding.radioButtonVeryHigh.setChecked(true);
                        break;
                }
            }
            if(task.getName() != null)
                binding.editTextName.setText(task.getName());
        }
        binding.buttonSelectCategory.setOnClickListener(v -> {
            mListener.goToSelectCategory();
        });
        binding.buttonSubmit.setOnClickListener(v -> {
            int checkedRadioButtonId = binding.radioGroup.getCheckedRadioButtonId();
            if(binding.editTextName.getText() == null || binding.textViewCategory.getText().toString().equals("N/A")
            || checkedRadioButtonId == -1)
                Toast.makeText(getContext(), "Please fill or select all the fields", Toast.LENGTH_SHORT).show();
            else {
                RadioButton radioButton = binding.getRoot().findViewById(checkedRadioButtonId);
                task = new Task();
                task.setName(binding.editTextName.getText().toString());
                task.setCategory(binding.textViewCategory.getText().toString());
                task.setPriorityStr(radioButton.getText().toString());
                task.setPriority(priorities.indexOf(radioButton.getText().toString()));
                mListener.addNewTaskToTasks(task);
            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (AddTaskListener) context;
    }
}