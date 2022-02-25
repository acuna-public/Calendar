	package ru.ointeractive.calendarview;
	
	import android.content.Context;
	import android.support.v7.widget.LinearSnapHelper;
	import android.support.v7.widget.RecyclerView;
	import android.util.AttributeSet;
	import android.widget.TextView;
	
	import ru.ointeractive.androdesign.widget.LinearLayoutManager;
	import ru.ointeractive.andromeda.graphic.Graphic;
	import upl.core.Calendar;
	import upl.core.Date;
	import upl.core.Int;
	import upl.util.ArrayList;
	
	public class HorizontalCalendarView extends CalendarView {
		
		public HorizontalCalendarAdapter adapter;
		
		protected long mDateStart = new Date ().getTimeInMillis ();
		
		public HorizontalCalendarView (Context context) {
			this (context, null);
		}
		
		public HorizontalCalendarView (Context context, AttributeSet attrs) {
			this (context, attrs, 0);
		}
		
		public HorizontalCalendarView (Context context, AttributeSet attrs, int defStyleAttr) {
			
			super (context, attrs, defStyleAttr);
			
			mCurrentTextColor = getContext ().getResources ().getColor (android.R.color.white);
			
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
			
			new LinearSnapHelper ().attachToRecyclerView (recyclerView);
			
			recyclerView.setLayoutManager (new LinearLayoutManager (getContext (), LinearLayoutManager.HORIZONTAL, false));
			
			adapter = new HorizontalCalendarAdapter (this, mLayout);
			
			if (Int.size (mDates) == 0) addData ();
			
			recyclerView.setAdapter (adapter);
			recyclerView.scrollToPosition (getCurrentDay ());
			
		}
		
		@Override
		public void addData () {
			
			mDates = new ArrayList<> ();
			
			mCalendar.setTimeInMillis (mDateStart);
			mCalendar.setFirstDayOfWeek (Calendar.MONDAY);
			
			CalendarSelection selection = new CalendarSelection ()
				                      //.setColor (mCurrentTextColor)
				                      .setBackground (Graphic.toDrawable (getContext (), R.drawable.calendar_current));
			
			Calendar current = new Calendar ();
			
			for (int i = 0; i < daysNum; i++) {
				
				Calendar calendar = new Calendar (mCalendar);
				calendar.addNewDay (i);
				
				addDate (calendar);
				
				if (calendar.equals (current)) {
					
					currentDay = i;
					
					selection.addColor (calendar);
					selection.addBackground (calendar);
					
				}
				
			}
			
			setSelection (selection);
			
		}
		
	}