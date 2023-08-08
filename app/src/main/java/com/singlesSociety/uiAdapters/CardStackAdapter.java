package com.singlesSociety.uiAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.singlesSociety.Arvi.widget.PlayableItemsRecyclerView;
import com.singlesSociety.R;
import com.singlesSociety.UiModels.PreviewProfileModel;
import com.singlesSociety.UiModels.ShowCaseModel;

import java.util.ArrayList;

public class CardStackAdapter extends RecyclerView.Adapter<CardStackAdapter.ItemViewholder> {

    private ArrayList<PreviewProfileModel> userProfileToPreview;
    private ArrayList<String> usersLikedList;
    private ArrayList<ArrayList<ShowCaseModel>> showCaseMainList;
    private ShowCaseAdapter showCaseAdapter;
    private VisibleUserListener visibleUserListener;
    private ScrollStateListener scrollStateListener;
    Context context;

    public CardStackAdapter(ArrayList<PreviewProfileModel> userProfileToPreview,ArrayList<String> usersLikedList, Context context) {
        this.userProfileToPreview = userProfileToPreview;
        this.usersLikedList = usersLikedList;
        this.context = context;
        showCaseMainList = processShowCaseList(userProfileToPreview,usersLikedList);
    }

    public void setVisibleUserListener(VisibleUserListener visibleUserListener) {
        this.visibleUserListener = visibleUserListener;
    }

    public void setScrollStateListener(ScrollStateListener scrollStateListener) {
        this.scrollStateListener = scrollStateListener;
    }

    @NonNull
    @Override
    public ItemViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.showcase_layout, parent, false);
        return new ItemViewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewholder holder, int position) {
        ShowCaseAdapter showCaseAdapter = new ShowCaseAdapter(context,showCaseMainList.get(position));
        LinearLayoutManager layoutManager = new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false);
        holder.showcaseRecyclerview.setLayoutManager(layoutManager);
        holder.showcaseRecyclerview.setAdapter(showCaseAdapter);
        //holder.showcaseRecyclerview.addItemDecoration(new LinePagerIndicator(showCaseMainList.get(position).size()));
        visibleUserListener.onUserVisible(showCaseMainList.get(position).get(0).getUserId());

        holder.showcaseRecyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
            }
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //reached end
                   // Toast.makeText(context, "End Reached", Toast.LENGTH_SHORT).show();
                  //  scrollStateListener.onReadyToGoDown();
                }

                if (!recyclerView.canScrollVertically(-1) && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    //reached top
                   // Toast.makeText(context, "Top Reached", Toast.LENGTH_SHORT).show();
                  //  scrollStateListener.onReadyToMoveUp();
                }
                if(newState == RecyclerView.SCROLL_STATE_DRAGGING){
                    //scrolling
                }
            }

        });
    }


    @Override
    public int getItemCount() {
        return showCaseMainList.size();
    }

    private ArrayList<ArrayList<ShowCaseModel>> processShowCaseList(ArrayList<PreviewProfileModel> userProfileToPreview,ArrayList<String> usersLikedList) {
        ArrayList<ArrayList<ShowCaseModel>> showCaseModelMainArrayList = new ArrayList<>();

        for (PreviewProfileModel previewProfileModel : userProfileToPreview) {
            ArrayList<ShowCaseModel> showCaseModelArrayList = new ArrayList<>();
            ArrayList<String> mainStrings = new ArrayList<>();
            ArrayList<String> quoteStrings = new ArrayList<>();
            ArrayList<String> aboutStrings = new ArrayList<>();
            ArrayList<String> careerStrings = new ArrayList<>();
            ArrayList<String> imageStrings = new ArrayList<>();
            ArrayList<String> goalStrings = new ArrayList<>();
            mainStrings.add(previewProfileModel.getFirstname());
            mainStrings.add(String.valueOf(previewProfileModel.getAge()));
            mainStrings.add(previewProfileModel.getCity());
            mainStrings.add(previewProfileModel.getOccupation());
            mainStrings.add(previewProfileModel.getImage1Url());
            mainStrings.add(previewProfileModel.getUserId());
            ShowCaseModel showCaseModel = new ShowCaseModel(mainStrings, 1, usersLikedList, previewProfileModel.getUserId());
            showCaseModelArrayList.add(showCaseModel);

            quoteStrings.add(previewProfileModel.getQuote());
            ShowCaseModel showCaseModel1 = new ShowCaseModel(quoteStrings, 2, usersLikedList, previewProfileModel.getUserId());
            showCaseModelArrayList.add(showCaseModel1);

            ShowCaseModel showCaseModel9 = new ShowCaseModel(goalStrings, 9, usersLikedList, previewProfileModel.getUserId());
            showCaseModelArrayList.add(showCaseModel9);

            aboutStrings.add(previewProfileModel.getStatus());
            aboutStrings.add(previewProfileModel.getSmoking());
            aboutStrings.add(previewProfileModel.getDrinking());
            aboutStrings.add(previewProfileModel.getLanguage());
            aboutStrings.add(previewProfileModel.getReligion());
            aboutStrings.add(previewProfileModel.getMarriageGoals());
            ShowCaseModel showCaseModel2 = new ShowCaseModel(aboutStrings, 3, usersLikedList, previewProfileModel.getUserId());
            showCaseModelArrayList.add(showCaseModel2);

            careerStrings.add(previewProfileModel.getEducationLevel());
            careerStrings.add(previewProfileModel.getOccupation());
            careerStrings.add(previewProfileModel.getWorkplace());
            careerStrings.add(previewProfileModel.getImage2Url());
            ShowCaseModel showCaseModel3 = new ShowCaseModel(careerStrings, 4, usersLikedList, previewProfileModel.getUserId());
            showCaseModelArrayList.add(showCaseModel3);

            imageStrings.add(previewProfileModel.getImage3Url());
            ShowCaseModel showCaseModel5 = new ShowCaseModel(imageStrings, 6, usersLikedList, previewProfileModel.getUserId());
            showCaseModelArrayList.add(showCaseModel5);
            showCaseModelMainArrayList.add(showCaseModelArrayList);
        }
        return showCaseModelMainArrayList;
    }

    public static class ItemViewholder extends RecyclerView.ViewHolder{

        PlayableItemsRecyclerView showcaseRecyclerview;
        public ItemViewholder(View ItemView){
            super(ItemView);
            showcaseRecyclerview = ItemView.findViewById(R.id.showcase_recyclerview);

        }

    }

    public interface VisibleUserListener{
         void onUserVisible(String userId);
    }

    public interface ScrollStateListener{
        void onReadyToMoveUp();
        void onReadyToGoDown();
    }
}
