package org.cct.home.map;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cct.home.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.Ground;
import com.baidu.mapapi.map.GroundOverlay;
import com.baidu.mapapi.map.ItemizedOverlay;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupClickListener;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;
import com.org.ifamily.entity.Host;
import com.org.ifamily.entity.Location;
import com.org.ifamily.implement.Locationimpl;


public class MapActivity extends Activity {

	//百度Key
	private static final String BD_KEY="PUAUE6A1LyMc7wdn9YlngIVq"; 
	//地图管理器
	private BMapManager mBMapMan=null;
	//地图视图
	private MapView mMapView=null;   
	private LocationClient mLocationClient=null;
	private MyOverlay mOverlay = null;
	private PopupOverlay   pop  = null;
	private Button button = null;
	private OverlayItem mCurItem = null;
	private GroundOverlay mGroundOverlay;
	private Ground mGround;
	
	private ImageButton buttonBack;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//在setContentView之前必须先初始化百度地图！！切记
		try {
			mBMapMan=new BMapManager(getApplication());  
			mBMapMan.init(BD_KEY, null); 			
			setContentView(R.layout.map);
			
	

			//注意：请在试用setContentView前初始化BMapManager对象，否则会报错   
			mMapView=(MapView)findViewById(R.id.bmapsView);  
			mMapView.setBuiltInZoomControls(true);  
			mMapView.setEnabled(true);
			//设置启用内置的缩放控件  
			MapController mMapController=mMapView.getController();  
			// 得到mMapView的控制权,可以用它控制和驱动平移和缩放  
			Intent intent = getIntent();
			final Host host = (Host) intent.getSerializableExtra("HOSTSURE");
			Locationimpl locationimpl = new Locationimpl();
			Location location = locationimpl.querynearest(host.getHostnum());
			GeoPoint p =new GeoPoint((int)(location.getOffsetLat()*1E6),(int)(location.getOffsetLng()*1E6));
			//用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)  
			mMapController.setCenter(p);//设置地图中心点  
			mMapController.setZoom(18);//设置地图zoom级别  
			initOverlay();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Toast.makeText(MapActivity.this, "当前网络状态不佳 ，请打开网络或稍后再试", Toast.LENGTH_SHORT).show();
		}
		buttonBack = (ImageButton)findViewById(R.id.imageButton_map);
		buttonBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				MapActivity.this.finish();
			}
		});
	};
	
	private void initOverlay() {
		// TODO Auto-generated method stub
		Intent intent = getIntent();
		final Host host = (Host) intent.getSerializableExtra("HOSTSURE");
		Locationimpl locationimpl = new Locationimpl();
		Location location = locationimpl.querynearest(host.getHostnum());		 
	    mOverlay = new MyOverlay(getResources().getDrawable(R.drawable.nav_turn_via_1),mMapView);	
         /**
          * 准备overlay 数据
          */
	    GeoPoint point =new GeoPoint((int)(location.getOffsetLat()*1E6),(int)(location.getOffsetLng()*1E6));
        OverlayItem item = new OverlayItem(point,"", "");
        item.setMarker(getResources().getDrawable(R.drawable.nav_turn_via_1));
         mOverlay.addItem(item);
         // 初始化 ground 图层
         mGroundOverlay = new GroundOverlay(mMapView);
         /**
          * 将overlay 添加至MapView中
          */
         mMapView.getOverlays().add(mOverlay);
         mMapView.getOverlays().add(mGroundOverlay);
         mGroundOverlay.addGround(mGround);
         /**
          * 刷新地图
          */
         mMapView.refresh();
         
         /**
          * 向地图添加自定义View.
          */
         button = new Button(this);         
         /**
          * 创建一个popupoverlay
          */
         PopupClickListener popListener = new PopupClickListener(){
			@Override
			public void onClickedPopup(int index) {

			}
         };
         pop = new PopupOverlay(mMapView,popListener);
         
	}

	public class MyOverlay extends ItemizedOverlay{

		public MyOverlay(Drawable defaultMarker, MapView mapView) {
			super(defaultMarker, mapView);
		}
		
		@Override
		public boolean onTap(int index){
			OverlayItem item = getItem(index);
			mCurItem = item ;
			Intent intent = getIntent();
			final Host host = (Host) intent.getSerializableExtra("HOSTSURE");
			Locationimpl locationimpl = new Locationimpl();
			Location location = locationimpl.querynearest(host.getHostnum());
			button.setText(location.getAddress());
			button.setTextSize(16);
			button.setWidth(800);
			 GeoPoint pt =new GeoPoint((int)(location.getOffsetLat()*1E6),(int)(location.getOffsetLng()*1E6));
				// 弹出自定义View
			pop.showPopup(button, pt, 32);
			return true;
		}
		
		@Override
		public boolean onTap(GeoPoint pt , MapView mMapView){
			if (pop != null){
                pop.hidePop();
                mMapView.removeView(button);
			}
			return false;
		}
    	
    }
	
	    @Override  
	    protected void onDestroy(){  
	    	    if(mLocationClient!=null&&mLocationClient.isStarted())
	    	    	mLocationClient.stop();
	            mMapView.destroy();  
	            if(mBMapMan!=null){  
	                    mBMapMan.destroy();  
	                    mBMapMan=null;  
	            }  
	            super.onDestroy();  
	    }  
	    @Override  
	    protected void onPause(){  
	            mMapView.onPause();  
	            if(mBMapMan!=null){  
	                   mBMapMan.stop();  
	            }  
	            super.onPause();  
	    }  
	    @Override  
	    protected void onResume(){  
	            mMapView.onResume();  
	            if(mBMapMan!=null){  
	                    mBMapMan.start();  
	            }  
	           super.onResume();  
	    }

}