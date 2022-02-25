	package ru.ointeractive.calendarview;
	
	import android.support.annotation.NonNull;
	import android.support.v7.widget.RecyclerView;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	
	import upl.core.Calendar;
	
	public class HorizontalCalendarAdapter extends RecyclerView.Adapter<ViewHolder> {
		
		private final CalendarView mCalendar;
		private final int mLayout;
		
		public CalendarView.OnDayClickListener mSelectedListener;
		
		public HorizontalCalendarAdapter (CalendarView calendar, int layout) {
			
			mCalendar = calendar;
			mLayout = layout;
			
		}
		
		@NonNull
		@Override
		public ViewHolder onCreateViewHolder (@NonNull ViewGroup group, int i) {
			return new ViewHolder (LayoutInflater.from (mCalendar.getContext ()).inflate (mLayout, group, false));
		}
		
		@Override
		public void onBindViewHolder (@NonNull ViewHolder holder, int position) {
			
			Calendar calendar = mCalendar.mDates.get (position);
			boolean isCurrent = (mCalendar.currentDay == position);
			
			if (holder.nameText != null)
				holder.nameText.setText (mCalendar.getCurrentWeek (calendar));
			
			if (holder.dayText != null)
				mCalendar.setText (calendar, holder.dayText);
			
			holder.layout.setOnClickListener (new View.OnClickListener () {
				
				@Override
				public void onClick (View view) {
					
					if (mSelectedListener != null)
						mSelectedListener.onDayClick (view, mCalendar, holder.getAdapterPosition (), isCurrent);
					
				}
				
			});
			
		}
		
		@Override
		public int getItemCount () {
			return mCalendar.mDates.length ();
		}
		
	}