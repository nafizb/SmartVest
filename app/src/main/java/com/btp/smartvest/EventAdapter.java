package com.btp.smartvest;

import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class EventAdapter extends FirebaseRecyclerAdapter<Event, EventAdapter.EventHolder> {

    RecyclerView recyclerView;
    TimelineActivity activity;
    Ringtone r;

    public EventAdapter(@NonNull FirebaseRecyclerOptions<Event> options, TimelineActivity activity) {
        super(options);
        this.activity = activity;
    }

    @Override
    protected void onBindViewHolder(@NonNull EventHolder holder, int position, @NonNull Event model) {
        holder.bind(model);
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_event, parent, false);
        return new EventHolder(view);
    }

    public class EventHolder extends RecyclerView.ViewHolder {

        TextView titleText;
        TextView detailText;
        TextView timeText;
        ImageView avatarImage;

        public EventHolder(View itemView) {
            super(itemView);

            titleText = itemView.findViewById(R.id.titleText);
            detailText = itemView.findViewById(R.id.detailText);
            timeText = itemView.findViewById(R.id.timeText);
            avatarImage = itemView.findViewById(R.id.avatarImage);
        }

        void bind(Event model) {
            String title;
            String detail;
            int iconBg;
            int icon;
            boolean isImportant;


            if (model.getTemp() >= 39) {

                isImportant = true;
                title = "Dangerous range. Take caution \uD83E\uDD2F";
                iconBg = R.drawable.circle_red;
                icon = R.drawable.ic_alert;

                //playAlarm()
            } else if (model.getTemp() >= 38) {

                isImportant = true;
                title = "Temperature is a bit higher than usual \uD83E\uDD12";
                iconBg = R.drawable.circle_yellow;
                icon = R.drawable.ic_warn;

            } else {
                isImportant = true;
                title = "Nothing to worry about \uD83D\uDC76";
                iconBg = R.drawable.circle_green;
                icon = R.drawable.ic_fine;
            }


            detail = ((double)Math.round(model.getTemp()*100) / 100) + "°C";
            titleText.setText(title);
            detailText.setText(detail);
            avatarImage.setBackgroundResource(iconBg);
            avatarImage.setImageResource(icon);
            avatarImage.setVisibility(isImportant ? View.VISIBLE : View.INVISIBLE);
            detailText.setVisibility(isImportant ? View.VISIBLE : View.GONE);
            timeText.setText(DateUtils.formatDateTime(activity, model.getTimestamp(), DateUtils.FORMAT_SHOW_DATE |
                    DateUtils.FORMAT_SHOW_YEAR | DateUtils.FORMAT_SHOW_WEEKDAY | DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_ABBREV_ALL));
            //timeText.setText(DateUtils.getRelativeTimeSpanString(model.getTimestamp()));
        }
    }

    void playAlarm() {
        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM);
            if (r == null) {
                r = RingtoneManager.getRingtone(activity.getApplication(), notification);
            }
            if (!r.isPlaying())
                r.play();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
