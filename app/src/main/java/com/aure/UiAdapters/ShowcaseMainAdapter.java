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
    private int cummulative = 0;
    private boolean isUserSwiped = false;
    private boolean isBottomReached = false;

    private ShowcaseViewProgressStateChange showcaseViewProgressStateChange;

    public interface ShowcaseViewProgressStateChange{
       void onProgressChange(int progress);
       void onInitialize(int initialValue);
       void onNegativeProgress(int nProgress);
    }

    public void setShowcaseViewProgressStateChange(ShowcaseViewProgressStateChange showcaseViewProgressStateChange) {
        this.showcaseViewProgressStateChange = showcaseViewProgressStateChange;
    }
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

        showcaseViewProgressStateChange.onInitialize(showCaseMainModelArrayList.get(position).getShowcaseMetadata().getTotalShowcaseHeight());
        showcaseViewProgressStateChange.onProgressChange(10);
        showCaseAdapter.setListener(new ShowCaseAdapter.ViewAddedListener() {
            @Override

            public void onViewAdded(int size) {
            }

            @Override
            public void onUserSwiped() {
                isUserSwiped = true;
            }

            @Override
            public void onBottomReached() {
               isBottomReached = true;
            }
        });

        holder.showcaseRecyclerview.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });


        holder.showcaseRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                if (dy > 0) {
                     showcaseViewProgressStateChange.onProgressChange(dy);

                } else if (dy < 0) {

                      showcaseViewProgressStateChange.onNegativeProgress(dy);
                }

                if(!holder.showcaseRecyclerview.canScrollVertically(1)){
                    showcaseViewProgressStateChange.onInitialize(10);
                }
                else if(!holder.showcaseRecyclerview.canScrollVertically(-1)){
                    isBottomReached = true;
                }
            }
        });
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
           // RecyclerViewPagerIndicator recyclerViewPagerIndicator = new RecyclerViewPagerIndicator(6,6,25, ContextCompat.getColor(context,R.color.light_text_color),ContextCompat.getColor(context,R.color.pinkypinky));
            showcaseRecyclerview.addItemDecoration(new RecyclerViewPagerIndicator(context));
            ItemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {

        }

    }

}
