package edu.uncc.taskmanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import edu.uncc.taskmanager.models.Task;

public class TaskAdapter extends ArrayAdapter<Task> {

    public TaskAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Task> tasks) {
        super(context, resource, tasks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.task_list_item, parent, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textViewName = convertView.findViewById(R.id.textViewName);
            viewHolder.textViewCategory = convertView.findViewById(R.id.textViewCategory);
            viewHolder.textViewPriority = convertView.findViewById(R.id.textViewPriority);
            convertView.setTag(viewHolder);
        }

        Task task = getItem(position);
        if(task != null){
            ViewHolder viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.textViewName.setText(task.getName());
            viewHolder.textViewCategory.setText(task.getCategory());
            viewHolder.textViewPriority.setText(task.getPriorityStr());
        }

        return convertView;
    }

    private static class ViewHolder {
        TextView textViewName;
        TextView textViewCategory;
        TextView textViewPriority;
    }
}
