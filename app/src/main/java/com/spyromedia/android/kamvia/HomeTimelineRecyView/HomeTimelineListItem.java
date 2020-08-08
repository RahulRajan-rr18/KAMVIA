package com.spyromedia.android.kamvia.HomeTimelineRecyView;

	public class HomeTimelineListItem {

		private  String userid;
		private  String heading;
		private  String condent;

		public String getUserid() {
			return userid;
		}

		public String getHeading() {
			return heading;

		}

		public String getCondent() {
			return condent;
		}

		public HomeTimelineListItem(String userid, String heading, String condent) {
			this.userid = userid;
			this.heading = heading;
            this.condent = condent;
		}

}
