package edu.uncc.taskmanager.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Comparator;

import edu.uncc.taskmanager.R;
import edu.uncc.taskmanager.TaskAdapter;
import edu.uncc.taskmanager.databinding.FragmentTasksBinding;
import edu.uncc.taskmanager.models.Task;

public class TasksFragment extends Fragment {

    private ArrayList<Task> mTasks = new ArrayList<>();
    FragmentTasksBinding binding;
    TasksListener mListener;
    TaskAdapter taskAdapter;

    public interface TasksListener{
        ArrayList<Task> getAllTasks();
        void gotoAddTask();
        void gotoTaskDetails(Task task);
    }

    public TasksFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTasksBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTasks = mListener.getAllTasks();
        taskAdapter = new TaskAdapter(view.getContext(), R.layout.task_list_item, mTasks);
        binding.listView.setAdapter(taskAdapter);
        binding.buttonAddNew.setOnClickListener(v -> {
            mListener.gotoAddTask();
        });
        binding.buttonClearAll.setOnClickListener(v -> {
            clearListAndRefreshAdapter();
        });
        binding.imageViewSortAsc.setClickable(true);
        binding.imageViewSortAsc.setOnClickListener(v -> {
            mTasks.sort(Comparator.comparing(Task::getPriority));
            taskAdapter.notifyDataSetChanged();
            binding.textViewSortIndicator.setText("Sort by Priority (ASC)");
        });
        binding.imageViewSortDesc.setClickable(true);
        binding.imageViewSortDesc.setOnClickListener(v -> {
            mTasks.sort(Comparator.comparing(Task::getPriority).reversed());
            taskAdapter.notifyDataSetChanged();
            binding.textViewSortIndicator.setText("Sort by Priority (DESC)");
        });

        binding.listView.setOnItemClickListener((parent, view1, position, id) -> {
            mListener.gotoTaskDetails(mTasks.get(position));
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (TasksListener) context;
    }

    public void clearListAndRefreshAdapter(){
        taskAdapter.clear();
        taskAdapter.notifyDataSetChanged();
    }
}