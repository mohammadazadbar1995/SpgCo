package com.spg.sgpco.createProjcet;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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
import com.spg.sgpco.baseView.BaseFragment;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.service.ResponseModel.ListCitiesItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class StateFragment extends BaseFragment implements StateAdapter.OnItemClickListener, BackPressedFragment {


    public ArrayList<ListCitiesItem> stateLists;
    public ArrayList<ListCitiesItem> stateListsFilter = new ArrayList<>();
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
    private StateAdapter adapter;


    public StateFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_project_type, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvCenterTitle.setText(getResources().getString(R.string.select_province));
        edtSearchBar.setHint(getString(R.string.search_province));

        setAdapter();
        searchInList();
        return view;

    }

    private void searchInList() {
        edtSearchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence == null || charSequence.toString().trim().isEmpty()) {
                    stateListsFilter.clear();
                    stateListsFilter.addAll(stateLists);
                } else {
                    stateListsFilter.clear();
                    String searchText = charSequence.toString().trim();
                    for (ListCitiesItem list :
                            stateLists) {
                        if (list.getState().contains(searchText)) {
                            stateListsFilter.add(list);
                        }
                    }
                }
                adapter.setList(stateListsFilter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setAdapter() {
        adapter = new StateAdapter(stateLists, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recycle.setHasFixedSize(true);
        recycle.setLayoutManager(layoutManager);
        recycle.setAdapter(adapter);

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @Override
    public void onPopBackStack() {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public void onItemClick(int position, ListCitiesItem state) {
        Intent intent = new Intent(getContext(), StateFragment.class);
        intent.putExtra("State", state);
        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
        }
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack();
        }
    }
}
