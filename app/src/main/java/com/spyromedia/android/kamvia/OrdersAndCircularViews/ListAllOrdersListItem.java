package com.spyromedia.android.kamvia.OrdersAndCircularViews;

	public class ListAllOrdersListItem {

		private  String orderId;
		private  String orderDetails;


		public String getOrderId() {
			return orderId;
		}

		public String getOrderDetails() {
			return orderDetails;

		}



		public ListAllOrdersListItem(String orderId, String orderDetails) {
			this.orderId = orderId;
			this.orderDetails = orderDetails;

		}

}
