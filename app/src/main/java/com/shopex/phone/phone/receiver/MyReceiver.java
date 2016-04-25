
package com.shopex.phone.phone.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.TrafficStats;
import android.util.Log;

import com.shopex.phone.phone.activity.Showmain;
import com.shopex.phone.phone.utils.DataSupport;


/**
 * <pre>
 * 业务名:
 * 功能说明:
 * 编写日期:	2012-3-13
 *
 * 历史记录
 * 1、修改日期：
 *    修改人：
 *    修改内容：
 * </pre>
 */
public class MyReceiver extends BroadcastReceiver {

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.content.BroadcastReceiver#onReceive(android.content.Context,
	 * android.content.Intent)
	 */
	@Override
	public void onReceive(Context context, Intent intent) {
		DataSupport minsert = new DataSupport(context);
		long g3_down_total = TrafficStats.getMobileRxBytes(); // 获取通过Mobile连接收到的字节总数，这里Android123提示大家不包含WiFi
		long g3_up_total = TrafficStats.getMobileTxBytes(); // Mobile发送的总字节数
		long mrdown_total = TrafficStats.getTotalRxBytes(); // 获取总的接受字节数，包含Mobile和WiFi等
		long mtup_total = TrafficStats.getTotalTxBytes(); // 总的发送字节数，包含Mobile和WiFi等

		minsert.insertNow(g3_down_total, Showmain.RXG, Showmain.RX3G,
				Showmain.SHUTDOWN);
		minsert.insertNow(g3_up_total, Showmain.TXG, Showmain.TX3G,
				Showmain.SHUTDOWN);
		minsert.insertNow(mrdown_total, Showmain.RX, Showmain.RXT,
				Showmain.SHUTDOWN);
		minsert.insertNow(mtup_total, Showmain.TX, Showmain.TXT,
				Showmain.SHUTDOWN);
		if (Showmain.isLog) {
			Log.i("liuliang", "shutdown>>>>>>>>>start");
		}
	}

}
