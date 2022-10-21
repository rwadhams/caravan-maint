package com.wadhams.caravan.maintenance.comparator

import com.wadhams.caravan.maintenance.dto.CaravanMaintenanceItem

class CaravanMaintenanceItemComparator implements Comparator<CaravanMaintenanceItem> {

	@Override
	public int compare(CaravanMaintenanceItem cmi1, CaravanMaintenanceItem cmi2) {
		//sort by due, then frequency
		if (cmi1.due == cmi2.due) {
			return cmi1.frequency.compareTo(cmi2.frequency)
		}
		else if (cmi1.due) {
			return -1
		}
		else {
			return 1
		}
	}
}