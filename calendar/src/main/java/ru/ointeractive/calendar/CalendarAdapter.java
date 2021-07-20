	package ru.ointeractive.calendar;
	
	import android.content.Context;
	import android.view.LayoutInflater;
	import android.view.View;
	import android.view.ViewGroup;
	import android.widget.BaseAdapter;
	import android.widget.TextView;
	
	import ru.ointeractive.andromeda.graphic.Graphic;
	import ru.ointeractive.jabadaba.Calendar;
	import ru.ointeractive.jabadaba.Int;
	
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
			
			textView.setText (mCalendar.getCurrentDay (calendar));
			
			for (CalendarView.Selection sel : mCalendar.mSelections)
				for (Calendar cal : sel.getDates ())
					if (calendar.isSameDay (cal)) {
						
						if (sel.getColor () != null)
							textView.setTextColor (sel.getColor ());
						
						if (sel.getBackground () != null)
							textView.setBackgroundDrawable (sel.getBackground ());
						
					}
			
			if (calendar.isSameDay (mCalendar.currentDay)) {
				
				textView.setBackgroundDrawable (Graphic.toDrawable (mCalendar.getContext (), R.drawable.calendar_selected));
				textView.setTextColor (mCalendar.getContext ().getResources ().getColor (android.R.color.white));
				
			}
			
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