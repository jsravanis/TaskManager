package edu.uncc.taskmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import java.util.ArrayList;

import edu.uncc.taskmanager.databinding.ActivityMainBinding;
import edu.uncc.taskmanager.fragments.AddTaskFragment;
import edu.uncc.taskmanager.fragments.SelectCategoryFragment;
import edu.uncc.taskmanager.fragments.TaskDetailsFragment;
import edu.uncc.taskmanager.fragments.TasksFragment;
import edu.uncc.taskmanager.models.Data;
import edu.uncc.taskmanager.models.Task;

public class MainActivity extends AppCompatActivity implements SelectCategoryFragment.SelectCategoryListener,
        TasksFragment.TasksListener, AddTaskFragment.AddTaskListener, TaskDetailsFragment.TaskDetailListener {

    public static final String ADD_TASK = "ADD_TASK";
    public static final String TASKS_LIST = "TASKS_LIST";
    private ArrayList<Task> mTasks = new ArrayList<>();

    ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mTasks.addAll(Data.sampleTestTasks); //adding for testing
        getSupportFragmentManager().beginTransaction()
                .add(R.id.rootView, new TasksFragment(), TASKS_LIST)
                .commit();
    }

    @Override
    public void sendSelectedCategory(String selectedCategory) {
         AddTaskFragment addTaskFragment = (AddTaskFragment) getSupportFragmentManager().findFragmentByTag(ADD_TASK);
         if(addTaskFragment != null){
             addTaskFragment.setSelectedCategory(selectedCategory);
             getSupportFragmentManager().popBackStack();
         }
    }

    @Override
    public ArrayList<Task> getAllTasks() {
        return mTasks;
    }

    @Override
    public void gotoAddTask() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new AddTaskFragment(), ADD_TASK)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void gotoTaskDetails(Task task) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, TaskDetailsFragment.newInstance(task))
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void goToSelectCategory() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.rootView, new SelectCategoryFragment())
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void addNewTaskToTasks(Task task) {
        mTasks.add(task);
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void goBackToTasks() {
        getSupportFragmentManager().popBackStack();
    }

    @Override
    public void deleteTask(Task mTask) {
        mTasks.remove(mTask);
        getSupportFragmentManager().popBackStack();
    }
}