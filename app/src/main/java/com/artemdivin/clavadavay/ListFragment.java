package com.artemdivin.clavadavay;

import android.app.AlertDialog;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.artemdivin.clavadavay.base.BaseFragment;
import com.artemdivin.clavadavay.db.entity.Words;
import com.artemdivin.clavadavay.ui.IItemClick;
import com.artemdivin.clavadavay.ui.WordsAdapter;
import com.artemdivin.clavadavay.viewmodel.WordsViewModel;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.realm.RealmResults;

public class ListFragment extends BaseFragment implements IItemClick {

    @BindView(R.id.recycler_view)
    RecyclerView recyclerView;

    @BindView(R.id.tv_no_data)
    TextView tvNoData;

    private WordsViewModel viewModel;
    private WordsAdapter adapter;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.item_add:
                nextFragment(EditFragment.newInstance(false));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
        setTitle(getString(R.string.lanel_dic));

        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL);
        adapter = new WordsAdapter(this);
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        viewModel = ViewModelProviders.of(getActivity()).get(WordsViewModel.class);
        viewModel.getAllWorldsLocal().observe(this, this::showDic);

    }

    private void showDic(RealmResults<Words> words) {
        boolean dataExist = words != null && !words.isEmpty();
        tvNoData.setVisibility(dataExist ? View.INVISIBLE : View.VISIBLE);
        if (dataExist) {
            List<Words> result = new ArrayList<>(words);
            adapter.setData(result);
        }else{
            adapter.setData(null);
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.list_fragment;
    }

    @Override
    public void onLongClick(String origin) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(R.string.label_delete)
                .setNegativeButton(R.string.button_no, (dialogInterface, i) -> dialogInterface.dismiss())
                .setPositiveButton(R.string.button_yes, (dialogInterface, i) ->
                        viewModel.deleteWord(origin));
        builder.create().show();
    }

    @Override
    public void onItemClick(String origin, String translate) {
        nextFragment(EditFragment.newInstance(origin, translate, true));
    }
}
