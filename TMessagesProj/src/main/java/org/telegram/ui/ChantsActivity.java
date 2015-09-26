package org.telegram.ui;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import org.telegram.data.chants.Chant;
import org.telegram.data.chants.ChantsService;
import org.telegram.messenger.LocaleController;
import org.telegram.messenger.R;
import org.telegram.ui.ActionBar.ActionBar;
import org.telegram.ui.ActionBar.BaseFragment;
import org.telegram.ui.Components.LayoutHelper;

import java.util.List;

public class ChantsActivity extends BaseFragment {

    private TextView emptyTextView;
    private ListView listView;
    private ArrayAdapter<Chant> listViewAdapter;
    private static MediaPlayer mediaPlayer;

    private static Chant currentlyPlayingChant;

    @Override
    public View createView(Context context) {
        actionBar.setBackButtonImage(R.drawable.ic_ab_back);
        actionBar.setAllowOverlayTitle(true);
        actionBar.setTitle(LocaleController.getString("Chants", R.string.Chants));

        actionBar.setActionBarMenuOnItemClick(new ActionBar.ActionBarMenuOnItemClick() {
            @Override
            public void onItemClick(int id) {
                if (id == -1) {
                    finishFragment();
                }
            }
        });

        listViewAdapter = new ArrayAdapter<Chant>(context, R.layout.list_item_chant, R.id.chant_title) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                View v =  super.getView(position, convertView, parent);
                ImageView playPause = (ImageView) v.findViewById(R.id.btn_play);
                if (getItem(position).equals(currentlyPlayingChant)) {
                    playPause.setImageResource(R.drawable.ic_action_pause);
                } else {
                    playPause.setImageResource(R.drawable.ic_action_play);
                }
                return v;
            }
        };

        new ChantsService(context).load(new ChantsService.ChantsCallback() {
            @Override
            public void onSuccess(List<Chant> botItems) {
                listViewAdapter.addAll(botItems);
                listViewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure() {

            }
        });

        fragmentView = new FrameLayout(context);

        LinearLayout emptyTextLayout = new LinearLayout(context);
        emptyTextLayout.setVisibility(View.INVISIBLE);
        emptyTextLayout.setOrientation(LinearLayout.VERTICAL);
        ((FrameLayout) fragmentView).addView(emptyTextLayout);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) emptyTextLayout.getLayoutParams();
        layoutParams.width = LayoutHelper.MATCH_PARENT;
        layoutParams.height = LayoutHelper.MATCH_PARENT;
        layoutParams.gravity = Gravity.TOP;
        emptyTextLayout.setLayoutParams(layoutParams);
        emptyTextLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        emptyTextView = new TextView(context);
        emptyTextView.setTextColor(0xff808080);
        emptyTextView.setTextSize(TypedValue.COMPLEX_UNIT_DIP, 20);
        emptyTextView.setGravity(Gravity.CENTER);
        emptyTextView.setText(LocaleController.getString("NoChants", R.string.NoChants));
        emptyTextLayout.addView(emptyTextView);
        LinearLayout.LayoutParams layoutParams1 = (LinearLayout.LayoutParams) emptyTextView.getLayoutParams();
        layoutParams1.width = LayoutHelper.MATCH_PARENT;
        layoutParams1.height = LayoutHelper.MATCH_PARENT;
        layoutParams1.weight = 0.5f;
        emptyTextView.setLayoutParams(layoutParams1);

        FrameLayout frameLayout = new FrameLayout(context);
        emptyTextLayout.addView(frameLayout);
        layoutParams1 = (LinearLayout.LayoutParams) frameLayout.getLayoutParams();
        layoutParams1.width = LayoutHelper.MATCH_PARENT;
        layoutParams1.height = LayoutHelper.MATCH_PARENT;
        layoutParams1.weight = 0.5f;
        frameLayout.setLayoutParams(layoutParams1);

        listView = new ListView(context);

        listView.setEmptyView(emptyTextLayout);
        listView.setVerticalScrollBarEnabled(false);
        listView.setDivider(null);
        listView.setDividerHeight(0);
        listView.setFastScrollEnabled(true);
        listView.setScrollBarStyle(View.SCROLLBARS_OUTSIDE_OVERLAY);
        listView.setAdapter(listViewAdapter);
        if (Build.VERSION.SDK_INT >= 11) {
            listView.setVerticalScrollbarPosition(LocaleController.isRTL ?
                                                  ListView.SCROLLBAR_POSITION_LEFT :
                                                  ListView.SCROLLBAR_POSITION_RIGHT);
        }
        ((FrameLayout) fragmentView).addView(listView);
        layoutParams = (FrameLayout.LayoutParams) listView.getLayoutParams();
        layoutParams.width = LayoutHelper.MATCH_PARENT;
        layoutParams.height = LayoutHelper.MATCH_PARENT;
        listView.setLayoutParams(layoutParams);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long itemId) {
                Chant chant = (Chant) adapterView.getItemAtPosition(pos);
                if (!TextUtils.isEmpty(chant.url)) {
                    final MediaPlayer player = getPlayer();
                    if (chant.equals(currentlyPlayingChant)) {
                        player.stop();
                        player.reset();
                        setCurrentChant(null);
                    } else {
                        try {
                            player.setDataSource(chant.url);
                            player.prepareAsync();
                            player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                                @Override
                                public void onPrepared(MediaPlayer mp) {
                                    mp.start();
                                }
                            });
                            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                @Override
                                public void onCompletion(MediaPlayer mp) {
                                    player.reset();
                                    setCurrentChant(null);
                                }
                            });
                            setCurrentChant(chant);
                        } catch (Exception ignored) {
                        }
                    }
                }
            }
        });

        return fragmentView;
    }

    private void setCurrentChant(Chant chant) {
        currentlyPlayingChant = chant;
        listViewAdapter.notifyDataSetChanged();
    }

    @NonNull
    private MediaPlayer getPlayer() {
        if (mediaPlayer == null) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }
        return mediaPlayer;
    }

}
