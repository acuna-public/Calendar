	package ru.ointeractive.calendar;
	
	import android.content.Context;
	import android.support.v7.widget.RecyclerView;
	import android.util.AttributeSet;
	import android.widget.TextView;
	
	import java.util.ArrayList;
	
	import ru.ointeractive.androdesign.widget.LinearLayoutManager;
	import ru.ointeractive.jabadaba.Calendar;
	import ru.ointeractive.jabadaba.Int;
	import ru.ointeractive.jabadaba.Locales;
	
	public class HorizontalCalendarView extends CalendarView {
		
		public HorizontalCalendarAdapter adapter;
		
		protected long mDateStart = Locales.time ();
		
		public HorizontalCalendarView (Context context) {
			super (context);
		}
		
		public HorizontalCalendarView (Context context, AttributeSet attrs) {
			super (context, attrs);
		}
		
		public HorizontalCalendarView (Context context, AttributeSet attrs, int defStyleAttr) {
			super (context, attrs, defStyleAttr);
		}
		
		public void setStartDate (long time) {
			mDateStart = time;
		}
		
		@Override
		public void build () {
			
			setDayLayout (R.layout.list_horizontal_calendar_day);
			
			inflate (getContext (), R.layout.horizontal_calendar, this);
			
			TextView date = findViewById (R.id.date);
			
			if (date != null)
				date.setText (getCurrentMonth ());
			
			RecyclerView recyclerView = findViewById (R.id.days);
			
			//new LinearSnapHelper ().attachToRecyclerView (recyclerView);
			
			recyclerView.setLayoutManager (new LinearLayoutManager (getContext (), LinearLayoutManager.HORIZONTAL, false));
			
			adapter = new HorizontalCalendarAdapter (this, mLayout);
			
			if (Int.size (mDates) == 0) addData ();
			
			recyclerView.setAdapter (adapter);
			recyclerView.scrollToPosition (getCurrentDay ());
			
		}
		
		@Override
		public void addData () {
			
			mDates = new ArrayList<> ();
			
			mCalendar = new Calendar (mDateStart);
			
			mCalendar.setFirstDayOfWeek (Calendar.MONDAY);
			
			for (int i = 0; i < mDaysNum; i++) {
				
				Calendar calendar = new Calendar (mCalendar);
				calendar.addNewDay (i);
				
				addDate (calendar);
				
				if (mCalendar.isSameDay (calendar))
					currentDay = calendar;
				
			}
			
		}
		
	}