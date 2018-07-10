package com.artemdivin.clavadavay.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import io.reactivex.disposables.CompositeDisposable;

public abstract class BaseFragment extends Fragment {

    public CompositeDisposable disposable = new CompositeDisposable();

    public interface FragmentListener {

        void onNextFragment(BaseFragment fragment);

        void onBackFragment();

        void setTitle(CharSequence title);
    }

    @Nullable private FragmentListener listener;
    Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (FragmentListener) getActivity();
        }catch (ClassCastException e){
            throw new ClassCastException();
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;

    }

    public void nextFragment(BaseFragment fragment){
        if (listener != null){
            listener.onNextFragment(fragment);
        }
    }

    public void backFragment(){
        if (listener != null){
            listener.onBackFragment();
        }
    }

    public void setTitle(CharSequence title){
        if (listener != null){
            listener.setTitle(title);
        }
    }

    protected abstract int getLayoutRes();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(getLayoutRes(), container, false);
        unbinder = ButterKnife.bind(this, root);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (unbinder != null){
            unbinder.unbind();
        }
        if (disposable !=null){
            disposable.dispose();
        }
    }
}
