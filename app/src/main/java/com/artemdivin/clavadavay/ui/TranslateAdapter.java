package com.artemdivin.clavadavay.ui;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.artemdivin.clavadavay.R;
import com.artemdivin.clavadavay.modelobject.Tr;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TranslateAdapter extends AbstractRecyclerViewAdapter<Tr, TranslateAdapter.VariantViewHolder> {

    private IAddToDictionary addToDictionary;

    public TranslateAdapter(IAddToDictionary addToDictionary) {
        this.addToDictionary = addToDictionary;
    }

    @Override
    protected VariantViewHolder getViewHolder(View parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_choose, null);
        return new VariantViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(VariantViewHolder holder, Tr val) {
        holder.tvVariant.setText(val.getText());
    }

    public class VariantViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_variant)
        TextView tvVariant;

        VariantViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String text = tvVariant.getText().toString();
                    if (!text.equals("Нет вариантов")) {
                        addToDictionary.onAddDictionary(tvVariant.getText().toString());
                    }
                }
            });
        }
    }
}

