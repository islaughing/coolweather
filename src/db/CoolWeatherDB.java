package db;

import java.util.ArrayList;
import java.util.List;

import moder.City;
import moder.County;
import moder.Province;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class CoolWeatherDB {
	public static final String DB_NAME="cool_weather";
	public static final int VERSION=1;
	private SQLiteDatabase db;
	private static CoolWeatherDB coolWeatherDB;
	private CoolWeatherDB(Context context){
		CoolWeatherOpenHelper dbHelper=new CoolWeatherOpenHelper(context, DB_NAME, null, VERSION);
		db=dbHelper.getWritableDatabase();
	}
	public synchronized CoolWeatherDB getInstance(Context context){
		if(coolWeatherDB==null){
			coolWeatherDB=new CoolWeatherDB(context);
		}
		return coolWeatherDB;
	}
	public void saveProvince(Province province){
		if(province!=null){
			ContentValues values=new ContentValues();
			values.put("province_name", province.getProvinceName());
			values.put("province_code", province.getProvinceCode());
			db.insert(DB_NAME, null, values);
		}
	}
	public List<Province> loadProvinces(){
		List<Province> list=new ArrayList<Province>();
		Cursor cursor=db.query(DB_NAME, null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				Province province=new Province();
				province.setId(cursor.getInt(cursor.getColumnIndex("id")));
				province.setProvinceName(cursor.getString(cursor.getColumnIndex("province_name")));
				province.setProvinceCode(cursor.getString(cursor.getColumnIndex("province_code")));
				list.add(province);
			}while(cursor.moveToNext());
		}
		return list;
		
	}
	public void saveCity(City city){
		if(city!=null){
			ContentValues values=new ContentValues();
			values.put("city_name", city.getCityName());
			values.put("city_code", city.getCityCode());
			values.put("province_id", city.getProvinceId());
			db.insert(DB_NAME, null, values);
		}
	}
	public List<City> loadCitys(){
		List<City> list=new ArrayList<City>();
		Cursor cursor=db.query(DB_NAME, null, null, null, null, null, null);
		if(cursor.moveToFirst()){
			do{
				City city=new City();
				city.setId(cursor.getInt(cursor.getColumnIndex("id")));
				city.setCityName(cursor.getString(cursor.getColumnIndex("city_name")));
				city.setCityCode(cursor.getString(cursor.getColumnIndex("city_code")));
				city.setProvinceId(cursor.getInt(cursor.getColumnIndex("province_id")));
				list.add(city);
			}while(cursor.moveToNext());
		}
		return list;
		
	}
	public void saveCounty(County county){
		if(county!=null){
			ContentValues values=new ContentValues();
			values.put("county_name",county.getCountyName());
			values.put("county_code", county.getCountyCode());
			values.put("city_id", county.getCityId());
			db.insert(DB_NAME, null, values);
		}
	}
	public List<County> loadCounties(){
		List<County>list=new ArrayList<County>();
		Cursor cursor=db.query(DB_NAME, null, null, null, null, null, null);
		County county=new County();
		county.setCountyName(cursor.getString(cursor.getColumnIndex("county_name")));
		county.setCountyCode(cursor.getString(cursor.getColumnIndex("county_code")));
		county.setCityId(cursor.getInt(cursor.getColumnIndex("city_id")));
		list.add(county);
		return list;
		
	}
}
