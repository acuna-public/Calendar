	package ru.ointeractive.calendarview;
	
	import android.support.v7.widget.RecyclerView;
	import android.view.View;
	import android.widget.TextView;
	
	class ViewHolder extends RecyclerView.ViewHolder {
		
		TextView nameText, dayText;
		View layout;
		
		protected ViewHolder (View view) {
			
			super (view);
			
			layout = view.findViewById (R.id.layout);
			
			nameText = view.findViewById (R.id.name);
			dayText = view.findViewById (R.id.day);
			
		}
		
	}
