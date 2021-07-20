	package ru.ointeractive.calendar;
	
	import android.support.annotation.NonNull;
	import android.support.v7.widget.RecyclerView;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	
	import ru.ointeractive.andromeda.graphic.Graphic;
	import ru.ointeractive.jabadaba.Arrays;
	import ru.ointeractive.jabadaba.Calendar;
	import ru.ointeractive.jabadaba.Int;
	
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
			
			boolean isCurrent = (mCalendar.currentDay != null && calendar.isSameDay (mCalendar.currentDay));
			boolean isMarked = Arrays.contains (calendar, mCalendar.mMarkedDates);
			
			if (holder.nameText != null)
				holder.nameText.setText (mCalendar.getCurrentWeek (calendar));
			
			if (holder.dayText != null) {
				
				holder.dayText.setText (mCalendar.getCurrentDay (calendar));
				
				if (isCurrent)
					holder.dayText.setBackgroundDrawable (Graphic.toDrawable (mCalendar.getContext (), R.drawable.calendar_selected));
				
				if (isMarked)
					holder.dayText.setTextColor (mCalendar.mMarkedTextColor);
				else if (isCurrent)
					holder.dayText.setTextColor (mCalendar.getContext ().getResources ().getColor (mCalendar.mSelectedTextColor));
				
			}
			
			holder.layout.setOnClickListener (new View.OnClickListener () {
				
				@Override
				public void onClick (View view) {
					mSelectedListener.onDayClick (view, calendar, isCurrent, isMarked);
				}
				
			});
			
		}
		
		@Override
		public int getItemCount () {
			return Int.size (mCalendar.mDates);
		}
		
	}
