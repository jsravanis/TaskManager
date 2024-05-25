package edu.uncc.taskmanager.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import edu.uncc.taskmanager.R;
import edu.uncc.taskmanager.databinding.FragmentSelectCategoryBinding;
import edu.uncc.taskmanager.models.Data;

public class SelectCategoryFragment extends Fragment {

    FragmentSelectCategoryBinding binding;
    ArrayAdapter<String> categoryAdapter;
    SelectCategoryListener mListener;

    public  interface SelectCategoryListener {
        void sendSelectedCategory(String selectedCategory);
    }

    public SelectCategoryFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentSelectCategoryBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        categoryAdapter = new ArrayAdapter<>(view.getContext(), android.R.layout.simple_list_item_1, android.R.id.text1, Data.categories);
        binding.listView.setAdapter(categoryAdapter);
        binding.listView.setOnItemClickListener((parent, view1, position, id) -> {
            mListener.sendSelectedCategory(Data.categories[position]);
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        mListener = (SelectCategoryListener) context;
    }
}