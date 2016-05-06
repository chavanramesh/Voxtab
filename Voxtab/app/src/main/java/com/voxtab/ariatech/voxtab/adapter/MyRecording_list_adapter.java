package com.voxtab.ariatech.voxtab.adapter;

import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.view.LayoutInflater;





import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.voxtab.ariatech.voxtab.MyRecordingsActivity;
import com.voxtab.ariatech.voxtab.R;
import com.voxtab.ariatech.voxtab.bean.File_status_type;
import com.voxtab.ariatech.voxtab.bean.MyRecording;
import com.voxtab.ariatech.voxtab.database.DatabaseHandlerNew;
import com.voxtab.ariatech.voxtab.globaldata.GlobalData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Local User on 30-Jan-16.
 */
public class MyRecording_list_adapter extends BaseAdapter {


    private Context context;
    private ArrayList<MyRecording> rowItems;
    public ViewHolder holder = null;
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public ViewHolder holderLastSelected = null;

    boolean isActiveFlag = false;
    public SeekBar mSeekBarPlayer;
    private boolean running = true;
    ViewHolder selecetdHolder = null;
    Timer timer = new Timer();
    private TimerTask task;
    private int duration = 0;
    boolean pause = false;
    int progresss = 0;
    int selectedIndex = -1;

    public MyRecording_list_adapter(Context context, ArrayList<MyRecording> rowItems) {
        this.context = context;
        this.rowItems = rowItems;
    }


    @Override
    public int getCount() {
        return MyRecordingsActivity.recordings.size();
    }

    @Override
    public Object getItem(int position) {
        return MyRecordingsActivity.recordings.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
//        convertView = null;


        try {


            if (convertView == null) {
                final LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                holder = new ViewHolder();

                convertView = mInflater.inflate(R.layout.custom_list_recordings_new, null);

                holder.lin_progressbar = (LinearLayout) convertView.findViewById(R.id.lin_seekbar);

                holder.lin_text = (LinearLayout) convertView.findViewById(R.id.lin_text);
                holder.order_text = (LinearLayout) convertView.findViewById(R.id.order_text);


                holder.onclicklin = (LinearLayout) convertView.findViewById(R.id.onclicklin);
                holder.txt_filename = (TextView) convertView.findViewById(R.id.txt_filename);
                holder.txt_date_time = (TextView) convertView.findViewById(R.id.txt_date_time);
                holder.txt_file_status = (TextView) convertView.findViewById(R.id.txt_file_status);
                holder.txt_file_time = (TextView) convertView.findViewById(R.id.txt_file_time);
                holder.img_select = (ImageView) convertView.findViewById(R.id.img_selection_icon);
                holder.img_play_record = (ImageView) convertView.findViewById(R.id.img_play_record);
                holder.progress_bar = (SeekBar) convertView.findViewById(R.id.progress_file);
                holder.textVIewInit = (TextView) convertView.findViewById(R.id.textViewInit);
                holder.textVIewEnd = (TextView) convertView.findViewById(R.id.textViewEnd);



            }else {
                holder = (ViewHolder) convertView.getTag();
            }

            final MyRecording bean = MyRecordingsActivity.recordings.get(position);

            holder.lin_progressbar.setTag(position);
            holder.txt_filename.setTag(position);
            holder.txt_date_time.setTag(position);
            holder.txt_file_status.setTag(position);
            holder.txt_file_time.setTag(position);
            holder.img_select.setTag(position);
            holder.img_play_record.setTag(holder);
            holder.progress_bar.setTag(position);
            holder.textVIewInit.setTag(position);
            holder.textVIewEnd.setTag(position);
            holder.onclicklin.setTag(""+position);
            holder.lin_text.setTag(""+position);

//            holder.onclicklin.setTag(holder);
//            holder.lin_text.setTag(holder);
            holder.order_text.setTag(holder);
            holder.img_play_record.setImageResource(R.drawable.play_icon_image);


         // Set playing option layout
            if(bean.isActionFlag()) {
                holder.lin_progressbar.setVisibility(View.VISIBLE);
            }else {
                holder.lin_progressbar.setVisibility(View.GONE);
            }

            holder.txt_filename.setText("");
            holder.txt_date_time.setText("");
            holder.txt_file_time.setText("");


              try {
                  holder.txt_filename.setText(bean.getRecName());
                  holder.txt_date_time.setText(bean.getCreatedDate());
                  holder.txt_file_time.setText(bean.getRecDuration());
                  holder.textVIewEnd.setText(bean.getRecDuration());
              }catch (Exception e) {
              GlobalData.printError(e);
              }




                if (bean.isSelectFlag()) {
                    holder.img_select.setImageResource(R.drawable.filled_tick);
                } else {
                    holder.img_select.setImageResource(R.drawable.unfilled);
                }


                holder.lin_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int position=-1;
                        try {
                            position = Integer.parseInt(v.getTag().toString());
                        }catch (Exception e){
                            GlobalData.printError(e);
                        }


                        if(position==-1){
                            return;
                        }
                        selectedIndex = -1;

                        removeAllActionFlag();

                        MyRecordingsActivity.recordings.get(position).setActionFlag(true);

                        try {
                            if (mediaPlayer != null) {
                                mediaPlayer.stop();
                                mediaPlayer.release();
                            }
                        }catch (Exception e){
                            GlobalData.printError(e);
                        }

                        try {
                        notifyDataSetChanged();


                        selectedIndex = position;
                        }catch (Exception e){
                            GlobalData.printError(e);
                        }


//                        if (bean1.isClickFlag()) {
//                            viewHoldertemp.lin_progressbar.setVisibility(View.VISIBLE);
//                            MyRecordingsActivity.recordings.get(position).setClickFlag(false);
//                        } else {
//                            stopAudio(viewHoldertemp.progress_bar, holderLastSelected);
//                            MyRecordingsActivity.recordings.get(position).setActionFlag(false);
//                            removeAllActionFlag();
//
//                            viewHoldertemp.lin_progressbar.setVisibility(View.GONE);
//                            viewHoldertemp.img_play_record.setImageResource(R.drawable.play_icon_image);
//                            MyRecordingsActivity.recordings.get(position).setClickFlag(true);
//                        }
//                        MyRecordingsActivity.lst_recordings.refreshDrawableState();

                    }
                });

//                holder.lin_text.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        ViewHolder viewHoldertemp = (ViewHolder) v.getTag();
//
//                        MyRecording bean1 = MyRecordingsActivity.recordings.get(position);
//                        if (bean1.isClickFlag()) {
//                            viewHoldertemp.lin_progressbar.setVisibility(View.VISIBLE);
//                            MyRecordingsActivity.recordings.get(position).setClickFlag(false);
//                        } else {
//
//                            stopAudio(viewHoldertemp.progress_bar, holderLastSelected);
//                            MyRecordingsActivity.recordings.get(position).setActionFlag(false);
//                            removeAllActionFlag();
//                            if (mediaPlayer != null) {
//                                mediaPlayer.stop();
//                                mediaPlayer.release();
//                            }
//                            viewHoldertemp.lin_progressbar.setVisibility(View.GONE);
//                            viewHoldertemp.img_play_record.setImageResource(R.drawable.play_icon_image);
//                            MyRecordingsActivity.recordings.get(position).setClickFlag(true);
//
//                        }
//                        MyRecordingsActivity.lst_recordings.refreshDrawableState();
//                        //   notifyDataSetChanged();
//                    }
//                });

                holder.order_text.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        try {

                ViewHolder viewHoldertemp = (ViewHolder) v.getTag();

                        MyRecording bean1 = MyRecordingsActivity.recordings.get(position);
                        if (bean1.isClickFlag()) {
                            viewHoldertemp.lin_progressbar.setVisibility(View.VISIBLE);
                            MyRecordingsActivity.recordings.get(position).setClickFlag(false);
                        } else {
                            stopAudio(viewHoldertemp.progress_bar, holderLastSelected);
                            MyRecordingsActivity.recordings.get(position).setActionFlag(false);
                            removeAllActionFlag();
                            try {
                                if (mediaPlayer != null) {
                                    mediaPlayer.stop();
                                    mediaPlayer.release();
                                }
                            }catch (Exception e){
                                GlobalData.printError(e);
                            }

                            viewHoldertemp.lin_progressbar.setVisibility(View.GONE);
                            viewHoldertemp.img_play_record.setImageResource(R.drawable.play_icon_image);
                            MyRecordingsActivity.recordings.get(position).setClickFlag(true);
                        }
                        MyRecordingsActivity.lst_recordings.refreshDrawableState();
                        //   notifyDataSetChanged();

                        }catch (Exception e){
                            GlobalData.printError(e);
                        }


                    }
                });

                holder.img_play_record.setOnClickListener(new View.OnClickListener() {
                                                              @Override
                                                              public void onClick(View v) {
                                                                  int postemp = 0;
                                                                  try {





                                                                      ViewHolder viewHoldertemp = (ViewHolder) v.getTag();
                                                                      SeekBar seekBartemp = (SeekBar) viewHoldertemp.progress_bar;
                                                                      postemp = Integer.parseInt(seekBartemp.getTag().toString());


                                                                      if(mediaPlayer!=null) {

                                                                          try {
                                                                              if (mediaPlayer.isPlaying()) {
                                                                                  stopAudio(seekBartemp, viewHoldertemp);
                                                                              } else {
                                                                                  playAudio(viewHoldertemp, postemp);
                                                                              }
                                                                          }catch (IllegalStateException il){
                                                                              playAudio(viewHoldertemp, postemp);
                                                                          }

                                                                          catch (Exception e){
                                                                              GlobalData.printError(e);
                                                                          }


                                                                      }else {
                                                                          playAudio(viewHoldertemp, postemp);
                                                                      }
//                                                                      MyRecording bean1 = MyRecordingsActivity.recordings.get(postemp);
//
////                                                                      if (bean1.isActionFlag()) {
//                                                                          MyRecordingsActivity.recordings.get(postemp).setActionFlag(false);
//                                                                          viewHoldertemp.lin_progressbar.setVisibility(View.VISIBLE);
//                                                                          removeAllActionFlag();
//
//                                                                          try {
//
//                                                                              viewHoldertemp.img_play_record.setImageResource(R.drawable.play_icon_image);
//                                                                              MyRecordingsActivity.lst_recordings.refreshDrawableState();
//                                                                              stopAudio(viewHoldertemp.progress_bar, holderLastSelected);
//                                                                              mediaPlayer.stop();
//                                                                              mediaPlayer.release();
//
//                                                                          } catch (Exception e) {
//                                                                              GlobalData.printError(e);
//                                                                          }

//                                                                      holderLastSelected = viewHoldertemp;
                                                                          return;
//                                                                      } else {
//                                                                          isActiveFlag = false;
//                                                                          removeAllActionFlag();
//                                                                          MyRecordingsActivity.recordings.get(postemp).setActionFlag(true);
//                                                                          viewHoldertemp.lin_progressbar.setVisibility(View.VISIBLE);
//                                                                      }


//                                                                      try {
//
//
//                                                                          if (holderLastSelected != null) {
//                                                                              holderLastSelected.lin_progressbar.setVisibility(View.VISIBLE);
//                                                                              holderLastSelected.img_play_record.setImageResource(R.drawable.play_icon_image);
//                                                                              MyRecordingsActivity.lst_recordings.refreshDrawableState();
//                                                                              stopAudio(holderLastSelected.progress_bar, holderLastSelected);
//                                                                          }
//
//                                                                      } catch (Exception e) {
//                                                                          GlobalData.printError(e);
//                                                                      }




//                                                           viewHoldertemp = (ViewHolder) getViewByPosition(postemp).getTag();
//                                                           viewHoldertemp.textVIewInit.setText("we");
//                                                           viewHoldertemp.textVIewEnd.setText("we");

                                                                      // MyRecordingsActivity.lst_recordings.invalidateViews();
                                                                      //View view=MyRecordingsActivity.lst_recordings.getChildAt(postemp);


                                                                      //   notifyDataSetChanged();

                                                                      //View view= updateItemAtPosition(0);





//                                                      MyRecordingsActivity.lst_recordings.invalidateViews();


                                                                  } catch (Exception e) {
                                                                      GlobalData.printError(e);
                                                                  }


                                                              }
                                                          }

                );

                holder.img_select.setOnClickListener(new View.OnClickListener()

                                                     {
                                                         @Override
                                                         public void onClick(View v) {

                                                             try {
                                                                 int pos = Integer.parseInt(v.getTag().toString());
                                                                 MyRecording bean = MyRecordingsActivity.recordings.get(pos);
                                                                 if (bean.isSelectFlag()) {
                                                                     MyRecordingsActivity.recordings.get(pos).setSelectFlag(false);
//                                                                     holder.img_select.setImageResource(R.drawable.unfilled);

                                                                 } else {
                                                                     bean.setSelectFlag(true);

//                                                                     int count = getSelectedRecordingCount();
//                                                                     MyRecordingsActivity.txt_number_selected_files.setText(count + "");
//                                                                     notifyDataSetChanged();
                                                                 }
                                                                 MyRecordingsActivity.lin_actions.setVisibility(View.VISIBLE);

                                                                 MyRecordingsActivity. img_share.setVisibility(View.VISIBLE);
                                                                 MyRecordingsActivity. img_delete.setVisibility(View.VISIBLE);
                                                                 MyRecordingsActivity. img_edit.setVisibility(View.VISIBLE);


                                                                 MyRecordingsActivity.img_banner.setVisibility(View.GONE);


                                                                 int count = getSelectedRecordingCount();

                                                                 //top bar ddisable when count 0
                                                                 if (count > 0) {

                                                                     MyRecordingsActivity.txt_number_selected_files.setText(count + "");
                                                                     notifyDataSetChanged();

                                                                 } else {
                                                                     MyRecordingsActivity.lin_actions.setVisibility(View.GONE);
                                                                     MyRecordingsActivity.img_banner.setVisibility(View.VISIBLE);


                                                                 }

                                                             } catch (Exception e) {
                                                                 GlobalData.printError(e);
                                                             }


                                                         }
                                                     }

                );


            holder.txt_file_status.setText("");

                 if(bean.getUpMasterId().length()>0) {

                     int value = GlobalData.getFileStatusColor(bean.getUpMasterId());
                     if (value != 0) {



                         holder.txt_file_status.setBackgroundColor(context.getResources().getColor(value,null));
                     }
                     DatabaseHandlerNew db=new DatabaseHandlerNew(context);
                     try {
                         db.open();

                         File_status_type type=db.getFile_status_type(bean.getUpMasterId());


                         if(type.getFile_status_msg().length()>0)
                         holder.txt_file_status.setText(type.getFile_status_msg());
                     }catch (Exception e){
                         GlobalData.printError(e);
                     }finally {
                         db.close();;
                     }




                 }

            holder.txt_file_status.setVisibility(View.GONE);
            if(holder.txt_file_status.getText().length()>0){
                holder.txt_file_status.setVisibility(View.VISIBLE);
            }







                if (getSelectedRecordingCount() == 1) {
                    MyRecordingsActivity.img_info.setVisibility(View.VISIBLE);
                    MyRecordingsActivity.img_edit.setVisibility(View.VISIBLE);
                } else {
                    MyRecordingsActivity.img_info.setVisibility(View.GONE);
                    MyRecordingsActivity.img_edit.setVisibility(View.GONE);

                }

                convertView.setTag(holder);


        } catch (Exception e)

        {
            GlobalData.printError(e);
        }

        try {

            if(selectedIndex==position){
                holderLastSelected = holder;
//                playAudio(holder,selectedIndex);
                try {



                    if(mediaPlayer.isPlaying()){
                        holder.img_play_record.setImageResource(R.drawable.stop_icon);
                    }else {
                        holder.img_play_record.setImageResource(R.drawable.play_icon_image);
                    }

                }catch (IllegalStateException il){
                    holder.img_play_record.setImageResource(R.drawable.play_icon_image);
                }

                catch (Exception e){
                    GlobalData.printError(e);
                }

            }

        }catch (Exception e){
            GlobalData.printError(e);

        }


        return convertView;
    }




    public void removeAllActionFlag() {

        try {

            for (int i = 0; i < MyRecordingsActivity.recordings.size(); i++) {

                try {
                    MyRecordingsActivity.recordings.get(i).setActionFlag(false);
//                    ((ViewHolder) getViewByPosition(i).getTag()).lin_progressbar.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    GlobalData.printError(e);
                }


            }

            GlobalData.longpressFlag = false;


            try {


//                notifyDataSetChanged();
//                MyRecordingsActivity.lst_recordings.setAdapter( MyRecordingsActivity. lst_recordings.getAdapter());
            } catch (Exception e) {
                GlobalData.printError(e);
            }
        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }


    public void removeAllOpenFlag() {

        try {

            for (int i = 0; i < MyRecordingsActivity.recordings.size(); i++) {

                try {
                    MyRecordingsActivity.recordings.get(i).setOpenLayout(false);
//                    ((ViewHolder) getViewByPosition(i).getTag()).lin_progressbar.setVisibility(View.VISIBLE);
                } catch (Exception e) {
                    GlobalData.printError(e);
                }


            }




            try {


//                notifyDataSetChanged();
//                MyRecordingsActivity.lst_recordings.setAdapter( MyRecordingsActivity. lst_recordings.getAdapter());
            } catch (Exception e) {
                GlobalData.printError(e);
            }
        } catch (Exception e) {
            GlobalData.printError(e);
        }
    }

    public static class ViewHolder {

        TextView txt_filename, txt_date_time, txt_file_status, txt_file_time;
        ImageView img_select, img_play_record;
        SeekBar progress_bar;
        LinearLayout lin_progressbar, onclicklin, lin_text, order_text;
        TextView textVIewInit;
        TextView textVIewEnd;


    }

    public void playAudio(ViewHolder viewHolder, int position) {

        try {






            int pos = position;

//             //  ((ViewHolder)  getViewByPosition(position).getTag()).lin_progressbar.setVisibility(View.GONE);
//
//            viewHolder.textVIewInit.setText("we");
//
//            return;


            SeekBar seekBar = new SeekBar(context);

            TextView textViewEnd;
            TextView textViewstart;
            //  ViewHolder viewHolder=new ViewHolder();
            try {


                // viewHolder = (ViewHolder) view.getTag();
//                viewHolder =this.holderSelected;
                seekBar = (SeekBar) viewHolder.progress_bar;
                textViewEnd = (TextView) viewHolder.textVIewEnd;
                textViewstart = (TextView) viewHolder.textVIewInit;

                holderLastSelected.textVIewEnd.setVisibility(View
                        .VISIBLE);


                textViewstart.setVisibility(View.VISIBLE);
                textViewEnd.setVisibility(View.VISIBLE);


                pos = Integer.parseInt(seekBar.getTag().toString());

            } catch (Exception e) {
                GlobalData.printError(e);
            }


            MyRecording bean = MyRecordingsActivity.recordings.get(pos);


            if (!bean.isActionFlag()) {
                // MyRecordingsActivity.recordings.get(pos).setActionFlag(false);
                viewHolder.img_play_record.setImageResource(R.drawable.play_icon_image);
                viewHolder.lin_progressbar.setVisibility(View.VISIBLE);
                MyRecordingsActivity.lst_recordings.refreshDrawableState();
                stopAudio(seekBar, viewHolder);

            } else {


                holderLastSelected = viewHolder;
                //  removeAllActionFlag();
                // MyRecordingsActivity.recordings.get(pos).setActionFlag(true);
                viewHolder.img_play_record.setImageResource(R.drawable.stop_icon);
                viewHolder.lin_progressbar.setVisibility(View.VISIBLE);
                MyRecordingsActivity.lst_recordings.refreshDrawableState();

                //selected Seekbar
                mSeekBarPlayer = seekBar;

                mSeekBarPlayer.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                    @Override
                    public void onStopTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onStartTrackingTouch(SeekBar seekBar) {
                    }

                    @Override
                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                        try {
                            if (fromUser) {
                                mSeekBarPlayer = seekBar;
                                mediaPlayer.seekTo(progress);
                                // updateTime();
                            }
                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }
                    }
                });


                playAudio(MyRecordingsActivity.recordings.get(pos).getRecLocalPath(), seekBar, viewHolder, position);
                MyRecordingsActivity.lst_recordings.setSelection(position);
            }


        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return;
    }

    public void playAudio(String path, final SeekBar mBar, final ViewHolder holder, final int position) {

        try {

//            this.holderSelected= holder;

            try {

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

            } catch (Exception e) {
                GlobalData.printError(e);
            } finally {

                mediaPlayer = new MediaPlayer();
            }


            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(path);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mediaPlayer.prepareAsync();


            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                public void onCompletion(MediaPlayer mp) {
// TODO Auto-generated method stub
//your code if the file was completely played either show an alert to user or start another activity or file.
//even you can finish you activity here

                    try {
                        if (holderLastSelected != null) {
                            holderLastSelected.lin_progressbar.setVisibility(View.VISIBLE);
                            holderLastSelected.img_play_record.setImageResource(R.drawable.play_icon_image);
                            MyRecordingsActivity.recordings.get(position).setActionFlag(false);
                            removeAllActionFlag();
                            MyRecordingsActivity.lst_recordings.refreshDrawableState();
                            stopAudio(holderLastSelected.progress_bar, holderLastSelected);
                        }
                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }
                }
            });

            // You can show progress dialog here untill it prepared to play
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(final MediaPlayer mp) {
                    // Now dismis progress dialog, Media palyer will start playing


                    mp.start();
                    try {

                        holder.textVIewEnd.setText("" + GlobalData.convertSecondsToHMmSs(mp.getDuration()));
                        holder.textVIewEnd.setVisibility(View.VISIBLE);
                        holder.textVIewInit.setVisibility(View.VISIBLE);

                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }

                    duration = mediaPlayer.getDuration();
                    mSeekBarPlayer.setMax(duration);

                    mSeekBarPlayer.setProgress(mediaPlayer.getCurrentPosition());

//                    selecetdHolder = holder;

                    mSeekBarPlayer.postDelayed(onEverySecond, 1000);

//                    notifyDataSetChanged();

                    try {
                        //holder.progress_bar.setMax(mp.getDuration());

                        int duration = mp.getDuration();
                        int period = duration / 1000;
                        task = new TimerTask() {

                            @Override
                            public void run() {
                                mBar.post(new Runnable() {
                                    @Override
                                    public void run() {
                                        if (!pause) {

                                            try {
                                                progresss++;
                                                // mBar.setProgress(progresss);
                                                holder.textVIewInit.setText("" + GlobalData.convertSecondsToHMmSs(mp.getCurrentPosition()));
                                                //  holder.progress_bar.setMax(mp.getCurrentPosition());
                                            } catch (Exception e) {
                                                GlobalData.printError(e);
                                            }

                                        }
                                    }
                                });
                            }
                        };
                        timer = new Timer();
                        timer.schedule(task, 0, period );

                    } catch (Exception e) {
                        GlobalData.printError(e);
                    }

                }
            });
            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });


        } catch (Exception e) {
            GlobalData.printError(e);
        }


    }

    private Runnable onEverySecond = new Runnable() {
        @Override
        public void run() {

            try {

                if (true == running) {
                    if (mSeekBarPlayer != null) {
                        mSeekBarPlayer.setProgress(mediaPlayer.getCurrentPosition());
                    }

                    if (mediaPlayer.isPlaying()) {
                        try {

                            if (selecetdHolder != null) {
                                selecetdHolder.textVIewInit.setText("" + GlobalData.convertSecondsToHMmSs(mediaPlayer.getCurrentPosition()));
                            }
                        } catch (Exception e) {
                            GlobalData.printError(e);
                        }
                        mSeekBarPlayer.postDelayed(onEverySecond, 1000);
                        // updateTime();
                    }
                }


            } catch (Exception e) {
                GlobalData.printError(e);
            }
        }
    };

    public void stopAudio(SeekBar seekBar, ViewHolder holder) {

        try {

            if (mediaPlayer != null) {
                mediaPlayer.stop();

                try {

                    mSeekBarPlayer.removeCallbacks(onEverySecond);
                    mSeekBarPlayer.setProgress(0);
                } catch (Exception e) {
                    GlobalData.printError(e);
                }

                mediaPlayer.release();
                mediaPlayer = null;

                try {
                    timer.cancel();
                    task.cancel();
                } catch (Exception e) {
                    // GlobalData.printError(e);
                }


            }

            holder.textVIewInit.setText("00:00:00");
            holder.textVIewEnd.setVisibility(View.VISIBLE);
            holder.textVIewInit.setVisibility(View.VISIBLE);
            holder.img_play_record.setImageResource(R.drawable.stop_icon);
        } catch (Exception e) {
            GlobalData.printError(e);
        }

       notifyDataSetChanged();
    }

    public int getSelectedRecordingCount() {
        int count = 0;
        try {

            for (int i = 0; i < MyRecordingsActivity.recordings.size(); i++) {
                if (MyRecordingsActivity.recordings.get(i).isSelectFlag()) {
                    count = count + 1;
                }
            }

        } catch (Exception e) {
            GlobalData.printError(e);
        }

        return count;

    }

    public View getViewByPosition(int pos) {
        final int firstListItemPosition = MyRecordingsActivity.lst_recordings.getFirstVisiblePosition();
        final int lastListItemPosition = firstListItemPosition + MyRecordingsActivity.lst_recordings.getChildCount() - 1;

        if (pos < firstListItemPosition || pos > lastListItemPosition) {
            //  ((ViewHolder) (MyRecordingsActivity.lst_recordings.getAdapter().getView(pos, null, MyRecordingsActivity.lst_recordings)).getTag()).lin_progressbar.setVisibility(View.GONE);
            return MyRecordingsActivity.lst_recordings.getAdapter().getView(pos, null, MyRecordingsActivity.lst_recordings);
        } else {
            final int childIndex = pos - firstListItemPosition;

            // ((ViewHolder)MyRecordingsActivity.lst_recordings.getChildAt(childIndex).getTag()).lin_progressbar.setVisibility(View.GONE);
            return MyRecordingsActivity.lst_recordings.getChildAt(childIndex);
        }
    }


}