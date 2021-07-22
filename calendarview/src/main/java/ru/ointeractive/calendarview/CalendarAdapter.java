	package ru.ointeractive.calendarview;
	
	import android.content.Context;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.BaseAdapter;
	import android.widget.TextView;
	
	import upl.core.Calendar;
	import upl.core.Int;
	
	public class CalendarAdapter extends BaseAdapter {
		
		private CalendarView mCalendar;
		private int mLayout;
		
		public CalendarView.OnClickListener mSelectedListener;
		
		public CalendarAdapter (CalendarView calendar, int layout) {
			
			mCalendar = calendar;
			mLayout = layout;
			
		}
		
		@Override
		public View getView (int position, View convertView, ViewGroup parent) {
			
			View grid;
			
			if (convertView == null) {
				
				LayoutInflater inflater = (LayoutInflater) mCalendar.getContext ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
				grid = inflater.inflate (mLayout, parent, false);
				
			} else grid = convertView;
			
			TextView textView = grid.findViewById (R.id.day);
			
			Calendar calendar = mCalendar.mDates.get (position);
			
			mCalendar.setText (calendar, textView);
			
			return grid;
			
		}
		
		@Override
		public int getCount () {
			return Int.size (mCalendar.mDates);
		}
		
		@Override
		public Object getItem (int position) {
			return mCalendar.mDates.get (position);
		}
		
		@Override
		public long getItemId (int position) {
			return position;
		}
		
	}