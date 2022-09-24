package com.aure.UiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;

import com.aure.R;
import com.aure.UiModels.RecyclerViewPagerIndicator;
import com.aure.UiModels.ShowCaseMainModel;





import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ShowcaseMainAdapter extends RecyclerView.Adapter<ShowcaseMainAdapter.ShowcaseMainGeneralItemViewHolder> {


    private Context context;
    private ArrayList<ShowCaseMainModel> showCaseMainModelArrayList;
    private ShowCaseAdapter showCaseAdapter;

    public ShowcaseMainAdapter(Context context, ArrayList<ShowCaseMainModel> showCaseMainModelArrayList){
        this.context = context;
        this.showCaseMainModelArrayList = showCaseMainModelArrayList;
    }

    @NonNull
    @Override
    public ShowcaseMainGeneralItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showcase_layout, parent, false);
        return new ShowcaseMainGeneralItemViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ShowcaseMainGeneralItemViewHolder holder, int position) {
        showCaseAdapter = new ShowCaseAdapter(context,showCaseMainModelArrayList.get(position).getShowCaseModelArrayList());
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        holder.showcaseRecyclerview.setLayoutManager(layoutManager);
        holder.showcaseRecyclerview.setAdapter(showCaseAdapter);
    }

    @Override
    public int getItemCount() {
        return showCaseMainModelArrayList.size();
    }

    public class ShowcaseMainGeneralItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        RecyclerView showcaseRecyclerview;

        public ShowcaseMainGeneralItemViewHolder(View ItemView){
            super(ItemView);
            showcaseRecyclerview = ItemView.findViewById(R.id.showcase_recyclerview);
            showcaseRecyclerview.addItemDecoration(new RecyclerViewPagerIndicator(context));
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

}
