package com.spyromedia.android.kamvia.HomeTimelineRecyView;

	public class HomeTimelineListItem {

		private  String postPdfUrl;
		private  String heading;
		private  String condent;
		private  String postImage;
		private  String postId;

		public String getPOstUrl() {
			return postPdfUrl;
		}

		public String getPostId() {
			return postId;
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

		public HomeTimelineListItem(String postId,String pdfUrl, String heading, String condent , String postimage) {
			this.postPdfUrl = pdfUrl;
			this.heading = heading;
            this.condent = condent;
            this.postImage= postimage;
            this.postId = postId;

		}

}
