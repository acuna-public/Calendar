	package ru.ointeractive.calendarview;
	
	import android.content.Context;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.BaseAdapter;
	import android.widget.TextView;
	
	import upl.core.Int;
	
	public class CalendarDayAdapter extends BaseAdapter {
		
		private final CalendarView mCalendar;
		private final int mLayout;
		
		private final String[] mDays;
		
		public CalendarDayAdapter (CalendarView calendar, int layout) {
			
			mCalendar = calendar;
			mLayout = layout;
			
			mDays = new String[7];
			
			String[] days = mCalendar.getContext ().getResources ().getStringArray (R.array.day);
			System.arraycopy (days, 0, mDays, 0, 7);
			
		}
		
		@Override
		public View getView (int position, View convertView, ViewGroup parent) {
			
			View grid;
			
			if (convertView == null) {
				
				LayoutInflater inflater = (LayoutInflater) mCalendar.getContext ().getSystemService (Context.LAYOUT_INFLATER_SERVICE);
				grid = inflater.inflate (mLayout, parent, false);
				
			} else grid = convertView;
			
			TextView textView = grid.findViewById (R.id.name);
			
			textView.setText (mDays[position]);
			
			return grid;
			
		}
		
		@Override
		public int getCount () {
			return Int.size (mDays);
		}
		
		@Override
		public Object getItem (int position) {
			return mDays[position];
		}
		
		@Override
		public long getItemId (int position) {
			return position;
		}
		
	}
