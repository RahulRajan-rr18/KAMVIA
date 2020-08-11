package com.spyromedia.android.kamvia.HomeTimelineRecyView;

	public class HomeTimelineListItem {

		private  String userid;
		private  String heading;
		private  String condent;
		private  String postImage;

		public String getUserid() {
			return userid;
		}

		public String getHeading() {
			return heading;

		}

		public String getCondent() {
			return condent;
		}

		public String getPostImage() {
			return postImage;
		}

		public HomeTimelineListItem(String userid, String heading, String condent , String postimage) {
			this.userid = userid;
			this.heading = heading;
            this.condent = condent;
            this.postImage= postimage;

		}

}
