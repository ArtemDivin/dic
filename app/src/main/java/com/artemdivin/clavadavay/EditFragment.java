package com.artemdivin.clavadavay;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.artemdivin.clavadavay.base.BaseFragment;
import com.artemdivin.clavadavay.helper.DialogHelper;
import com.artemdivin.clavadavay.helper.ExceptionHelper;
import com.artemdivin.clavadavay.helper.UIHelper;
import com.artemdivin.clavadavay.modelobject.Resource;
import com.artemdivin.clavadavay.modelobject.Tr;
import com.artemdivin.clavadavay.modelobject.TranslateModel;
import com.artemdivin.clavadavay.db.entity.Words;
import com.artemdivin.clavadavay.ui.IAddToDictionary;
import com.artemdivin.clavadavay.ui.TranslateAdapter;
import com.artemdivin.clavadavay.viewmodel.WordsViewModel;
import com.jakewharton.rxbinding2.view.RxView;
import com.jakewharton.rxbinding2.widget.RxTextView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class EditFragment extends BaseFragment implements IAddToDictionary {

    public static final String ARG_ORIGIN = "arg_origin";
    public static final String ARG_TRANSLATE = "arg_translate";
    public static final String ARG_MODE_TYPE = "arg_is_edit";

    @BindView(R.id.et_word)
    EditText originalWord;

    @BindView(R.id.et_translate)
    EditText translateWord;

    @BindView(R.id.recycler_view)
    RecyclerView rvVariant;

    @BindView(R.id.ll_variant_list)
    LinearLayout variantLayout;

    @BindView(R.id.btn_save)
    Button btnSave;

    @BindView(R.id.progress)
    ProgressBar progress;

    private WordsViewModel viewModel;
    private TranslateAdapter adapter;

    public static EditFragment newInstance(boolean isEditMode) {
        return newInstance("", "", isEditMode);
    }

    public static EditFragment newInstance(String origin, String translate, boolean isEditMode) {
        Bundle b = new Bundle();
        b.putString(ARG_ORIGIN, origin);
        b.putString(ARG_TRANSLATE, translate);
        b.putBoolean(ARG_MODE_TYPE, isEditMode);
        EditFragment fragment = new EditFragment();
        fragment.setArguments(b);

        return fragment;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_edit, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_delete:
                viewModel.deleteWord(originalWord.getText().toString());
                backFragment();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(getActivity()).get(WordsViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            originalWord.setText(getArguments().getString(ARG_ORIGIN));
            translateWord.setText(getArguments().getString(ARG_TRANSLATE));

            boolean isEditMode = getArguments().getBoolean(ARG_MODE_TYPE);

            setTitle(isEditMode ? getString(R.string.label_edit) : getString(R.string.label_add));
            setHasOptionsMenu(isEditMode);
            btnSave.setText(isEditMode ? R.string.button_save : R.string.button_add);
        }

        originalWord.requestFocus();
        UIHelper.showKeyboard(getActivity());

        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        adapter = new TranslateAdapter(this);
        rvVariant.setLayoutManager(manager);
        rvVariant.setAdapter(adapter);


        Disposable a = RxTextView
                .textChanges(originalWord)
                .skipInitialValue()
                .filter(charSequence -> charSequence.length() > 1)
                .debounce(500, TimeUnit.MILLISECONDS)
                .flatMap(charSequence -> {
                    viewModel.getWordsRemote(charSequence.toString()).observe(this, this::handleResource);
                    return Observable.empty();
                })
                //.doOnError(resource -> DialogHelper.showDialog(getActivity(), resource))

                .subscribe();

        Disposable b = RxView.clicks(btnSave)
                .subscribe((o) -> {
                    String original = originalWord.getText().toString();
                    String translate = translateWord.getText().toString();
                    if (original.length() == 0 || translate.length() == 0) {
                        DialogHelper.showDialog(getActivity(), getString(R.string.message_empty_field));
                        return;
                    }
                    viewModel.saveResult(new Words(original, translate));
                    backFragment();
                });

        disposable.add(a);
        disposable.add(b);


    }

    private void handleResource(Resource<TranslateModel> resource) {
        {
            if (resource != null) {
                switch (resource.status) {
                    case LOADING:
                        progress.setVisibility(View.VISIBLE);
                        break;
                    case SUCCESS:
                        progress.setVisibility(View.GONE);
                        showResult(resource.data);
                        break;
                    case ERROR:
                        progress.setVisibility(View.GONE);

                        String error = ExceptionHelper.getException(resource.error, getActivity());
                        DialogHelper.showDialog(getActivity(), error);
                        break;
                }
            }
        }
    }


    private void showResult(TranslateModel translateModel) {
        if (translateModel != null && !translateModel.getDef().isEmpty()) {
            variantLayout.setVisibility(View.VISIBLE);
            UIHelper.hideKeyboard(getActivity(), originalWord);
            List<Tr> data = translateModel.getDef().get(0).getTr();
            adapter.setData(data);
        } else {
            adapter.setData(Tr.getEmptyList());
        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.edit_fragment;
    }

    @Override
    public void onAddDictionary(String val) {
        translateWord.setText(val);
    }

}
