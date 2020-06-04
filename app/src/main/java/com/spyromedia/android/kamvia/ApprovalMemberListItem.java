package com.spyromedia.android.kamvia;

	public class ApprovalMemberListItem {

		private  String memberName;
		private  String memberLocation;
		private  String memberStationCode;
		private String member_id;

		public String getMemberName() {
			return memberName;
		}

		public String getMemberLocation() {
			return memberLocation;

		}

		public String getMember_id() {
			return member_id;
		}

		public String getMemberStationCode() {
			return memberStationCode;
		}

		public ApprovalMemberListItem(String member_id,String memberName, String memberLocation, String memberLocationCode) {
			this.memberName = memberName;
			this.memberLocation = memberLocation;
            this.memberStationCode = memberLocationCode;
            this.member_id = member_id;
		}

}
