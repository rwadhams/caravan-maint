package com.wadhams.caravan.maintenance.dto

import groovy.transform.ToString

@ToString
class CaravanMaintenanceItem {
	String name
	List<String> maint
	int frequency
	int seq
	
	boolean due
}