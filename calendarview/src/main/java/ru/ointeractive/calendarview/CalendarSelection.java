	package ru.ointeractive.calendarview;
	
	import android.graphics.drawable.Drawable;
	
	import java.util.ArrayList;
	import java.util.List;
	
	import upl.core.Calendar;
	
	public class CalendarSelection {
		
		int color;
		Drawable background;
		final List<Calendar> colorDates = new ArrayList<> (), backgroundDates = new ArrayList<> ();
		
		public CalendarSelection setColor (int color) {
			
			this.color = color;
			return this;
			
		}
		
		public CalendarSelection setBackground (Drawable drawable) {
			
			this.background = drawable;
			return this;
			
		}
		
		public CalendarSelection addColor (Calendar calendar) {
			
			colorDates.add (calendar);
			return this;
			
		}
		
		public CalendarSelection addBackground (Calendar calendar) {
			
			backgroundDates.add (calendar);
			return this;
			
		}
		
	}
