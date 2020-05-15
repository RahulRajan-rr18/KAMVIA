package com.spyromedia.android.kamvia;

	public class SearchResultItem {

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

		public SearchResultItem(String memberName, String memberLocation, String memberLocationCode) {
			this.memberName = memberName;
			this.memberLocation = memberLocation;
            this.memberStationCode = memberLocationCode;
		}

}
