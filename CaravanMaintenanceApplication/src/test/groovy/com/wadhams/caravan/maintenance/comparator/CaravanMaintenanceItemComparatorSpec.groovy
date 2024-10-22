package com.wadhams.caravan.maintenance.comparator

import com.wadhams.caravan.maintenance.dto.CaravanMaintenanceItem
import spock.lang.Specification

class CaravanMaintenanceItemComparatorSpec extends Specification {
	def "correct due sort order"() {
		given:
			CaravanMaintenanceItemComparator comparator = new CaravanMaintenanceItemComparator()
			CaravanMaintenanceItem item01 = new CaravanMaintenanceItem(name: 'Item_01', frequency: 90, seq: 50, due: true)
			CaravanMaintenanceItem item02 = new CaravanMaintenanceItem(name: 'Item_02', frequency: 90, seq: 50, due: false)
			CaravanMaintenanceItem item03 = new CaravanMaintenanceItem(name: 'Item_03', frequency: 90, seq: 50, due: true)
			List<CaravanMaintenanceItem> cmiList = []
			cmiList << item01
			cmiList << item02
			cmiList << item03
			
		when:
			Collections.sort(cmiList, comparator)
			
		then:
			cmiList[0].name == 'Item_01'
			cmiList[1].name == 'Item_03'
			cmiList[2].name == 'Item_02'
	}
	
	def "correct seq sort order"() {
		given:
			CaravanMaintenanceItemComparator comparator = new CaravanMaintenanceItemComparator()
			CaravanMaintenanceItem item01 = new CaravanMaintenanceItem(name: 'Item_01', frequency: 90, seq: 60, due: true)
			CaravanMaintenanceItem item02 = new CaravanMaintenanceItem(name: 'Item_02', frequency: 90, seq: 40, due: true)
			CaravanMaintenanceItem item03 = new CaravanMaintenanceItem(name: 'Item_03', frequency: 90, seq: 50, due: true)
			List<CaravanMaintenanceItem> cmiList = []
			cmiList << item01
			cmiList << item02
			cmiList << item03
			
		when:
			Collections.sort(cmiList, comparator)
			
		then:
			cmiList[0].name == 'Item_02'
			cmiList[1].name == 'Item_03'
			cmiList[2].name == 'Item_01'
	}
	
	def "correct frequency sort order"() {
		given:
			CaravanMaintenanceItemComparator comparator = new CaravanMaintenanceItemComparator()
			CaravanMaintenanceItem item01 = new CaravanMaintenanceItem(name: 'Item_01', frequency: 90, seq: 30, due: true)
			CaravanMaintenanceItem item02 = new CaravanMaintenanceItem(name: 'Item_02', frequency: 180, seq: 30, due: true)
			CaravanMaintenanceItem item03 = new CaravanMaintenanceItem(name: 'Item_03', frequency: 7, seq: 30, due: true)
			List<CaravanMaintenanceItem> cmiList = []
			cmiList << item01
			cmiList << item02
			cmiList << item03
			
		when:
			Collections.sort(cmiList, comparator)
			
		then:
			cmiList[0].name == 'Item_03'
			cmiList[1].name == 'Item_01'
			cmiList[2].name == 'Item_02'
	}
	
	def "correct sort order"() {
		given:
			CaravanMaintenanceItemComparator comparator = new CaravanMaintenanceItemComparator()
			CaravanMaintenanceItem item01 = new CaravanMaintenanceItem(name: 'Item_01', frequency: 180, seq: 50, due: true)
			CaravanMaintenanceItem item02 = new CaravanMaintenanceItem(name: 'Item_02', frequency: 7, seq: 50, due: true)
			CaravanMaintenanceItem item03 = new CaravanMaintenanceItem(name: 'Item_03', frequency: 90, seq: 50, due: true)
			CaravanMaintenanceItem item04 = new CaravanMaintenanceItem(name: 'Item_04', frequency: 90, seq: 50, due: false)
			CaravanMaintenanceItem item05 = new CaravanMaintenanceItem(name: 'Item_05', frequency: 180, seq: 10, due: true)
			CaravanMaintenanceItem item06 = new CaravanMaintenanceItem(name: 'Item_06', frequency: 90, seq: 60, due: true)
			CaravanMaintenanceItem item07 = new CaravanMaintenanceItem(name: 'Item_07', frequency: 90, seq: 20, due: true)
			CaravanMaintenanceItem item08 = new CaravanMaintenanceItem(name: 'Item_08', frequency: 90, seq: 40, due: true)
			List<CaravanMaintenanceItem> cmiList = []
			cmiList << item01
			cmiList << item02
			cmiList << item03
			cmiList << item04
			cmiList << item05
			cmiList << item06
			cmiList << item07
			cmiList << item08
			
		when:
			Collections.sort(cmiList, comparator)
			
		then:
			cmiList[0].name == 'Item_05'
			cmiList[1].name == 'Item_07'
			cmiList[2].name == 'Item_08'
			cmiList[3].name == 'Item_02'
			cmiList[4].name == 'Item_03'
			cmiList[5].name == 'Item_01'
			cmiList[6].name == 'Item_06'
			cmiList[7].name == 'Item_04'
	}

}
