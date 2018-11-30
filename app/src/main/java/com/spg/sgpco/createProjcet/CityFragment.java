package com.spg.sgpco.createProjcet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.spg.sgpco.R;
import com.spg.sgpco.activity.BackPressedFragment;
import com.spg.sgpco.baseView.BaseEditText;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.service.ResponseModel.CitiesListItem;
import com.spg.sgpco.service.ResponseModel.ListCitiesItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class CityFragment extends Fragment implements CityAdapter.OnItemClickListener,BackPressedFragment {


    public ArrayList<CitiesListItem> citiesItems;
    public ArrayList<CitiesListItem> citiesItemsFilter = new ArrayList<>();
    Unbinder unbinder;
    @BindView(R.id.tvCenterTitle)
    BaseTextView tvCenterTitle;
    @BindView(R.id.edtSearchBar)
    BaseEditText edtSearchBar;
    @BindView(R.id.searchBar)
    CardView searchBar;
    @BindView(R.id.recycle)
    RecyclerView recycle;
    @BindView(R.id.roundedLoadingView)
    RoundedLoadingView roundedLoadingView;
    @BindView(R.id.root)
    BaseRelativeLayout root;
    private CityAdapter adapter;


    public CityFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_project_type, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvCenterTitle.setText(getResources().getString(R.string.select_city));
        edtSearchBar.setHint(getString(R.string.search_city));

        setAdapter();
        searchInList();
        return view;

    }

    private void setAdapter() {
        adapter = new CityAdapter(citiesItems, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapter);

    }

    private void searchInList() {
        edtSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence == null || charSequence.toString().trim().isEmpty()) {
                    citiesItemsFilter.clear();
                    citiesItemsFilter.addAll(citiesItems);
                } else {
                    citiesItemsFilter.clear();
                    String searchText = charSequence.toString().trim();
                    for (CitiesListItem list :
                            citiesItems) {
                        if (list.getCity().contains(searchText)) {
                            citiesItemsFilter.add(list);
                        }
                    }
                }
                adapter.setList(citiesItemsFilter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onItemClick(int position, CitiesListItem city) {
        Intent intent = new Intent(getContext(), CityFragment.class);
        intent.putExtra("City", city);
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
        }
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack();
        }
    }

    @Override
    public void onPopBackStack() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }
}
