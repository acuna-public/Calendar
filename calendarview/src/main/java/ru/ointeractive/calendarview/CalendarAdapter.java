	package ru.ointeractive.calendarview;
	
	import android.content.Context;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.BaseAdapter;
	import android.widget.TextView;
	
	import upl.core.Calendar;
	
	public class CalendarAdapter extends BaseAdapter {
		
		private final CalendarView calendarView;
		private final int mLayout;
		
		public CalendarView.OnDayClickListener mSelectedListener;
		
		public CalendarAdapter (CalendarView calendarView, int layout) {
			
			this.calendarView = calendarView;
			mLayout = layout;
			
		}
		
		@Override
		public View getView (int position, View convertView, ViewGroup parent) {
			
			View grid;
			
			//if (convertView == null) {
				
				LayoutInflater inflater = (LayoutInflater) calendarView.getContext ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
				grid = inflater.inflate (mLayout, parent, false);
				
			//} else grid = convertView;
			
			TextView textView = grid.findViewById (R.id.day);
			
			Calendar calendar = calendarView.mDates.get (position);
			boolean isCurrent = (calendarView.currentDay == position);
			
			calendarView.setText (calendar, textView);
			
			View layout = grid.findViewById (R.id.layout);
			
			layout.setOnClickListener (new View.OnClickListener () {
				
				@Override
				public void onClick (View view) {
					
					if (mSelectedListener != null)
						mSelectedListener.onDayClick (grid, calendarView, position, isCurrent);
					
				}
				
			});
			
			return grid;
			
		}
		
		@Override
		public int getCount () {
			return calendarView.mDates.length ();
		}
		
		@Override
		public Object getItem (int position) {
			return calendarView.mDates.get (position);
		}
		
		@Override
		public long getItemId (int position) {
			return position;
		}
		
	}