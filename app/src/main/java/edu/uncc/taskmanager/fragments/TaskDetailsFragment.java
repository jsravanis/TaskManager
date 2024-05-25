package edu.uncc.taskmanager.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.uncc.taskmanager.R;
import edu.uncc.taskmanager.databinding.FragmentTaskDetailsBinding;
import edu.uncc.taskmanager.models.Task;

public class TaskDetailsFragment extends Fragment {
    private static final String TASK = "task";
    private Task mTask;
    FragmentTaskDetailsBinding binding;
    TaskDetailListener mListener;

    public interface TaskDetailListener {
        void goBackToTasks();
        void deleteTask(Task mTask);
    }

    public TaskDetailsFragment() {
    }

    public static TaskDetailsFragment newInstance(Task task) {
        TaskDetailsFragment fragment = new TaskDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(TASK, task);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTask = (Task) getArguments().getSerializable(TASK);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskDetailsBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if(mTask != null){
            binding.textViewName.setText(mTask.getName());
            binding.textViewCategory.setText(mTask.getCategory());
            binding.textViewPriority.setText(mTask.getPriorityStr());
        }
        binding.buttonBack.setOnClickListener(v -> {
            mListener.goBackToTasks();
        });
        binding.imageViewDelete.setClickable(true);
        binding.imageViewDelete.setOnClickListener(v -> {
            mListener.deleteTask(mTask);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TaskDetailListener) context;
    }
}