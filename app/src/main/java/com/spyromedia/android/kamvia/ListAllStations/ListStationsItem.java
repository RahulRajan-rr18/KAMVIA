package com.spyromedia.android.kamvia.ListAllStations;

	public class ListStationsItem {

		private  String stationId;
		private  String StationName;


		public String getStationId() {
			return stationId;
		}

		public String getStationName() {
			return StationName;

		}



		public ListStationsItem(String stationId, String StationName) {
			this.stationId = stationId;
			this.StationName = StationName;

		}

}
