	package ru.ointeractive.calendarview;
	
	import android.support.annotation.NonNull;
	import android.support.v7.widget.RecyclerView;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	
	import upl.core.Calendar;
	import upl.core.Int;
	
	public class HorizontalCalendarAdapter extends RecyclerView.Adapter<CalendarView.ViewHolder> {
		
		private CalendarView mCalendar;
		private int mLayout;
		
		public CalendarView.OnClickListener mSelectedListener;
		
		public HorizontalCalendarAdapter (CalendarView calendar, int layout) {
			
			mCalendar = calendar;
			mLayout = layout;
			
		}
		
		@NonNull
		@Override
		public CalendarView.ViewHolder onCreateViewHolder (@NonNull ViewGroup group, int i) {
			return new CalendarView.ViewHolder (LayoutInflater.from (mCalendar.getContext ()).inflate (mLayout, group, false));
		}
		
		@Override
		public void onBindViewHolder (@NonNull CalendarView.ViewHolder holder, int position) {
			
			Calendar calendar = mCalendar.mDates.get (position);
			
			boolean isCurrent = (mCalendar.currentDay != null && calendar.equals (mCalendar.currentDay));
			
			if (holder.nameText != null)
				holder.nameText.setText (mCalendar.getCurrentWeek (calendar));
			
			if (holder.dayText != null)
				mCalendar.setText (calendar, holder.dayText);
			
			holder.layout.setOnClickListener (new View.OnClickListener () {
				
				@Override
				public void onClick (View view) {
					mSelectedListener.onDayClick (view, calendar, isCurrent);
				}
				
			});
			
		}
		
		@Override
		public int getItemCount () {
			return Int.size (mCalendar.mDates);
		}
		
	}
