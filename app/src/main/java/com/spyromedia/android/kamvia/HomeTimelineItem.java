package com.spyromedia.android.kamvia;

	public class HomeTimelineItem {

		private  String memberName;
		private  String memberLocation;
		private  String memberStationCode;

		public String getMemberName() {
			return memberName;
		}

		public String getMemberLocation() {
			return memberLocation;

		}

		public String getMemberStationCode() {
			return memberStationCode;
		}

		public HomeTimelineItem(String memberName, String memberLocation, String memberLocationCode) {
			this.memberName = memberName;
			this.memberLocation = memberLocation;
            this.memberStationCode = memberLocationCode;
		}

}
