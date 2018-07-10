package com.artemdivin.clavadavay.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.artemdivin.clavadavay.R;
import com.artemdivin.clavadavay.db.entity.Words;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WordsAdapter extends AbstractRecyclerViewAdapter<Words, WordsAdapter.WordsViewHolder> {

    private IItemClick click;

    public WordsAdapter(IItemClick click) {
        this.click = click;
    }

    @Override
    protected WordsViewHolder getViewHolder(View parent) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word, null);
        return new WordsViewHolder(v);
    }

    @Override
    protected void onBindViewHolder(WordsViewHolder holder, Words val) {
        holder.origin.setText(val.getOriginal());
        holder.translate.setText(val.getTranslate());
    }

    public class WordsViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_origin)
        TextView origin;
        @BindView(R.id.tv_translate)
        TextView translate;

        WordsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);

            itemView.setOnLongClickListener(view -> {
                click.onLongClick(origin.getText().toString());
                return true;
            });
            itemView.setOnClickListener(view -> click.onItemClick(origin.getText().toString(), translate.getText().toString()));
        }
    }
}
