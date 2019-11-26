package com.btp.smartvest;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class TimelineActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    EventAdapter eventAdapter;

    static boolean locked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        recyclerView = findViewById(R.id.recyclerView);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setStackFromEnd(true);
        llm.setReverseLayout(true);
        recyclerView.setLayoutManager(llm);

        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("logs")
                .orderByChild("timestamp")
                .limitToLast(50);

        FirebaseRecyclerOptions<Event> options =
                new FirebaseRecyclerOptions.Builder<Event>()
                        .setQuery(query, Event.class)
                        .build();

        eventAdapter = new EventAdapter(options, this);

        eventAdapter.registerAdapterDataObserver(new RecyclerView.AdapterDataObserver() {
            @Override
            public void onItemRangeInserted(int positionStart, int itemCount) {
                super.onItemRangeInserted(positionStart, itemCount);
                int friendlyMessageCount = eventAdapter.getItemCount();
                int lastVisiblePosition = ((LinearLayoutManager) recyclerView.getLayoutManager())
                        .findLastCompletelyVisibleItemPosition();

                // If the recycler view is initially being loaded or the user is at the bottom of the list, scroll
                // to the bottom of the list to show the newly added message.
                if (lastVisiblePosition == -1 ||
                        (positionStart >= (friendlyMessageCount - 1) && lastVisiblePosition == (positionStart - 1))) {
                    recyclerView.scrollToPosition(positionStart);
                }
            }
        });

        recyclerView.setAdapter(eventAdapter);


    }

    @Override
    protected void onStop() {
        super.onStop();
        eventAdapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        eventAdapter.startListening();
    }

}
