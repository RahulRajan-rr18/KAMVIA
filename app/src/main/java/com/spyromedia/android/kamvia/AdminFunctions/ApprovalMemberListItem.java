package com.spyromedia.android.kamvia.AdminFunctions;

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

		public String getMemberStationCode() {
			return memberStationCode;
		}

		public String getMember_id() {
			return member_id;
		}

		public ApprovalMemberListItem(String memberName, String memberLocation, String memberLocationCode,String member_id) {
			this.memberName = memberName;
			this.memberLocation = memberLocation;
            this.memberStationCode = memberLocationCode;
            this.member_id = member_id;
		}

}
