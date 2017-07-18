package com.btv.entiy;

import java.io.Serializable;

public class EMMJsObject implements Serializable {
		private String apiname;
		private String oncallback;
		private String errorcallback;
		private String param;
		private String invokeID;
		public String getApiname() {
			return apiname;
		}
		public void setApiname(String apiname) {
			this.apiname = apiname;
		}
		public String getOncallback() {
			return oncallback;
		}
		public void setOncallback(String oncallback) {
			this.oncallback = oncallback;
		}
		public String getErrorcallback() {
			return errorcallback;
		}
		public void setErrorcallback(String errorcallback) {
			this.errorcallback = errorcallback;
		}
		public String getParam() {
			return param;
		}
		public void setParam(String param) {
			this.param = param;
		}
		public String getInvokeID() {
			return invokeID;
		}
		public void setInvokeID(String invokeID) {
			this.invokeID = invokeID;
		}
		public EMMJsObject(String apiname, String oncallback, String errorcallback,
						   String param, String invokeID) {
			super();
			this.apiname = apiname;
			this.oncallback = oncallback;
			this.errorcallback = errorcallback;
			this.param = param;
			this.invokeID = invokeID;
		}
}
