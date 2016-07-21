package com.doaasaif.wheelfortune.listener;

import android.os.Parcelable;

import java.util.ArrayList;

public interface VolleyListener
{
	public void onRemoteCallComplete(ArrayList<Parcelable> result);

	public void onRemoteCallComplete(Parcelable result);


}
