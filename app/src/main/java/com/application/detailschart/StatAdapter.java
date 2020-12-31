package com.application.detailschart;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.recyclerview.widget.RecyclerView;

import com.application.detailschart.model.StatLocal;
import com.google.android.material.textview.MaterialTextView;

import java.util.ArrayList;
import java.util.List;

public class StatAdapter extends RecyclerView.Adapter<StatViewHolder> {

    List<StatLocal> list = new ArrayList<>();
    int total = 0;
    int[] colors = {
            0xff00ddff,
            0xffff8800,
            0xffcc0000,
            0xff99cc00,
            0xffffd600,
            0xffaa66cc,
            0xff000000,
            0xff0099cc,
            0xff669900,
            0xffff4444,
            0xff33b5e5,
            0xffffd600

    };
    private int width, height;

    Context context;

    public void setContext(Context context) {
        this.context = context;
    }

    Animation mAnimationSlide;

    public StatAdapter(int width, int height) {
        this.width = width / 12;
        this.height = height;
    }

    public void setList(List<StatLocal> list) {
        this.list = list;
        updateTotal();
    }

    private void updateTotal() {
        total = 0;
        for (int i = 0; i < list.size(); i++) {
            total += Integer.parseInt(list.get(i).getStat());
        }
    }

    @NonNull
    @Override
    public StatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_stat, parent, false);
        return new StatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatViewHolder holder, int position) {
        mAnimationSlide = AnimationUtils.loadAnimation(context, R.anim.animation);

        StatLocal statLocal = list.get(position);
        double valStat = Double.parseDouble(statLocal.getStat());
        double totalVal = total + 0.00;
        float percent = (float) ((valStat / totalVal) * 100);
       //   ViewGroup.LayoutParams parentLayoutParams = holder.parent.getLayoutParams();
        //        parentLayoutParams.width = width;
        //        holder.parent.setLayoutParams(parentLayoutParams);
        //
        //    holder.guideline.setGuidelinePercent(percent);
        ViewGroup.LayoutParams viewStatParams = holder.viewStat.getLayoutParams();
      int heightL = (int) ((percent * height) / 100);
      //  holder.viewStat.setLayoutParams(viewStatParams);
      //  holder.viewStat.startAnimation(mAnimationSlide);
        ResizeAnimation resizeAnimation = new ResizeAnimation(
                holder.viewStat,
                heightL,
                1
        );
        resizeAnimation.setDuration(2000);
        holder.viewStat.startAnimation(resizeAnimation);
        holder.viewStat.setBackgroundTintList(ColorStateList.valueOf(colors[position]));
        holder.textStat.setText(statLocal.getStat());
        holder.monthStat.setText(statLocal.getMonth().substring(0, 3));


    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class StatViewHolder extends RecyclerView.ViewHolder {
    View viewStat;
    MaterialTextView monthStat, textStat;
    ConstraintLayout parent;
    Guideline guideline;

    public StatViewHolder(@NonNull View itemView) {
        super(itemView);
        viewStat = itemView.findViewById(R.id.viewStat);
        monthStat = itemView.findViewById(R.id.monthStat);
        textStat = itemView.findViewById(R.id.textStat);
        parent = itemView.findViewById(R.id.parent);
        guideline = itemView.findViewById(R.id.guideline);
    }
}
