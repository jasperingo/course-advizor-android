package com.jasperanelechukwu.android.courseadvizor.ui.fragments.dashboard;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.jasperanelechukwu.android.courseadvizor.R;
import com.jasperanelechukwu.android.courseadvizor.ui.adapters.ResultListAdapter;
import com.jasperanelechukwu.android.courseadvizor.viewmodels.ResultsViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ResultsFragment extends Fragment {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_results, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final ResultsViewModel viewModel = new ViewModelProvider(this).get(ResultsViewModel.class);

        final ResultListAdapter listAdapter = new ResultListAdapter(
            view1 -> viewModel.fetchResults(),
            result -> Toast.makeText(requireContext(), result.getCourseCode(), Toast.LENGTH_LONG).show()
        );

        viewModel.getStudentsUiState().observe(getViewLifecycleOwner(), resultsUiState -> {
            listAdapter.setUiState(resultsUiState);

            if (!resultsUiState.isLoaded() && !resultsUiState.isLoading() && resultsUiState.getError() == null) {
                viewModel.fetchResults();
            }
        });

        RecyclerView recyclerView = view.findViewById(R.id.results_list_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));

        recyclerView.setAdapter(listAdapter);
    }
}
