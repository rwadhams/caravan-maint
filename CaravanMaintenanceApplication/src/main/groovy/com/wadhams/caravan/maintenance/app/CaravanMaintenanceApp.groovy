package com.wadhams.caravan.maintenance.app

import com.wadhams.caravan.maintenance.comparator.CaravanMaintenanceItemComparator
import com.wadhams.caravan.maintenance.dto.CaravanMaintenanceItem
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class CaravanMaintenanceApp {
	DateTimeFormatter dtf = DateTimeFormatter.ofPattern('dd/MM/yyyy')
	List<CaravanMaintenanceItem> cmiList
	Map<String, LocalDate> cmcMap
	
	static main(args) {
		println 'CaravanMaintenanceApp started...'
		println ''

		CaravanMaintenanceApp app = new CaravanMaintenanceApp()
		app.execute()
		
		println ''
		println 'CaravanMaintenanceApp ended.'
	}
	
	def execute() {
		File cmiFile
		URL cmiResource = getClass().getClassLoader().getResource("CaravanMaintenanceItems.xml")
		if (cmiResource == null) {
			throw new IllegalArgumentException("file not found!")
		}
		else {
			cmiFile = new File(cmiResource.toURI())
		}
//		println "Processing: ${cmiFile.name}"
//		println ''

		File cmcFile
		URL cmcResource = getClass().getClassLoader().getResource("CaravanMaintenanceCompleted.xml")
		if (cmcResource == null) {
			throw new IllegalArgumentException("file not found!")
		}
		else {
			cmcFile = new File(cmcResource.toURI())
		}
//		println "Processing: ${cmcFile.name}"
//		println ''

		
		def cmi = new XmlSlurper().parse(cmiFile)
		cmiList = buildCaravanMaintenanceItemList(cmi.item)
//		println cmiList
//		println ''
		
		def cmc = new XmlSlurper().parse(cmcFile)
		cmcMap = buildCaravanMaintenanceCompleteMap(cmc.complete)
//		println cmcMap
//		println ''
		
		identifyDueItems()
		
		cmiList.sort(new CaravanMaintenanceItemComparator())
		
		File f1 = new File("out/caravan-maintenance-report.txt")
		f1.withPrintWriter {pw ->
			reportMaintenanceText(pw)
		}
		
		println ''
		
		File f2 = new File("out/caravan-maintenance-missing-item-report.txt")
		f2.withPrintWriter {pw ->
			reportItemsMissingFromCompletedText(pw)
		}
	}
	
	def identifyDueItems() {
		LocalDate today = LocalDate.now()
		
		cmiList.each {cmi ->
			//println cmi
			
			LocalDate completedDate = cmcMap[cmi.name]
			if (!completedDate) {
				cmi.due = true
			}
			else {
				LocalDate dueDate = completedDate + cmi.frequency
				if (dueDate.isBefore(today)) {
					cmi.due = true
				}
				else {
					cmi.due = false
				}
			}
		}
	}
	
	def reportMaintenanceText(PrintWriter pw) {
		cmiList.each {cmi ->
			//println cmi
			
			if (cmi.due) {
				pw.println "[${cmi.name}] maintenance is due."
				cmi.maint.each {m ->
					pw.println "\t$m"
				}
			}
			else {
				pw.println "[${cmi.name}] maintenance is up to date."
			}
			
			pw.println ''
		}
	}
	
//	def reportDueText(CaravanMaintenanceItem cmi, PrintWriter pw) {
//		pw.println "[${cmi.name}] maintenance is due."
//		cmi.maint.each {m ->
//			pw.println "\t$m"
//		}
//	}

	def reportItemsMissingFromCompletedText(PrintWriter pw) {
		pw.println 'Items missing from CaravanMaintenanceCompleted.xml'
		
		List<String> missing = []
		cmiList.each {cmi ->
			if (!cmcMap.containsKey(cmi.name)) {
				missing << cmi.name
			}
		}
		
		if (missing) {
			missing.each {name ->
				pw.println "\t<complete name=\"$name\" date=\"\" />"
			}
		}
		else {
			pw.println '\tNONE'
		}
	}
	
	List<CaravanMaintenanceItem> buildCaravanMaintenanceItemList(items) {
		List<CaravanMaintenanceItem> cmiList = []
		
		items.each {item ->
			String name = item.name
			List<String> maintList = item.maint.collect {it.text()}
			String frequency = item.frequency
			cmiList << new CaravanMaintenanceItem(name : "$name", maint : maintList, frequency : Integer.parseInt(frequency))
		}
		
		return cmiList
	}
	
	Map<String, LocalDate> buildCaravanMaintenanceCompleteMap(completed) {
		Map<String, LocalDate> cmcMap = [:]
		
		completed.each {complete ->
			String name = complete.@name
			
			LocalDate date
			String completedDate = complete.@date.text()
			if (completedDate) {
				date = LocalDate.parse(completedDate, dtf)
			}
			cmcMap[name] = date
		}
		
		return cmcMap
	}
	
}
