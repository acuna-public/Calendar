	package ru.ointeractive.calendar;
	
	import android.content.Context;
	import android.content.res.TypedArray;
	import android.graphics.drawable.Drawable;
	import android.support.v7.widget.RecyclerView;
	import android.util.AttributeSet;
	import android.view.View;
	import android.widget.GridView;
	import android.widget.TextView;
	
	import java.util.ArrayList;
	import java.util.List;
	
	import ru.ointeractive.androdesign.widget.LinearLayout;
	import ru.ointeractive.jabadaba.Calendar;
	
	public class CalendarView extends LinearLayout {
		
		protected int mDaysNum = 30;
		
		protected int mSelectedTextColor = android.R.color.white;
		protected int mMarkedTextColor, mPrevTextColor = R.color.light_gray;
		protected Calendar currentDay;
		
		protected int mLayout = R.layout.list_calendar_day, mDayNameLayout = R.layout.list_calendar_name;
		
		public List<Calendar> mDates = new ArrayList<> ();
		public List<Calendar> mMarkedDates = new ArrayList<> ();
		
		public Calendar mCalendar;
		
		public CalendarAdapter adapter;
		
		public List<Selection> mSelections = new ArrayList<> ();
		
		public CalendarView (Context context) {
			this (context, null);
		}
		
		public CalendarView (Context context, AttributeSet attrs) {
			this (context, attrs, 0);
		}
		
		public CalendarView (Context context, AttributeSet attrs, int defStyleAttr) {
			
			super (context, attrs, defStyleAttr);
			
			TypedArray array = context.obtainStyledAttributes (attrs, R.styleable.CalendarView);
			
			for (int i = 0; i < array.getIndexCount (); ++i) {
				
				int attr = array.getIndex (i);
				
				if (attr == R.styleable.CalendarView_selected_text_color)
					setSelectedTextColor (array.getColor (attr, getResources ().getColor (mSelectedTextColor)));
				else if (attr == R.styleable.CalendarView_markedTextColor)
					setMarkedTextColor (array.getResourceId (attr, R.color.primary));
				else if (attr == R.styleable.CalendarView_dayNameLayout)
					setDayNameLayout (array.getResourceId (attr, 0));
				else if (attr == R.styleable.CalendarView_dayLayout)
					setDayLayout (array.getResourceId (attr, 0));
				else if (attr == R.styleable.CalendarView_daysNum)
					setDaysNum (array.getInt (attr, mDaysNum));
				else if (attr == R.styleable.CalendarView_prevTextColor)
					setPrevTextColor (array.getColor (attr, getResources ().getColor (mPrevTextColor)));
				
			}
			
			array.recycle ();
			
		}
		
		public void setSelectedTextColor (int color) {
			mSelectedTextColor = color;
		}
		
		public void setPrevTextColor (int color) {
			mPrevTextColor = color;
		}
		
		public void setMarkedTextColor (int color) {
			mMarkedTextColor = getResources ().getColor (color);
		}
		
		public void setDayLayout (int layout) {
			mLayout = layout;
		}
		
		public void setDayNameLayout (int layout) {
			mDayNameLayout = layout;
		}
		
		public void setDaysNum (int num) {
			mDaysNum = num;
		}
		
		protected String getCurrentMonth () {
			
			int dayOfWeek = mCalendar.get (Calendar.MONTH);
			return getResources ().getStringArray (R.array.month)[(dayOfWeek)];
			
		}
		
		public void addDate (Calendar date) {
			mDates.add (date);
		}
		
		public void addMarkedDate (Calendar date) {
			mMarkedDates.add (date);
		}
		
		public void build () {
			
			inflate (getContext (), R.layout.calendar, this);
			
			mCalendar = new Calendar ();
			
			mCalendar.setFirstDayOfWeek (Calendar.MONDAY);
			
			mCalendar.set (Calendar.DAY_OF_MONTH, 1);
			
			TextView date = findViewById (R.id.date);
			date.setText (getCurrentMonth ());
			
			GridView gridView = findViewById (R.id.names);
			
			CalendarDayAdapter adapter = new CalendarDayAdapter (this, mDayNameLayout);
			
			gridView.setAdapter (adapter);
			
			gridView = findViewById (R.id.days);
			
			setDaysNum (mCalendar.getActualMaximum (Calendar.DAY_OF_MONTH));
			
			this.adapter = new CalendarAdapter (this, mLayout);
			
			addData ();
			
			gridView.setAdapter (this.adapter);
			
		}
		
		public void addData () {
			
			mDates = new ArrayList<> ();
			
			int oldDay = 0;
			
			if (mCalendar.get (Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
				
				while (mCalendar.get (Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
					
					mCalendar.addNewDay (-1);
					oldDay++;
					
				}
				
				Selection selection = new Selection ().setColor (mPrevTextColor);
				
				for (int i = 0; i < oldDay; i++) {
					
					Calendar calendar = new Calendar (mCalendar);
					
					calendar.addNewDay (i);
					
					addDate (calendar);
					
					selection.add (calendar);
					
					mSelections.add (selection);
					
				}
				
				mCalendar.add (Calendar.MONTH, 1);
				mCalendar.set (Calendar.DAY_OF_MONTH, 1);
				
			}
			
			int i;
			
			Calendar calendar = new Calendar (mCalendar);
			
			addDate (calendar);
			
			Calendar cal = new Calendar ();
			
			for (i = 0; i < mDaysNum - 1; i++) {
				
				calendar = new Calendar (mCalendar);
				
				calendar.addNewDay ();
				
				addDate (calendar);
				
				mCalendar.addNewDay ();
				
				if (cal.isSameDay (calendar))
					currentDay = cal;
				
			}
			
			if (mCalendar.get (Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
				
				mCalendar.add (Calendar.MONTH, 1);
				mCalendar.set (Calendar.DAY_OF_MONTH, 1);
				
				while (mCalendar.get (Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
					
					calendar = new Calendar ();
					
					calendar.setFirstDayOfWeek (Calendar.MONDAY);
					
					calendar.set (Calendar.DAY_OF_MONTH, 1);
					
					calendar.addNewDay (i);
					
					addDate (calendar);
					
					mCalendar.addNewDay ();
					
				}
				
			}
			
		}
		
		public int getCurrentDay () {
			return (currentDay != null ? currentDay.get (Calendar.DAY_OF_MONTH) : -1); // TODO
		}
		
		public void setListener (OnClickListener listener) {
			adapter.mSelectedListener = listener;
		}
		
		protected static class ViewHolder extends RecyclerView.ViewHolder {
			
			TextView nameText, dayText;
			View layout;
			
			protected ViewHolder (View view) {
				
				super (view);
				
				layout = view.findViewById (R.id.layout);
				
				nameText = view.findViewById (R.id.name);
				dayText = view.findViewById (R.id.day);
				
			}
			
		}
		
		public interface OnClickListener {
			
			void onDayClick (View view, Calendar calendar, boolean isCurrent, boolean isMarked);
			
		}
		
		protected String getCurrentWeek (Calendar calendar) {
			
			int dayOfWeek = calendar.get (Calendar.DAY_OF_WEEK);
			return getContext ().getResources ().getStringArray (R.array.day)[(dayOfWeek - 1)];
			
		}
		
		protected String getCurrentDay (Calendar calendar) {
			
			int dayOfMonth = calendar.get (Calendar.DAY_OF_MONTH);
			return String.valueOf (dayOfMonth);
			
		}
		
		public static class Selection {
			
			private Integer color;
			private Drawable background;
			private final List<Calendar> dates = new ArrayList<> ();
			
			public Selection setColor (int color) {
				
				this.color = color;
				return this;
				
			}
			
			public Integer getColor () {
				return color;
			}
			
			public Selection setBackground (Drawable drawable) {
				
				this.background = drawable;
				return this;
				
			}
			
			public Drawable getBackground () {
				return background;
			}
			
			public Selection add (Calendar calendar) {
				
				dates.add (calendar);
				return this;
				
			}
			
			public List<Calendar> getDates () {
				return dates;
			}
			
		}
		
	}