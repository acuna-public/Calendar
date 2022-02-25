	package ru.ointeractive.calendarview;
	
	import android.content.Context;
	import android.content.res.TypedArray;
	import android.util.AttributeSet;
	import android.view.View;
	import android.widget.GridView;
	import android.widget.TextView;
	
	import ru.ointeractive.androdesign.widget.LinearLayout;
	import ru.ointeractive.andromeda.graphic.Graphic;
	import upl.core.Calendar;
	import upl.util.ArrayList;
	import upl.util.List;
	
	public class CalendarView extends LinearLayout {
		
		protected int daysNum = 35; // 7x5
		
		protected int mCurrentTextColor = 0;
		protected int mPrevTextColor = R.color.light_gray;
		protected int currentDay = -1;
		
		protected int mLayout = R.layout.list_calendar_day, mDayNameLayout = R.layout.list_calendar_name;
		
		protected OnDayClickListener mDayClickListener;
		protected OnClickListener mClickListener;
		
		public List<Calendar> mDates = new ArrayList<> ();
		
		public Calendar mCalendar = new Calendar ();
		
		public CalendarAdapter adapter;
		
		protected TextView date;
		
		List<CalendarSelection> mSelections = new ArrayList<> ();
		
		public String dateFormat = "MMMM";
		
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
				
				if (attr == R.styleable.CalendarView_currentTextColor)
					setCurrentTextColor (array.getColor (attr, getResources ().getColor (mCurrentTextColor)));
				else if (attr == R.styleable.CalendarView_dayNameLayout)
					setDayNameLayout (array.getResourceId (attr, 0));
				else if (attr == R.styleable.CalendarView_dayLayout)
					setDayLayout (array.getResourceId (attr, 0));
				else if (attr == R.styleable.CalendarView_daysNum)
					setDaysNum (array.getInt (attr, daysNum));
				else if (attr == R.styleable.CalendarView_prevTextColor)
					setPrevTextColor (array.getColor (attr, getResources ().getColor (mPrevTextColor)));
				else if (attr == R.styleable.CalendarView_dateFormat)
					setDateFormat (array.getString (attr));
				
			}
			
			array.recycle ();
			
		}
		
		public void setCurrentTextColor (int color) {
			mCurrentTextColor = color;
		}
		
		public void setPrevTextColor (int color) {
			mPrevTextColor = color;
		}
		
		public void setDayLayout (int layout) {
			mLayout = layout;
		}
		
		public void setDayNameLayout (int layout) {
			mDayNameLayout = layout;
		}
		
		public void setDaysNum (int num) {
			daysNum = num;
		}
		
		public void setDateFormat (String format) {
			dateFormat = format;
		}
		
		protected String getCurrentMonth () {
			return mCalendar.getDate (dateFormat);
		}
		
		public void addDate (Calendar date) {
			mDates.add (date);
		}
		
		public CalendarView setSelection (CalendarSelection sel) {
			
			mSelections.add (sel);
			return this;
			
		}
		
		public void build () {
			
			inflate (getContext (), R.layout.calendar, this);
			
			date = findViewById (R.id.date);
			
			date.setText (getCurrentMonth ());
			
			View button = findViewById (R.id.prev);
			
			button.setOnClickListener (new View.OnClickListener () {
				
				@Override
				public void onClick (View view) {
					
					loaded = false;
					
					mSelections = new ArrayList<> ();
					
					mCalendar.add (Calendar.MONTH, -1);
					
					date.setText (getCurrentMonth ());
					
					addData (true);
					
					adapter.notifyDataSetChanged ();
					
					if (mClickListener != null)
						mClickListener.onPrevClick (view, CalendarView.this);
					
				}
				
			});
			
			button = findViewById (R.id.next);
			
			button.setOnClickListener (new View.OnClickListener () {
				
				@Override
				public void onClick (View view) {
					
					loaded = false;
					
					mSelections = new ArrayList<> ();
					
					mCalendar.add (Calendar.MONTH, 1);
					
					date.setText (getCurrentMonth ());
					
					addData (true);
					
					adapter.notifyDataSetChanged ();
					
					if (mClickListener != null)
						mClickListener.onNextClick (view, CalendarView.this);
					
				}
				
			});
			
			GridView gridView = findViewById (R.id.names);
			
			CalendarDayAdapter dayAdapter = new CalendarDayAdapter (getContext (), mDayNameLayout);
			
			gridView.setAdapter (dayAdapter);
			
			gridView = findViewById (R.id.days);
			
			setDaysNum (mCalendar.getActualMaximum (Calendar.DAY_OF_MONTH));
			
			adapter = new CalendarAdapter (this, mLayout);
			
			adapter.mSelectedListener = mDayClickListener;
			
			addData ();
			
			gridView.setAdapter (adapter);
			
		}
		
		public CalendarView setListener (OnClickListener listener) {
			
			mClickListener = listener;
			return this;
			
		}
		
		boolean loaded = false;
		
		public void addData () {
			addData (false);
		}
		
		public void addData (boolean select) {
			
			if (!loaded) {
				
				CalendarSelection prevSelection = new CalendarSelection ()
					             .setColor (mPrevTextColor);
				
				CalendarSelection currentSelection = new CalendarSelection ()
					             .setBackground (Graphic.toDrawable (getContext (), R.drawable.calendar_current));
				
				if (mCurrentTextColor != 0)
					currentSelection.setColor (mCurrentTextColor);
				
				final Calendar cal = new Calendar ();
				
				mDates = new ArrayList<> ();
				
				mCalendar.setFirstDayOfWeek (Calendar.MONDAY);
				
				mCalendar.set (Calendar.DAY_OF_MONTH, 1);
				
				int daysNum = 0, prevDay = 0;
				
				if (mCalendar.get (Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
					
					while (mCalendar.get (Calendar.DAY_OF_WEEK) != Calendar.MONDAY) {
						
						prevDay++;
						mCalendar.addNewDay (-1);
						
					}
					
					for (int i = 0; i < prevDay; i++) {
						
						Calendar calendar = new Calendar (mCalendar);
						
						calendar.addNewDay (i);
						
						addDate (calendar);
						
						prevSelection.addColor (calendar);
						
					}
					
					mCalendar.add (Calendar.MONTH, 1);
					mCalendar.set (Calendar.DAY_OF_MONTH, 1);
					
				}
				
				for (int i = 0; i < mCalendar.getActualMaximum (Calendar.DAY_OF_MONTH); i++) {
					
					Calendar calendar = new Calendar (mCalendar);
					
					calendar.addNewDay (i);
					
					addDate (calendar);
					
					if (!select && calendar.equals (cal)) {
						
						currentDay = daysNum;
						
						currentSelection.addColor (calendar);
						currentSelection.addBackground (calendar);
						
					}
					
					daysNum++;
					
				}
				
				setSelection (currentSelection);
				
				while (daysNum < 42) { // 7x6
					
					Calendar calendar = new Calendar (mCalendar);
					
					calendar.addNewDay (daysNum);
					
					addDate (calendar);
					
					prevSelection.addColor (calendar);
					
					daysNum++;
					
				}
				
				setSelection (prevSelection);
				
				loaded = true;
				
			}
			
		}
		
		public int getCurrentDay () {
			return currentDay;
		}
		
		public void setListener (OnDayClickListener listener) {
			mDayClickListener = listener;
		}
		
		public interface OnDayClickListener {
			
			void onDayClick (View view, CalendarView calendarView, int position, boolean isCurrent);
			
		}
		
		public interface OnClickListener {
			
			boolean onPrevClick (View view, CalendarView calendarView);
			boolean onNextClick (View view, CalendarView calendarView);
			
		}
		
		protected String getCurrentWeek (Calendar calendar) {
			
			int dayOfWeek = calendar.get (Calendar.DAY_OF_WEEK);
			return getContext ().getResources ().getStringArray (R.array.day)[(dayOfWeek - 1)];
			
		}
		
		protected String getCurrentDay (Calendar calendar) {
			
			int dayOfMonth = calendar.get (Calendar.DAY_OF_MONTH);
			return String.valueOf (dayOfMonth);
			
		}
		
		public void setText (Calendar calendar, TextView textView) {
			
			textView.setText (getCurrentDay (calendar));
			
			for (CalendarSelection sel : mSelections) {
				
				for (Calendar cal : sel.colorDates)
					if (calendar.equals (cal) && sel.color != 0)
						textView.setTextColor (sel.color);
				
				for (Calendar cal : sel.backgroundDates)
					if (calendar.equals (cal))
						textView.setBackgroundDrawable (sel.background);
				
			}
			
		}
		
	}