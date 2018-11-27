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
import com.spg.sgpco.baseView.BaseEditText;
import com.spg.sgpco.baseView.BaseRelativeLayout;
import com.spg.sgpco.baseView.BaseTextView;
import com.spg.sgpco.customView.RoundedLoadingView;
import com.spg.sgpco.enums.TypeEnum;
import com.spg.sgpco.service.ResponseModel.SettingResultItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import static android.app.Activity.RESULT_OK;


/**
 * Created by m.azadbar on 5/28/2018.
 */

public class TypeProjectFragment extends Fragment implements TypeProjectAdapter.OnItemClickListener {


    public ArrayList<SettingResultItem> listTypeProjects;
    public ArrayList<SettingResultItem> listTypeProjectsFilter = new ArrayList<>();
    public TypeEnum typeEnum;
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
    private TypeProjectAdapter adapter;


    public TypeProjectFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_project_type, container, false);
        unbinder = ButterKnife.bind(this, view);
        tvCenterTitle.setText(getResources().getString(R.string.select_type_name_projects));
        edtSearchBar.setHint("تست");
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
                    listTypeProjectsFilter.clear();
                    listTypeProjectsFilter.addAll(listTypeProjects);
                } else {
                    listTypeProjectsFilter.clear();
                    String searchText = charSequence.toString().trim();
                    for (SettingResultItem list :
                            listTypeProjects) {
                        if (list.getTitle().contains(searchText)) {
                            listTypeProjectsFilter.add(list);
                        }
                    }
                }
                adapter.setList(listTypeProjectsFilter);

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void setAdapter() {
        adapter = new TypeProjectAdapter(getActivity(), listTypeProjects, this);
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
    public void onItemClick(int position, SettingResultItem typeProject) {
        Intent intent = new Intent(getContext(), TypeProjectFragment.class);
        if (typeEnum == TypeEnum.TYPE_PROJECT) {
            intent.putExtra("TypeProject", listTypeProjects.get(position));
        } else if (typeEnum == TypeEnum.HEAT_SOURCE) {
            intent.putExtra("HeatSource", listTypeProjects.get(position));
        } else if (typeEnum == TypeEnum.GENDER_FLOOR) {
            intent.putExtra("Gender_Project", listTypeProjects.get(position));
        } else if (typeEnum == TypeEnum.TYPE_SPACE) {
            intent.putExtra("TYPE_SPACE", listTypeProjects.get(position));
        }

        if (getTargetFragment() != null) {
            getTargetFragment().onActivityResult(getTargetRequestCode(), RESULT_OK, intent);
        }
        if (getFragmentManager() != null) {
            getFragmentManager().popBackStack();
        }
    }
}
