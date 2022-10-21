package com.wadhams.caravan.maintenance.dto

import java.time.LocalDate

import groovy.transform.ToString

@ToString
class CaravanMaintenanceComplete {
	String name
	LocalDate date
}